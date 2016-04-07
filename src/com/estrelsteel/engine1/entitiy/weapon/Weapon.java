package com.estrelsteel.engine1.entitiy.weapon;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.player.Player;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.world.Location;

public class Weapon extends Entity {
	
	private double damage;
	private int weight;
	
	public Weapon(EntityType type, double damage, int weight) {
		super(type, new Location());
		this.damage = damage;
		this.weight = weight;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void attack(Engine1 engine, Location location) {
		for(Entity e : engine.world.getEntities()) {
			if(e instanceof Player && (e.getLocation().collidesWith(location) || location.collidesWith(e.getLocation())) && e != engine.player) {
				((Player) e).setHealth(((Player) e).getHealth() - damage);
				
				if(Engine1.multiplayer) {
					Engine1.client.sendData((Packets.DAMAGE.getID() + Packets.SPLIT.getID() + e.getName() + Packets.SPLIT.getID() + damage +  Packets.SPLIT.getID() + engine.player.getName()).getBytes());	
				}
			}
		}
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}

}
