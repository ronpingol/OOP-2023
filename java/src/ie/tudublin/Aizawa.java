package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import java.util.ArrayList;
import ddf.minim.*;

public class Aizawa extends PApplet {
    Minim minim;
    AudioPlayer song;
    float x = 0.1f;
    float y = 0;
    float z = 0;

    float a = 0.95f;
    float b = 0.7f;
    float c = 0.6f;
    float d = 3.5f;
    float e = 0.25f;
    float f = 0.1f;

    ArrayList<PVector> points = new ArrayList<PVector>();

    public void settings() {
        size(800, 600, P3D);
    }

    public void setup() {
        background(0);
        colorMode(HSB);

        minim = new Minim(this);
        song = minim.loadFile("somethingComforting.mp3");
        song.play();
    }

    public void draw() {
        float dt = 0.01f;
        float dx = (z - b) * x - d * y;
        float dy = d * x + (z - b) * y;
        float dz = c + a * z - (z * z * z) / 3 - (x * x + y * y) * (1 + e * z) + f * z * x * x * x;

        x += dx * dt;
        y += dy * dt;
        z += dz * dt;

        points.add(new PVector(x, y, z));

        PGraphics3D pg = (PGraphics3D) g;
        pg.beginDraw();
        pg.translate(width / 2, height / 2);
        pg.scale(50);
        pg.stroke(255);
        pg.noFill();

        float hu = 0;
        pg.beginShape();
        for (PVector v : points) {
            stroke(hu, 255, 255);
            pg.vertex(v.x, v.y, v.z);
            hu += 0.1;
            if (hu > 255) {
                hu = 0;
            }
        }
        pg.endShape();
        pg.endDraw();
    }
}
