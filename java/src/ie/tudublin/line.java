package ie.tudublin;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

public class line extends PApplet {

    Minim minim;
    AudioPlayer player;
    FFT fft;
    float bass;
    float treble;

    public void settings() {
        size(800, 600, P3D);

    }

    public void setup() {

        minim = new Minim(this);
        player = minim.loadFile("somethingComforting.mp3");
        player.play();
        fft = new FFT(player.bufferSize(), player.sampleRate());
    }
    
    

    public void draw() {
        background(0);
        lights();
    
        // Rotate the camera around the solar system
        float cameraX = sin(frameCount * 0.01f) * 1000;
        float cameraY = cos(frameCount * 0.01f) * 1000;
        camera(cameraX, cameraY, 600, 0, 0, 0, 0, 1, 0);
    
        // Analyze the audio data
        fft.forward(player.mix);
        bass = fft.getBand(0);
        treble = fft.getBand(fft.specSize() - 1);
    
        // Draw the sun
        pushMatrix();
        fill(135, 206, 235);
        sphere(80 + bass / 4);
        popMatrix();
    
        // Draw the planets
        pushMatrix();
        rotateY(frameCount * 0.005f);
        translate(200, 0, 0);
        fill(30, 144, 255);
        noStroke();
        sphereDetail(30, 20); // increase the polygon resolution
        sphere(30 + treble / 8);
        popMatrix();
    
        pushMatrix();
        rotateY(frameCount * 0.003f);
        translate(350, 0, 0);
        fill(30, 144, 255);
        noStroke();
        float planetSize = 40 + bass / 6;
        planetSize = map(planetSize, 40, 80, 30, 60); // adjust the planet size based on the bass frequency
        sphereDetail(30, 20); // increase the polygon resolution
        sphere(planetSize);
        popMatrix();
        
    
        pushMatrix();
        rotateY(frameCount * 0.001f);
        translate(500, 0, 0);
        fill(30, 144, 255);
        noStroke();
        float planetY = map(treble, 0, 255, -100, 100); // adjust the planet position based on the treble frequency
        translate(0, planetY, 0);
        sphereDetail(30, 20); // increase the polygon resolution
        sphere(50 + treble / 10);
        popMatrix();
        
    
        // Draw the moons
        pushMatrix();
        rotateY(frameCount * 0.01f);
        translate(200, 0, 0);
        rotateZ(frameCount * 0.01f);
        fill(255);
        noStroke();
        sphereDetail(20, 10); // increase the polygon resolution
        sphere(10 + bass / 12);
        popMatrix();
    
        pushMatrix();
        rotateY(frameCount * 0.01f);
        translate(350, 0, 0);
        rotateZ(frameCount * -0.01f);
        fill(255);
        noStroke();
        sphereDetail(20, 10); // increase the polygon resolution
        sphere(20 + treble / 15);
        popMatrix();
    
        pushMatrix();
        rotateY(frameCount * 0.01f);
        translate(500, 0, 0);
        rotateZ(frameCount * 0.01f);
        fill(255);
        noStroke();
        sphereDetail(20, 10); // increase the polygon resolution
        sphere(15 + bass / 15);
        popMatrix();
    }
    
    
}