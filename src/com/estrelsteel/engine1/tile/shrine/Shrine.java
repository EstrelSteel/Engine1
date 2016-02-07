package com.estrelsteel.engine1.tile.shrine;

import java.util.ArrayList;

import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.world.Location;

public class Shrine {
	private Team team;
	private Location loc;
	private ArrayList<Tile> tiles;
	
	public Shrine(Team team, Location loc) {
		this.team = team;
		this.loc = loc;
		this.tiles = new ArrayList<Tile>();
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
		return;
	}

	public Location getLocation() {
		return loc;
	}

	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}
}
