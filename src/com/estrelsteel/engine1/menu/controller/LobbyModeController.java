package com.estrelsteel.engine1.menu.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.maps.Gamemode;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.sound.Effects;
import com.estrelsteel.engine1.world.Location;

public class LobbyModeController extends LobbyMainController implements MouseMotionListener {
	private Engine1 engine;
	private MenuItem item;
	
	public LobbyModeController(Menu menu, String name, Engine1 engine) {
		super(menu, name, engine);
		this.engine = engine;
	}

	public void mouseClicked(MouseEvent e) {
		if(getMenu().isOpen() && !e.isConsumed()) {
			Location loc = new Location(e.getX(), e.getY(), 1, 1);
			for(int i = 0; i < getMenu().getMenuItems().size(); i++) {
				item = getMenu().getMenuItems().get(i);
				if(item.getType() == MenuItemType.BUTTON_SELECTED_2) {
					item.setType(MenuItemType.BUTTON_NOT_SELECTED);
					e.consume();
				}
				if(item.getClickLocation().collidesWith(loc)) {
					Effects.SELECT.getSound().play();
					if((item.getType() == MenuItemType.BACK_BUTTON)) {
						engine.lobbyModeHud.setOpen(false, engine);
						engine.lobbyVoteHud.setOpen(true, engine);
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.BUTTON_SELECTED_1 && item.getClickLocation().getY() > 0) {
						item.setType(MenuItemType.BUTTON_SELECTED_2);
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.CLASSIC_BUTTON) {
						Engine1.gmVote = Gamemode.CLASSIC;
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.REVERSE_BUTTON) {
						Engine1.gmVote = Gamemode.REVERSE;
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.START_BUTTON) {
						startGame();
						e.consume();
					}
				}
				if(item.getClickLocation().getY() == (64 * (Engine1.gmVote.getID() + 1)) && (item.getType() == MenuItemType.BUTTON_SELECTED_1)) {
					item.setType(MenuItemType.BUTTON_SELECTED_2);
					e.consume();
				}
			}
		}
	}
}
