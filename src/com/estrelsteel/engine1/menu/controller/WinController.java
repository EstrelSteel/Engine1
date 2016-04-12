package com.estrelsteel.engine1.menu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.estrelsteel.engine1.menu.Menu;

public class WinController extends MenuController {
	
	public WinController(Menu menu, String name) {
		super(menu, name);
	}

	public void mouseClicked(MouseEvent e) {
		
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
		if(getMenu().isOpen()) {
			if(e.getKeyCode() == 10) {
				getMenu().setOpen(false);
			}
		}
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
