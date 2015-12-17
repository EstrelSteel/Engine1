package com.estrelsteel.engine1.entitiy;

import java.util.ArrayList;

public class Animation {
	private ArrayList<EntityImage> images;
	private int frame;
	private int wait;
	private int maxWait;
	private boolean paused;
	
	public Animation(int maxWait) {
		images = new ArrayList<EntityImage>();
		this.maxWait = maxWait;
	}
	
	public ArrayList<EntityImage> getImages() {
		return images;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public int getWait() {
		return wait;
	}
	
	public int getMaxWait() {
		return maxWait;
	}
	
	public EntityImage getCurrentImage() {
		return images.get(frame);
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public boolean equals(Animation animation) {
		if(images.equals(animation.getImages()) && maxWait == animation.getMaxWait()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public EntityImage run() {
		if(!paused) {
			wait++;
			if(wait >= maxWait) {
				wait = 0;
				frame++;
			}
			if(frame >= images.size()) {
				frame = 0;
			}
		}
		return images.get(frame);
	}
	
	public void addImage(EntityImage image) {
		images.add(image);
		return;
	}
	
	public void setImages(ArrayList<EntityImage> images) {
		this.images = images;
		return;
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
		return; 
	}
	
	public void setWait(int wait) {
		this.wait = wait;
		return;
	}
	
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
		return;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
		return;
	}
}
