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

public class ContractController extends MenuController {
	
	private Engine1 engine;
	
	public ContractController(Menu menu, Engine1 engine) {
		super(menu, "CONTRACT_CONTROLLER");
		this.engine = engine;
	}

	public void mouseClicked(MouseEvent e) {
		if(!e.isConsumed() && getMenu().isOpen()) {
			Location point = new Location(e.getX(), e.getY());
			for(MenuItem item : getMenu().getMenuItems()) {
				if(item.getClickLocation().collidesWith(point)) {
					if(item instanceof MenuText) {
						if(((MenuText) item).getText().equalsIgnoreCase("Begin")) {
							Effects.SELECT.getSound().play();
							engine.activeBlock = new Block(BlockType.getRandomBlockType(), new Location(98, 500, 64, 64, 0));
							engine.claw.setLocation(new Location(98, 468, 128, 128));
							engine.claw_arm.setLocation(new Location(226, 468, 300, 128));
							engine.world = new World();
							engine.world = engine.contracts.get(engine.contractPos).getMap().getMap().load1(engine.world);
							engine.world.setEntities(new ArrayList<Entity>());
							engine.world = engine.addBasics(engine.world);
							engine.score = 0.0;
							((MenuText) engine.hud.getMenuItems().get(1)).setText("" + engine.score);
							engine.hud.setOpen(true, engine);
							engine.hudContract.setOpen(true, engine);
							engine.startTime = System.currentTimeMillis();
							engine.world.setCollideMapPosition(0);
							engine.mainMenu.setOpen(false, engine);
							engine.contract.setOpen(false, engine);
							e.consume();
						}
						else if(((MenuText) item).getText().equalsIgnoreCase("Exit")) {
							Effects.SELECT.getSound().play();
							engine.mainMenu.setOpen(true, engine);
							engine.contract.setOpen(false, engine);
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
