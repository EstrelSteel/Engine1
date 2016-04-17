package com.estrelsteel.engine1.contract;


public class MinTotalScore implements Requirement {
	
	private double score;
	
	public MinTotalScore() {
		this.score = 0;
	}
	
	public MinTotalScore(double score) {
		this.score = score;
	}
	
	public double getMinimumScore() {
		return score;
	}

	public boolean checkRequirement(RequireData data) {
		if(score <= data.getScore()) {
			return true;
		}
		return false;
	}

	public static Requirement create(String line) {
		String[] args = line.split("/");
		MinTotalScore require = new MinTotalScore();
		require.setMinimumScore(Double.parseDouble(args[1].trim()));
		return require;
	}
	
	public void setMinimumScore(double score) {
		this.score = score;
	}
	
	public String toString() {
		return "Collect a score of " + score + " or more.";
	}

}
