package com.estrelsteel.engine1.menu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.block.BlockType;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.sound.Effects;
import com.estrelsteel.engine1.world.Location;

public class HudController extends MenuController {
	
	private Engine1 engine;
	
	public HudController(Menu menu, Engine1 engine) {
		super(menu, "HUD_CONTROLLER");
		this.engine = engine;
	}

	public void mouseClicked(MouseEvent e) {
		if(!e.isConsumed() && getMenu().isOpen()) {
			Location point = new Location(e.getX(), e.getY());
			for(MenuItem item : getMenu().getMenuItems()) {
				if(item.getClickLocation().collidesWith(point)) {
					if(item.getType() == MenuItemType.PAINT) {
						Effects.PAINT.getSound().play();
						engine.activeBlock.setType(BlockType.getRandomBlockType().getType());
						e.consume();
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void execute(int id) {
		
	}

	public void execute(int id, double time) {
		
	}
	
}
