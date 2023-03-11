package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import java.util.ArrayList;

public class Fireworks extends PApplet {

    Minim minim;
    AudioPlayer song;
    FFT fft;
    int numFireworks = 10;
    Firework[] fireworks = new Firework[numFireworks];
    
    ArrayList<FireworkParticle> particles = new ArrayList<FireworkParticle>();

    public void settings() {
        size(800, 600, P3D);
    }

    public void setup() {
        minim = new Minim(this);
        song = minim.loadFile("somethingComforting.mp3");
        fft = new FFT(song.bufferSize(), song.sampleRate());
        song.play();
        for (int i = 0; i < numFireworks; i++) {
            fireworks[i] = new Firework();
        }
        fft.forward(song.mix);
    }

    public void draw() {
        background(0);
        fft.forward(song.mix);

        float cameraX = map(fft.getBand(20), 0, 255, -400, 400);
        float cameraY = map(fft.getBand(40), 0, 255, -200, 200);
        float cameraZ = map(fft.getBand(80), 0, 255, -800, 800);
        camera(cameraX, cameraY, cameraZ, 0, 0, 0, 0, 1, 0);

        for (int i = 0; i < numFireworks; i++) {
            fireworks[i].update(fft);
            fireworks[i].display();
        }
        for (int i = particles.size() - 1; i >= 0; i--) {
            FireworkParticle p = particles.get(i);
            p.update();
            p.display();
            if (p.isDead()) {
                particles.remove(i);
            }
        }
    }

    class Firework {
        PVector position;
        PVector velocity;
        float size;
        int color;
        float lifespan;
        boolean exploded;

        Firework() {
            position = new PVector(random(-width / 2, width / 2), random(-height / 2, height / 2), random(-1000, -200));
            velocity = new PVector(0, 0, random(2, 5));
            size = random(10, 20);
            color = color(random(200, 255), random(200, 255), random(200, 255));
            lifespan = random(1, 3);
            exploded = false;
        }

        void update(FFT fft) {
            float speed = map(fft.getBand(20), 0, 255, 0.5f, 2);
            position.add(velocity.copy().mult(speed));
            if (position.z > 0 && !exploded) {
                explode();
            }
            if (exploded && lifespan > 0) {
                lifespan -= 0.05;
            }
        }

        void display() {
            if (!exploded) {
                stroke(color);
                strokeWeight(size);
                point(position.x, position.y, position.z);
            } else if (lifespan > 0) {
                noStroke();
                fill(color);
                float r = map(lifespan, 1, 0, 0, size * 10);
                ellipse(position.x, position.y, r, r / 2);
            }
        }

        void explode() {
            exploded = true;
            lifespan = 1;
            for (int i = 0; i < 20; i++) {
                float angle = random(0, TWO_PI);
                PVector explosionVelocity = PVector.fromAngle(angle);
                explosionVelocity.mult(random(1, 5));
                float explosionSize = random(2, 5);
                FireworkParticle p = new FireworkParticle(position.copy(), explosionVelocity, explosionSize, color);
                particles.add(p);
            }
        }
        
    }
}