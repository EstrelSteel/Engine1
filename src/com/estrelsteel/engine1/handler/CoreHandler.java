package com.estrelsteel.engine1.handler;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.world.Location;

public class CoreHandler extends WindowAdapter implements WindowStateListener, WindowFocusListener, FocusListener, ComponentListener {

	Engine1 engine;
	
	public CoreHandler(Engine1 engine) {
		this.engine = engine;
	}

	public void windowClosing(WindowEvent e) {
		engine.stop();
    }

	@Override
	public void focusGained(FocusEvent e) {
		//System.out.println("focused");
		engine.focused = 0;
	}

	@Override
	public void focusLost(FocusEvent e) {
		//System.out.println("un-focused");
		engine.focused = 1;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
//		double scaleWidth = (double) e.getComponent().getWidth() / (double) Engine1.startWidth;
//		double scaleHeight = (double) e.getComponent().getHeight() / (double) Engine1.startHeight;
//		scaleWidth = scaleWidth - 1;
//		scaleHeight = scaleHeight - 1;
//		System.out.println(scaleWidth + ", " + scaleHeight);
//		Location tempLoc;
//		for(Tile t : engine.world.getTiles()) {
//			tempLoc = t.getLocation();
//			tempLoc.setX(tempLoc.getX() - (int) ((scaleWidth * tempLoc.getStartWidth() - tempLoc.getStartWidth()) / 2));
//			tempLoc.setY(tempLoc.getY() - (int) ((scaleHeight * tempLoc.getStartHeight() - tempLoc.getStartHeight()) / 2));
//			tempLoc.setWidth((int) (scaleWidth * tempLoc.getStartWidth()));
//			tempLoc.setHeight((int) (scaleHeight * tempLoc.getStartHeight()));
//			t.setLocation(tempLoc);
//		}
//		
//		for(Entity entity : engine.world.getEntities()) {
//			tempLoc = entity.getLocation();
//			tempLoc.setX(tempLoc.getX() - (int) ((scaleWidth * tempLoc.getStartWidth() - tempLoc.getStartWidth()) / 2));
//			tempLoc.setY(tempLoc.getY() - (int) ((scaleHeight * tempLoc.getStartHeight() - tempLoc.getStartHeight()) / 2));
//			tempLoc.setWidth((int) (scaleWidth * tempLoc.getStartWidth()));
//			tempLoc.setHeight((int) (scaleHeight * tempLoc.getStartHeight()));
//			entity.setLocation(tempLoc);
//		}
	}

	@Override
	public void componentShown(ComponentEvent e) {
		
	}
}
