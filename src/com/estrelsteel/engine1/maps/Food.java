package com.estrelsteel.engine1.maps;

import com.estrelsteel.engine1.collide.CollideMap;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class Food extends Map {
	public World load1(World world) {
		world.addCollideMap(new CollideMap(EntityType.ICE_CREAM_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.DONUT_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.PIE_MAP, new Location(0, 0, 256, 256)));
		return world;
	}
}