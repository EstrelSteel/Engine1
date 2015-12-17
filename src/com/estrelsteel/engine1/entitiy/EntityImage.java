package com.estrelsteel.engine1.entitiy;

import java.awt.image.BufferedImage;

import com.estrelsteel.engine1.world.Image;
import com.estrelsteel.engine1.world.Location;

public class EntityImage extends Image {
	private Location loc;
	private BufferedImage eImg;
	
	public EntityImage(String src) {
		super(src);
		this.loc = new Location(0,0,16,16);
	}
	
	public EntityImage(String src, boolean scan) {
		super(src, scan);
		this.loc = new Location(0,0,16,16);
	}
	
	public EntityImage(String src, Location loc) {
		super(src);
		this.loc = loc;
	}
	
	public EntityImage(String src, Location loc, boolean scan) {
		super(src, scan);
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public BufferedImage getEntity() {
		if(getImage() == null) {
			System.out.println("" + getSRC());
			System.out.println(loc.getX() + loc.getY() + loc.getWidth() + loc.getHeight());
		}
		if(!isImageLoaded()) {
			loadImage();
		}
		if(eImg == null) {
			eImg = getImage().getSubimage(loc.getX(), loc.getY(), loc.getWidth(), loc.getHeight());
			System.out.println("updating eImg");
			if(getScan()) {
				boolean keepScan = false;
				for(int x = 0; x < eImg.getWidth(); x++) {
					for(int y = 0; y < eImg.getHeight(); y++) {
						if(eImg.getRGB(x, y) == -1310580 || eImg.getRGB(x, y) == -16777216) {
							eImg.setRGB(x, y, 0);
							keepScan = true;
						}
					}
				}
				if(!keepScan) {
					setScan(false);
				}
			}
		}
		return eImg;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}
}
