package com.estrelsteel.engine1.menu;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Player;
import com.estrelsteel.engine1.maps.Gamemode;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.online.Vote;
import com.estrelsteel.engine1.world.Location;

public class LobbyMainController extends MenuController implements MouseMotionListener {
	private Engine1 engine;
	private MenuItem item;
	
	public LobbyMainController(Menu menu, String name, Engine1 engine) {
		super(menu, name);
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
					if(item.getType() == MenuItemType.VOTE_BUTTON) {
						engine.lobbyMainHud.setOpen(false, engine);
						engine.lobbyVoteHud.setOpen(true, engine);
						e.consume();
					}
					else if(item.getType() == MenuItemType.ADMIN_BUTTON) {
						String[] args = JOptionPane.showInputDialog("Enter a command", "").split(" ");
						
						if(args[0].trim().equalsIgnoreCase("pick") && args.length >= 3) {
							if(args[1].trim().equalsIgnoreCase("map")) {
								Engine1.server.votes.add(new Vote(Maps.findByID(Engine1.stringtoint(args[2].trim())).getID(), 999999));
							}
							else if(args[1].trim().equalsIgnoreCase("gm")) {
								Engine1.server.gmVotes.add(new Vote(Gamemode.findByID(Engine1.stringtoint(args[2].trim())).getID(), 999999));
							}
						}
						else if(args[0].trim().equalsIgnoreCase("minotaur") && args.length >= 2) {
							for(Player p : engine.world.getPlayers()) {
								if(p.getName().split("#")[0].equalsIgnoreCase(args[1].trim())) {
									Engine1.server.minotaur = p.getName();
								}
							}
						}
					}
					else if(item.getType() == MenuItemType.START_BUTTON) {
						if(Engine1.server.users.size() >= 2) {
							Packets.sendPacketToAllUsers(Packets.REQUEST_VOTES.getID(), Engine1.server);
						}
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
