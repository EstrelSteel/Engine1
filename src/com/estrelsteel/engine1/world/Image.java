package com.estrelsteel.engine1.world;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Image {
	private BufferedImage img;
	private String src;
	private boolean scan;
	
	public Image(String src) {
		this.src = src;
		this.scan = true;
	}
	
	public Image(String src, boolean scan) {
		this.src = src;
		this.scan = scan;
	}
	
	public BufferedImage getImage() {
		return this.img;
	}
	
	public String getSRC() {
		return this.src;
	}
	
	public boolean getScan() {
		return scan;
	}
	
	public boolean equals(Image img) {
		if(src.equals(img.getSRC())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void loadImage() {
		InputStream is = getClass().getResourceAsStream(src);
		try {
			this.img = ImageIO.read(is);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public boolean isImageLoaded() {
		if(img != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void unloadImage() {
		this.img = null;
		return;
	}
	
	public void setSRC(String src) {
		this.src = src;
		return;
	}
	
	public void setImage(BufferedImage img) {
		this.img = img;
		return;
	}
	
	public void setScan(boolean scan) {
		this.scan = scan;
		return;
	}
}
