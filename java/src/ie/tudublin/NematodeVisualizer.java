package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class NematodeVisualizer extends PApplet
{

	public void keyPressed()
	{
		if (keyCode == LEFT)
		{
			index = (index == 0) ? nematodes.size() - 1 : index - 1;
		}
		if (keyCode == RIGHT)
		{
			index = (index + 1) % nematodes.size();
		}
		
	}

	int index = 0; 

	void drawArrows()
	{
		float offset = width * 0.02f;

		float sx1 = width * 0.2f;
		float sx2 = width * 0.3f;
		

		line(sx1, height / 2, sx2, height / 2);
		line(sx1, height / 2, sx1 + offset, (height / 2) - offset);
		line(sx1, height / 2, sx1 + offset, (height / 2) + offset);

		float sx3 = width - (width * 0.2f);
		float sx4 = width - (width * 0.3f);

		line(sx3, height / 2, sx4, height / 2);
		line(sx3, height / 2, sx3 - offset, (height / 2) - offset);
		line(sx3, height / 2, sx3 - offset, (height / 2) + offset);
	}

	public void settings()
	{
		size(800, 800);
	}

	public void setup() {
		colorMode(HSB);
		background(0);
		smooth();		
		noCursor();
		
		loadNematodes();
}
	

	public void loadNematodes()
	{
		Table t = loadTable("nematodes.csv", "header");
		for(TableRow r:t.rows())
		{
			Nematode n = new Nematode(this, r);
			nematodes.add(n);
		}
	}

	float x1, y1, x2, y2;
	float x1dir, x2dir, y1dir, y2dir;
	float c = 0;

	ArrayList<Nematode> nematodes = new ArrayList<>();
	
	public void draw()
	{	
		background(0);
		strokeWeight(5);
		int c = (int) map(index, 0, nematodes.size(), 0, 255);


		stroke(c, 255, 255);
		fill(c, 255, 255);

		drawArrows();

		Nematode n = nematodes.get(index);
		n.render(width / 2, height / 2);
	}
}