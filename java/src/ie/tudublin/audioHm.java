package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class audioHm extends PApplet {

  Minim minim;
  AudioPlayer song;
  FFT fft;
  int numBands = 512;
  float[] bandSize = new float[numBands];

  float angle = 0;
  float colorAngle = 0;
  
  public void settings(){
    size(640, 640, P3D);
    smooth(8);
  }
  
  public void setup() {
    minim = new Minim(this);
    song = minim.loadFile("bloodybass.mp3");
    fft = new FFT(song.bufferSize(), song.sampleRate());
    song.play();
    frameRate(60);
  }

  public void draw() {
    background(0);
    lights();
    
    // Sphere field in top-left corner
    pushMatrix();
    translate(width/4, height/4, -100);
    drawShapeField();
    popMatrix();
    
    // Sphere field in top-right corner
    pushMatrix();
    translate(width/4*3, height/4, -100);
    drawShapeField();
    popMatrix();
    
    // Sphere field in bottom-right corner
    pushMatrix();
    translate(width/4*3, height/4*3, -100);
    drawShapeField();
    popMatrix();
    
    // Sphere field in bottom-left corner
    pushMatrix();
    translate(width/4, height/4*3, -100);
    drawShapeField();
    popMatrix();
    
    // Sphere field in center of screen
    pushMatrix();
    translate(width/2, height/2, -100);
    drawShapeField();
    popMatrix();
  }
  
  private void drawShapeField() {
    rotateX(angle / 30.0f);
    rotateY(angle*1.3f / 30.0f);
    rotateZ(angle*0.7f / 30.0f);
    noStroke();
    float r = 200;
    fft.forward(song.mix);
    for (int i = 0; i < numBands; i++) {
        bandSize[i] = fft.getBand(i)*5;
    }
    angle += map(bandSize[0], 0, 100, 0.01f, 0.1f);
    colorAngle += map(bandSize[1], 0, 100, 0.001f, 0.01f);
    for (int i = 0; i < 360; i += 5) {
        float x = r * cos(radians(i));
        float y = r * sin(radians(i));
        float z = 0;
        float size = map(bandSize[i/5], 0, 255, 0, 50);
        float yOffset = map(sin(angle+i), -1, 1, -50, 50);
        int c = color(map(bandSize[i/5], 0, 255, 0, 360), 255, 255);
        fill(c);
        pushMatrix();
        translate(x, y+yOffset, z);
        if (i % 2 == 0) {
            box(size);
        } else {
            sphere(size);
        }
        popMatrix();
    }
}

  
}
