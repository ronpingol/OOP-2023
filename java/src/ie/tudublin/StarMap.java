package ie.tudublin;

import processing.core.PApplet;
import java.util.ArrayList;
import processing.data.Table;
import processing.data.TableRow;


public class StarMap extends PApplet
{

	ArrayList<Star> star = new ArrayList<Star>();

	public void settings()
	{
		size(500, 500);
	}

	void loadStars()
	{
		Table table = loadTable("\\OOP-2023\\java\\data\\HabHYG15ly.csv", "header");
		for(TableRow r:table.rows())
		{
			Star s = new Star(r);
			star.add(s);
		}
	}

	public void setup() {
		colorMode(HSB);
		background(0);
		smooth();	
	}

	public void draw()
	{	
		strokeWeight(2);		

		drawGrid();
	}

	public void drawGrid(){
		stroke(255);

		float border = 50.f;

		int count = 10;

		float gap = (width - (border * 2.0f)) / (float) count;
		for (int i = -5;i <= 5; i++){
			float x = border + (gap * (i + 5));
			line(x, border, x, height - border);
			line(border, x, width - border, x);
		}
	}


	public void printStars(){
		
	}

	class Star{
		boolean hab;
		String displayName;
		float distance;
		float xG;
		float yG;
		float zG;
		float absMag;
		

		public Star(TableRow tr)
		{
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
		
		public Star(boolean hab, String displayName, float distance, float xG, float yG, float zG, float absMag) {
			this.hab = hab;
			this.displayName = displayName;
			this.distance = distance;
			this.xG = xG;
			this.yG = yG;
			this.zG = zG;
			this.absMag = absMag;
		}

		public String toString(){
			return hab + "\t\t" + ; 
		}
	}


}
