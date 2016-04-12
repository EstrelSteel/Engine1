package com.estrelsteel.engine1.font;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.world.Image;
import com.estrelsteel.engine1.world.Location;

public class Font {
	private String charBlock0;
	private String charBlock1;
	private String charBlock2;
	private String charBlock3;
	private String charBlock4;
	private String charBlock5;
	private Image image;
	private Location charSize;
	private Location textLoc;
	private Location charSpace;
	private ArrayList<BufferedImage> imgs;
	
	public Font() {
		charBlock0 = "ABCDEFGH";
		charBlock1 = "IJKLMNOP";
		charBlock2 = "QRSTUVWX";
		charBlock3 = "YZ123456";
		charBlock4 = "7890.,!?";
		charBlock5 = ";:-_ ###";
		image = new Image(Engine1.filesPath + "/assets/res/img/font.png");
		charSize = new Location(0, 0, 16, 16);
		textLoc = new Location(0, 0, 256, 32);
		charSpace = new Location(0, 0, 2, 2);
		imgs = new ArrayList<BufferedImage>();
	}
	
	public String getCharBlock0() {
		return charBlock0;
	}
	
	public String getCharBlock1() {
		return charBlock1;
	}
	
	public String getCharBlock2() {
		return charBlock2;
	}
	
	public String getCharBlock3() {
		return charBlock3;
	}
	
	public String getCharBlock4() {
		return charBlock4;
	}
	
	public String getCharBlock5() {
		return charBlock5;
	}
	
	public Location getCharSize() {
		return charSize;
	}
	
	public Location getTextLocation() {
		return textLoc;
	}
	
	public Location getCharSpace() {
		return charSpace;
	}
	
	public Graphics2D renderString(Graphics2D ctx, String str) {
		AffineTransform trans;
		int x = textLoc.getX();
		int y = textLoc.getY();
		int iReduce = 0;
		int xPush = 0;
		int yPush = 112;
		for(int i = 0; i < str.length(); i++) {
			if(imgs.size() != str.length()) {
				for(xPush = 0; xPush < charBlock0.length();  xPush++) {
					if(str.toUpperCase().charAt(i) == charBlock0.charAt(xPush)) {
						yPush = 0;
						break;
					}
					if(str.toUpperCase().charAt(i) == charBlock1.charAt(xPush)) {
						yPush = 16;
						break;
					}
					if(str.toUpperCase().charAt(i) == charBlock2.charAt(xPush)) {
						yPush = 32;
						break;
					}
					if(str.toUpperCase().charAt(i) == charBlock3.charAt(xPush)) {
						yPush = 48;
						break;
					}
					if(str.toUpperCase().charAt(i) == charBlock4.charAt(xPush)) {
						yPush = 64;
						break;
					}
					if(str.toUpperCase().charAt(i) == charBlock5.charAt(xPush)) {
						yPush = 80;
						break;
					}
				}
			}
			trans = new AffineTransform();
			if(x + charSize.getWidth() > textLoc.getX() + textLoc.getWidth()) {
				x = textLoc.getX();
				y = y + (charSize.getHeight()) + (charSpace.getHeight());
				iReduce = i;
			}
			else {
				x = (charSpace.getWidth() * (i - iReduce)) + (charSize.getWidth() * (i- iReduce));
			}
			trans.translate(x, y);
			trans.scale(charSize.getWidth() / 16, charSize.getHeight() / 16);
			
			trans.rotate(Math.toRadians(charSize.getRotation()), charSize.getWidth() / 2, charSize.getHeight() / 2);
			if(!image.isImageLoaded()) {
				image.loadImage();
			}
			if(i < imgs.size() && imgs.get(i) != null) {
				ctx.drawImage(imgs.get(i), trans, null);
			}
			else {
				if(i < imgs.size()) {
					imgs.add(image.getImage().getSubimage(xPush * 16, yPush, 16, 16));
				}
				else {
					imgs.set(i, image.getImage().getSubimage(xPush * 16, yPush, 16, 16));
				}
				ctx.drawImage(imgs.get(i), trans, null);
			}
		}
		return ctx;
	}
	
	public void setCharSize(Location charSize) {
		this.charSize = charSize;
		return;
	}
	
	public void setTextLocation(Location textLoc) {
		this.textLoc = textLoc;
		return;
	}
	
	public void setCharSpace(Location charSpace) {
		this.charSpace = charSpace;
		return;
	}
}
