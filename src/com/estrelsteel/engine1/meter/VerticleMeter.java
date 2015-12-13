package com.estrelsteel.engine1.meter;

import com.estrelsteel.engine1.world.Location;

public class VerticleMeter extends Meter {
	private boolean downUp = true;
	
	public VerticleMeter(Location loc, double max, double value, boolean outBounds) {
		super(loc, max, 0, value, outBounds);
		this.downUp = true;
	}
	
	public VerticleMeter(Location loc, boolean downUp, double max, double min, double value, boolean outBounds) {
		super(loc, max, min, value, outBounds);
		this.downUp = downUp;
	}
	
	public boolean getDownUp() {
		return downUp;
	}
	
	public Location getFillArea() {
		Location loc;
		if(downUp) {
			double w = getLocation().getWidth();
			double h = getLocation().getHeight();
			int x = getLocation().getX();
			int y = (int) (getLocation().getY() + h);
			double percentage = getPercentage();
			double newHeight = h * percentage;
			loc = new Location(x, (int) (y - newHeight), (int) w, (int) newHeight);
		}
		else {
			int x = getLocation().getX();
			int y = getLocation().getY();
			double w = getLocation().getWidth();
			double h = getLocation().getHeight();
			double percentage = getPercentage();
			double newHeight = h * percentage;
			loc = new Location(x, y, (int) w, (int) newHeight);
		}
		return loc;
	}
	
	public void setDownUp(boolean downUp) {
		this.downUp = downUp;
		return;
	}
	
}
