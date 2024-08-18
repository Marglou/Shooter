package Shooter.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public class Crosshair {
	protected int x = 0;
	protected int y = 0;
	protected int cushion = 10;
	protected int size = 30;
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.drawOval(x, y, size, size);
		g.drawLine(x + (size/2), y, x + (size/2), y + size);
		g.drawLine(x, y + (size/2), x + size, y + (size/2));
	}
	
	public void animate(){
		size = 40;
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				size = 30;
				timer.cancel();
			}
		};
		timer.schedule(task, 100);
		
	}

	// ----------------- Getters et setters ---------------------------

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCushion() {
		return cushion;
	}

	public void setCushion(int cushion) {
		this.cushion = cushion;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}