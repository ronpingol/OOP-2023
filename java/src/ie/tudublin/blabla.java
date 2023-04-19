package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class blabla extends PApplet {

    Minim minim;
    AudioPlayer song;
    FFT fft;
    int numBands = 512;
    float[] bandSize = new float[numBands];

    float angle = 0;
    float colorAngle = 0;

    public void settings() {
        size(800, 600, P3D);
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
        background(30, 30, 30);
        lights();

        // Cube field in top-left corner
        pushMatrix();
        translate(width / 4, height / 4, -200);
        drawShapeField(0);
        popMatrix();

        // Sphere field in top-right corner
        pushMatrix();
        translate(width / 4 * 3, height / 4, -200);
        drawShapeField(1);
        popMatrix();

        // Pyramid field in bottom-right corner
        pushMatrix();
        translate(width / 4 * 3, height / 4 * 3, -200);
        drawShapeField(2);
        popMatrix();

        // Cylinder field in bottom-left corner
        pushMatrix();
        translate(width / 4, height / 4 * 3, -200);
        drawShapeField(3);
        popMatrix();

        // Cone field in center of screen
        pushMatrix();
        translate(width / 2, height / 2, -200);
        drawShapeField(4);
        popMatrix();
    }

    private void drawShapeField(int shapeIndex) {
        rotateX(angle / 30.0f);
        rotateY(angle * 1.3f / 30.0f);
        rotateZ(angle * 0.7f / 30.0f);
        noStroke();
        float r = 200;
        fft.forward(song.mix);
        for (int i = 0; i < numBands; i++) {
            bandSize[i] = fft.getBand(i) * 5;
        }
        angle += map(bandSize[0], 0, 100, 0.01f, 0.1f);
        colorAngle += map(bandSize[1], 0, 100, 0.001f, 0.01f);
        float shapeSize = map(bandSize[shapeIndex * 100], 0, 255, 0, 100);
        int c = color(map(bandSize[shapeIndex * 100], 0, 255, 0, 360), 255, 255);
        fill(c);
        switch (shapeIndex) {
            case 0: // Cube
                box(shapeSize);
                break;
            case 1: // Sphere
                sphere(shapeSize);
                break;
            case 2: // Pyramid
                float halfSize = shapeSize / 2.0f;
                beginShape(TRIANGLES);
                vertex(0, halfSize, 0);
                vertex(-halfSize, -halfSize, halfSize);
                vertex(halfSize, -halfSize, halfSize);

                vertex(0, halfSize, 0);
                vertex(halfSize, -halfSize, -halfSize);
                vertex(-halfSize, -halfSize, -halfSize);

                vertex(0, halfSize, 0);
                vertex(-halfSize, -halfSize, -halfSize);
                vertex(-halfSize, -halfSize, halfSize);

                endShape();
                break;
            case 3: // Cylinder
                float height = shapeSize * 2;
                float radius = shapeSize / 2;
                beginShape(TRIANGLE_STRIP);
                for (int i = 0; i <= 360; i += 10) {
                    float x = radius * cos(radians(i));
                    float z = radius * sin(radians(i));
                    vertex(x, height / 2, z);
                    vertex(x, -height / 2, z);
                }
                endShape();
                break;
            case 4: // Torus
                float torusRadius = shapeSize / 3;
                float tubeRadius = shapeSize / 6;
                beginShape(QUAD_STRIP);
                for (int i = 0; i <= 360; i += 20) {
                    for (int j = 0; j <= 360; j += 20) {
                        float x = (torusRadius + tubeRadius * cos(radians(j))) * cos(radians(i));
                        float y = (torusRadius + tubeRadius * cos(radians(j))) * sin(radians(i));
                        float z = tubeRadius * sin(radians(j));
                        vertex(x, y, z);
                        float x2 = (torusRadius + tubeRadius * cos(radians(j + 20))) * cos(radians(i));
                        float y2 = (torusRadius + tubeRadius * cos(radians(j + 20))) * sin(radians(i));
                        float z2 = tubeRadius * sin(radians(j + 20));
                        vertex(x2, y2, z2);
                    }
                }
                endShape();
                break;
            case 5: // Tetrahedron
                r = shapeSize / sqrt(3);
                beginShape(TRIANGLES);
                vertex(-r, -r, -r);
                vertex(r, -r, r);
                vertex(r, r, -r);

                vertex(-r, -r, -r);
                vertex(-r, r, r);
                vertex(r, r, -r);

                vertex(-r, -r, -r);
                vertex(-r, r, r);
                vertex(r, -r, r);

                vertex(r, -r, r);
                vertex(-r, r, r);
                vertex(r, r, -r);
                endShape();
                break;
        }
    }
}
