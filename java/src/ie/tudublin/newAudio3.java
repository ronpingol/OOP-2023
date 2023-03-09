package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class newAudio3 extends PApplet {

  Minim minim;
  AudioPlayer song;
  FFT fft;
  int numBands = 256;
  float[] bandSize = new float[numBands];
  float[][] terrain;
  int cols, rows;
  int scl = 20;
  float flying = 0;

  float cameraX = 0;
  float cameraY = 0;
  float cameraZ = 0;
  
  public void settings() {
    size(800, 600, P3D);
  }

  public void setup() {
    minim = new Minim(this);
    song = minim.loadFile("somethingComforting.mp3");
    fft = new FFT(song.bufferSize(), song.sampleRate());
    song.play();
    cols = width / scl;
    rows = height / scl;
    terrain = new float[cols][rows];
  }

  public void draw() {
    background(0);

    // Update and display terrain
    fft.forward(song.mix);
    flying -= fft.getBand(0) / 10; // Slower terrain movement
    float yoff = flying;
    for (int y = 0; y < rows; y++) {
      float xoff = 0;
      for (int x = 0; x < cols; x++) {
        terrain[x][y] = map(noise(xoff, yoff), 0, 1, -100, 100);
        xoff += 0.1;
      }
      yoff += 0.1;
    }
    stroke(255);
    noFill();
    translate(width/2, height/2 + 50);
    rotateX(PI/3);
    translate(-width/2, -height/2);
    for (int y = 0; y < rows-1; y++) {
      beginShape(TRIANGLE_STRIP);
      for (int x = 0; x < cols; x++) {
        vertex(x*scl, y*scl, terrain[x][y]);
        vertex(x*scl, (y+1)*scl, terrain[x][y+1]);
      }
      endShape();
    }

    // Draw pulsing lights
    float radius = map(fft.getBand(10), 0, 255, 100, 400);
    float pulse = map(sin(frameCount * 0.05f), -1, 1, 0.5f, 1.5f); // Slower pulse animation
    noStroke();
    fill(0, 255, 255, 200);
    translate(width/2, height/2, -300);
    for (int i = 0; i < 30; i++) {
      float angle = map(i, 0, 30, 0, TWO_PI);
      float x = cos(angle) * radius * pulse;
      float y = sin(angle) * radius * pulse;
      float z = map(fft.getBand(i * 8), 0, 255, -200, 200);
      pushMatrix();
      translate(x, y, z);
      sphere(10);
      popMatrix();
    }

    // Draw floating cubes
    float cubeSize = map(fft.getBand(20), 0, 255, 30, 100);
    stroke(255);
    noFill();
    translate(-width/2, -height/2, 0);
    for (int y = 0; y < rows-1; y += 3) {
      for (int x = 0; x < cols-1; x += 3) {
        float cubeX = x * scl + scl / 2;
        float cubeY = y * scl + scl / 2;
        float cubeZ = map(terrain[x][y], -100, 100, -300, 300);
        pushMatrix();
        translate(cubeX, cubeY, cubeZ);
        rotateX(frameCount * 0.005f); // Slower rotation
        rotateY(frameCount * 0.005f); // Slower rotation
        box(cubeSize);
        popMatrix();
      }
    }
  }
}  