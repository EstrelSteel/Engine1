package com.estrelsteel.engine1.menu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.block.Block;
import com.estrelsteel.engine1.entitiy.block.BlockType;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuText;
import com.estrelsteel.engine1.sound.Effects;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class FinshedContractController extends MenuController {
	
	private Engine1 engine;
	
	public FinshedContractController(Menu menu, Engine1 engine) {
		super(menu, "FINISH_CONTROLLER");
		this.engine = engine;
	}

	public void mouseClicked(MouseEvent e) {
		if(!e.isConsumed() && getMenu().isOpen()) {
			Location point = new Location(e.getX(), e.getY());
			for(MenuItem item : getMenu().getMenuItems()) {
				if(item.getClickLocation().collidesWith(point)) {
					if(item instanceof MenuText) {
						if(((MenuText) item).getText().equalsIgnoreCase("Next")) {
							Effects.SELECT.getSound().play();
							engine.world.setCollideMapPosition(0);
							engine.contract.setOpen(true, engine);
							engine.victory.setOpen(false, engine);
							engine.defeat.setOpen(false, engine);
							e.consume();
						}
						else if(((MenuText) item).getText().equalsIgnoreCase("Exit")) {
							Effects.SELECT.getSound().play();
							engine.mainMenu.setOpen(true, engine);
							engine.contract.setOpen(false, engine);
							engine.victory.setOpen(false, engine);
							engine.defeat.setOpen(false, engine);
							engine.over.setOpen(false, engine);
							e.consume();
						}
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
