package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class RunningPerson extends PApplet {

  Minim minim;
  AudioPlayer song;
  FFT fft;
  int numBands = 256;
  float[] bandSize = new float[numBands];

  // Variables for running person
  float personX = 0;
  float personY = 200;
  float personZ = 0;
  float personSpeed = 0.1f;
  float personDirection = 1;
  float personFlip = 0;
  float personFlipSpeed = 0.2f;
  
  public void settings() {
    size(800, 600, P3D);
  }

  public void setup() {
    minim = new Minim(this);
    song = minim.loadFile("somethingComforting.mp3");
    fft = new FFT(song.bufferSize(), song.sampleRate());
    song.play();
  }

  public void draw() {
    background(0);
  
    // Update and display running person
    fft.forward(song.mix);
    personX += personDirection * personSpeed * fft.getBand(100);
    personFlip += personDirection * personFlipSpeed * fft.getBand(50);
    if (personX > width || personX < 0) {
      personDirection *= -1;
    }
    stroke(255);
    fill(255, 255, 0);
    pushMatrix();
    translate(personX, personY, personZ);
    if (personDirection > 0) {
      rotateY(map(sin(personFlip), -1, 1, -PI/4, PI/4));
    } else {
      rotateY(map(sin(personFlip), -1, 1, PI + PI/4, PI - PI/4));
    }
    box(50, 100, 30);
    popMatrix();
  
    // Draw pulsing lights
    float radius = map(fft.getBand(10), 0, 255, 100, 400);
    float pulse = map(sin(frameCount * 0.1f), -1, 1, 0.5f, 1.5f);
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
  }
}
