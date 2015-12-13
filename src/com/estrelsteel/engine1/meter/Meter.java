package com.estrelsteel.engine1.meter;

import java.awt.Color;
import java.awt.Graphics2D;

import com.estrelsteel.engine1.world.Location;

public abstract class Meter {
	private double max;
	private double min;
	private double value;
	private Color backColour;
	private Color frontColour;
	private boolean outBounds = true;
	private Location loc;
	
	public Meter(Location loc, double max, double min, double value, boolean outBounds) {
		if(min > max) {
			System.out.println("ERROR >>> >>> >>> >>> >>>");
			System.out.println("MINIMUM VALUE IS GREATER THAN MAXIMUM");
			System.out.println("SWITCHING MINIMUM AND MAXIMUM");
			double oldMin = min;
			min = max;
			max = oldMin;
		}
		this.max = max;
		this.min = min;
		this.value = value;
		this.backColour = Color.RED;
		this.frontColour = Color.GREEN;
		this.outBounds = outBounds;
		this.loc = loc;
	}
	
	public double getMax() {
		return max;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getValue() {
		return value;
	}
	
	public Color getBackColour() {
		return backColour;
	}
	
	public Color getFrontColour() {
		return frontColour;
	}
	
	public boolean getOutBounds() {
		return outBounds;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public double getPercentage() {
		if((value > max || value < min) && outBounds) {
			System.out.println("ERROR >>> >>> >>> >>> >>>");
			System.out.println("VALUE OUT OF BOUNDS");
			System.out.println("CONTINUING TO CALCULATE");
		}
		else if((value > max || value < min) && !outBounds) {
			System.out.println("ERROR >>> >>> >>> >>> >>>");
			System.out.println("VALUE OUT OF BOUNDS");
			System.out.println("STOPPING CALCULATION");
			return -999.999;
		}
		double calVal = value - min;
		double calMax = max - min;
		double percentage = calVal / calMax;
		return percentage;
	}
	
	public abstract Location getFillArea();
	
	public Graphics2D drawMeter(Graphics2D ctx) {
		Location loc = getFillArea();
		ctx.setColor(backColour);
		ctx.fillRect(this.loc.getX(), this.loc.getY(), this.loc.getWidth(), this.loc.getHeight());
		ctx.setColor(frontColour);
		ctx.fillRect(loc.getX(), loc.getY(), loc.getWidth(), loc.getHeight());
		return ctx;
	}
	
	public Graphics2D drawMeterWithoutBack(Graphics2D ctx) {
		Location loc = getFillArea();
		ctx.setColor(frontColour);
		ctx.fillRect(loc.getX(), loc.getY(), loc.getWidth(), loc.getHeight());
		return ctx;
	}
	
	public void setMax(double max) {
		this.max = max;
		return;
	}
	
	public void setMin(double min) {
		this.min = min;
		return;
	}
	
	public void setValue(double value) {
		this.value = value;
		return;
	}
	
	public void setBackColour(Color backColour) {
		this.backColour = backColour;
		return;
	}
	
	public void setFrontColour(Color frontColour) {
		this.frontColour = frontColour;
		return;
	}
	
	public void setOutBounds(boolean outBounds) {
		this.outBounds = outBounds;
		return;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}
}
