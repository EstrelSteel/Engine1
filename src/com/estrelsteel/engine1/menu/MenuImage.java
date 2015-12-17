package com.estrelsteel.engine1.menu;

import java.awt.image.BufferedImage;

import com.estrelsteel.engine1.world.Image;
import com.estrelsteel.engine1.world.Location;

public class MenuImage extends Image {
	private Location loc;
	private BufferedImage mImg;
	
	public MenuImage(String src) {
		super(src);
		this.loc = new Location(0,0,16,16);
	}
	
	public MenuImage(String src, boolean scan) {
		super(src, scan);
		this.loc = new Location(0,0,16,16);
	}
	
	public MenuImage(String src, Location loc) {
		super(src);
		this.loc = loc;
	}
	
	public MenuImage(String src, Location loc, boolean scan) {
		super(src, scan);
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public BufferedImage getMenuImage() {
		if(getImage() == null) {
			System.out.println("" + getSRC());
			System.out.println(loc.getX() + loc.getY() + loc.getWidth() + loc.getHeight());
		}
		if(!isImageLoaded()) {
			loadImage();
		}
		if(mImg == null) {
			mImg = getImage().getSubimage(loc.getX(), loc.getY(), loc.getWidth(), loc.getHeight());
			System.out.println("updating mImg");
			if(getScan()) {
				boolean keepScan = false;
				for(int x = 0; x < mImg.getWidth(); x++) {
					for(int y = 0; y < mImg.getHeight(); y++) {
						if(mImg.getRGB(x, y) == -1310580 || mImg.getRGB(x, y) == -16777216) {
							mImg.setRGB(x, y, 0);
							keepScan = true;
						}
					}
				}
				if(!keepScan) {
					setScan(false);
				}
			}
		}
		return mImg;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}
}
