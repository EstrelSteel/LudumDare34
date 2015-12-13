package com.estrelsteel.engine1.meter;

import com.estrelsteel.engine1.world.Location;

public class HorizontalMeter extends Meter {
	private boolean leftRight = true;
	
	public HorizontalMeter(Location loc, double max, double value, boolean outBounds) {
		super(loc, max, 0, value, outBounds);
		this.leftRight = true;
	}
	
	public HorizontalMeter(Location loc, boolean leftRight, double max, double min, double value, boolean outBounds) {
		super(loc, max, min, value, outBounds);
		this.leftRight = leftRight;
	}
	
	public boolean getLeftRight() {
		return leftRight;
	}
	
	public Location getFillArea() {
		Location loc;
		if(leftRight) {
			int x = getLocation().getX();
			int y = getLocation().getY();
			double w = getLocation().getWidth();
			double h = getLocation().getHeight();
			double percentage = getPercentage();
			double newWidth = w * percentage;
			loc = new Location(x, y, (int) newWidth, (int) h);
		}
		else {
			double w = getLocation().getWidth();
			double h = getLocation().getHeight();
			int x = (int) (getLocation().getX() + w);
			int y = getLocation().getY();
			double percentage = getPercentage();
			double newWidth = w * percentage;
			loc = new Location((int) (x - newWidth), y, (int) newWidth, (int) h);
		}
		return loc;
	}
	
	public void setLeftRight(boolean leftRight) {
		this.leftRight = leftRight;
		return;
	}
	
}
