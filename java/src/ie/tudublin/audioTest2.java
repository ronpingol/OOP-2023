package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class audioTest2 extends PApplet
{
Minim minim;
AudioPlayer song;

float songLevel;
float branchAngle;
float branchLength;
float branchThickness;
float branchDecay;
float branchNoise;

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
}

    public void draw() {
        background(0);
        songLevel = song.mix.level();
        translate(width / 2, height);
        branch(100);
    }

    void branch(float len) {
        strokeWeight(branchThickness);
        line(0, 0, 0, -len);
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
    }

    public void stop() {
        song.close();
        minim.stop();
        super.stop();
    }
}
