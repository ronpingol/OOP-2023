package ie.tudublin;

import processing.core.*;

public class Piano {
    private boolean[] keys = new boolean[10];
    private PApplet p;

    public Piano(PApplet p) {
        this.p = p;
    }

    public void activateKey(int i) {
        keys[i] = true;
    }

    public void deactivateKey(int i) {
        keys[i] = false;
    }

    public boolean isKeyPressed(int i) {
        return keys[i];
    }
}
