package com.estrelsteel.engine1.entitiy;

import java.util.ArrayList;

import com.estrelsteel.engine1.sound.Effects;

public class Animation {
	private ArrayList<EntityImage> images;
	private int frame;
	private int wait;
	private int maxWait;
	private boolean paused;
	private boolean ran;
	private Effects sfx;
	
	public Animation(int maxWait) {
		images = new ArrayList<EntityImage>();
		this.maxWait = maxWait;
		this.ran = false;
	}
	
	public Animation(int maxWait, Effects sfx) {
		images = new ArrayList<EntityImage>();
		this.maxWait = maxWait;
		this.ran = false;
		this.sfx = sfx;
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
	
	public Effects getSoundEffect() {
		return sfx;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public boolean hasRan() {
		return ran;
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
		if(!paused && !ran) {
			wait++;
			if(wait >= maxWait) {
				wait = 0;
				frame++;
			}
			if(frame >= images.size()) {
				frame = 0;
			}
			ran = true;
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
	
	public void setRan(boolean ran) {
		this.ran = ran;
		return;
	}
	
	public void setSoundEffect(Effects sfx) {
		this.sfx = sfx;
	}
}