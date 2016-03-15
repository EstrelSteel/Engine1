package com.estrelsteel.engine1.menu;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Location;

public class EndController extends MenuController {
	
	private Engine1 engine;
	private MenuItem item;

	public EndController(Menu menu, String name, Engine1 engine) {
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
						engine.canWin = false;
						engine.world = Maps.LOBBY.getMap().load();
						engine.world = engine.addBasics(engine.world);
						engine.player.getLocation().setX(0);
						engine.player.getLocation().setY(0);
						engine.player.moveDown(engine.world);
						engine.player.setHealth(engine.player.getMaxHealth());
						engine.player.setActiveAnimationNum(0);
						engine.victory.setOpen(false, engine);
						engine.defeat.setOpen(false, engine);
						engine.lobbyMainHud.setOpen(true, engine);
						engine.player.setWalkspeed(5);
						engine.player.setSlowWalkspeed(1);
						engine.world.setMainCamera(engine.playerCamera);
						Engine1.client.sendData((Packets.PLAYER_DATA.getID() + Packets.SPLIT.getID() + engine.player.getName() + Packets.SPLIT.getID() + EntityType.WALPOLE.getID() + Packets.SPLIT.getID() +
								Team.BLUE.getID() + Packets.SPLIT.getID() + EntityType.SWORD_DIAMOND.getID() + Packets.SPLIT.getID() + EntityType.SLASH.getID()).getBytes());
					}
					else if(item.getType() == MenuItemType.QUIT_TEXT) {
						engine.victory.setOpen(false, engine);
						engine.defeat.setOpen(false, engine);
						engine.mainMenu.setOpen(true, engine);
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
