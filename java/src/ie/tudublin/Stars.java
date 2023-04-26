package ie.tudublin;

import processing.data.TableRow;

public class Stars {

    private boolean habitable;
    private String displayName;
    private float distance;
    private float xG;
    private float yG;
    private float zG;
    private float absMag;

    public boolean isHabitable() {
        return habitable;
    }



    public void setHabitable(boolean habitable) {
        this.habitable = habitable;
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



    @Override
    public String toString() {
        return "Star [absMag=" + absMag + ", displayName=" + displayName + ", distance=" + distance + ", hab=" + hab
                + ", xG=" + xG + ", yG=" + yG + ", zG=" + zG + "]";
    }



    public Stars(boolean habitable, String displayName, float distance, float xGalactic, float yGalactic, float zGalactic, float absMag) {
        this.habitable = habitable;
        this.displayName = displayName;
        this.distance = distance;
        this.xG = xGalactic;
        this.yG = yGalactic;
        this.zG = zGalactic;
        this.absMag = absMag;
    }

    public Stars(TableRow row) {;
        this.habitable = row.getInt("Hab?") == 1;
        this.displayName = row.getString("DisplayName");
        this.distance = row.getFloat("Distance");
        this.xG = row.getFloat("Xg");
        this.yG = row.getFloat("Yg");
        this.zG = row.getFloat("Zg");
        this.absMag = row.getFloat("AbsMag");
    }



    public float getSize() {
        return 0;
    }

    
}