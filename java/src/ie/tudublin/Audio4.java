package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import java.util.ArrayList;

public class Audio4 extends PApplet {

    Minim minim;
    AudioPlayer song;
    FFT fft;
    int numBands = 512;
    float[] bandSize = new float[numBands];

    float angle = 0;
    float colorAngle = 0;

    // Variables for fireworks animation
    int fireworksThreshold = 75; // Amplitude threshold to trigger fireworks
    boolean fireworksActive = false;
    PShape fireworksShape;
    ArrayList<Particle> particles = new ArrayList<Particle>();

    public void settings() {
        size(640, 640, P3D);
        smooth(8);
    }

    public void setup() {
        minim = new Minim(this);
        song = minim.loadFile("somethingComforting.mp3");
        fft = new FFT(song.bufferSize(), song.sampleRate());
        song.play();
        frameRate(60);

        // Create the fireworks shape
        fireworksShape = createShape();
        fireworksShape.beginShape(POINTS);
        fireworksShape.noFill();
        for (int i = 0; i < 50; i++) {
            fireworksShape.stroke(random(255), random(255), random(255));
            fireworksShape.vertex(0, 0, 0);
            fireworksShape.vertex(random(-10, 10), random(-10, 10), random(-10, 10));
        }
        fireworksShape.endShape();
    }

    public void draw() {
        background(0);
        lights();
        translate(width / 2, height / 2, -100);
        rotateX(angle / 4.0f);
        rotateY(angle * 1.3f / 4.0f);
        rotateZ(angle * 0.7f / 4.0f);
        noStroke();
        float r = 200;
        fft.forward(song.mix);
        for (int i = 0; i < numBands; i++) {
            bandSize[i] = fft.getBand(i) * 5;
        }
        angle += map(bandSize[0], 0, 100, 0.01f, 0.1f);
        colorAngle += map(bandSize[1], 0, 100, 0.001f, 0.01f);

        // Check if fireworks should be triggered
        if (bandSize[0] > fireworksThreshold && !fireworksActive) {
            fireworksActive = true;
            particles.clear();
            for (int i = 0; i < 100; i++) {
                Particle p = new Particle(random(-50, 50), random(-50, 50), random(-50, 50), random(10, 20));
                particles.add(p);
            }
        }

        // Update and draw particles for fireworks animation
        if (fireworksActive) {
            for (int i = particles.size() - 1; i >= 0; i--) {
                Particle p = particles.get(i);
                p.update();
                p.draw();
                if (p.shouldRemove()) {
                    particles.remove(i);
                }
            }
            if (fireworksActive && particles.size() == 0) {
                fireworksActive = false;
            }
            
            // Draw the spheres as before
            pushMatrix();
            translate(0, 0, -100);
            for (int i = 0; i < 360; i += 5) {
                float x = r * cos(radians(i));
                float y = r * sin(radians(i));
                float z = 0;
                float size = map(bandSize[i / 5], 0, 255, 0, 50);
                float yOffset = map(sin(angle + i), -1, 1, -50, 50);
                fill(map(sin(colorAngle + i / 5), -1, 1, 0, 255),
                        map(cos(colorAngle + i / 5), -1, 1, 0, 255),
                        map(sin(colorAngle + i / 5), -1, 1, 255, 0));
                pushMatrix();
                translate(x, y + yOffset, z);
                sphere(size);
                popMatrix();
            }
            popMatrix();
        }
    }

    // Particle class for fireworks animation
    class Particle {
        PVector pos;
        PVector vel;
        float lifespan;
        float gravity;

        Particle(float x, float y, float z, float lifespan) {
            pos = new PVector(x, y, z);
            vel = new PVector(random(-1, 1), random(-1, 1), random(-1, 1));
            vel.normalize();
            vel.mult(random(10, 20));
            this.lifespan = lifespan;
            this.gravity = random(0.05f, 0.1f);
        }

        void update() {
            vel.add(new PVector(0, gravity, 0));
            pos.add(vel);
            vel.mult(0.9f);
            lifespan -= 0.4f;
        }

        void draw() {
            stroke(255, lifespan * 5);
            strokeWeight(lifespan / 10.0f);
            pushMatrix();
            translate(pos.x, pos.y, pos.z);
            shape(fireworksShape);
            popMatrix();

        }

        boolean shouldRemove() {
            return lifespan < 0;
        }
    }

}
