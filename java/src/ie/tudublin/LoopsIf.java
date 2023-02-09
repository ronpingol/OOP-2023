package ie.tudublin;

import processing.core.PApplet;

public class LoopsIf extends PApplet {

    public void settings() {
        size(500, 500);
        cx = width / 2;
        cy = height / 2;
    }

    int mode = 0;
    float cx;
    float cy;

    public void keyPressed() {

        if (keyCode >= '0' && keyCode <= 9)
            mode = keyCode - '0';
    }

    public void setup() {
        colorMode(HSB);
    }

    public void draw() {
        background(0);
        noStroke();
        switch (mode) {
            case 0:
                fill(255, 200, 200);
                if (mouseX < cx) {
                    rect(0, 0, cx, height);
                } else {
                    rect(cx, 0, cx, height);
                }
                break;
            case 1:
                fill(255, 200, 200);
                if (mouseX < cx && mouseY < cy) {
                    rect(0, 0, cx, height);
                } else if (mouseX > cx && mouseY < cy) {
                    rect(cx, 0, cx, height);
                } else if (mouseX < cx && mouseY > cy) {
                    rect(0, cy, cx, cy);
                } else {
                    rect(cx, cy, cx, cy);
                }
                break;
        }
    }

}
