package com.estrelsteel.engine1.tile;

import java.awt.image.BufferedImage;

import com.estrelsteel.engine1.world.Image;
import com.estrelsteel.engine1.world.Location;

public class TileImage extends Image {
	private Location loc;
	private BufferedImage tImg;
	
	public TileImage(String src) {
		super(src);
		this.loc = new Location(0,0,16,16);
	}
	
	public TileImage(String src, boolean scan) {
		super(src, scan);
		this.loc = new Location(0,0,16,16);
	}
	
	public TileImage(String src, Location loc) {
		super(src);
		this.loc = loc;
	}
	
	public TileImage(String src, Location loc, boolean scan) {
		super(src, scan);
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public BufferedImage getTile() {
		if(getImage() == null) {
			System.out.println("" + getSRC());
			System.out.println(loc.getX() + loc.getY() + loc.getWidth() + loc.getHeight());
		}
		if(!isImageLoaded()) {
			loadImage();
		}
		if(tImg == null) {
			tImg = getImage().getSubimage(loc.getX(), loc.getY(), loc.getWidth(), loc.getHeight());
			if(getScan()) {
				boolean keepScan = false;
				for(int x = 0; x < tImg.getWidth(); x++) {
					for(int y = 0; y < tImg.getHeight(); y++) {
						if(tImg.getRGB(x, y) == -1310580 || tImg.getRGB(x, y) == -16777216) {
							tImg.setRGB(x, y, 0);
							keepScan = true;
						}
					}
				}
				if(!keepScan) {
					setScan(false);
				}
			}
		}
		return tImg;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}
}
