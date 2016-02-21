package com.estrelsteel.engine1.menu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.online.Packets;
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
					if((item.getType() == MenuItemType.BACK_BUTTON)) {
						engine.lobbyMapHud.setOpen(false);
						engine.lobbyVoteHud.setOpen(true);
						e.consume();
					}
					else if(item.getType() == MenuItemType.BUTTON_SELECTED_1 && item.getClickLocation().getY() > 0) {
						item.setType(MenuItemType.BUTTON_SELECTED_2);
						e.consume();
					}
					else if(item.getType() == MenuItemType.MINES_BUTTON) {
						Engine1.vote = Maps.MINES;
						e.consume();
					}
					else if(item.getType() == MenuItemType.START_BUTTON) {
						Packets.sendPacketToAllUsers(Packets.REQUEST_VOTES.getID(), engine.server);
						e.consume();
					}
				}
				if(item.getClickLocation().getY() == (64 * (Engine1.vote.getID() + 1)) && (item.getType() == MenuItemType.BUTTON_NOT_SELECTED)) {
					item.setType(MenuItemType.BUTTON_SELECTED_2);
					e.consume();
				}
			}
		}
	}
}
