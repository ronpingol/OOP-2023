package ie.tudublin;

import processing.core.*;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;


public class FireworkParticle extends PApplet{
    PVector position;
    PVector velocity;
    float size;
    int color;
    float lifespan;

    FireworkParticle(PVector pos, PVector vel, float s, int c) {
        position = pos;
        velocity = vel;
        size = s;
        color = c;
        lifespan = 1;
    }

    void update() {
        velocity.mult(0.97f); // Add some drag to the particle
        position.add(velocity);
        lifespan -= 0.03;
    }

    void display() {
        noStroke();
        fill(color, lifespan * 255);
        ellipse(position.x, position.y, size, size);
    }

    boolean isDead() {
        return lifespan <= 0;
    }
}

