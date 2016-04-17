package com.estrelsteel.engine1.contract;

import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.block.Block;
import com.estrelsteel.engine1.entitiy.block.BlockStatus;
import com.estrelsteel.engine1.entitiy.block.BlockType;


public class NoColour implements Requirement {
	
	private BlockType colour;
	
	public NoColour() {
		this.colour = BlockType.RED_BLOCK;
	}
	
	public NoColour(BlockType colour) {
		this.colour = colour;
	}
	
	public BlockType getColour() {
		return colour;
	}

	public boolean checkRequirement(RequireData data) {
		for(Entity e : data.getWorld().getEntities()) {
			if(e instanceof Block) {
				if(e.getType() == colour.getType() && ((Block) e).getStatus() == BlockStatus.PINNED) {
					return false;
				}
			}
		}
		return true;
	}

	public static Requirement create(String line) {
		String[] args = line.split("/");
		NoColour require = new NoColour();
		require.setColour(BlockType.getByID(Integer.parseInt(args[1].trim())));
		return require;
	}
	
	public void setColour(BlockType colour) {
		this.colour = colour;
	}
	
	public String toString() {
		return "Use no " + colour.getName().substring(0, colour.getName().indexOf("_")) + " parts.";
	}

}
