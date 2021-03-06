package com.estrelsteel.engine1.handler;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;

import com.estrelsteel.engine1.Engine1;

public class CoreHandler extends WindowAdapter implements WindowStateListener, WindowFocusListener, FocusListener, ComponentListener {

	Engine1 engine;
	
	public CoreHandler(Engine1 engine) {
		this.engine = engine;
	}

	public void windowClosing(WindowEvent e) {
		try {
			engine.stop();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

	public void focusGained(FocusEvent e) {
		//System.out.println("focused");
		engine.focused = 0;
	}

	public void focusLost(FocusEvent e) {
		//System.out.println("un-focused");
		engine.focused = 1;
	}

	public void componentHidden(ComponentEvent e) {
		
	}

	public void componentMoved(ComponentEvent e) {
		
	}

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

	public void componentShown(ComponentEvent e) {
		
	}
}
