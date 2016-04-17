package com.estrelsteel.engine1.contract;

import java.util.ArrayList;


public interface Requirement {
	public boolean checkRequirement(RequireData data);
	
	public String toString();
	
	public static ArrayList<Requirement> create(String line) {
		String[] requirements = line.split("~");
		String[] args;
		ArrayList<Requirement> requires = new ArrayList<Requirement>();
		RequirementTypes type;
		for(String s : requirements) {
			args = s.split("/");
			type = RequirementTypes.findByID(Integer.parseInt(args[0].trim()));
			if(type == RequirementTypes.MIN_TOT_SCORE) {
				requires.add(MinTotalScore.create(s));
			}
			else if(type == RequirementTypes.NO_COLOUR) {
				requires.add(NoColour.create(s));
			}
			else if(type == RequirementTypes.BASIC) {
				requires.add(Basic.create(s));
			}
		}
		return requires;
	}
	
	public enum RequirementTypes {
		INVALID(-1),
		MIN_TOT_SCORE(0),
		NO_COLOUR(1),
		BASIC(2);
		
		private int id;
		
		RequirementTypes(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
		public static RequirementTypes findByID(int id) {
			for(RequirementTypes type : RequirementTypes.values()) {
				if(type.getID() == id) {
					return type;
				}
			}
			return RequirementTypes.INVALID;
		}
	}
}
