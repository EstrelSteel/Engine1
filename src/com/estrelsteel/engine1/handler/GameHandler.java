package com.estrelsteel.engine1.handler;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameHandler extends Handler {
	
	public enum GameControls {
		USE(32, 9, 13);
		
		private int primary;
		private int secondary;
		private int tertiary;
		private boolean pressed;
		
		GameControls(int primary, int secondary, int tertiary) {
			this.primary= primary;
			this.secondary = secondary;
			this.tertiary = tertiary;
			this.pressed = false;
		}
		
		public int getPrimaryKey() {
			return primary;
		}
		
		public int getSecondaryKey() {
			return secondary;
		}
		
		public int getTertiaryKey() {
			return tertiary;
		}
		
		public boolean isPressed() {
			return pressed;
		}
		
		public void setPressed(boolean pressed) {
			this.pressed = pressed;
		}
	}
	
	public GameHandler(String name) {
		super(name);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(GameControls.USE.getPrimaryKey() == e.getKeyCode() || GameControls.USE.getSecondaryKey() == e.getKeyCode() || GameControls.USE.getTertiaryKey() == e.getKeyCode()) {
			GameControls.USE.setPressed(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(GameControls.USE.getPrimaryKey() == e.getKeyCode() || GameControls.USE.getSecondaryKey() == e.getKeyCode() || GameControls.USE.getTertiaryKey() == e.getKeyCode()) {
			GameControls.USE.setPressed(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
