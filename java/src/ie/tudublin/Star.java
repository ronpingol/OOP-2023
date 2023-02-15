package ie.tudublin;

import processing.core.PApplet;
import processing.data.TableRow;


// This class represents a single star in the grid.
public class Star
{
    // Our properties.
    private boolean hab;
    private String displayName;
    private float distance;
    private float xG;
    private float yG;
    private float zG;
    private float absMag;

    
    // overrides method of the superclass
    @Override
    public String toString() {
        return "Star [absMag=" + absMag + ", displayName=" + displayName + ", distance=" + distance + ", hab=" + hab
                + ", xG=" + xG + ", yG=" + yG + ", zG=" + zG + "]";
    }

    // TableRow object has details of the star.
    public Star(TableRow tr)
    {
        // 'this' is used to call the other constructor.
        this(
            tr.getInt("Hab?") == 1, 
            tr.getString("Display Name"), 
            tr.getFloat("Distance"),
            tr.getFloat("Xg"),
            tr.getFloat("Yg"),
            tr.getFloat("Zg"),
            tr.getFloat("AbsMag")
        );
    }
    
    // properties of the star.
    public Star(boolean hab, String displayName, float distance, float xG, float yG, float zG, float absMag) {
        // 'this' used to assign passed values to the croressponding var of star object.
        this.hab = hab;
        this.displayName = displayName;
        this.distance = distance;
        this.xG = xG;
        this.yG = yG;
        this.zG = zG;
        this.absMag = absMag;
    }

    // encapsulation, these are getter and setter methods.
    public boolean isHab() {
        return hab;
    }
    public void setHab(boolean hab) {
        this.hab = hab;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public float getDistance() {
        return distance;
    }
    public void setDistance(float distance) {
        this.distance = distance;
    }
    public float getxG() {
        return xG;
    }
    public void setxG(float xG) {
        this.xG = xG;
    }
    public float getyG() {
        return yG;
    }
    public void setyG(float yG) {
        this.yG = yG;
    }
    public float getzG() {
        return zG;
    }
    public void setzG(float zG) {
        this.zG = zG;
    }
    public float getAbsMag() {
        return absMag;
    }
    public void setAbsMag(float absMag) {
        this.absMag = absMag;
    }

    // Uses the xG and yG co-ordinates to make a circle around the star
    public void render(StarMap pa)
    {
        float x = PApplet.map(xG, -5, 5, pa.border, pa.width - pa.border);
        float y = PApplet.map(yG, -5, 5, pa.border, pa.height - pa.border);

        
        pa.stroke(255, 90, 255);
        pa.line(x, y -5, x, y + 5);
        pa.line(x-5, y, x + 5, y);
        pa.stroke(255, 239, 0);
        pa.noFill();
        pa.circle(x, y, absMag);// absMag is used as the diameter of the circle we draw around the star.
        pa.fill(255);
        pa.textSize(16);
        pa.textAlign(PApplet.LEFT, PApplet.CENTER);
        pa.text(displayName, x + 20, y);
    }
}