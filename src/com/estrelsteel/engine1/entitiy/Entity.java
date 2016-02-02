package com.estrelsteel.engine1.entitiy;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.handler.PlayerHandler;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class Entity {
	private Location loc;
	private EntityType type;
	private Handler controls;
	private int walkspeed;
	private int slowWalkspeed;
	private boolean collide;
	private String name;
	private int activeAnimation;
	private Entity equiped;
	private boolean topEquip;
	private AffineTransform trans;
	private boolean noClip = false;
	
	public Entity() {
		this.loc = new Location(0, 0, 64, 64);
		this.controls = new PlayerHandler("PLAYER");
		this.name = "NULL";
		this.activeAnimation = 0;
	}
	
	public Entity(EntityType type, Location loc) {
		this.type = type;
		this.loc = loc;
		this.controls = null;
		this.walkspeed = 5;
		this.slowWalkspeed = (int) (this.walkspeed / 2);
		this.collide = true;
		this.name = "NULL";
		this.activeAnimation = 0;
	}
	
	public Entity(EntityType type, Location loc, Handler controls) {
		this.type = type;
		this.loc = loc;
		this.controls = controls;
		this.walkspeed = 5;
		this.slowWalkspeed = (int) (this.walkspeed / 2);
		this.collide = true;
		this.name = "NULL";
		this.activeAnimation = 0;
	}
	
	public Entity(EntityType type, Location loc, int walkspeed, Handler controls) {
		this.type = type;
		this.loc = loc;
		this.controls = controls;
		this.walkspeed = walkspeed;
		this.slowWalkspeed = (int) (this.walkspeed / 2);
		this.collide = true;
		this.name = "NULL";
		this.activeAnimation = 0;
	}
	
	public Entity(EntityType type, Location loc, int walkspeed, boolean collide, Handler controls, String name) {
		this.type = type;
		this.loc = loc;
		this.controls = controls;
		this.walkspeed = walkspeed;
		this.slowWalkspeed = (int) (this.walkspeed / 2);
		this.collide = collide;
		this.name = name;
		this.activeAnimation = 0;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public Handler getControls() {
		return controls;
	}
	
	public int getWalkspeed() {
		return walkspeed;
	}
	
	public int getSlowWalkspeed() {
		return slowWalkspeed;
	}
	
	public boolean getCollide() {
		return collide;
	}
	
	public String getName() {
		return name;
	}
	
	public int getActiveAnimationNum() {
		return activeAnimation;
	}
	
	public Animation getCurrentAnimation() {
		return type.getAnimations().get(activeAnimation);
	}
	
	public EntityImage getCurrentImage()  {
		return getCurrentAnimation().getImages().get(getCurrentAnimation().getFrame());
	}
	
	public Entity getEquiped() {
		return equiped;
	}
	
	public boolean getTopEquip() {
		return topEquip;
	}
	
	public AffineTransform getTransformation() {
		return trans;
	}
	
	public boolean isNoClip() {
		return noClip;
	}
	
	public boolean equals(Entity entity) {
		if(loc.equals(entity.getLocation()) && type.getID() == entity.getType().getID() && entity.getWalkspeed() == walkspeed && entity.getName() == name) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean basicEquals(Entity entity) {
		if(type.getID() == entity.getType().getID() && name.equalsIgnoreCase(entity.getName())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean moveUp(World world) {
		if(noClip || !world.doesCollide(this, new Location(loc.getX(), loc.getY() - walkspeed, loc.getWidth(), loc.getHeight()))) {
			loc.setX(loc.getX());
			loc.setY(loc.getY() - walkspeed);
			return true;
		}
		else if(noClip || !world.doesCollide(this, new Location(loc.getX(), loc.getY() - slowWalkspeed, loc.getWidth(), loc.getHeight()))) {
			loc.setX(loc.getX());
			loc.setY(loc.getY() - slowWalkspeed);
			return true;
		}
		else {
			return false;
		}
		
		
	}
	
	public boolean moveDown(World world) {
		if(noClip || !world.doesCollide(this, new Location(loc.getX(), loc.getY() + walkspeed, loc.getWidth(), loc.getHeight()))) {
			loc.setX(loc.getX());
			loc.setY(loc.getY() + walkspeed);
			return true;
		}
		else if(noClip || !world.doesCollide(this, new Location(loc.getX(), loc.getY() + slowWalkspeed, loc.getWidth(), loc.getHeight()))) {
			loc.setX(loc.getX());
			loc.setY(loc.getY() + slowWalkspeed);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean moveRight(World world) {
		if(noClip || !world.doesCollide(this, new Location(loc.getX() + walkspeed, loc.getY(), loc.getWidth(), loc.getHeight()))) {
			loc.setX(loc.getX() + walkspeed);
			loc.setY(loc.getY());
			return true;
		}
		else if(noClip || !world.doesCollide(this, new Location(loc.getX() + slowWalkspeed, loc.getY(), loc.getWidth(), loc.getHeight()))) {
			loc.setX(loc.getX() + slowWalkspeed);
			loc.setY(loc.getY());
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean moveLeft(World world) {
		if(noClip || !world.doesCollide(this, new Location(loc.getX() - walkspeed, loc.getY(), loc.getWidth(), loc.getHeight()))) {
			loc.setX(loc.getX() - walkspeed);
			loc.setY(loc.getY());
			return true;
		}
		else if(noClip || !world.doesCollide(this, new Location(loc.getX() - slowWalkspeed, loc.getY(), loc.getWidth(), loc.getHeight()))) {
			loc.setX(loc.getX() - slowWalkspeed);
			loc.setY(loc.getY());
			return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<String> convertToES1File(ArrayList<String> lines) {
		lines.add("new Entity :");
		lines = loc.convertToES1File(lines);
		lines.add("type = " + type.getName());
		lines.add("handler = " + controls.getName());
		lines.add("collide = " + collide);
		lines.add("walkspeed = " + walkspeed);
		lines.add("name = " + name);
		lines.add("end");
		return lines;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		return;
	}
	
	public void setType(EntityType type) {
		this.type = type;
		return;
	}
	
	public void setControls(Handler controls) {
		this.controls = controls;
		return;
	}
	
	public void setWalkspeed(int walkspeed) {
		this.walkspeed = walkspeed;
		return;
	}
	
	public void setSlowWalkspeed(int slowWalkspeed) {
		this.slowWalkspeed = slowWalkspeed;
		return;
	}
	
	public void setCollide(boolean collide) {
		this.collide = collide;
		return;
	}
	
	public void setName(String name) {
		this.name = name;
		return;
	}
	
	public void setActiveAnimationNum(int activeAnimation) {
		this.activeAnimation = activeAnimation;
	}
	
	public void setEquiped(Entity equiped) {
		this.equiped = equiped;
		return;
	}
	
	public void setTopEquip(boolean topEquip) {
		this.topEquip = topEquip;
		return;
	}
	
	public void setTransformation(AffineTransform trans) {
		this.trans = trans;
		return;
	}
	
	public void setNoClip(boolean noClip) {
		this.noClip = noClip;
		return;
	}
}
