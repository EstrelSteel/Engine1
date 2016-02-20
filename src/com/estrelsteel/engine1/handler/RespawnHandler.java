package com.estrelsteel.engine1.handler;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuController;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.tile.shrine.Shrine;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Location;

public class RespawnHandler extends MenuController {
	
	private Engine1 engine;
	private MenuItem item;
	
	public RespawnHandler(Menu menu, String name, Engine1 engine) {
		super(menu, name);
		this.engine = engine;
	}

	public void execute(int id) {
		for(Shrine s : engine.world.getShrines()) {
			if(s.getID() == id) {
				engine.player.setHealth(engine.player.getMaxHealth());
				engine.player.setActiveAnimationNum(0);
				engine.player.getLocation().setX(s.getLocation().getX());
				engine.player.getLocation().setY(s.getLocation().getY());
				engine.player.moveDown(engine.world);
				engine.player.setWalkspeed(5);
				engine.player.setSlowWalkspeed(1);
				engine.world.setMainCamera(engine.playerCamera);
				engine.respawn.setOpen(false);
				engine.overlayRespawn.setOpen(false);
				engine.hud.setOpen(true);
				engine.overlayHud.setOpen(true);
			}
		}
	}

	public void execute(int id, double time) {
		
	}

	public void mouseClicked(MouseEvent e) {
		if(getMenu().isOpen()) {
			Location loc = new Location(e.getX(), e.getY(), 1, 1);
			for(int i = 0; i < getMenu().getMenuItems().size(); i++) {
				item = getMenu().getMenuItems().get(i);
				if(item.getType().getID() >= 10 && item.getType().getID() <= 17 &&loc.collidesWith(item.getClickLocation())) {
					if((item.getType() == MenuItemType.SHRINE_B && engine.player.getTeam() == Team.BLUE)
							|| (item.getType() == MenuItemType.SHRINE_R && engine.player.getTeam() == Team.RED)
							|| (item.getType() == MenuItemType.SHRINE_N && engine.player.getTeam() == Team.NEUTRAL)
							|| (item.getType() == MenuItemType.SHRINE_RAB && engine.player.getTeam() == Team.BLUE)
							|| (item.getType() == MenuItemType.SHRINE_BAR && engine.player.getTeam() == Team.RED)
							|| (item.getType() == MenuItemType.SHRINE_BAN && engine.player.getTeam() == Team.NEUTRAL)
							|| (item.getType() == MenuItemType.SHRINE_RAN && engine.player.getTeam() == Team.NEUTRAL)) {
						execute(i);
					}
					else if(item.getType() == MenuItemType.SHRINE_N && engine.player.getTeam() == Team.NEUTRAL) {
						execute(i);
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

}
