package com.estrelsteel.engine1.menu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.sound.Effects;
import com.estrelsteel.engine1.tile.shrine.Shrine;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Location;

public class RespawnController extends MenuController {
	
	private Engine1 engine;
	private MenuItem item;
	
	public RespawnController(Menu menu, String name, Engine1 engine) {
		super(menu, name);
		this.engine = engine;
	}

	public void execute(int id) {
		Shrine s;
		for(int i = 0; i < engine.world.getShrines().size(); i++) {
			s = engine.world.getShrines().get(i);
			if(s.getID() == id) {
				engine.player.setHealth(engine.player.getMaxHealth());
				engine.player.setActiveAnimationNum(0);
				engine.player.getLocation().setX(s.getLocation().getX());
				engine.player.getLocation().setY(s.getLocation().getY());
				engine.player.moveDown(engine.world);
				engine.player.setWalkspeed(5);
				engine.player.setSlowWalkspeed(1);
				engine.world.setMainCamera(engine.playerCamera);
				engine.respawn.setOpen(false, engine);
				engine.overlayRespawn.setOpen(false, engine);
				engine.hud.setOpen(true, engine);
				engine.overlayHud.setOpen(true, engine);
				Effects.SELECT.getSound().play();
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
							|| (item.getType() == MenuItemType.SHRINE_BAN && engine.player.getTeam() == Team.NEUTRAL)
							|| (item.getType() == MenuItemType.SHRINE_RAN && engine.player.getTeam() == Team.NEUTRAL)) {
						execute(i);
					}
					else if(item.getType() == MenuItemType.SHRINE_N && engine.player.getTeam() == Team.NEUTRAL) {
						execute(i);
					}
					else if((item.getType() == MenuItemType.SHRINE_RAB && engine.player.getTeam() == Team.BLUE)
							|| (item.getType() == MenuItemType.SHRINE_BAR && engine.player.getTeam() == Team.RED)) {
						if(i == 0) {
							execute(i);
						}
						else if(i == 4) {
							execute(i);
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

}
