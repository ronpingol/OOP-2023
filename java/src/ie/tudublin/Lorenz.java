package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import java.util.ArrayList;

public class Lorenz extends PApplet {
    float x = 0.01f;
    float y = 0;
    float z = 0;

    float a = 10;
    float b = 28;
    float c = 8.0f / 3.0f;

    ArrayList<PVector> points = new ArrayList<PVector>();

    public void settings(){
        size(800,600, P3D);
    }

    public void setup(){
        background(0);
        colorMode(HSB);
    }

    public void draw(){
        //background(0); // single point.
        float dt = 0.01f;
        float dx = (a * (y - x)) * dt;
        float dy = (x * (b - z) - y) * dt;
        float dz = (x * y - c * z) * dt;
        x = x + dx;
        y = y + dy;
        z = z + dz;

        points.add(new PVector(x, y, z));

        PGraphics3D pg = (PGraphics3D)g;
        pg.beginDraw();
        pg.translate(width/2, height/2);
        pg.scale(5);
        pg.stroke(255);
        pg.noFill();

        float hu = 0;
        pg.beginShape();
        for(PVector v : points){
            stroke(hu, 255, 255);
            pg.vertex(v.x, v.y, v.z);
            hu += 0.1;
            if (hu > 255){
                hu = 0;
            }
        }
        pg.endShape();
        pg.endDraw();
    }
}
