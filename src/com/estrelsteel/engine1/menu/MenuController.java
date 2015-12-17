package com.estrelsteel.engine1.menu;

import java.awt.Graphics;

import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.world.Image;
import com.estrelsteel.engine1.world.Location;

public abstract class MenuController extends Handler {
	
	private Menu menu;
	private double tempX;
	private double tempY;
	private double alpha = 0.0;
	
	public MenuController(Menu menu, String name) {
		super(name);
		this.menu = menu;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public double getAlpha() {
		return alpha;
	}
	
	public abstract void execute(int id);
	
	public abstract void execute(int id, double time);
	
	public Menu glide(Location glideStart, Location loc, double time) {
		if(!new Location(menu.getLocation().getX() * -1, menu.getLocation().getY() * -1, 0, 0).equals(loc)) {
			double frameMoveX = loc.getRawXDistance(glideStart) / time;
			double frameMoveY = loc.getRawYDistance(glideStart) / time;
			tempX = tempX + frameMoveX;
			tempY = tempY + frameMoveY;
			menu.getLocation().setX((int) tempX);
			menu.getLocation().setY((int) tempY);
		}
		return menu;
	}
	
	public Graphics fadeOut(Graphics ctx, double time, Image img) {
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
		ctx.drawImage(img.getImage(), menu.getLocation().getX(), menu.getLocation().getY(), menu.getLocation().getWidth(), menu.getLocation().getHeight(), null);
		return ctx;
	}
	
	public Graphics fadeIn(Graphics ctx, double time, Image img) {
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
		ctx.drawImage(img.getImage(), menu.getLocation().getX(), menu.getLocation().getY(), menu.getLocation().getWidth(), menu.getLocation().getHeight(), null);
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
	
	public void setMenu(Menu menu) {
		this.menu = menu;
		return;
	}
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
		return;
	}
}