package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class testTest extends PApplet {

  Minim minim;
  AudioPlayer song;
  FFT fft;
  int numBands = 256;
  float[] bandSize = new float[numBands];
  float[] xPositions = new float[numBands];
  float[] yPositions = new float[numBands];
  float[] zPositions = new float[numBands];

  public void settings() {
    size(640, 640, P3D);
    smooth(8);
  }

  public void setup() {
    minim = new Minim(this);
    song = minim.loadFile("somethingComforting.mp3", 2048);
    fft = new FFT(song.bufferSize(), song.sampleRate());
    song.play();
    colorMode(HSB, 360, 100, 100);
    noStroke();
  }

  public void draw() {
    background(0);
    lights();
    fft.forward(song.mix);
    for (int i = 0; i < numBands; i++) {
      bandSize[i] = fft.getBand(i) * 2;
      float angle = map(i, 0, numBands, 0, TWO_PI);
      float radius = height / 3;
      float x = radius * cos(angle);
      float y = radius * sin(angle);
      xPositions[i] += map(bandSize[i], 0, 255, -5, 5);
      yPositions[i] += map(bandSize[i], 0, 255, -5, 5);
      zPositions[i] += map(bandSize[i], 0, 255, -5, 5);
      fill(map(i, 0, numBands, 0, 360), 100, 100);
      pushMatrix();
      translate(width / 2 + xPositions[i] + x, height / 2 + yPositions[i] + y, -200 + zPositions[i]);
      sphere(bandSize[i] / 2);
      popMatrix();
    }
  }
}
