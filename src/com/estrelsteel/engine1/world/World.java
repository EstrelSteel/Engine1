package com.estrelsteel.engine1.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.tile.Tile;

public class World {
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Camera> cameras = new ArrayList<Camera>();
	private Camera mainCamera;
	
	private double width;
	private double height;
	
	public World() {
		this.width = 1;
		this.height = 1;
		this.cameras.add(new Camera(new Location(0, 0)));
		this.mainCamera = cameras.get(0);
	}
	
	public World(double width, double height) {
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
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public ArrayList<Entity> getEntities() {
		 return entities;
	}
	
	public ArrayList<Camera> getCameras() {
		return cameras;
	}

	public Camera getMainCamera() {
		return mainCamera;
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
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
	
	public Graphics2D renderWorld(Graphics2D ctx) {
		mainCamera.focus(this);
		int x = mainCamera.getLocation().getX();
		int y = mainCamera.getLocation().getY();
		int displayX = 0;
		int displayY = 0;
		AffineTransform trans;
		for(Tile t : tiles) {
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
			ctx.setColor(Color.BLACK);
			if(!t.getType().getImage().isImageLoaded()) {
				t.getType().getImage().loadImage();
			}
			if(displayX + t.getLocation().getWidth() > 0 && displayX < Engine1.startWidth && displayY + t.getLocation().getHeight() > 0 && displayY < Engine1.startHeight) {
				trans = new AffineTransform();
				trans.translate(displayX, displayY);
				trans.scale(t.getLocation().getWidth() / t.getType().getLocation().getWidth(), t.getLocation().getHeight() / t.getType().getLocation().getHeight());
				
				trans.rotate(Math.toRadians(t.getLocation().getRotation()), t.getLocation().getWidth() / 2, t.getLocation().getHeight() / 2);
				
				ctx.drawImage(t.getType().getImage().getTile(), trans, null);
			}
		}
		for(Entity e : entities) {
			if(mainCamera.getFollowX()) {
				if(mainCamera.getEntity().equals(e)) {
					displayX = (int) ((Engine1.WIDTH / 2) - (e.getLocation().getWidth() / 2));
				}
				else {
					displayX = e.getLocation().getX() - (e.getLocation().getWidth() / 2) + x;
				}
			}
			else {
				displayX = e.getLocation().getX() + x;
			}
			
			if(mainCamera.getFollowY()) {
				if(mainCamera.getEntity().equals(e)) {
					displayY = (int) ((Engine1.HEIGHT / 2) - (e.getLocation().getHeight() / 2));
				}
				else {
					displayY = e.getLocation().getY() - (e.getLocation().getHeight() / 2) + y;
				}
			}
			else {
				displayY = e.getLocation().getY() + y;
			}
			if(displayX + e.getLocation().getWidth() > 0 && displayX < Engine1.startWidth && displayY + e.getLocation().getHeight() > 0 && displayY < Engine1.startHeight) {
				if(!e.getCurrentImage().isImageLoaded()) {
					e.getCurrentImage().loadImage();
				}
				trans = new AffineTransform();
				trans.translate(displayX, displayY);
				trans.scale(e.getLocation().getWidth() / e.getCurrentImage().getLocation().getWidth(), e.getLocation().getHeight() / e.getCurrentImage().getLocation().getHeight());
				
				trans.rotate(Math.toRadians(e.getLocation().getRotation()), e.getLocation().getWidth() / 2, e.getLocation().getHeight() / 2);
				
				ctx.drawImage(e.getCurrentImage().getEntity(), trans, null);
			}
		}
		return ctx;
	}
	
	
	
	public boolean equals(World world) {
		if(world.getWidth() == width && world.getHeight() == height && world.getTiles() == tiles && world.getEntities() == entities && cameras == world.getCameras()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean doesCollide(Entity e, Location loc) {
		for(Tile tile : tiles) {
			if(tile.getCollide() && tile.getLocation().collidesWith(loc, mainCamera)) {
				return true;
			}
			
		}
		for(Entity entity : entities) {
			if(!e.equals(entity) && entity.getCollide() && loc.collidesWith(entity.getLocation(), mainCamera)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean doesCollide(Tile t, Location loc) {
		for(Tile tile : tiles) {
			if(!t.equals(tile) && tile.getCollide() && tile.getLocation().collidesWith(loc, mainCamera)) {
				return true;
			}
			
		}
		for(Entity entity : entities) {
			if(entity.getCollide() && loc.collidesWith(entity.getLocation(), mainCamera)) {
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
	
	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
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
	
	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
		return;
	}
	
	public void setMainCamera(int camera) {
		this.mainCamera = cameras.get(camera);
		return;
	}
}
