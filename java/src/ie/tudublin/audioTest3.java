package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PVector;

public class audioTest3 extends PApplet {
  Minim minim;
  AudioPlayer song;

  float songLevel;
  float branchAngle;
  float branchLength;
  float branchThickness;
  float branchDecay;
  float branchNoise;

  PVector[] leaves;

  public void settings() {
    size(800, 600);
  }

  public void setup() {
    minim = new Minim(this);
    song = minim.loadFile("shelter.mp3");
    song.play();
    stroke(255);
    strokeWeight(2);
    branchAngle = radians(20);
    branchLength = 100;
    branchThickness = 10;
    branchDecay = 0.6f;
    branchNoise = 0.2f;
    
    leaves = new PVector[100];
    for (int i = 0; i < leaves.length; i++) {
      leaves[i] = new PVector(random(-width / 2, width / 2), random(-height, 0));
    }
  }

  public void draw() {
    background(0);
    songLevel = song.mix.level();
    translate(width / 2, height);
    branch(100);
  }

  void branch(float len) {
    strokeWeight(branchThickness);

    translate(0, -len);

    if (len > 4) {
      pushMatrix();
      rotate(branchAngle + map(noise((float)(frameCount * 0.05)), 0, 1, -branchNoise, branchNoise));
      branch(len * branchDecay);
      popMatrix();

      pushMatrix();
      rotate(-branchAngle + map(noise((float)frameCount * 0.05f), 0, 1, -branchNoise, branchNoise));
      branch(len * branchDecay);
      popMatrix();
    }
  
    branchThickness *= branchDecay;
  
    // draw leaves on smaller branches
    if (len < 20) {
      noStroke();
      fill(0, 255, 0, 200);
      ellipse(0, 0, 20, 20);
    }
}


//   void branch(float len) {
//     strokeWeight(branchThickness);
  
//     // Color the text with a rainbow gradient
//     float hue = (float)frameCount * 0.1f % 360;
//     for (int i = 0; i < len; i++) {
//       float saturation = map(i, 0, len - 1, 100, 50);
//       fill(hue, saturation, 100);
//       //text("|", 0, -i * 14);
//     }

//     // Add leaves
//     if (len < 20) {
//       noStroke();
//       fill(0, 255, 0);
//       ellipse(0, -len, 30, 20);
//     }
  
//     translate(0, -len);
  
//     if (len > 4) {
//       pushMatrix();
//       rotate(branchAngle + map(noise((float)(frameCount * 0.05)), 0, 1, -branchNoise, branchNoise));
//       branch(len * branchDecay);
//       popMatrix();
  
//       pushMatrix();
//       rotate(-branchAngle + map(noise((float)frameCount * 0.05f), 0, 1, -branchNoise, branchNoise));
//       branch(len * branchDecay);
//       popMatrix();
//     }
  
//     branchThickness *= branchDecay;
//   }

  public void stop() {
    song.close();
    minim.stop();
    super.stop();
  }
}