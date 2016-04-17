package com.estrelsteel.engine1.contract;

import java.util.ArrayList;

import com.estrelsteel.engine1.maps.Map;
import com.estrelsteel.engine1.maps.Map.Maps;

public class Contract {
	private ArrayList<Requirement> requirements;
	private String name;
	private boolean finished;
	private Maps map;
	
	public Contract() {
		this.name = "NULL";
		this.requirements = new ArrayList<Requirement>();
		this.finished = false;
	}
	
	public Contract(String name, Maps map) {
		this.name = name;
		this.requirements = new ArrayList<Requirement>();
		this.finished = false;
		this.map = map;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Requirement> getRequirements() {
		return requirements;
	}
	
	public Maps getMap() {
		return map;
	}
	
	public static Contract create(String line) {
		Contract c = new Contract();
		String[] args = line.split("=");
		c.setName(args[0].trim());
		c.setMap(Maps.findByID(Integer.parseInt(args[1].trim())));
		c.setRequirements(Requirement.create(args[2].trim()));
		return c;
	}
	
	public boolean checkFinished(RequireData data, boolean finished) {
		for(Requirement r : requirements) {
			if((r instanceof NoColour || finished)) {
				if(!r.checkRequirement(data)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRequirements(ArrayList<Requirement> requirements) {
		this.requirements = requirements;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public void setMap(Maps map) {
		this.map = map;
	}
}
