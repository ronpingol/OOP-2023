package ie.tudublin;

import java.util.ArrayList;


import processing.core.PApplet;
import processing.data.*;

public class cafe extends PApplet
{	
	public float halfX;
	public float Border = 40f;
	public float Border2 = 20f;
	
	public float totalMoney;
	
	

	ArrayList<Product> products = new ArrayList<Product>();
	ArrayList<Product> bill = new ArrayList<Product>();
	Product billTotal = new Product("Total:", 0);
	
	public void settings()
	{
		size(800, 600);

		loadProducts();
		printProducts();
		halfX = width/2f;
		totalMoney = 0;
		

	}

	public void loadProducts()
	{
		Table table = loadTable("cafe.csv", "header");

		for(TableRow row:table.rows())
		{
			Product myProduct = new Product(row);
			products.add(myProduct);
		}

		
	}

	public void printProducts()
	{
		for(Product t:products)
		{
			println(t);
		}
	}



	public void mousePressed()
	{
		for(int i = 1; i <= products.size(); i++){

			float y1 = map(i,1,products.size() + 1, Border, height - Border );
		
			if(bill.size() != 23){ // limit before values go over the table

			if (mouseX >= Border && mouseX <= halfX - Border * 3 && mouseY >= y1 && mouseY <= y1 + ((height - Border)/7)-10 )
			{
				
				bill.add(products.get(i-1));
				bill.remove(billTotal);
				totalMoney = totalMoney + products.get(i-1).getPrice();
				billTotal.setPrice(totalMoney);
				bill.add(billTotal);
				return;
			}
			
			}
		

		}
		
	}
	
	public void drawBill(){
		stroke(0);
		fill(255);
		rect(halfX + Border2, Border, halfX - Border2 * 2, height - Border * 2 );
		
		fill(0);
		textAlign(CENTER,TOP);
		text("Your Bill", halfX + halfX/2, Border + 10);
		
		
		for(int i = 1;  i <= bill.size() ; i++){

			
			fill(0);
			textSize(15);
			textAlign(LEFT);
			text(bill.get(i-1).getName(), halfX + Border, Border* 2 + i*20);

			fill(0);
			textSize(15);

			textAlign(RIGHT);
			String myPrice = nf(bill.get(i-1).getPrice(),1,2);
			text(myPrice, width - Border * 2 , Border* 2 + i*20  );


		}
		
	
	}

	
	void displayProducts()
	{
		for(int i = 1; i <= products.size(); i++){

			fill(255);
			float y = map(i,1,products.size() + 1, Border, height - Border );
			rect(Border, y , halfX - Border * 3, ((height - Border)/7)-10);

			fill(0);
			textSize(15);
			textAlign(LEFT);

			text(products.get(i-1).getName(), Border + 5, y + ((height - Border)/7-10)/2  );

			textAlign(RIGHT);
			text(products.get(i-1).getPrice(), halfX - Border * 2 - 20, y + ((height - Border)/7-10)/2  );
		}
		

	
		
		

	}

	public void setup() 
	{
		colorMode(HSB);
	}
	
	public void draw()
	{			
		background(200);

		textSize(30);
		fill(0);
		textAlign(CENTER, CENTER);
		text("Cafe Rubis Till System", halfX, 15 );
		
		

		line(halfX, Border, halfX, height - Border);

		displayProducts();
		drawBill();
	}
}