package ie.tudublin;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.util.ArrayList;

public class StarsMaps extends PApplet {

    public void settings(){
        size(800,800);
    }

    ArrayList<Stars> starsList = new ArrayList<Stars>();

    void loadStars()
    {
        Table table = loadTable("HabHYG15ly.csv", "header");
        for(TableRow r:table.rows())
        {
            Stars s = new Stars(r);
            starsList.add(s);
        }
    }

    public void printStars() {
        for (Stars star : starsList) {
            System.out.println(star.toString());
        }
    }

    float border = 50;

    public void drawGrid() {
        // set the background color
        background(0);
    
        // draw the purple gridlines with labels
        stroke(128, 0, 128);
        textSize(12);
        textAlign(CENTER, CENTER);
        float startX = border;
        float endX = width - border;
        float startY = border;
        float endY = height - border;
        float step = 50;
        for (float i = -5 * step; i <= 5 * step; i += step) {
            // draw the horizontal gridlines
            float y = map(i, -5 * step, 5 * step, startY, endY);
            line(startX, y, endX, y);
    
            // draw the y-axis labels
            fill(128, 0, 128);
            text(i / step + " pc", startX / 2, y);
    
            // draw the vertical gridlines
            float x = map(i, -5 * step, 5 * step, startX, endX);
            line(x, startY, x, endY);
    
            // draw the x-axis labels
            fill(128, 0, 128);
            text(i / step + " pc", x, endY + 25);
        }
    }

    public void draw() {
        // set the background color
        background(0);
    
        // draw the purple gridlines with labels
        drawGrid();
    
        // draw the stars on the canvas
        for (Stars star : s) {
            // convert Xg and Yg values to screen coordinates
            float x = map(star.getxG(), -5 * 50, 5 * 50, border, width - border);
            float y = map(star.getxG(), -5 * 50, 5 * 50, border, height - border);
    
            // draw a yellow cross at the star position
            stroke(255, 255, 0);
            line(x - 5, y, x + 5, y);
            line(x, y - 5, x, y + 5);
    
            // draw a red circle with diameter equal to star's size
            noFill();
            stroke(255, 0, 0);
            float size = star.getSize();
            ellipse(x, y, size, size);
    
            // print the star name beside the star
            fill(255);
            textAlign(LEFT, CENTER);
            text(star.getDisplayName(), x + 10, y);
        }
    }
    
    


}