package com.estrelsteel.engine1.tile.shrine;

import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.world.Location;

public class Shrine {
	private Team team;
	private Location loc;
	private ArrayList<Tile> tiles;
	private int id;
	private double count;
	private double resetCount;
	private double maxCount;
	
	public Shrine(int id, Team team, Location loc) {
		this.id = id;
		this.team = team;
		this.loc = loc;
		this.resetCount = 300.0;
		this.count = 300.0;
		this.maxCount = 600.0;
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
	
	public double getCount() {
		return count;
	}
	
	public double getMaxCount() {
		return maxCount;
	}
	
	public Team getLowerTeam() {
		if(team == Team.BLUE) {
			return Team.BLUE;
		}
		else if(team == Team.RED) {
			return Team.NEUTRAL;
		}
		else if(team == Team.NEUTRAL) {
			return Team.BLUE;
		}
		else {
			return Team.OFF;
		}
	}
	
	public Team getHigherTeam() {
		if(team == Team.BLUE) {
			return Team.NEUTRAL;
		}
		else if(team == Team.RED) {
			return Team.RED;
		}
		else if(team == Team.NEUTRAL) {
			return Team.RED;
		}
		else {
			return Team.OFF;
		}
	}
	
	public double getResetCount() {
		return resetCount;
	}
	
	public void addCount(double c) {
		if(count < 0.0) {
			count = 0.0;
		}
		count = count + c;
		hasConverted();
		return;
	}
	
	public void subtractCount(double c) {
		if(count > maxCount) {
			count = maxCount;
		}
		count = count - c;
		hasConverted();
		return;
	}
	
	public boolean hasConverted() {
		if(count <= 0) {
			if(team != Team.BLUE) {
				team = getLowerTeam();
				count = resetCount;
				update();
			}
			if(Engine1.multiplayer) {
				Engine1.client.sendData((Packets.SHRINE_CAP.getID() + "✂" + id + "✂" + team.getID()).getBytes());
			}
			return true;
		}
		else if(count >= maxCount) {
			if(team != Team.RED) {
				team = getHigherTeam();
				count = resetCount;
				update();
			}
			if(Engine1.multiplayer) {
				Engine1.client.sendData((Packets.SHRINE_CAP.getID() + "✂" + id + "✂" + team.getID()).getBytes());
			}
			return true;
		}
		else {
			return false;
		}
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
	
	public void setCount(double count) {
		this.count = count;
		return;
	}
	
	public void setMaxCount(double maxCount) {
		this.maxCount = maxCount;
		return;
	}
	
	public void setResetCount(double resetCount) {
		this.resetCount = resetCount;
		return;
	}
}
