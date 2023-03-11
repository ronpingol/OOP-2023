package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class Clouds extends PApplet {

    Minim minim;
    AudioPlayer song;
    FFT fft;
    int numStars = 1000;
    int numClouds = 20;
    Cloud[] clouds = new Cloud[numClouds];

    public void settings() {
        size(800, 600, P3D);
    }

    public void setup() {
        minim = new Minim(this);
        song = minim.loadFile("somethingComforting.mp3");
        fft = new FFT(song.bufferSize(), song.sampleRate());
        song.play();
        for (int i = 0; i < numClouds; i++) {
            clouds[i] = new Cloud();
        }

        numStars = 1000; // Define the number of stars in the starfield
        fft.forward(song.mix); // Perform the FFT on the first frame
    }

    public void draw() {
        background(0);
        fft.forward(song.mix);

        float cameraX = map(fft.getBand(20), 0, 255, -400, 400);
        float cameraY = map(fft.getBand(40), 0, 255, -200, 200);
        float cameraZ = map(fft.getBand(80), 0, 255, -800, 800);
        camera(cameraX, cameraY, cameraZ, 0, 0, 0, 0, 1, 0);

        // Draw starfield
        pushMatrix();
        translate(width / 2, height / 2, -800);
        for (int i = 0; i < numStars; i++) {
            float x = random(-width, width);
            float y = random(-height, height);
            float z = random(-1000, 1000);
            stroke(255);
            point(x, y, z);
        }
        popMatrix();

        for (int i = 0; i < numClouds; i++) {
            clouds[i].update(fft);
            clouds[i].display();
        }
    }

    class Cloud {
        PVector position;
        PVector velocity;
        float size;
        int color;

        Cloud() {
            position = new PVector(random(-width / 2, width / 2), random(-height / 2, height / 2), random(-1000, -200));
            velocity = new PVector(0, 0, random(1, 3));
            size = random(50, 200);
            color = color(random(200, 255), random(200, 255), random(200, 255));
        }

        void update(FFT fft) {
            float speed = map(fft.getBand(20), 0, 255, 0.5f, 2);
            position.add(velocity.copy().mult(speed));
            if (position.z > 200) {
                position.z = random(-1000, -200);
            }
        }

        void display() {
            pushMatrix();
            translate(position.x, position.y, position.z);
            noStroke();
            fill(color);
            ellipse(0, 0, size, size / 2);
            popMatrix();
        }
    }
}
