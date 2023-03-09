package ie.tudublin;
import ie.tudublin.Piano;


import processing.core.*;
import processing.opengl.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class MusicPiano extends PApplet {

    Minim minim;
    AudioPlayer song;
    FFT fft;

    Piano piano;

    float[] angles = new float[10];
    float[] keys = new float[10];
    

    public void settings() {
        size(800, 800, P3D);
    }

    public void setup() {
        minim = new Minim(this);
        song = minim.loadFile("clarity.mp3");
        song.play();
        fft = new FFT(song.bufferSize(), song.sampleRate());
        piano = new Piano(this);
    }

    public void keyPressed() {
        int i = -1;
        switch (key) {
            case 'a':
                i = 0;
                break;
            case 's':
                i = 1;
                break;
            case 'd':
                i = 2;
                break;
            case 'f':
                i = 3;
                break;
            case 'g':
                i = 4;
                break;
            case 'h':
                i = 5;
                break;
            case 'j':
                i = 6;
                break;
            case 'k':
                i = 7;
                break;
            case 'l':
                i = 8;
                break;
            case ';':
                i = 9;
                break;
        }

        if (i != -1) {
            piano.activateKey(i);
        }
    }

    public void keyReleased() {
        int i = -1;
        switch (key) {
            case 'a':
                i = 0;
                break;
            case 's':
                i = 1;
                break;
            case 'd':
                i = 2;
                break;
            case 'f':
                i = 3;
                break;
            case 'g':
                i = 4;
                break;
            case 'h':
                i = 5;
                break;
            case 'j':
                i = 6;
                break;
            case 'k':
                i = 7;
                break;
            case 'l':
                i = 8;
                break;
            case ';':
                i = 9;
                break;
        }

        if (i != -1) {
            piano.deactivateKey(i);
        }
    }

    public void draw() {
        background(0);
        lights();

        float[] bands = getSmoothedBands();

        for (int i = 0; i < 10; i++) {
            angles[i] += map(bands[i], 0, 1, 0.01f, 0.1f);
            keys[i] = lerp(keys[i], piano.isKeyPressed(i) ? 1 : 0, 0.1f);
            pushMatrix();
            translate(width / 2, height / 2, -250);
            rotateX(angles[i] * 0.7f);
            rotateY(angles[i] * 1.3f);
            rotateZ(angles[i]);
            fill(keys[i] > 0.5 ? 255 : 100);
            box(50, 300, 50);
            popMatrix();
        }
    }

    public float[] getSmoothedBands() {
        fft.forward(song.mix);
        float[] bands = new float[10];
        for (int i = 0; i < bands.length; i++) {
            int start = i * (fft.specSize() / bands.length);
            int end = start + (fft.specSize() / bands.length);
            float sum = 0;
            for (int j = start; j < end; j++) {
                sum += fft.getBand(j);
            }
            float average = sum / (end - start);
            bands[i] = average;
        }
        return bands;
    }
}
           
