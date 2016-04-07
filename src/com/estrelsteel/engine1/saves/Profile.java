package com.estrelsteel.engine1.saves;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.player.Player;
import com.estrelsteel.engine1.entitiy.player.PlayerClass;
import com.estrelsteel.engine1.entitiy.player.PlayerTypes;
import com.estrelsteel.engine1.entitiy.weapon.WeaponType;
import com.estrelsteel.engine1.tile.shrine.Team;

public class Profile {
	
	private String username;
	private long id;
	private ArrayList<WeaponType> weapons;
	private ArrayList<WeaponType> minotaurWeapons;
	private ArrayList<EntityType> slashes;
	private boolean transHud;
	private WeaponType weapon;
	private WeaponType minotaurWeapon;
	private EntityType slash;
	private ArrayList<PlayerClass> classes;
	
	public Profile() {
		this.username = "NULL";
		this.id = (int) (Math.random() * 1000000000);
		this.weapons = new ArrayList<WeaponType>();
		this.minotaurWeapons = new ArrayList<WeaponType>();
		this.slashes = new ArrayList<EntityType>();
		this.transHud = false;
		this.weapon = WeaponType.DIAMOND_SWORD;
		this.minotaurWeapon = WeaponType.DIAMOND_WAR_AXE;
		this.slash = EntityType.SLASH;

		classes = new ArrayList<PlayerClass>();
		classes.add(new PlayerClass(username, PlayerTypes.HUMAN, Team.BLUE, weapon, 1.0));
		classes.add(new PlayerClass(username, PlayerTypes.MINOTAUR, Team.RED, minotaurWeapon, 1.3));
		classes.add(new PlayerClass(username, PlayerTypes.HUMAN_REVERSE, Team.RED, weapon, 1.0));
		classes.add(new PlayerClass(username, PlayerTypes.MINOTAUR_REVERSE, Team.BLUE, minotaurWeapon, 1.3));
	}
	
	public Profile(String username) {
		this.username = username;
		this.id = (int) (Math.random() * 1000000000);
		this.weapons = new ArrayList<WeaponType>();
		this.minotaurWeapons = new ArrayList<WeaponType>();
		this.slashes = new ArrayList<EntityType>();
		this.transHud = false;
		this.weapon = WeaponType.DIAMOND_SWORD;
		this.minotaurWeapon = WeaponType.DIAMOND_WAR_AXE;
		this.slash = EntityType.SLASH;

		classes = new ArrayList<PlayerClass>();
		classes.add(new PlayerClass(username, PlayerTypes.HUMAN, Team.BLUE, weapon, 1.0));
		classes.add(new PlayerClass(username, PlayerTypes.MINOTAUR, Team.RED, minotaurWeapon, 1.3));
		classes.add(new PlayerClass(username, PlayerTypes.HUMAN_REVERSE, Team.RED, weapon, 1.0));
		classes.add(new PlayerClass(username, PlayerTypes.MINOTAUR_REVERSE, Team.BLUE, minotaurWeapon, 1.3));
	}
	
	public String getUsername() {
		return username;
	}
	
	public long getID() {
		return id;
	}
	
	public ArrayList<WeaponType> getWeapons() {
		return weapons;
	}
	
	public ArrayList<WeaponType> getMinotaurWeapons() {
		return minotaurWeapons;
	}
	
	public ArrayList<EntityType> getSlashes() {
		return slashes;
	}
	
	public WeaponType getWeapon() {
		return weapon;
	}
	
	public WeaponType getMinotaurWeapon() {
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
		engine.player.setEquiped(weapon.getWeapon());
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
		for(WeaponType t : weapons) {
			lines.add("weapons " + t.getID());
		}
		for(WeaponType t : minotaurWeapons) {
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
				weapons.add(WeaponType.findByID(Integer.parseInt(args[1].trim())));
			}
			else if(args[0].trim().equalsIgnoreCase("minotaurWeapons")) {
				minotaurWeapons.add(WeaponType.findByID(Integer.parseInt(args[1].trim())));
			}
			else if(args[0].trim().equalsIgnoreCase("slashes")) {
				slashes.add(EntityType.findByID(Integer.parseInt(args[1].trim())));
			}
			else if(args[0].trim().equalsIgnoreCase("weapon")) {
				weapon = WeaponType.findByID(Integer.parseInt(args[1].trim()));
			}
			else if(args[0].trim().equalsIgnoreCase("minotaurWeapon")) {
				minotaurWeapon = WeaponType.findByID(Integer.parseInt(args[1].trim()));
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
		classes.add(new PlayerClass(username + "#" + id, PlayerTypes.HUMAN, Team.BLUE, weapon, 1.0));
		classes.add(new PlayerClass(username + "#" + id, PlayerTypes.MINOTAUR, Team.RED, minotaurWeapon, 1.3));
		classes.add(new PlayerClass(username + "#" + id, PlayerTypes.HUMAN_REVERSE, Team.RED, weapon, 1.0));
		classes.add(new PlayerClass(username + "#" + id, PlayerTypes.MINOTAUR_REVERSE, Team.BLUE, minotaurWeapon, 1.3));
	}
	
	public void setUsername(String username) {
		this.username = username;
		return;
	}
	
	public void setTransHud(boolean transHud) {
		this.transHud = transHud;
		return;
	}
	
	public void setWeapons(ArrayList<WeaponType> weapons) {
		this.weapons = weapons;
		return;
	}
	
	public void setMinotaurWeapons(ArrayList<WeaponType> minotaurWeapons) {
		this.minotaurWeapons = minotaurWeapons;
		return;
	}
	
	public void setSlashes(ArrayList<EntityType> slashes) {
		this.slashes = slashes;
		return;
	}
	
	public void setWeapon(WeaponType weapon) {
		if(weapons.contains(weapon)) {
			this.weapon = weapon;
		}
		return;
	}
	
	public void setMinotaurWeapon(WeaponType minotaurWeapon) {
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
