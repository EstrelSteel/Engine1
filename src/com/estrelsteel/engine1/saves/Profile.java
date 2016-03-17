package com.estrelsteel.engine1.saves;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.player.Player;
import com.estrelsteel.engine1.entitiy.player.PlayerClass;
import com.estrelsteel.engine1.entitiy.player.PlayerTypes;
import com.estrelsteel.engine1.tile.shrine.Team;

public class Profile {
	
	private String username;
	private long id;
	private ArrayList<EntityType> weapons;
	private ArrayList<EntityType> minotaurWeapons;
	private ArrayList<EntityType> slashes;
	private boolean transHud;
	private EntityType weapon;
	private EntityType minotaurWeapon;
	private EntityType slash;
	private ArrayList<PlayerClass> classes;
	
	public Profile() {
		this.username = "NULL";
		this.id = (int) (Math.random() * 1000000000);
		this.weapons = new ArrayList<EntityType>();
		this.minotaurWeapons = new ArrayList<EntityType>();
		this.slashes = new ArrayList<EntityType>();
		this.transHud = false;
		this.weapon = EntityType.SWORD_DIAMOND;
		this.minotaurWeapon = EntityType.WAR_AXE_DIAMOND;
		this.slash = EntityType.SLASH;

		classes = new ArrayList<PlayerClass>();
		classes.add(new PlayerClass(username, PlayerTypes.HUMAN, Team.BLUE, 5, weapon));
		classes.add(new PlayerClass(username, PlayerTypes.MINOTAUR, Team.RED, 8, minotaurWeapon));
	}
	
	public Profile(String username) {
		this.username = username;
		this.id = (int) (Math.random() * 1000000000);
		this.weapons = new ArrayList<EntityType>();
		this.minotaurWeapons = new ArrayList<EntityType>();
		this.slashes = new ArrayList<EntityType>();
		this.transHud = false;
		this.weapon = EntityType.SWORD_DIAMOND;
		this.minotaurWeapon = EntityType.WAR_AXE_DIAMOND;
		this.slash = EntityType.SLASH;

		classes = new ArrayList<PlayerClass>();
		classes.add(new PlayerClass(username, PlayerTypes.HUMAN, Team.BLUE, 5, weapon));
		classes.add(new PlayerClass(username, PlayerTypes.MINOTAUR, Team.RED, 7, minotaurWeapon));
	}
	
	public String getUsername() {
		return username;
	}
	
	public long getID() {
		return id;
	}
	
	public ArrayList<EntityType> getWeapons() {
		return weapons;
	}
	
	public ArrayList<EntityType> getMinotaurWeapons() {
		return minotaurWeapons;
	}
	
	public ArrayList<EntityType> getSlashes() {
		return slashes;
	}
	
	public EntityType getWeapon() {
		return weapon;
	}
	
	public EntityType getMinotaurWeapon() {
		return minotaurWeapon;
	}
	
	public EntityType getSlash() {
		return slash;
	}
	
	public ArrayList<PlayerClass> getPlayerClasses() {
		return classes;
	}
	
	public boolean isTransHud() {
		return transHud;
	}
	
	@Deprecated
	public void loadForPlayer(Engine1 engine) {
		engine.player.setName(username + "#" + id);
		engine.player.getEquiped().setType(weapon);
		engine.slash.setType(slash);
		return;
	}
	
	public Player configPlayer(Player player) {
		player.setName(username + "#" + id);
		ArrayList<PlayerClass> classes = getPlayerClasses();
		player = classes.get(0).convertPlayerToClass(player);
		return player;
	}
	
	public ArrayList<String> save() {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("version " + Engine1.build);
		lines.add("username " + username);
		lines.add("id " + id);
		for(EntityType t : weapons) {
			lines.add("weapons " + t.getID());
		}
		for(EntityType t : minotaurWeapons) {
			lines.add("minotaurWeapons " + t.getID());
		}
		for(EntityType t : slashes) {
			lines.add("slashes " + t.getID());
		}
		lines.add("weapon " + weapon.getID());
		lines.add("minotaurWeapon " + minotaurWeapon.getID());
		lines.add("slash " + slash.getID());
		lines.add("transHud " + transHud);
		return lines;
	}
	
	public void load(ArrayList<String> lines) {
		String[] args;
		for(String l : lines) {
			args = l.split(" ");
			if(args[0].trim().equalsIgnoreCase("username")) {
				username = args[1].trim();
			}
			else if(args[0].trim().equalsIgnoreCase("id")) {
				id = Long.parseLong(args[1].trim());
			}
			else if(args[0].trim().equalsIgnoreCase("weapons")) {
				weapons.add(EntityType.findByID(Integer.parseInt(args[1].trim())));
			}
			else if(args[0].trim().equalsIgnoreCase("minotaurWeapons")) {
				minotaurWeapons.add(EntityType.findByID(Integer.parseInt(args[1].trim())));
			}
			else if(args[0].trim().equalsIgnoreCase("slashes")) {
				slashes.add(EntityType.findByID(Integer.parseInt(args[1].trim())));
			}
			else if(args[0].trim().equalsIgnoreCase("weapon")) {
				weapon = EntityType.findByID(Integer.parseInt(args[1].trim()));
			}
			else if(args[0].trim().equalsIgnoreCase("minotaurWeapon")) {
				minotaurWeapon = EntityType.findByID(Integer.parseInt(args[1].trim()));
			}
			else if(args[0].trim().equalsIgnoreCase("slash")) {
				slash = EntityType.findByID(Integer.parseInt(args[1].trim()));
			}
			else if(args[0].trim().equalsIgnoreCase("transHud")) {
				transHud = Boolean.getBoolean(args[1].trim());
			}
		}
		if(username.equalsIgnoreCase("NULL")) {
			username = JOptionPane.showInputDialog("Enter your username", "");
			username.replace('#', ' ');
		}
		if(id <= 0) {
			this.id = (int) (Math.random() * 1000000000);
		}

		classes = new ArrayList<PlayerClass>();
		classes.add(new PlayerClass(username + "#" + id, PlayerTypes.HUMAN, Team.BLUE, 5, weapon));
		classes.add(new PlayerClass(username + "#" + id, PlayerTypes.MINOTAUR, Team.RED, 7, minotaurWeapon));
	}
	
	public void setUsername(String username) {
		this.username = username;
		return;
	}
	
	public void setTransHud(boolean transHud) {
		this.transHud = transHud;
		return;
	}
	
	public void setWeapons(ArrayList<EntityType> weapons) {
		this.weapons = weapons;
		return;
	}
	
	public void setMinotaurWeapons(ArrayList<EntityType> minotaurWeapons) {
		this.minotaurWeapons = minotaurWeapons;
		return;
	}
	
	public void setSlashes(ArrayList<EntityType> slashes) {
		this.slashes = slashes;
		return;
	}
	
	public void setWeapon(EntityType weapon) {
		if(weapons.contains(weapon)) {
			this.weapon = weapon;
		}
		return;
	}
	
	public void setMinotaurWeapon(EntityType minotaurWeapon) {
		if(minotaurWeapons.contains(minotaurWeapon)) {
			this.minotaurWeapon = minotaurWeapon;
		}
		return;
	}
	
	public void setSlashes(EntityType slash) {
		if(slashes.contains(slash)) {
			this.slash = slash;
		}
		return;
	}
}
