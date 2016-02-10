package com.estrelsteel.engine1.tile.shrine;

import java.util.ArrayList;

import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.world.Location;

public class Shrine {
	private Team team;
	private Location loc;
	private ArrayList<Tile> tiles;
	private int id;
	
	public Shrine(int id, Team team, Location loc) {
		this.id = id;
		this.team = team;
		this.loc = loc;
		this.tiles = new ArrayList<Tile>();
		tiles.add(new Tile(TileType.SHRINE, new Location(loc.getX(), loc.getY(), 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX(), loc.getY(), 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX(), loc.getY(), 64, 64, 270), false, null));
		
		tiles.add(new Tile(TileType.SHRINE, new Location(loc.getX() + 64, loc.getY(), 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX() + 64, loc.getY(), 64, 64, 0), false, null));
		
		tiles.add(new Tile(TileType.SHRINE, new Location(loc.getX() + 128, loc.getY(), 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX() + 128, loc.getY(), 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX() + 128, loc.getY(), 64, 64, 90), false, null));
		
		//NEXT

		tiles.add(new Tile(TileType.SHRINE, new Location(loc.getX(), loc.getY() + 64, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX(), loc.getY() + 64, 64, 64, 270), false, null));
		
		tiles.add(new Tile(TileType.SHRINE, new Location(loc.getX() + 64, loc.getY() + 64, 64, 64, 0), false, null));
		
		tiles.add(new Tile(TileType.SHRINE, new Location(loc.getX() + 128, loc.getY() + 64, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX() + 128, loc.getY() + 64, 64, 64, 90), false, null));
		
		//NEXT

		tiles.add(new Tile(TileType.SHRINE, new Location(loc.getX(), loc.getY() + 128, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX(), loc.getY() + 128, 64, 64, 180), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX(), loc.getY() + 128, 64, 64, 270), false, null));
		
		tiles.add(new Tile(TileType.SHRINE, new Location(loc.getX() + 64, loc.getY() + 128, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX() + 64, loc.getY() + 128, 64, 64, 180), false, null));
		
		tiles.add(new Tile(TileType.SHRINE, new Location(loc.getX() + 128, loc.getY() + 128, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX() + 128, loc.getY() + 128, 64, 64, 180), false, null));
		tiles.add(new Tile(TileType.SHRINE_EDGE, new Location(loc.getX() + 128, loc.getY() + 128, 64, 64, 90), false, null));
		
		// EH!!!
		
		tiles.add(new Tile(TileType.FILTER_GOLD, new Location(loc.getX(), loc.getY(), 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.FILTER_GOLD, new Location(loc.getX() + 128, loc.getY() + 128, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.FILTER_GOLD, new Location(loc.getX() + 64, loc.getY() + 128, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.FILTER_GOLD, new Location(loc.getX() + 128, loc.getY() + 64, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.FILTER_GOLD, new Location(loc.getX() + 128, loc.getY(), 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.FILTER_GOLD, new Location(loc.getX(), loc.getY() + 64, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.FILTER_GOLD, new Location(loc.getX() + 64, loc.getY() + 64, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.FILTER_GOLD, new Location(loc.getX(), loc.getY() + 128, 64, 64, 0), false, null));
		tiles.add(new Tile(TileType.FILTER_GOLD, new Location(loc.getX() + 64, loc.getY(), 64, 64, 0), false, null));
		update();
	}

	public int getID() {
		return id;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public Location getLocation() {
		return loc;
	}
	
	public void update() {
		Tile tile;
		for(int i = 0; i < tiles.size(); i++) {
			tile = tiles.get(i);
			if(tile.getType().getName().startsWith("FILTER")) {
				tile.setType(TileType.findByName("FILTER_" + team.getColour()));
			}
		}
		return;
	}
	
	public void update(Team team) {
		this.team = team;
		update();
		return;
	}
	
	public String convertToJava() {
		return "new Shrine(" + id + ", Team." + team.getName() +", " + loc.convertToJava() + ")";
	}

	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	public void setTeam(Team team) {
		this.team = team;
		return;
	}
	
	public void setID(int id) {
		this.id = id;
		return;
	}
}
