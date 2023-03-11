package ie.tudublin;

import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;
import processing.core.PApplet;

public class flowers extends PApplet {
    Minim minim;
    AudioPlayer song;
    BeatDetect beat;
    Flower flower;
    
    public void settings() {
      size(600, 600, P3D);
    }
    
    public void setup() {
      minim = new Minim(this);
      song = minim.loadFile("somethingComforting.mp3");
      beat = new BeatDetect();
      flower = new Flower(width/2, height/2, 50);
    }
    
    public void draw() {
      background(255);
      song.play();
      beat.detect(song.mix);
      if (beat.isOnset()) {
        flower.size = random(50, 100);
      }
      float amplitude = song.mix.level();
      flower.update(amplitude);
      flower.draw();
    }
    
    class Flower {
      float x, y;
      float size;
      float angle;
      
      Flower(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.angle = 0;
      }
      
      void draw() {
        pushMatrix();
        translate(x, y, 0);
        rotateX(angle);
        rotateY(angle);
        noStroke();
        fill(255, 0, 0);
        sphere(size);
        for (int i = 0; i < 5; i++) {
          pushMatrix();
          rotateY(radians(72 * i));
          translate(size/2, 0, 0);
          sphere(size/3);
          popMatrix();
        }
        popMatrix();
      }
      
      void update(float amplitude) {
        angle += amplitude * 0.05;
      }
    }
}