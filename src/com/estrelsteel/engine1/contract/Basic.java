package com.estrelsteel.engine1.contract;

import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.block.Block;
import com.estrelsteel.engine1.entitiy.block.BlockStatus;


public class Basic implements Requirement {

	public boolean checkRequirement(RequireData data) {
		for(Entity e : data.getWorld().getEntities()) {
			if(e instanceof Block) {
				if(((Block) e).getStatus() == BlockStatus.PINNED) {
					return true;
				}
			}
		}
		return false;
	}

	public static Requirement create(String line) {
		Basic require = new Basic();
		return require;
	}
	public String toString() {
		return "Use [SPACE] to move and place a part.";
	}

}
