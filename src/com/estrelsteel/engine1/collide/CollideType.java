package com.estrelsteel.engine1.collide;

public enum CollideType {
	FULL(1.0), HALF(0.5), NONE(-1);
	
	private double value;
	
	CollideType(double value) {
		this.value = value;
	}
	
	public double getScore() {
		return value;
	}
}
