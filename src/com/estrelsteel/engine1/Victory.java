package com.estrelsteel.engine1;

import com.estrelsteel.engine1.maps.Gamemode;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.World;

public class Victory {
	
	public static Team checkVicotry(World world, Gamemode gm) {
		int mino = 0;
		int valShrines = 0;
		if(gm == Gamemode.CLASSIC) {
			for(int i = world.getShrines().size() - 1; i > -1; i--) {
				if(mino == 0 && world.getShrines().get(i).getTeam() == Team.BLUE) {
					return Team.BLUE;
				}
				else if(world.getShrines().get(i).getTeam() == Team.RED) {
					mino++;
				}
				if(world.getShrines().get(i).getTeam() != Team.OFF || world.getShrines().get(i).getTeam() != Team.HIDDEN) {
					valShrines++;
				}
			}
			if(mino == valShrines) {
				return Team.RED;
			}
		} 
		else if(gm == Gamemode.REVERSE) {
			for(int i = world.getShrines().size() - 1; i > -1; i--) {
				if(mino == 0 && world.getShrines().get(i).getTeam() == Team.RED) {
					return Team.RED;
				}
				else if(world.getShrines().get(i).getTeam() == Team.BLUE) {
					mino++;
				}
				if(world.getShrines().get(i).getTeam() != Team.OFF || world.getShrines().get(i).getTeam() != Team.HIDDEN) {
					valShrines++;
				}
			}
			if(mino == valShrines) {
				return Team.BLUE;
			}
		}
		return Team.OFF;
	}
	
	
}
