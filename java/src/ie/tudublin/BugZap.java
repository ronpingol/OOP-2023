package ie.tudublin;

import processing.core.PApplet;

public class BugZap extends PApplet {
	float playerX;
	float playerY;
	float playerWidth;
	float playerHeight;
	float halfWidth;
	float halfHeight;
	float bugX;
	float bugY;
	float bugWidth;
	float bugHeight;

	public BugZap() {
		// playerX = 0;
		// playerY = 0;
		playerWidth = 50;
		playerHeight = 30;
		halfWidth = playerWidth * 0.5f;
		halfHeight = playerHeight * 0.5f;

		bugX = 0;
		bugY = 0;
		bugWidth = 0;
		bugHeight = 0;
	}

	public void settings() {
		size(500, 500);

	}

	public void setup() {
		colorMode(HSB);
		background(0);

		playerX = width / 2;
		playerY = height - (playerHeight * 2);

		/*
		 * x1 = random(0, width);
		 * x2 = random(0, width);
		 * y1 = random(0, height);
		 * y2 = random(0, height);
		 * 
		 * float range = 5;
		 * 
		 * x1dir = random(-range, range);
		 * x2dir = random(-range, range);
		 * y1dir = random(-range, range);
		 * y2dir = random(-range, range);
		 * 
		 * smooth();
		 */

	}

	/*
	 * float x1, y1, x2, y2;
	 * float x1dir, x2dir, y1dir, y2dir;
	 * float c = 0;
	 */

	public void draw() {
		/*
		 * strokeWeight(2);
		 * stroke(c, 255, 255);
		 * c = (c + 1f) % 255;
		 * line(x1, y1, x2, y2);
		 * 
		 * x1 += x1dir;
		 * x2 += x2dir;
		 * y1 += y1dir;
		 * y2 += y2dir;
		 * 
		 * if (x1 < 0 || x1 > width) {
		 * x1dir = -x1dir;
		 * }
		 * if (y1 < 0 || y1 > height) {
		 * y1dir = -y1dir;
		 * }
		 * 
		 * if (x2 < 0 || x2 > width) {
		 * x2dir = -x2dir;
		 * }
		 * if (y2 < 0 || y2 > height) {
		 * y2dir = -y2dir;
		 * 
		 * 
		 * }
		 */

		//drawPlayer(50, 100);

		if ((frameCount % 60) == 0) {
			// Do something
			drawPlayer(50, 100);
		}

	}

	public void drawPlayer(float x, float y) {
		stroke(255);
		fill(255);

		line(playerX - halfWidth, playerY + halfHeight, playerX + halfWidth, playerY + halfHeight);

		line(playerX - halfWidth, playerY + halfHeight, playerX - halfWidth, playerY);

		line(playerX - halfWidth, playerY, playerX - (playerWidth * 0.25f), playerY - halfHeight);

		line(playerX - (playerWidth * 0.25f), playerY - halfHeight, playerX + (playerWidth * 0.25f),
				playerY - halfHeight);

		line(playerX + (playerWidth * 0.25f), playerY - halfHeight, playerX + halfWidth, playerY);

		line(playerX - halfWidth, playerY, playerX + halfWidth, playerY + halfHeight);
	}

	public void keyPressed() {
		if (keyCode == LEFT && playerX > halfWidth) {
			System.out.println("Left arrow pressed");

			playerX--;
		}
		if (keyCode == RIGHT && playerX < width - halfWidth) {
			System.out.println("Right arrow pressed");

			playerX++;
		}
		if (key == ' ') {
			System.out.println("SPACE key pressed");
		}
	}
}
