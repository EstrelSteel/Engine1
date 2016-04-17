package com.estrelsteel.engine1.maps;

import com.estrelsteel.engine1.collide.CollideMap;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class TheEnd extends Map {
	public World load1(World world) {
		world.addCollideMap(new CollideMap(EntityType.CIRCLE_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.PENTAGON_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.DONUT_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.STAR_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.ROD_CENTRE_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.L_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.BUBBLES_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.ARROW_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.ROD_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.CAR_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.PIE_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.ICE_CREAM_MAP, new Location(0, 0, 256, 256)));
		world.addCollideMap(new CollideMap(EntityType.STAR_6_MAP, new Location(0, 0, 256, 256)));
		return world;
	}
}