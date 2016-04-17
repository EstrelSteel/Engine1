package com.estrelsteel.engine1.menu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.menu.MenuText;
import com.estrelsteel.engine1.sound.Effects;
import com.estrelsteel.engine1.world.Location;

public class MainMenuController extends MenuController {
	
	private Engine1 engine;
	
	public MainMenuController(Menu menu, Engine1 engine) {
		super(menu, "MAIN_MENU_CONTROLLER");
		this.engine = engine;
	}

	public void mouseClicked(MouseEvent e) {
		if(!e.isConsumed() && getMenu().isOpen()) {
			Location point = new Location(e.getX(), e.getY());
			for(MenuItem item : getMenu().getMenuItems()) {
				if(item.getClickLocation().collidesWith(point)) {
					if(item.getType() == MenuItemType.START) {
						Effects.SELECT.getSound().play();
						engine.contract.setOpen(true, engine);
						engine.mainMenu.setOpen(false, engine);
						
						e.consume();
					}
					else if(item.getType() == MenuItemType.QUIT) {
						Effects.SELECT.getSound().play();
						try {
							engine.stop();
						}
						catch (IOException e1) {
							e1.printStackTrace();
						}
						e.consume();
					}
					else if(item instanceof MenuText) {
						if(((MenuText) item).getText().equalsIgnoreCase("Restart Progress")) {
							engine.contractPos = 0;
							engine.updateContractMenu();
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
