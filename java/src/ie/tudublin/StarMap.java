package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;


// StarMap inherits methods/properties of PApplet class
public class StarMap extends PApplet {

	// declaring global ArrayList of Star objects.
    ArrayList<Star> stars = new ArrayList<Star>();
    
	// Used to set margins
    public float border;

	// Drawing our grid.
    void drawGrid()
    {
        stroke(0, 245, 0);
        textAlign(CENTER, CENTER);
        textSize(20);
        for(int i = -5; i <=5; i ++)
        {
			// Used to position the lines and text.
            float x = map(i, -5, 5, border, width - border);

			// Draws vertical line.
            line(x, border, x, height - border);
			// Draws horizontal line.
            line(border, x, width - border, x);

            fill(255);

			// Draws the numeric labels for the grid.
            text(i, x, border * 0.5f);
            text(i, border * 0.5f, x);
        }
    }

	// Prints the details of the star objects.
    void printStars()
    {	
		// This is an enchanced loop.
        for(Star s:stars)
        {
			// Uses the toString() in our Star class.
            System.out.println(s);
        }
    }

	// Loading our data from the .csv file.
    void loadStars()
    {
        Table table = loadTable("HabHYG15ly.csv", "header");
		// This is an enchanced loop.
		// for each row we create a new star object, we pass TableRow to the Star constructor.
        for(TableRow r:table.rows())
        {
            Star s = new Star(r);

			// Add new object to the ArrayList.
            stars.add(s);
        }
    }

	// Creating our display window.
    public void settings() {
        size(800, 800);
    }

	// references to star objects.
    Star firstStar = null;
    Star secondStar = null;



    public void mouseClicked()
    {
		// enhanced loop, looping through each star object.
        for(Star s:stars)
        {
			// maps the coordinates of a star object.
            float x = map(s.getxG(), -5, 5, border, width - border);
            float y = map(s.getyG(), -5, 5, border, height - border);

			// Checks if the distance of the mouse click and the star object is < than 20 pixels, if yes it means a star object has been clicked.
            if (dist(mouseX, mouseY, x, y) < 20)
            {
				// If no satr object is picked.
                if (firstStar == null)
                {
                    firstStar = s;
                    break;
                }
				// If one star object has been picked but not two.
                else if (secondStar == null)
                {
                    secondStar = s;
                    break;
                } 
				// if both have been selected.
                else
                {
                    firstStar = s;
                    secondStar = null;
                    break;
                }
            }
        }
    }

	// Calling some of our methods.
    public void setup() {
        colorMode(RGB);
        loadStars();
        printStars();

		// Used to create a margin around the edges of grid.
        border = width * 0.1f;
    }

    public void drawStars()
    {
        for(Star s:stars)
        {
			// call render method on star object, and pass 'this' reference. referes to current instance.
            s.render(this);
        }
    }

    public void draw() 
    {
        background(0);
        drawGrid();
        drawStars();

        if (firstStar != null)
        {
			// if a star is selected, it gets the x and y coordinates.
            float x = map(firstStar.getxG(), -5, 5, border, width - border);
            float y = map(firstStar.getyG(), -5, 5, border, height - border);

			
            if (secondStar != null)
            {
				// If a second star has been selected, calculates the x and y coordinates of the second star and draws a line between the two stars.
                float x2 = map(secondStar.getxG(), -5, 5, border, width - border);
                float y2 = map(secondStar.getyG(), -5, 5, border, height - border);

                stroke(255, 255, 0);
                line(x, y, x2, y2);

                float dist = dist(firstStar.getxG(), firstStar.getyG(), firstStar.getzG(), secondStar.getxG(), secondStar.getyG(), secondStar.getzG());

				// Displays distance between the two stars.
                fill(255);
                textAlign(CENTER, CENTER);
                text("Distance between: " + firstStar.getDisplayName() + " and " + secondStar.getDisplayName() + " is " + dist + " parsecs", width / 2, height - (border * 0.5f));
            }
            else
            {
				// if only one star has been selected it draws line between the star and current mouse position.
                stroke(255, 255, 0);
                line(x, y, mouseX, mouseY);
            }
        }
    }
}