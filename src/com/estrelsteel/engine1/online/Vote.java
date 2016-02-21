package com.estrelsteel.engine1.online;

public class Vote {
	private int id;
	private int c;
	
	public Vote(int id, int c) {
		this.id = id;
		this.c = c;
	}
	
	public int getID() {
		return id;
	}
	
	public int getCount() {
		return c;
	}
	
	public void addCount() {
		c++;
		return;
	}
	
	public void setID(int id) {
		this.id = id;
		return;
	}
	
	public void setCount(int c) {
		this.c = c;
		return;
	}
}
