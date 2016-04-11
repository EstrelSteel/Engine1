package com.estrelsteel.engine1.menu;

import java.awt.event.MouseEvent;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.menu.controller.MainMenuController;
import com.estrelsteel.engine1.sound.Effects;
import com.estrelsteel.engine1.world.Location;

public class N_MainMenuController extends MainMenuController {

	private MenuItem item;
	private Engine1 engine;
	
	public N_MainMenuController(Menu menu, String name, Engine1 engine) {
		super(menu, name, engine);
		this.engine = engine;
	}
	
	public void mouseClicked(MouseEvent e) {
		if(getMenu().isOpen() && !e.isConsumed()) {
			Location loc = new Location(e.getX(), e.getY(), 1, 1);
			for(int i = 0; i < getMenu().getMenuItems().size(); i++) {
				item = getMenu().getMenuItems().get(i);
				if(item.getClickLocation().collidesWith(loc)) {
					Effects.SELECT.getSound().play();
					if(item instanceof MenuText) {
						if(((MenuText) item).getText().equalsIgnoreCase("Host")) {
							engine.StartClientServer(true);
							e.consume();
						}
						else if(((MenuText) item).getText().equalsIgnoreCase("Connect")) {
							engine.StartClientServer(false);
							e.consume();
						}
					}
				}
			}
		}
	}
	
}
