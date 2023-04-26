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
  float noiseScale = 0.05f;
  float noiseStrength = 100;

  public void settings() {
    size(1280, 720, P3D);
    smooth(8);
  }

  public void setup() {
    minim = new Minim(this);
    song = minim.loadFile("somethingComforting.mp3");
    fft = new FFT(song.bufferSize(), song.sampleRate());
    song.play();
    frameRate(60);
  }

  public void draw() {
    background(0);
    lights();

    // Cube field in top-left corner
    pushMatrix();
    translate(width / 4, height / 4, -100);
    drawCubeField();
    popMatrix();

    // Cube field in top-right corner
    pushMatrix();
    translate(width / 4 * 3, height / 4, -100);
    drawCubeField();
    popMatrix();

    // Cube field in bottom-right corner
    pushMatrix();
    translate(width / 4 * 3, height / 4 * 3, -100);
    drawCubeField();
    popMatrix();

    // Cube field in bottom-left corner
    pushMatrix();
    translate(width / 4, height / 4 * 3, -100);
    drawCubeField();
    popMatrix();

    // Cube field in center of screen
    pushMatrix();
    translate(width / 2, height / 2, -100);
    drawCubeField();
    popMatrix();
  }

  private void drawCubeField() {
    rotateX(angle / 30.0f);
    rotateY(angle * 1.3f / 30.0f);
    rotateZ(angle * 0.7f / 30.0f);
    noStroke();
    fft.forward(song.mix);
    for (int i = 0; i < numBands; i++) {
      bandSize[i] = fft.getBand(i) * 5;
    }
    angle += map(bandSize[0], 0, 100, 0.01f, 0.1f);
    colorAngle += map(bandSize[1], 0, 100, 0.001f, 0.01f);
    for (int i = -200; i < 200; i += 20) {
      for (int j = -200; j < 200; j += 20) {
        for (int k = -200; k < 200; k += 20) {
          float x = i;
          float y = j;
          float z = k;
          float size = map(bandSize[(i + 200) / 20], 0, 255, 0, 50);
          float yOffset = noise(i * noiseScale, j * noiseScale, k * noiseScale) * noiseStrength;
          // Assign a different color for each cube based on its position
          int cubeColor = color(map(i + colorAngle, 0, 512, 0, 255),
              map(j + colorAngle, 0, 512, 0, 255),
              map(k + colorAngle, 0, 512, 0, 255));
          fill(cubeColor);
          pushMatrix();
          translate(x, y + yOffset, z);
          box(size);
          popMatrix();
        }
      }
    }
  }
}