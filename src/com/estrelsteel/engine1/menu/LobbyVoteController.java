package com.estrelsteel.engine1.menu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.online.Packets;
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
						engine.lobbyVoteHud.setOpen(false);
						engine.lobbyMainHud.setOpen(true);
						e.consume();
					}
					else if((item.getType() == MenuItemType.MAPS_BUTTON)) {
						engine.lobbyVoteHud.setOpen(false);
						engine.lobbyMapHud.setOpen(true);
						e.consume();
					}
					else if(item.getType() == MenuItemType.START_BUTTON) {
						Packets.sendPacketToAllUsers(Packets.REQUEST_VOTES.getID(), engine.server);
						e.consume();
					}
				}
			}
		}
	}
}
