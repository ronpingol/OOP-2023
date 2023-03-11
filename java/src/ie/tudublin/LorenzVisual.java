package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PGraphics3D;
import java.util.ArrayList;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class LorenzVisual extends PApplet {
    float x = 0.01f;
    float y = 0;
    float z = 0;

    float a = 10;
    float b = 28;
    float c = 8.0f / 3.0f;

    float depth = 1000;


    ArrayList<PVector> points = new ArrayList<PVector>();

    Minim minim;
    AudioPlayer song;
    FFT fft;

    public void settings(){
        size(800,600, P3D);
    }

    public void setup(){
        background(0);
        colorMode(HSB);
        minim = new Minim(this);
        song = minim.loadFile("somethingComforting.mp3");
        song.play();
        fft = new FFT(song.bufferSize(), song.sampleRate());
        fft.linAverages(128);
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
    
        fft.forward(song.mix);
        float bass = fft.getBand(0); // get the amplitude of the first frequency band
        float treble = fft.getBand(127); // get the amplitude of the last frequency band
        float freq = map(bass, 0, 1, 0.1f, 0.5f); // map the bass amplitude to a frequency between 0.1 and 0.5
    
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
            // Check if the point is outside the screen dimensions and adjust if necessary
            if (v.x < -width/2) v.x += width;
            else if (v.x > width/2) v.x -= width;
            if (v.y < -height/2) v.y += height;
            else if (v.y > height/2) v.y -= height;
            if (v.z < -depth/2) v.z += depth;
            else if (v.z > depth/2) v.z -= depth;
    
            stroke(hu, 255, 255);
            pg.vertex(v.x, v.y, v.z);
            hu += 0.1;
            if (hu > 255){
                hu = 0;
            }
        }
        pg.endShape();
        pg.endDraw();
    
        // adjust the parameters of the Lorenz system based on the audio
        a = map(bass, 0, 1, 5, 20);
        b = map(treble, 0, 1, 20, 50);
        c = freq;
    }
    
}



