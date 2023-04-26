package ie.tudublin;

import processing.core.PApplet;

import java.util.ArrayList;
import processing.data.Table;
import processing.data.TableRow;




public class Gnatt extends PApplet
{	
	ArrayList<task> tasks = new ArrayList<task>();
	
	float border = 40;
	float nameGap = 150;
	private int maxMonths = 30;
	private float rowHeight = 40;
	private int whichTask = -1;
	private boolean isEnd = false;
	
	public void settings()
	{
		size(800, 600);
	}

	public void loadTasks()
	{
		Table table = loadTable("tasks.csv", "header");
		for(TableRow r: table.rows()) 
	
		{

			task ts = new task(r);
			tasks.add(ts);
		}
	}

	public void printTasks()
	{
		for(task t: tasks) {
			System.out.println(t);
		}
	}
	
	public void mousePressed()
	{


		for(int i = 0 ; i < tasks.size() ; i ++)
		{
			float y1 = (border + border + rowHeight * i) - 15;
			float y2 = (border + border + rowHeight * i) + 20;

			float x1 = map(tasks.get(i).getStart(), 1, maxMonths, nameGap, width - border);
			float x2 = map(tasks.get(i).getEnd(), 1, maxMonths, nameGap, width - border);
			
			if (mouseX >= x1 && mouseX <= x1 + 20 && mouseY >= y1 && mouseY <= y2)
			{
				whichTask = i;
				isEnd = false;
				return;
			}

			if (mouseX <= x2 && mouseX >= x2 - 20 && mouseY >= y1 && mouseY <= y2)
			{
				whichTask = i;
				isEnd = true;
				return;
			}
		}		
		// default value for whichTask
		whichTask = -1;	
		println("Mouse pressed");	
	}

	public void mouseDragged()
	{

		if (whichTask != -1)
		{
			int month = (int)map(mouseX, nameGap, width - border, 1, maxMonths);
			
			if (month >= 1 && month <= maxMonths)
			{
				task task = tasks.get(whichTask); 
				if (isEnd)
				{
					if (month - task.getStart() > 0)
					{
						task.setEnd(month);
					}
				}
				else
				{
					if (task.getEnd() - month > 0 )
					{
						task.setStart(month);
					}
				}
			}
		}
		println("Mouse dragged");
	}

	public void displayTasks() 
	{
		textAlign(LEFT,CENTER);

		for(int i = 1 ; i <= maxMonths ; i ++)
		{
			float x = map(i, 1, maxMonths, nameGap, width - border);
			line(x, border, x, height - border);
			fill(255);
			text(i, x, border * 0.5f);
		}
		
		for(int i = 0; i < tasks.size(); i++) {

			float y = border + border + rowHeight * i;
			fill(255);
			text(tasks.get(i).getTask(), border, y);

			noStroke();

			fill(map(i, 0, tasks.size(), 0, 255), 255, 255);

			float s = map(tasks.get(i).getStart(), 1, maxMonths, nameGap, width - border);
			float e = map(tasks.get(i).getEnd(), 1, maxMonths, nameGap, width - border);
			rect(s, y - 15, e-s, rowHeight - 5, 5.0f);

		}

		
	}

	
	
	public void setup() 
	{
		colorMode(HSB);
		loadTasks();
		printTasks();

	}
	
	public void draw()
	{			
		background(0);
		stroke(255,0,225);

		displayTasks();

		
		
	
		
	}
}