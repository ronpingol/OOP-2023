package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import java.util.ArrayList;
import ddf.minim.*;

public class randomLines extends PApplet {
    Minim minim;
    AudioPlayer song;
    ArrayList<PVector> positions = new ArrayList<PVector>();

    public void settings() {
        size(800, 600, P3D);
    }

    public void setup() {
        background(0);
        colorMode(HSB);
        frameRate(10);

        minim = new Minim(this);
        song = minim.loadFile("somethingComforting.mp3");
        song.play();
    }

    public void draw() {
        float dt = 0.001f;
        float x = random(-width/2, width/2);
        float y = random(-height/2, height/2);
        float z = random(-200, 200);
        
        float scale = random(1, 5);
        int c = color(random(255), 255, 255);
        
        positions.add(new PVector(x, y, z));
        
        PGraphics3D pg = (PGraphics3D) g;
        pg.beginDraw();
        pg.translate(width/2, height/2);
        pg.scale(scale);
        pg.stroke(c);
        pg.strokeWeight(1);
        pg.noFill();
        
        pg.beginShape();
        for (PVector p : positions) {
          pg.vertex(p.x, p.y, p.z);
          p.x += random(-dt, dt);
          p.y += random(-dt, dt);
          p.z += random(-dt, dt);
        }
        pg.endShape();
        pg.endDraw();
    }
}
