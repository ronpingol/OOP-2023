package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class newAudio extends PApplet {

  Minim minim;
  AudioPlayer song;
  FFT fft;
  int numBands = 512;
  float[] bandSize = new float[numBands];

  float angle = 0;
  float colorAngle = 0;
  
  public void settings(){
    size(640, 640, P3D);
  }
  
  public void setup() {
    minim = new Minim(this);
    song = minim.loadFile("somethingComforting.mp3");
    fft = new FFT(song.bufferSize(), song.sampleRate());
    song.play();
  }

  public void draw() {
    background(0);
    lights();
    translate(width/2, height/2, -100);
    rotateX(angle);
    rotateY(angle*1.3f);
    rotateZ(angle*0.7f);
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
      fill(color(map(i+colorAngle, 0, 360, 0, 255), 255, 255));
      pushMatrix();
      translate(x, y, z);
      sphere(size);
      popMatrix();
    }
  }
}
