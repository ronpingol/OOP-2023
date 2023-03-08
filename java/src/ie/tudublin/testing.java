package ie.tudublin;

import processing.core.PApplet;

public class testing extends PApplet
{

    public void settings()
	{
		size(640, 360);
	}

    public void setup()
    {
        colorMode(RGB);
		background(255);
    }

    public void draw()
    {
        strokeWeight(2);
        stroke(0);

        line(100,50,600,250);
    }

}