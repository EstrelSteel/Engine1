package com.estrelsteel.engine1.contract;

import com.estrelsteel.engine1.world.World;

public class RequireData {
	private World world;
	private double score;
	
	public RequireData(World world, double score) {
		this.world = world;
		this.score = score;
	}
	
	public World getWorld() {
		return world;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
}
