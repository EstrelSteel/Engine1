package com.estrelsteel.engine1.entitiy.block;

import com.estrelsteel.engine1.collide.CollideMap;
import com.estrelsteel.engine1.collide.CollideType;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.world.Location;

public class Block extends Entity {
	
	private BlockStatus status;
	
	public Block(BlockType type, Location loc) {
		super(type.getType(), loc, 10, null);
		this.status = BlockStatus.IDLE;
		setCollide(false);
	}
	
	public BlockStatus getStatus() {
		return status;
	}

	public CollideType getCollideType(CollideMap map) {
		int x = map.getCentre().getX();
		int y = map.getCentre().getY(); 
		Location point = getLocation().getRotatedPoint(getLocation().getX() - x, getLocation().getY() - y, getLocation().getRotation());
		point.setX(point.getX() + x);
		point.setY(point.getY() + y);
		if(map.getCollide(point.getX(), point.getY())) {
			point = getLocation().getRotatedPoint(getLocation().getX() + getLocation().getWidth() - x, getLocation().getY() + getLocation().getHeight() - y, getLocation().getRotation());
			point.setX(point.getX() + x);
			point.setY(point.getY() + y);
			if(map.getCollide(point.getX(), point.getY())) {
				return CollideType.FULL;
			}
			return CollideType.HALF;
		}
		return CollideType.NONE;
	}
	
	public void setStatus(BlockStatus status) {
		this.status = status;
	}
}
