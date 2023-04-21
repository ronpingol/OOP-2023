package ie.tudublin;

import ddf.minim.*;
import ddf.minim.analysis.*;
import processing.core.PApplet;

public class audioHmTest extends PApplet {
    Minim minim;
    AudioPlayer song;
    FFT fft;
    int numBands = 512;
    float[] bandSize = new float[numBands];

    float angle = 0;
    float colorAngle = 0;

    int[] colors = {
            color(255, 0, 0), // Red
            color(0, 255, 0), // Green
            color(0, 0, 255), // Blue
            color(255, 255, 0), // Yellow
            color(255, 0, 255) // Magenta
    };

    int pyramidSize = 50;
    int numPyramids = 10;
    Pyramid[] pyramids = new Pyramid[numPyramids];

    public void settings(){
        size(640, 640, P3D);
        smooth(8);
      }

    public void setup() {
        minim = new Minim(this);
        song = minim.loadFile("somethingComforting.mp3");
        fft = new FFT(song.bufferSize(), song.sampleRate());
        song.play();
        frameRate(60);
    }

    public void draw() {
        background(0);
        lights();

        // Pyramid field in top-left corner
        pushMatrix();
        translate(width / 4, height / 4, -100);
        drawShapeField();
        popMatrix();

        // Pyramid field in top-right corner
        pushMatrix();
        translate(width / 4 * 3, height / 4, -100);
        drawShapeField();
        popMatrix();

        // Pyramid field in bottom-right corner
        pushMatrix();
        translate(width / 4 * 3, height / 4 * 3, -100);
        drawShapeField();
        popMatrix();

        // Pyramid field in bottom-left corner
        pushMatrix();
        translate(width / 4, height / 4 * 3, -100);
        drawShapeField();
        popMatrix();

        // Pyramid field in center of screen
        pushMatrix();
        translate(width / 2, height / 2, -100);
        drawShapeField();
        popMatrix();
    }

    void drawShapeField() {
        rotateX(angle / 30.0f);
        rotateY(angle * 1.3f / 30.0f);
        rotateZ(angle * 0.7f / 30.0f);
        noStroke();
        fft.forward(song.mix);
        for (int i = 0; i < numBands; i++) {
            bandSize[i] = fft.getBand(i) * 5;
        }
        angle += map(bandSize[0], 0, 255, 0.005f, 0.05f);
        colorAngle += map(bandSize[1], 0, 255, 0.0005f, 0.005f);
        for (int i = 0; i < numPyramids; i++) {
            float x = random(-width / 4, width / 4);
            float y = random(-height / 4, height / 4);
            float z = random(-100, 0);
            float size = pyramidSize + bandSize[i % numBands];
            int c = colors[i % colors.length];
            pyramids[i] = new Pyramid(x, y, z, size, c);
            pyramids[i].display();
        }
    }

    class Pyramid {

        float x, y, z;
        float size;
        int color;
      
        public Pyramid(float x, float y, float z, float size, int color) {
          this.x = x;
          this.y = y;
          this.z = z;
          this.size = size;
          this.color = color;
        }
      
        public void display() {
          pushMatrix();
          translate(x, y, z);
          fill(color);
          beginShape(TRIANGLE_STRIP);
          vertex(-size, -size, -size);
          vertex( size, -size, -size);
          vertex( 0, 0, size);
          vertex(-size, -size, -size);
          vertex(-size, size, -size);
          vertex( 0, 0, size);
          vertex(size, size, -size);
          vertex(-size, size, -size);
          vertex(0, 0, size);
          vertex(size, size, -size);
          vertex(size, -size, -size);
          vertex(0, 0, size);
          endShape();
          popMatrix();
        }
      }
      
}