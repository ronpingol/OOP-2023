package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class UI extends PApplet
{	
	public ArrayList<Color> colors = new ArrayList<Color>();
	ArrayList<Resistor> resistors = new ArrayList<Resistor>();

	public Color findColor(int num)
	{
		for(Color c:colors)
		{
			if (c.num == num)
			{
				return c;
			}			
		}
		return null;
	}

	private void loadCodes()
	{
		Table t = loadTable("codes.csv", "header");
		for(TableRow row:t.rows())
		{
			Color c = new Color(row);
			colors.add(c);
		}
	}

	private void loadResistors()
	{
		Table t = loadTable("resistors.csv");
		for(TableRow row:t.rows())
		{			
			resistors.add(new Resistor(this,row.getInt(0)));
		}
	}

	private void printResistors()
	{
		for(Resistor r:resistors)
		{
			int i = r.value;
			int hundreds = (i / 100);
			int tens = (i - (hundreds * 100)) / 10;
			int ones = i - ((hundreds * 100)  + (tens * 10));
			print(hundreds + ",");
			print(tens + ",");
			println(ones);			
		}
	}

	public void settings()
	{
		size(500, 800);
		loadCodes();
		loadResistors();
		printResistors();
	}

	public void setup() 
	{
	}
	
	int resistorId = 0;
	boolean lastPressed = false;
	public void draw()
	{			
		background(200);
		stroke(255);

		for(int i = 0 ; i < resistors.size() ; i ++)
		{
			float y = map(i, 0, resistors.size(), 100, height - 100);
			resistors.get(i).render(width / 2, y);
		}
		
	}
}