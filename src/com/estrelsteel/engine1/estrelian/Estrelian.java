package com.estrelsteel.engine1.estrelian;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.world.World;

public class Estrelian {
	
	public Estrelian() {
		
	}
	
	public World loadWorld(String file, World world) throws IOException {
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		String head = br.readLine();
		ArrayList<String> lines = new ArrayList<String>();
		if(head.equalsIgnoreCase("!estrelian1") || head.equalsIgnoreCase("!es1")) {
			String line = head;
			while(line != null) {
				line = br.readLine();
				lines.add(line);
			}
			String type = "";
			String[] sLine;
			System.out.println(lines);
			String l;
			Tile tempTile = new Tile();
			Entity tempEntity = new Entity();
			Camera tempCamera = new Camera();
			int size;
			if(lines.size() > -1) {
				for(int i = 0; i < lines.size(); i++) {
					l = lines.get(i);
					if(l == null) {
						l = "";
					}
					sLine = l.split(" ");
					size = sLine.length;
					if(size > 2 && sLine[0].equalsIgnoreCase("set")) {
						if(sLine[1].equalsIgnoreCase("mainCamera")) {
							if(Engine1.stringtoint(sLine[2]) < 0) {
								sLine[2] = "1";
							}
							world.setMainCamera(Engine1.stringtoint(sLine[2]));
						}
					}
					if(size > 1 && sLine[0].equalsIgnoreCase("new")) {
						if(sLine[1].equalsIgnoreCase("tile")) {
							type = sLine[1];
							tempTile = new Tile();
						}
						if(sLine[1].equalsIgnoreCase("entity")) {
							type = sLine[1];
							tempEntity = new Entity();
						}
						if(sLine[1].equalsIgnoreCase("camera")) {
							type = sLine[1];
							tempCamera = new Camera();
						}
						
					}
					
					if(type.equalsIgnoreCase("tile")) {
						if(size > 2) {
							if(sLine[0].equalsIgnoreCase("x") && sLine[1].equalsIgnoreCase("=")) {
								tempTile.getLocation().setX(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("y") && sLine[1].equalsIgnoreCase("=")) {
								tempTile.getLocation().setY(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("w") && sLine[1].equalsIgnoreCase("=")) {
								tempTile.getLocation().setWidth(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("h") && sLine[1].equalsIgnoreCase("=")) {
								tempTile.getLocation().setHeight(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("collide") && sLine[1].equalsIgnoreCase("=")) {
								tempTile.setCollide(getTrueFalse(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("type") && sLine[1].equalsIgnoreCase("=")) {
								tempTile.setType(TileType.findByName(sLine[2]));
							}
						}
						if(sLine[0].equalsIgnoreCase("end")) {
							type = "";
							if(!world.containsTile(tempTile)) {
								world.addTile(tempTile);
							}
						}
					}
					if(type.equalsIgnoreCase("entity")) {
						if(size > 2) {
							if(sLine[0].equalsIgnoreCase("x") && sLine[1].equalsIgnoreCase("=")) {
								tempEntity.getLocation().setX(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("y") && sLine[1].equalsIgnoreCase("=")) {
								tempEntity.getLocation().setY(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("w") && sLine[1].equalsIgnoreCase("=")) {
								tempEntity.getLocation().setWidth(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("h") && sLine[1].equalsIgnoreCase("=")) {
								tempEntity.getLocation().setHeight(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("type") && sLine[1].equalsIgnoreCase("=")) {
								tempEntity.setType(EntityType.findByName(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("handler") && sLine[1].equalsIgnoreCase("=")) {
								for(Entity e : world.getEntities()) {
									if(e.getControls().getName().equalsIgnoreCase(sLine[2])) {
										tempEntity.setControls(e.getControls());
									}
								}
							}
							if(sLine[0].equalsIgnoreCase("collide") && sLine[1].equalsIgnoreCase("=")) {
								tempEntity.setCollide(getTrueFalse(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("walkspeed") && sLine[1].equalsIgnoreCase("=")) {
								tempEntity.setWalkspeed(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("name") && sLine[1].equalsIgnoreCase("=")) {
								tempEntity.setName(sLine[2]);
							}
						}
						if(sLine[0].equalsIgnoreCase("end")) {
							type = "";
							if(!world.containsEntity(tempEntity)) {
								world.addEntity(tempEntity);
							}
						}
					}
					if(type.equalsIgnoreCase("camera")) {
						if(size > 2) {
							if(sLine[0].equalsIgnoreCase("x") && sLine[1].equalsIgnoreCase("=")) {
								tempCamera.getLocation().setX(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("y") && sLine[1].equalsIgnoreCase("=")) {
								tempCamera.getLocation().setY(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("w") && sLine[1].equalsIgnoreCase("=")) {
								tempCamera.getLocation().setWidth(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("h") && sLine[1].equalsIgnoreCase("=")) {
								tempCamera.getLocation().setHeight(Engine1.stringtoint(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("handler") && sLine[1].equalsIgnoreCase("=")) {
								for(Camera c : world.getCameras()) {
									if(c.getCameraController().getName().equalsIgnoreCase(sLine[2])) {
										tempCamera.setCameraController(c.getCameraController());
									}
								}
							}
							if(sLine[0].equalsIgnoreCase("entity") && sLine[1].equalsIgnoreCase("=")) {
								System.out.println(sLine[2]);
								for(Entity e : world.getEntities()) {
									if(e.getName().equalsIgnoreCase(sLine[2])) {
										tempCamera.setEntity(e);
									}
								}
							}
							if(sLine[0].equalsIgnoreCase("followX") && sLine[1].equalsIgnoreCase("=")) {
								tempCamera.setFollowX(getTrueFalse(sLine[2]));
							}
							if(sLine[0].equalsIgnoreCase("followY") && sLine[1].equalsIgnoreCase("=")) {
								tempCamera.setFollowY(getTrueFalse(sLine[2]));
							}
						}
						if(sLine[0].equalsIgnoreCase("end")) {
							type = "";
							if(!world.containsCamera(tempCamera)) {
								world.addCamera(tempCamera);
							}
						}
					}
				}
			}
		}
		return world;
	}
	
	public synchronized void saveWorld(String file, World world) throws IOException {
		System.out.println("### SAVING ###");
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("!es1");
		lines = world.convertToES1File(lines);
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		pw.flush();
		for(int i = 0; i < lines.size(); i++) {
			pw.println(lines.get(i));
		}
		pw.close();
		System.out.println("### SAVE COMPLETE ###");
		return;
	}
	
	public static boolean getTrueFalse(String s) {
		if(s.trim().equalsIgnoreCase("true")) {
			return true;
		}
		else {
			return false;
		}
	}
}
