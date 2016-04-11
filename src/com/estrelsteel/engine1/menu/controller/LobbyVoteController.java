package com.estrelsteel.engine1.menu.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.sound.Effects;
import com.estrelsteel.engine1.world.Location;

public class LobbyVoteController extends LobbyMainController implements MouseMotionListener {
	private Engine1 engine;
	private MenuItem item;
	
	public LobbyVoteController(Menu menu, String name, Engine1 engine) {
		super(menu, name, engine);
		this.engine = engine;
	}

	public void mouseClicked(MouseEvent e) {
		if(getMenu().isOpen() && !e.isConsumed()) {
			Location loc = new Location(e.getX(), e.getY(), 1, 1);
			for(int i = 0; i < getMenu().getMenuItems().size(); i++) {
				item = getMenu().getMenuItems().get(i);
				if(item.getClickLocation().collidesWith(loc)) {
					if((item.getType() == MenuItemType.BACK_BUTTON)) {
						engine.lobbyVoteHud.setOpen(false, engine);
						engine.lobbyMainHud.setOpen(true, engine);
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if((item.getType() == MenuItemType.MAPS_BUTTON)) {
						engine.lobbyVoteHud.setOpen(false, engine);
						engine.lobbyMapHud.setOpen(true, engine);
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if((item.getType() == MenuItemType.MODE_BUTTON)) {
						engine.lobbyVoteHud.setOpen(false, engine);
						engine.lobbyModeHud.setOpen(true, engine);
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.START_BUTTON) {
						startGame();
						e.consume();
					}
				}
			}
		}
	}
}
