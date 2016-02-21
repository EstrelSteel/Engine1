package com.estrelsteel.engine1.tile.shrine;

import com.estrelsteel.engine1.world.Location;

public class BlankShrine extends Shrine {

	public BlankShrine(int id, Location loc) {
		super(id, Team.HIDDEN, loc);
	}
	
	public String convertToJava() {
		return "new BlankShrine(" + getID() + ", " + getLocation().convertToJava() + ")";
	}
	
	public void addCount(double c) {
		return;
	}
	
	public void subtractCount(double c) {
		return;
	}
	
}
