package com.estrelsteel.engine1.camera;

import java.awt.Color;
import java.awt.Graphics;

import com.estrelsteel.engine1.world.Image;
import com.estrelsteel.engine1.world.Location;

public abstract class CameraController {
	
	private Camera camera;
	private double tempX;
	private double tempY;
	private double alpha = 0.0;
	private String name = "TEST";
	
	public CameraController(Camera camera, String name) {
		this.camera = camera;
		this.name = name;
		tempX = camera.getLocation().getX();
		tempY = camera.getLocation().getY();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public double getAlpha() {
		return alpha;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void execute(int id);
	
	public abstract void execute(int id, double time);
	
	public Camera glide(Location glideStart, Location loc, double time) {
		if(!new Location(camera.getLocation().getX() * -1, camera.getLocation().getY() * -1, 0, 0).equals(loc)) {
			double frameMoveX = loc.getRawXDistance(glideStart) / time;
			double frameMoveY = loc.getRawYDistance(glideStart) / time;
			tempX = tempX + frameMoveX;
			tempY = tempY + frameMoveY;
			camera.getLocation().setX((int) tempX);
			camera.getLocation().setY((int) tempY);
		}
		return camera;
	}
	
	public Graphics fadeOut(Graphics ctx, double time, int width, int height, Image img) {
		alpha = alpha + (255.0 / time);
		if(alpha > 255.0) {
			alpha = 255.0;
		}
		if(alpha < 0.0) {
			alpha = 0.0;
		}
		for(int x = 0; x < img.getImage().getWidth(); x++) {
			for(int y = 0; y < img.getImage().getHeight(); y++) {
				img.getImage().setRGB(x, y, (int) alpha);
			}
		}
		ctx.drawImage(img.getImage(), camera.getLocation().getX() - (width / 2), camera.getLocation().getY() - (height / 2), width, height, null);
		return ctx;
	}
	
	public Graphics fadeOut(Graphics ctx, double time, int width, int height) {
		return fadeOut(ctx, time, width, height, Color.BLACK);
	}
	
	public Graphics fadeOut(Graphics ctx, double time, int width, int height, Color colour) {
		alpha = alpha + (255.0 / time);
		if(alpha > 255.0) {
			alpha = 255.0;
		}
		if(alpha < 0.0) {
			alpha = 0.0;
		}
		ctx.setColor(new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), (int) alpha));
		ctx.fillRect(camera.getLocation().getX() - (width / 2), camera.getLocation().getY() - (height / 2), width, height);
		return ctx;
	}
	
	public Graphics fadeIn(Graphics ctx, double time, int width, int height, Image img) {
		alpha = alpha - (255.0 / time);
		if(alpha > 255.0) {
			alpha = 255.0;
		}
		if(alpha < 0.0) {
			alpha = 0.0;
		}
		for(int x = 0; x < img.getImage().getWidth(); x++) {
			for(int y = 0; y < img.getImage().getHeight(); y++) {
				img.getImage().setRGB(x, y, (int) alpha);
			}
		}
		ctx.drawImage(img.getImage(), camera.getLocation().getX() - (width / 2), camera.getLocation().getY() - (height / 2), width, height, null);
		return ctx;
	}
	
	public Graphics fadeIn(Graphics ctx, double time, int width, int height) {
		return fadeIn(ctx, time, width, height, Color.BLACK);
	}
	
	public Graphics fadeIn(Graphics ctx, double time, int width, int height, Color colour) {
		alpha = alpha - (255.0 / time);
		if(alpha > 255.0) {
			alpha = 255.0;
		}
		if(alpha < 0.0) {
			alpha = 0.0;
		}
		ctx.setColor(new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), (int) alpha));
		ctx.fillRect(camera.getLocation().getX() - (width / 2), camera.getLocation().getY() - (height / 2), width, height);
		return ctx;
	}
	
	public boolean hasFaded() {
		if(alpha >= 255.0 || alpha <= 0.0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
		return;
	}
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
		return;
	}
}
