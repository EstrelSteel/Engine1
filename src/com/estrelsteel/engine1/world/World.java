package com.estrelsteel.engine1.world;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.Player;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.tile.shrine.Shrine;

public class World {
	private ArrayList<Chunk> chunks;
	private ArrayList<Tile> tiles;
	private ArrayList<Tile> collideTiles;
	private ArrayList<Entity> entities;
	private ArrayList<Camera> cameras;
	private ArrayList<Shrine> shrines;
	private ArrayList<Player> players;
	private Camera mainCamera;
	
	private double width;
	private double height;
	
	public World() {
		this.chunks = new ArrayList<Chunk>();
		this.tiles = new ArrayList<Tile>();
		this.collideTiles = new ArrayList<Tile>();
		this.entities = new ArrayList<Entity>();
		this.cameras = new ArrayList<Camera>();
		this.shrines = new ArrayList<Shrine>();
		this.players = new ArrayList<Player>();
		this.width = 1;
		this.height = 1;
		this.cameras.add(new Camera(new Location(0, 0)));
		this.mainCamera = cameras.get(0);
	}
	
	public World(double width, double height) {
		this.chunks = new ArrayList<Chunk>();
		this.tiles = new ArrayList<Tile>();
		this.collideTiles = new ArrayList<Tile>();
		this.entities = new ArrayList<Entity>();
		this.cameras = new ArrayList<Camera>();
		this.shrines = new ArrayList<Shrine>();
		this.players = new ArrayList<Player>();
		this.width = width;
		this.height = height;
		this.cameras.add(new Camera(new Location(0, 0)));
		this.mainCamera = cameras.get(0);
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public ArrayList<Chunk> getChunks() {
		return chunks;
	}
	
	public ArrayList<Tile> getAllTiles() {
		return tiles;
	}
	
	public ArrayList<Tile> getCollideTiles() {
		return collideTiles;
	}
	
	public ArrayList<Entity> getEntities() {
		 return entities;
	}
	
	public ArrayList<Camera> getCameras() {
		return cameras;
	}
	
	public ArrayList<Shrine> getShrines() {
		return shrines;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Camera getMainCamera() {
		return mainCamera;
	}
	
	public void addChunk(Chunk chunk) {
		chunks.add(chunk);
	}
	
	public void addTile(Tile tile) {
		if(tile.getCollide()) {
			collideTiles.add(tile);
			tiles.add(tile);
		}
		else {
			tiles.add(tile);
		}
		return;
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
		return;
	}
	
	public void addCamera(Camera camera) {
		cameras.add(camera);
		return;
	}
	
	public void addShrine(Shrine shrine) {
		shrines.add(shrine);
		return;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
		return;
	}
	
	public Graphics2D renderWorld(Graphics2D ctx) {
		mainCamera.focus(this);
		int x = mainCamera.getLocation().getX();
		int y = mainCamera.getLocation().getY();
		int displayX = 0;
		int displayY = 0;
		int displayXE = 0;
		int displayYE = 0;
		AffineTransform trans;
		AffineTransform transE = new AffineTransform();
		Tile t;
		Entity e;
		Chunk c;
		Shrine s;
		for(int i = 0; i < chunks.size(); i++) {
			c = chunks.get(i);
			if(mainCamera.getFollowX()) {
				displayX = c.getBounds().getX() - (c.getBounds().getWidth() / 2) + x;
			}
			else {
				displayX = c.getBounds().getX() + x;
			}
			if(mainCamera.getFollowY()) {
				displayY = c.getBounds().getY() - (c.getBounds().getHeight() / 2) + y;
			}
			else {
				displayY = c.getBounds().getY() + y;
			}
			if(displayX + c.getBounds().getWidth() > -300 && displayX < Engine1.startWidth + 10 && displayY + c.getBounds().getHeight() > -10 && displayY < Engine1.startHeight + 10) {
				for(int j = 0; j < c.getTiles().size(); j++) {
					t = c.getTiles().get(j);
					if(t.getType() != TileType.UNKOWN) {
						if(mainCamera.getFollowX()) {
							displayX = t.getLocation().getX() - (t.getLocation().getWidth() / 2) + x;
						}
						else {
							displayX = t.getLocation().getX() + x;
						}
						if(mainCamera.getFollowY()) {
							displayY = t.getLocation().getY() - (t.getLocation().getHeight() / 2) + y;
						}
						else {
							displayY = t.getLocation().getY() + y;
						}
						//ctx.setColor(Color.BLACK);
						if(displayX + t.getLocation().getWidth() > -10 && displayX < Engine1.startWidth + 10 && displayY + t.getLocation().getHeight() > -10 && displayY < Engine1.startHeight + 10) {
							if(!t.getType().getImage().isImageLoaded()) {
								t.getType().getImage().loadImage();
							}
							trans = new AffineTransform();
							trans.translate(displayX, displayY);
							trans.scale(t.getLocation().getWidth() / t.getType().getLocation().getWidth(), t.getLocation().getHeight() / t.getType().getLocation().getHeight());
							
							trans.rotate(Math.toRadians(t.getLocation().getRotation()), t.getLocation().getWidth() / 2, t.getLocation().getHeight() / 2);
							
							ctx.drawImage(t.getType().getImage().getTile(), trans, null);
						}
					}
				}
			}
		}
		for(int i = 0; i < tiles.size(); i++) {
			t = tiles.get(i);
			if(t.getType() != TileType.UNKOWN) {
				if(mainCamera.getFollowX()) {
					displayX = t.getLocation().getX() - (t.getLocation().getWidth() / 2) + x;
				}
				else {
					displayX = t.getLocation().getX() + x;
				}
				if(mainCamera.getFollowY()) {
					displayY = t.getLocation().getY() - (t.getLocation().getHeight() / 2) + y;
				}
				else {
					displayY = t.getLocation().getY() + y;
				}
				//ctx.setColor(Color.BLACK);
				if(displayX + t.getLocation().getWidth() > -10 && displayX < Engine1.startWidth + 10 && displayY + t.getLocation().getHeight() > -10 && displayY < Engine1.startHeight + 10) {
					if(!t.getType().getImage().isImageLoaded()) {
						t.getType().getImage().loadImage();
					}
					trans = new AffineTransform();
					trans.translate(displayX, displayY);
					trans.scale(t.getLocation().getWidth() / t.getType().getLocation().getWidth(), t.getLocation().getHeight() / t.getType().getLocation().getHeight());
					trans.rotate(Math.toRadians(t.getLocation().getRotation()), t.getLocation().getWidth() / (t.getType().getImage().getLocation().getWidth() / 2), t.getLocation().getHeight() / (t.getType().getImage().getLocation().getHeight() / 2));
					//transE.rotate(Math.toRadians(e.getEquiped().getLocation().getRotation()), e.getEquiped().getLocation().getWidth() / (e.getEquiped().getCurrentImage().getLocation().getWidth() / 2), e.getEquiped().getLocation().getHeight() / (e.getEquiped().getCurrentImage().getLocation().getHeight() / 2));
					ctx.drawImage(t.getType().getImage().getTile(), trans, null);
				}
			}
		}
		for(int i = 0; i < shrines.size(); i++) {
			s = shrines.get(i);
			for(int j = 0; j < s.getTiles().size(); j++) {
				t = s.getTiles().get(j);
				if(t.getType() != TileType.UNKOWN) {
					if(mainCamera.getFollowX()) {
						displayX = t.getLocation().getX() - (t.getLocation().getWidth() / 2) + x;
					}
					else {
						displayX = t.getLocation().getX() + x;
					}
					if(mainCamera.getFollowY()) {
						displayY = t.getLocation().getY() - (t.getLocation().getHeight() / 2) + y;
					}
					else {
						displayY = t.getLocation().getY() + y;
					}
					//ctx.setColor(Color.BLACK);
					if(displayX + t.getLocation().getWidth() > -10 && displayX < Engine1.startWidth + 10 && displayY + t.getLocation().getHeight() > -10 && displayY < Engine1.startHeight + 10) {
						if(!t.getType().getImage().isImageLoaded()) {
							t.getType().getImage().loadImage();
						}
						trans = new AffineTransform();
						trans.translate(displayX, displayY);
						trans.scale(t.getLocation().getWidth() / t.getType().getLocation().getWidth(), t.getLocation().getHeight() / t.getType().getLocation().getHeight());
						trans.rotate(Math.toRadians(t.getLocation().getRotation()), t.getLocation().getWidth() / (t.getType().getImage().getLocation().getWidth() / 2), t.getLocation().getHeight() / (t.getType().getImage().getLocation().getHeight() / 2));
						//transE.rotate(Math.toRadians(e.getEquiped().getLocation().getRotation()), e.getEquiped().getLocation().getWidth() / (e.getEquiped().getCurrentImage().getLocation().getWidth() / 2), e.getEquiped().getLocation().getHeight() / (e.getEquiped().getCurrentImage().getLocation().getHeight() / 2));
						ctx.drawImage(t.getType().getImage().getTile(), trans, null);
					}
				}
			}
		}
		for(int i = 0; i < entities.size(); i++) {
			e = entities.get(i);
			if(mainCamera.getFollowX()) {
				if(mainCamera.getEntity().equals(e)) {
					displayX = (int) ((Engine1.WIDTH / 2) - (e.getLocation().getWidth() / 2));
					if(e.getEquiped() != null) {
						displayXE = e.getEquiped().getLocation().getX() - (e.getEquiped().getLocation().getWidth() / 2) + x;
					}
				}
				else {
					displayX = e.getLocation().getX() - (e.getLocation().getWidth() / 2) + x;
					if(e.getEquiped() != null) {
						displayXE = e.getEquiped().getLocation().getX() - (e.getEquiped().getLocation().getWidth() / 2) + x;
					}
				}
			}
			else {
				displayX = e.getLocation().getX() + x;
				if(e.getEquiped() != null) {
					displayXE = e.getEquiped().getLocation().getX()  + x;
				}
			}
			
			if(mainCamera.getFollowY()) {
				if(mainCamera.getEntity().equals(e)) {
					displayY = (int) ((Engine1.HEIGHT / 2) - (e.getLocation().getHeight() / 2));
					if(e.getEquiped() != null) {
						displayYE = e.getEquiped().getLocation().getY() - (e.getEquiped().getLocation().getHeight() / 2) + y;
					}
				}
				else {
					displayY = e.getLocation().getY() - (e.getLocation().getHeight() / 2) + y;
					if(e.getEquiped() != null) {
						displayYE = e.getEquiped().getLocation().getY() - (e.getEquiped().getLocation().getHeight() / 2) + y;
					}
				}
			}
			else {
				displayY = e.getLocation().getY() + y;
				if(e.getEquiped() != null) {
					displayYE = e.getEquiped().getLocation().getY() + y;
				}
			}
			if(displayX + e.getLocation().getWidth() > 0 && displayX < Engine1.startWidth && displayY + e.getLocation().getHeight() > 0 && displayY < Engine1.startHeight) {
				if(!e.getCurrentImage().isImageLoaded()) {
					e.getCurrentImage().loadImage();
				}
				trans = new AffineTransform();
				trans.translate(displayX, displayY);
				trans.scale(e.getLocation().getWidth() / e.getCurrentImage().getLocation().getWidth(), e.getLocation().getHeight() / e.getCurrentImage().getLocation().getHeight());
				
				trans.rotate(Math.toRadians(e.getLocation().getRotation()), e.getLocation().getWidth() / (e.getCurrentImage().getLocation().getWidth() / 2), e.getLocation().getHeight() / (e.getCurrentImage().getLocation().getHeight() / 2));
				if(e.getEquiped() != null) {
					transE = new AffineTransform();
					transE.translate(displayXE, displayYE);
					transE.scale(e.getEquiped().getLocation().getWidth() / e.getEquiped().getCurrentImage().getLocation().getWidth(), e.getEquiped().getLocation().getHeight() / e.getEquiped().getCurrentImage().getLocation().getHeight());
					
					transE.rotate(Math.toRadians(e.getEquiped().getLocation().getRotation()), e.getEquiped().getLocation().getWidth() / (e.getEquiped().getCurrentImage().getLocation().getWidth() / 2), e.getEquiped().getLocation().getHeight() / (e.getEquiped().getCurrentImage().getLocation().getHeight() / 2));
				}
				
				if(e.getEquiped() != null && e.getTopEquip()) {
					ctx.drawImage(e.getEquiped().getCurrentImage().getEntity(), transE, null);
				}
				
				ctx.drawImage(e.getCurrentImage().getEntity(), trans, null);
				
				if(e.getEquiped() != null && !e.getTopEquip()) {
					ctx.drawImage(e.getEquiped().getCurrentImage().getEntity(), transE, null);
				}
			}
		}
		return ctx;
	}
	
	public void sortToChunks() {
		boolean found = false;
		Tile t;
		Chunk chunk;
		for(int i = 0; i < tiles.size(); i++) {
			t = tiles.get(i);
			found = false;
			for(Chunk c : chunks) {
				if(c.addTile(tiles.get(i))) {
					found = true;
					tiles.remove(i);
					i--;
					if(i < 0) {
						i = 0;
					}
				}
			}
			if(!found) {
				chunk = new Chunk(new Location((t.getLocation().getX() / (8 * 64)) * (8 * 16), (t.getLocation().getY() / (8 * 64)) * (8 * 16), 8 * 64, 8 * 64));
				
				if(chunk.addTile(tiles.get(i))) {
					//System.out.println("chunk created");
					chunks.add(chunk);
					tiles.remove(i);
					i--;
					if(i < 0) {
						i = 0;
					}
				}
			}
		}
		//System.out.println("chunks " + chunks.size());
	}
	
	public boolean equals(World world) {
		if(world.getWidth() == width && world.getHeight() == height && world.getAllTiles() == tiles && world.getEntities() == entities && cameras == world.getCameras()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean doesCollide(Entity e, Location loc) {
		for(Tile tile : collideTiles) {
			if(tile.getCollide() && tile.getLocation().collidesWith(loc)) {
				return true;
			}
			
		}
		for(Entity entity : entities) {
			if(!e.equals(entity) && entity.getCollide() && loc.collidesWith(entity.getLocation())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean doesCollide(Tile t, Location loc) {
		for(Tile tile : collideTiles) {
			if(!t.equals(tile) && tile.getCollide() && tile.getLocation().collidesWith(loc)) {
				return true;
			}
			
		}
		for(Entity entity : entities) {
			if(entity.getCollide() && loc.collidesWith(entity.getLocation())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsTile(Tile tile) {
		for(Tile t : tiles) {
			if(tile.equals(t)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsEntity(Entity entity) {
		for(Entity e : entities) {
			if(entity.basicEquals(e)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsCamera(Camera camera) {
		for(Camera c : cameras) {
			if(camera.equals(c)) {
				return true;
			}
		}
		return false;
	}
	public ArrayList<String> convertToJava(ArrayList<String> lines) {
		for(Tile t : tiles) {
			lines.add("world.addTile("+ t.convertToJava() + ");");
		}
		for(Shrine s : shrines) {
			lines.add("world.addShrine("+ s.convertToJava() + ");");
		}
		return lines;
	}
	
	public ArrayList<String> convertToES1File(ArrayList<String> lines) {
		for(Tile t : tiles) {
			lines = t.convertToES1File(lines);
			lines.add("");
		}
		lines.add("@ END OF TILES");
		for(Entity e : entities) {
			lines = e.convertToES1File(lines);
			lines.add("");
		}
		lines.add("@ END OF ENTITIES");
		int mainCamera = -1;
		Camera c;
		for(int i = 1; i < cameras.size(); i++) {
			c = cameras.get(i);
			lines = c.convertToES1File(lines);
			lines.add("");
			if(c.equals(mainCamera)) {
				mainCamera = i;
			}
		}
		lines.add("@ END OF CAMERAS");
		lines.add("set mainCamera " + mainCamera);
 		return lines;
	}
	
	public void setWidth(double width) {
		this.width = width;
		return;
	}
	
	public void setHeight(double height) {
		this.height = height;
		return;
	}
	
	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
		return;
	}
	
	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
		return;
	}
	
	public void setCollideTiles(ArrayList<Tile> collideTiles) {
		this.collideTiles = collideTiles;
		return;
	}
	
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
		return;
	}
	
	public void setCameras(ArrayList<Camera> cameras) {
		this.cameras = cameras;
		return;
	}
	
	public void setShrines(ArrayList<Shrine> shrines) {
		this.shrines = shrines;
		return;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
		return;
	}
	
	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
		return;
	}
	
	public void setMainCamera(int camera) {
		this.mainCamera = cameras.get(camera);
		return;
	}
}
