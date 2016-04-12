package com.estrelsteel.engine1.menu.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.sound.Effects;
import com.estrelsteel.engine1.world.Location;

public class LobbyMapController extends LobbyMainController implements MouseMotionListener {
	private Engine1 engine;
	private MenuItem item;
	
	public LobbyMapController(Menu menu, String name, Engine1 engine) {
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
						engine.lobbyMapHud.setOpen(false, engine);
						engine.lobbyVoteHud.setOpen(true, engine);
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.BUTTON_SELECTED_1 && item.getClickLocation().getY() > 0) {
						item.setType(MenuItemType.BUTTON_SELECTED_2);
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.MINES_BUTTON) {
						Engine1.vote = Maps.MINES;
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.ISLAND_BUTTON) {
						Engine1.vote = Maps.ISLAND;
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.ISLAND_LOOP_BUTTON) {
						Engine1.vote = Maps.ISLAND_LOOP;
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.SAND_BAR_BUTTON) {
						Engine1.vote = Maps.SAND_BAR;
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.ZIG_ZAG_BUTTON) {
						Engine1.vote = Maps.ZIG_ZAG;
						Effects.SELECT.getSound().play();
						e.consume();
					}
					else if(item.getType() == MenuItemType.START_BUTTON) {
						startGame();
						e.consume();
					}
				}
				if(item.getType().getName().equalsIgnoreCase(Engine1.vote.name() + "_BUTTON")) {
					for(int x = 0; x < getMenu().getMenuItems().size(); x++) {
						if(getMenu().getMenuItems().get(x).getClickLocation().equals(item.getClickLocation()) && getMenu().getMenuItems().get(x) != item) {
							getMenu().getMenuItems().get(x).setType(MenuItemType.BUTTON_SELECTED_2);
						}
					}
					e.consume();
				}
			}
		}
	}
}
