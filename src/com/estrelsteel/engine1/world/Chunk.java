package com.estrelsteel.engine1.world;

import java.util.ArrayList;

import com.estrelsteel.engine1.tile.Tile;

public class Chunk {

	private ArrayList<Tile> tiles;
	private Location bounds;
	
	public Chunk(Location bounds) {
		tiles = new ArrayList<Tile>();
		this.bounds = bounds;
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public Location getBounds() {
		return bounds;
	}
	
	public boolean addTile(Tile tile) {
		if(tile.getLocation().getX() >= bounds.getX() && tile.getLocation().getX() + tile.getLocation().getWidth() <= bounds.getX() + bounds.getWidth()
				&& tile.getLocation().getY() >= bounds.getY() && tile.getLocation().getY() + tile.getLocation().getHeight() <= bounds.getY() + bounds.getHeight()) {
			tiles.add(tile);
			return true;
		}
		if(tile.getLocation().collidesWith(bounds)) {
			tiles.add(tile);
			return true;
		}
		System.out.println("ERROR: Tile out of bounds");
		return false;
	}
	
	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
		return;
	}
	
	public void setBounds(Location bounds) {
		this.bounds = bounds;
		return;
	}
}
