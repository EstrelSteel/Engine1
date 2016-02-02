package com.estrelsteel.engine1.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.world.World;

public abstract class Handler implements KeyListener, MouseListener {
	
	private String name;
	
	public Handler(String name) {
		this.name = name;
	}
	
	public static void loadHandlers(Engine1 engine, ArrayList<World> worlds) {
		for(World world : worlds) {
			for(Tile t : world.getAllTiles()) {
				if(t.getControls() != null) {
					engine.addKeyListener(t.getControls());
					engine.addMouseListener(t.getControls());
				}
			}
			
			for(Entity e : world.getEntities()) {
				if(e.getControls() != null) {
					engine.addKeyListener(e.getControls());
					engine.addMouseListener(e.getControls());
				}
			}
		}
		engine.addKeyListener(engine.selector);
		
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void mouseClicked(MouseEvent e);

	public abstract void mouseEntered(MouseEvent e);

	public abstract void mouseExited(MouseEvent e);
		
	public abstract void mousePressed(MouseEvent e);
		
	public abstract void mouseReleased(MouseEvent e);

	public abstract void keyPressed(KeyEvent e);
		
	public abstract void keyReleased(KeyEvent e);		

	public abstract void keyTyped(KeyEvent e);

}
