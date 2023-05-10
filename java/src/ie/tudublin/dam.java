package ie.tudublin;

import processing.core.PApplet;
import ddf.minim.*;

public class dam extends PApplet {

  // Grass variables
  int numGrassBlades = 2000;
  Grass[] grass = new Grass[numGrassBlades];

  // Sky variables
  int numBirds = 50;
  Bird[] birds = new Bird[numBirds];

  // Wind variables
  float windAngle = 0;
  float windSpeed = 0.01f;

  public void settings() {
    size(800, 600, P3D);
  }

  public void setup() {
    Minim minim = new Minim(this);
    AudioPlayer player = minim.loadFile("somethingComforting.mp3");
    player.play();

    colorMode(RGB);
    for (int i = 0; i < numGrassBlades; i++) {
      float x = random(-width, width);
      float z = random(-height, height);
      float angle = random(TWO_PI);
      grass[i] = new Grass(x, z, angle);
    }
    for (int i = 0; i < numBirds; i++) {
      float x = random(-width, width);
      float y = random(-height, height);
      float z = random(-1000, -500);
      float angle = random(TWO_PI);
      birds[i] = new Bird(x, y, z, angle);
    }
    frameRate(60);
  }

  public void draw() {
    background(0x87, 0xCE, 0xFA);
    lights();

    // Draw grass
    pushMatrix();
    translate(width / 2, height / 2, 0);
    rotateY(windAngle);
    for (int i = 0; i < numGrassBlades; i++) {
      grass[i].display();
    }
    popMatrix();

    // Draw sky
    pushMatrix();
    translate(width / 2, height / 2, -800);
    for (int i = 0; i < numBirds; i++) {
      birds[i].update();
      birds[i].display();
    }
    popMatrix();

    // Update wind angle
    windAngle += windSpeed;
  }

  class Grass {
    float x, z, angle;
    float height, sway;

    Grass(float x, float z, float angle) {
      this.x = x;
      this.z = z;
      this.angle = angle;
      this.height = random(50, 200);
      this.sway = random(10, 30);
    }

    void display() {
      pushMatrix();
      translate(x, 0, z);
      rotateY(angle);
      float swayAngle = sin(angle + frameCount * 0.1f) * sway;
      translate(0, height / 2, 0);
      for (int i = 0; i < height; i += 20) {
        float r = map(i, 0, height, 1, 3);
        float y = i + sin(swayAngle + i * 0.01f) * 5;
        stroke(0, 128, 0);
        strokeWeight(r);
        point(0, y, 0);
      }
      popMatrix();
    }
  }

  class Bird {
    float x, y, z, angle;
    float targetX, targetY, targetZ;
    float speed, flapAngle;

    Bird(float x, float y, float z, float angle) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.angle = angle;
      this.speed = random(1, 5);
      this.flapAngle = random(0, TWO_PI);
      this.targetX = x;
      this.targetY = y;
      this.targetZ = z;
    }

    void update() {
      // Interpolate towards the target position
      x = lerp(x, targetX, 0.1f);
      y = lerp(y, targetY, 0.1f);
      z = lerp(z, targetZ, 0.1f);

      // Set a new target position when the bird reaches the old target
      float d = dist(x, y, z, targetX, targetY, targetZ);
      if (d < 5) {
        targetX = random(-width, width);
        targetY = random(-height, height);
        targetZ = random(-1000, -500);
      }

      angle += 0.01f;
      flapAngle += 0.1f;
    }

    void display() {
      pushMatrix();
      translate(x, y, z);
      rotateY(angle);
      rotateZ(flapAngle);
      noStroke();
      fill(255);
      sphere(10);
      popMatrix();
    }
  }

}