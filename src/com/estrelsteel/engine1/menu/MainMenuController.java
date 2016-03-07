package com.estrelsteel.engine1.menu;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.world.Location;

public class MainMenuController extends LobbyMainController implements MouseMotionListener {
	private Engine1 engine;
	private MenuItem item;
	
	public MainMenuController(Menu menu, String name, Engine1 engine) {
		super(menu, name, engine);
		this.engine = engine;
	}

	public void execute(int id) {
		
	}

	public void execute(int id, double time) {
		
	}

	public void mouseClicked(MouseEvent e) {
		if(getMenu().isOpen() && !e.isConsumed()) {
			Location loc = new Location(e.getX(), e.getY(), 1, 1);
			for(int i = 0; i < getMenu().getMenuItems().size(); i++) {
				item = getMenu().getMenuItems().get(i);
				if(item.getClickLocation().collidesWith(loc)) {
					if(item.getType() == MenuItemType.HOST_BUTTON) {
						engine.StartClientServer(true);
						e.consume();
					}
					else if(item.getType() == MenuItemType.CONNECT_BUTTON) {
						engine.StartClientServer(false);
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

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		if(getMenu().isOpen()) {
			Location loc = new Location(e.getX(), e.getY(), 1, 1);
			for(int i = 0; i < getMenu().getMenuItems().size(); i++) {
				item = getMenu().getMenuItems().get(i);
				if((item.getType() == MenuItemType.BUTTON_NOT_SELECTED)
						|| item.getType() == MenuItemType.BUTTON_SELECTED_1) {
					if(loc.collidesWith(item.getClickLocation())) {
						item.setType(MenuItemType.BUTTON_SELECTED_1);
					}
					else {
						item.setType(MenuItemType.BUTTON_NOT_SELECTED);
					}
				}
			}
		}
	}
}
