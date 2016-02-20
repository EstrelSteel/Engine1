package com.estrelsteel.engine1.handler;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuController;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Location;

public class EndHandler extends MenuController {
	
	private Engine1 engine;
	private MenuItem item;

	public EndHandler(Menu menu, String name, Engine1 engine) {
		super(menu, name);
		this.engine = engine;
	}

	public void mouseClicked(MouseEvent e) {
		if(getMenu().isOpen()) {
			Location loc = new Location(e.getX(), e.getY(), 1, 1);
			for(int i = 0; i < getMenu().getMenuItems().size(); i++) {
				item = getMenu().getMenuItems().get(i);
				if(loc.collidesWith(item.getClickLocation())) {
					if(item.getType() == MenuItemType.LOBBY_TEXT) {
						engine.world = engine.lobby.load();
						engine.world = engine.addBasics(engine.world);
						getMenu().setOpen(false);
					}
					else if(item.getType() == MenuItemType.QUIT_TEXT) {
						//TODO: Link to main menu
						getMenu().setOpen(false);
						try {
							
							engine.stop();
						}
						catch (IOException e1) {
							e1.printStackTrace();
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
		// TODO Auto-generated method stub
		
	}

	public void execute(int id, double time) {
		// TODO Auto-generated method stub
		
	}
	
}
