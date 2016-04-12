package com.estrelsteel.engine1.handler;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.world.Location;

public class Selector extends Handler {
	
	private int select = 0;
	public TileType type = TileType.AIR;
	private Engine1 engine;
	private Location loc;
	public boolean collide = false;
	public int rotation = 0;
	
	public Selector(String name, Engine1 engine) {
		super(name);
		this.engine = engine;
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
		if(engine.debug) {
			if(e.getKeyCode() == 61 && e.isAltDown()) {
				select = select + 1;
				if(select >= TileType.values().length) {
					select = 0;
				}
				type = TileType.values()[select];
				e.consume();
			}
			if(e.getKeyCode() == 45 && e.isAltDown()) {
				select = select - 1;
				if(select < 0) {
					select = TileType.values().length - 1;
				}
				type = TileType.values()[select];
				e.consume();
			}
			if(e.getKeyCode() == 67 && e.isAltDown()) {
				collide = !collide;
				e.consume();
			}
			if(e.getKeyCode() == 80 && e.isAltDown()) {
				loc = new Location(engine.player.getLocation().getX() / 64 * 64, engine.player.getLocation().getY() / 64 * 64, 64, 64, rotation);
				engine.world.addTile(new Tile(type, loc, collide, null));
				System.out.println("PLACED TILE: " + type.getName() + "\t" + loc.toString());
				e.consume();
			}
			if(e.getKeyCode() == 78 && e.isAltDown()) {
				engine.player.setNoClip(!engine.player.isNoClip());
				e.consume();
			}
			if(e.getKeyCode() == 82 && e.isAltDown()) {
				rotation = rotation + 90;
				if(rotation >= 360) {
					rotation = 0;
				}
				e.consume();
			}
			if(e.getKeyCode() == 73 && e.isAltDown()) {
				for(int i = 0; i < engine.world.getAllTiles().size(); i++) {
					if(engine.world.getAllTiles().get(i).getLocation().equals(new Location(engine.player.getLocation().getX() / 64 * 64, engine.player.getLocation().getY() / 64 * 64, 64, 64, rotation))) {
						engine.world.getAllTiles().remove(i);
						i--;
					}
				}
				e.consume();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
