package com.estrelsteel.engine1.maps;

import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.World;

public class Victory {
	
	public static Team checkVicotry(World world, Gamemode gm) {
		int mino = 0;
		int player = 0;
		int valShrines = 0;
		if(gm == Gamemode.CLASSIC) {
			for(int i = world.getShrines().size() - 1; i > -1; i--) {
				if(world.getShrines().get(i).getTeam() == Team.BLUE) {
					if(i == world.getShrines().size() - 1) {
						return Team.BLUE;
					}
					player++;
				}
				else if(world.getShrines().get(i).getTeam() == Team.RED) {
					mino++;
				}
				if(world.getShrines().get(i).getTeam() != Team.OFF && world.getShrines().get(i).getTeam() != Team.HIDDEN) {
					valShrines++;
				}
			}
			if(player >= valShrines) {
				return Team.BLUE;
			}
			if(mino >= valShrines) {
				return Team.RED;
			}
		} 
		else if(gm == Gamemode.REVERSE) {
			for(int i = 0; i < world.getShrines().size(); i++) {
				if(world.getShrines().get(i).getTeam() == Team.RED) {
					if(i == 0) {
						return Team.RED;
					}
					player++;
				}
				else if(world.getShrines().get(i).getTeam() == Team.BLUE) {
					mino++;
				}
				if(world.getShrines().get(i).getTeam() != Team.OFF && world.getShrines().get(i).getTeam() != Team.HIDDEN) {
					valShrines++;
				}
			}
			if(player >= valShrines) {
				return Team.RED;
			}
			if(mino >= valShrines) {
				return Team.BLUE;
			}
		}
		return Team.OFF;
	}
	
	
}
