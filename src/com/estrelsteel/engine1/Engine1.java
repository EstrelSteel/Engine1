package com.estrelsteel.engine1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.camera.TestCameraControl;
import com.estrelsteel.engine1.entitiy.Animation;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityImage;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.Player;
import com.estrelsteel.engine1.estrelian.Estrelian;
import com.estrelsteel.engine1.font.Font;
import com.estrelsteel.engine1.handler.CoreHandler;
import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.handler.PlayerHandler;
import com.estrelsteel.engine1.handler.PlayerHandler.PlayerControls;
import com.estrelsteel.engine1.handler.Selector;
import com.estrelsteel.engine1.maps.Map;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.menu.EndController;
import com.estrelsteel.engine1.menu.LobbyMainController;
import com.estrelsteel.engine1.menu.LobbyMapController;
import com.estrelsteel.engine1.menu.LobbyVoteController;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuImage;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.menu.RespawnController;
import com.estrelsteel.engine1.online.Client;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.online.Server;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.tile.shrine.Shrine;
import com.estrelsteel.engine1.tile.shrine.Team;
import com.estrelsteel.engine1.world.Chunk;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class Engine1 extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	public static final double SCALE = 1;
	public static int WIDTH = 650;
	public static final int startWidth = (int) (WIDTH * SCALE);
	public static int HEIGHT = 650;
	public static final int startHeight = (int) (HEIGHT * SCALE);
	public static Dimension dimension = new Dimension((int) (WIDTH * Engine1.SCALE), (int) (HEIGHT * Engine1.SCALE));
	
	public boolean running = false;
	public boolean applet = false;
	
	public int tickCount = 0;
	public int frames;
	public boolean debug = true;
	private boolean showFPS = true;
	public int fps;
	public int tps;
	public int focused = 0;
	
	private Thread thread; 
	public CoreHandler coreHandler;
	public PlayerHandler playerHandler = new PlayerHandler("PLAYER");
	
	public String title = "Aeris";
	public String version = "v0.1g";
	public int build = 7;
	public long time = System.currentTimeMillis();
	
	
	ArrayList<World> worlds = new ArrayList<World>();
	public World world;
	public World statictest = new World(WIDTH * SCALE, HEIGHT * SCALE);
	public World test = statictest;
	public Player player = new Player(EntityType.WALPOLE, new Location(0, 0, 64, 64), 5, true, playerHandler, "PLAYER");
	public Camera playerCamera = new Camera(new Location(0, 0, 0, 0), player);
	public Camera killCam = new Camera(new Location(0, 0, 0, 0), player);
	public TestCameraControl camControlTest = new TestCameraControl(playerCamera);
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	public Entity weapon = new Entity(EntityType.SWORD_RUBY, new Location(-1000, -1000, 0, 0, 0), 5, false, null, "WEAPON");
	public Entity slash = new Entity(EntityType.SLASH, new Location(-1000, -1000, 0, 0, 0), 5, false, null, "SLASH");
	public Location attackLoc;
	public World multiWorld = new World(WIDTH * SCALE, HEIGHT * SCALE);
	
	@SuppressWarnings("unused")
	private Estrelian es2 = new Estrelian();
	public static Server server;
	public static Client client;
	public static boolean multiplayer = false;
	public static Maps vote = Maps.INVALID;
	private String[] packetArgs;
	
	public Selector selector = new Selector("SELECTOR", this);
	private AffineTransform selectTrans;
	
	public Font fpsFont;
	
	public Menu hud = new Menu("hud", new Location(0, 0, 650, 650), new MenuImage("/com/estrelsteel/engine1/res/texture.png", new Location(0, 0, 16, 16)));
	public Menu overlayHud = new Menu("overlayHud", new Location(0, 0, 650, 650), new MenuImage("/com/estrelsteel/engine1/res/texture.png", new Location(0, 0, 16, 16)));
	public Menu respawn = new Menu("respawn", new Location(-10, -10, 670, 670), new MenuImage("/com/estrelsteel/engine1/res/respawn_back.png", new Location(0, 0, 65, 65)));
	public Menu overlayRespawn = new Menu("overlayRespawn", new Location(0, 0, 650, 650), new MenuImage("/com/estrelsteel/engine1/res/texture.png", new Location(0, 0, 16, 16)));
	public Menu lobbyMainHud = new Menu("lobbyMainHud", new Location(0, 0, 650, 650), new MenuImage("/com/estrelsteel/engine1/res/texture.png", new Location(0, 0, 16, 16)));
	public Menu lobbyVoteHud = new Menu("lobbyVoteHud", new Location(0, 0, 650, 650), new MenuImage("/com/estrelsteel/engine1/res/texture.png", new Location(0, 0, 16, 16)));
	public Menu lobbyMapHud = new Menu("lobbyMapHud", new Location(0, 0, 650, 650), new MenuImage("/com/estrelsteel/engine1/res/texture.png", new Location(0, 0, 16, 16)));
	public RespawnController respawnHandler = new RespawnController(overlayRespawn, "RespawnHandler", this);
	public Menu victory =  new Menu("victory", new Location(-10, -10, 670, 670), new MenuImage("/com/estrelsteel/engine1/res/respawn_back.png", new Location(0, 0, 65, 65)));
	public Menu defeat =  new Menu("defeat", new Location(-10, -10, 670, 670), new MenuImage("/com/estrelsteel/engine1/res/respawn_back.png", new Location(0, 0, 65, 65)));
	public EndController victoryHandler = new EndController(victory, "VictoryHandler", this);
	public EndController defeatHandler = new EndController(defeat, "DefeatHandler", this);
	public LobbyMainController lobbyMainHandler = new LobbyMainController(lobbyMainHud, "LobbyMainHandler", this);
	public LobbyVoteController lobbyVoteHandler = new LobbyVoteController(lobbyVoteHud, "LobbyVoteHandler", this);
	public LobbyMapController lobbyMapHandler = new LobbyMapController(lobbyMapHud, "LobbyMapHandler", this);
	
	public void start() {
		running = true;
		addFocusListener(coreHandler);
		thread = new Thread(this, title + version + "_main");
		thread.start();
		
		playerCamera.setFollowX(true);
		playerCamera.setFollowY(true);
		killCam.setFollowX(true);
		killCam.setFollowY(true);
		playerCamera.setCameraController(camControlTest);
		player.setSlowWalkspeed(1);
		player.setTeam(Team.BLUE);
		player.setEngine(this);
		player.setMaxHealth(100.0);
		
		EntityType.WALPOLE.getAnimations().get(0).setMaxWait(15);
		EntityType.WALPOLE.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(2 * 16, 0 * 16, 19, 21)));
		EntityType.JOHN_SNOW.getAnimations().get(0).setMaxWait(15);
		EntityType.JOHN_SNOW.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(2 * 16, 0 * 16, 19, 21)));
		EntityType.CLOUD.getAnimations().get(0).setMaxWait(7);
		EntityType.SLASH.getAnimations().get(0).setMaxWait(3);
		EntityType.SLASH_GOLD.getAnimations().get(0).setMaxWait(3);
		EntityType.SLASH_RUBY.getAnimations().get(0).setMaxWait(3);
		EntityType.SWORD_DIAMOND.getAnimations().get(0).setMaxWait(3);
		EntityType.SWORD_GOLD.getAnimations().get(0).setMaxWait(3);
		EntityType.SWORD_RUBY.getAnimations().get(0).setMaxWait(3);
		EntityType.WAR_AXE_DIAMOND.getAnimations().get(0).setMaxWait(3);
		EntityType.WAR_AXE_GOLD.getAnimations().get(0).setMaxWait(3);
		EntityType.WAR_AXE_RUBY.getAnimations().get(0).setMaxWait(3);
		EntityType.SPEAR.getAnimations().get(0).setMaxWait(3);
		EntityType.BOW.getAnimations().get(0).setMaxWait(3);
		EntityType.LEVER.getAnimations().get(0).setMaxWait(5);
		for(int i = 0; i < 10; i++) {
			if(i > 0 && i < 4) {
				EntityType.WALPOLE.getAnimations().add(new Animation(15));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(0 * 16, 2 * i * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(2 * 16, 2 * i * 16, 19, 21)));
				
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(15));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(0 * 16, 2 * i * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(2 * 16, 2 * i * 16, 19, 21)));
			
			}
			if(i > 3 && i < 5) {
				EntityType.WALPOLE.getAnimations().add(new Animation(24));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 0 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().add(new Animation(24));
				EntityType.WALPOLE.getAnimations().get(i + 1).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 2 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().add(new Animation(24));
				EntityType.WALPOLE.getAnimations().get(i + 2).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 4 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().add(new Animation(24));
				EntityType.WALPOLE.getAnimations().get(i + 3).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 6 * 16, 19, 21)));
				
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(24));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(8 * 16, 0 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(24));
				EntityType.JOHN_SNOW.getAnimations().get(i + 1).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(8 * 16, 2 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(24));
				EntityType.JOHN_SNOW.getAnimations().get(i + 2).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(8 * 16, 4 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(24));
				EntityType.JOHN_SNOW.getAnimations().get(i + 3).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(8 * 16, 6 * 16, 19, 21)));
			}
			if(i > 7 && i < 9) {
				EntityType.WALPOLE.getAnimations().add(new Animation(5));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(5));
				
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(0 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(2 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(4 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(6 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));

				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(0 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(2 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(4 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(6 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
			}
			if(i > 8 && i < 10) {
				EntityType.WALPOLE.getAnimations().add(new Animation(5));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(5));
				
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));

				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
			}
			if(i > -1 && i < 5) {
				EntityType.CLOUD.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/particle.png", new Location(i * 16, 0 * 16, 16, 16)));
			}
			if(i > 0 && i < 7) {
				EntityType.LEVER.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/aeris.png", new Location(i * 16, 0 * 16, 16, 16)));	
			}
			if(i > 0 && i < 8) {
				EntityType.SWORD_DIAMOND.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/weapon.png", new Location(i * 16, 0 * 16, 16, 16)));
				EntityType.SWORD_GOLD.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/weapon.png", new Location(i * 16, 1 * 16, 16, 16)));
				EntityType.SWORD_RUBY.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/weapon.png", new Location(i * 16, 2 * 16, 16, 16)));
				EntityType.WAR_AXE_DIAMOND.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/weapon.png", new Location(i * 16, 3 * 16, 16, 16)));
				EntityType.WAR_AXE_GOLD.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/weapon.png", new Location(i * 16, 4 * 16, 16, 16)));
				EntityType.WAR_AXE_RUBY.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/weapon.png", new Location(i * 16, 5 * 16, 16, 16)));
				
				EntityType.BOW.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/weapon.png", new Location(i * 16, 7 * 16, 16, 16)));
				
				EntityType.SLASH.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/particle.png", new Location(i * 16, 1 * 16, 16, 16)));
				EntityType.SLASH_GOLD.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/particle.png", new Location(i * 16, 2 * 16, 16, 16)));
				EntityType.SLASH_RUBY.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/particle.png", new Location(i * 16, 3 * 16, 16, 16)));
			
				EntityType.SPEAR.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/weapon.png", new Location(i * 32, 6 * 16, 32, 16)));
			}
		}
		
		TileType type;
		Chunk chunk = new Chunk(new Location(0, 0, 64, 64));
		for(int i = 0; i < TileType.values().length; i++) {
			type = TileType.findByID(i);
			if(i % 10 == 0) {
				chunk = new Chunk(new Location((i / 10) * (10 * 64), 0, 64 * 10, 64 * 10));
				statictest.addChunk(chunk);
			}
			statictest.getChunks().get(statictest.getChunks().size() - 1).addTile(new Tile(type, new Location(i * 64, 0, 64, 64, 0), false, null));
			//statictest.addTile(new Tile(type, new Location(i * 64, 0, 64, 64, 0), false, null));
		}
		EntityType eType;
		for(int i = 0; i < EntityType.values().length; i++) {
			eType = EntityType.findByID(i);
			statictest.addEntity(new Entity(eType, new Location(i * 64, 64, 64, 64, 0), 0, false, null, eType.getName()));
		}
		
		hud.addMenuItem(new MenuItem(MenuItemType.MOUSE, new Location(0, 650 - 192, 128, 256)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_ONE, new Location(144 + (60) * 0, 650 - 80, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_TWO, new Location(144 + (60) * 1, 650 - 80, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_THREE, new Location(144 + (60) * 2, 650 - 80, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_FOUR, new Location(144 + (60) * 3, 650 - 80, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_FIVE, new Location(144 + (60) * 4, 650 - 80, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.SHRINE_METER, new Location(37, 16, 576, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.HEALTH_FULL, new Location(144 + (60) * 5, 650 - 80, 64, 64)));
		hud.addText("health", new Location(144 + (60) * 5 + 64, 650 - 40, 0, 0));
		hud.setOpen(false);
		menus.add(hud);
		
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_B, new Location(37 + (128 * 0), 16, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_B, new Location(37 + (128 * 1), 16, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_N, new Location(37 + (128 * 2), 16, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_R, new Location(37 + (128 * 3), 16, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_R, new Location(37 + (128 * 4), 16, 64, 64)));
		overlayHud.setOpen(false);
		menus.add(overlayHud);
		
		respawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_METER, new Location(37, 650 / 2 + 650 / 8, 576, 64)));
		respawn.addMenuItem(new MenuItem(MenuItemType.RESPAWN_TEXT, new Location((650 - 512) / 2, 650 / 2 + 650 / 4, 512, 32)));
		respawn.addMenuItem(new MenuItem(MenuItemType.YOU_DIED_TEXT, new Location((650  - 512) / 2, 650 / 3, 512, 64)));
		respawn.setOpen(false);
		menus.add(respawn);
		

		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_B, new Location(37 + (128 * 0), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_B, new Location(37 + (128 * 1), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_N, new Location(37 + (128 * 2), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_R, new Location(37 + (128 * 3), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_R, new Location(37 + (128 * 4), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.setController(respawnHandler);
		overlayRespawn.setOpen(false);
		menus.add(overlayRespawn);
		
		victory.addMenuItem(new MenuItem(MenuItemType.VICTORY_TEXT, new Location((650  - 512) / 2, 650 / 3, 512, 64)));
		victory.addMenuItem(new MenuItem(MenuItemType.LOBBY_TEXT, new Location((650 - 160) / 2, 650 / 2 + 650 / 8, 160, 32)));
		victory.addMenuItem(new MenuItem(MenuItemType.QUIT_TEXT, new Location((650 - 160) / 2, 650 / 2 + 650 / 4, 160, 32)));
		victory.setController(victoryHandler);
		victory.setOpen(false);
		menus.add(victory);
		
		defeat.addMenuItem(new MenuItem(MenuItemType.DEFEAT_TEXT, new Location((650  - 512) / 2, 650 / 3, 512, 64)));
		defeat.addMenuItem(new MenuItem(MenuItemType.LOBBY_TEXT, new Location((650 - 160) / 2, 650 / 2 + 650 / 8, 160, 32)));
		defeat.addMenuItem(new MenuItem(MenuItemType.QUIT_TEXT, new Location((650 - 160) / 2, 650 / 2 + 650 / 4, 160, 32)));
		defeat.setController(defeatHandler);
		defeat.setOpen(false);
		menus.add(defeat);
		
		lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 0, 256, 64)));
		lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.VOTE_BUTTON, new Location(0, 0, 256, 64)));
		lobbyMainHud.setController(lobbyMainHandler);
		lobbyMainHud.setOpen(true);
		menus.add(lobbyMainHud);
		
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 0, 256, 64)));
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.BACK_BUTTON, new Location(0, 0, 256, 64)));
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 64, 256, 64)));
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.MAPS_BUTTON, new Location(0, 64, 256, 64)));
		lobbyVoteHud.setController(lobbyVoteHandler);
		lobbyVoteHud.setOpen(false);
		menus.add(lobbyVoteHud);
		
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 0, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BACK_BUTTON, new Location(0, 0, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 64, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.MINES_BUTTON, new Location(0, 64, 256, 64)));
		lobbyMapHud.setController(lobbyMapHandler);
		lobbyMapHud.setOpen(false);
		menus.add(lobbyMapHud);
		
		player.setEquiped(weapon);
//		statictest.addTile(new Tile(TileType.TREE_PINE_TOP, new Location(200, 200, 64, 64, 0), false, null));
//		statictest.addTile(new Tile(TileType.TREE_PINE_BOTTOM, new Location(200, 264, 64, 64, 0), false, null));
//		statictest.addTile(new Tile(TileType.TREE_TOP, new Location(264, 200, 64, 64, 0), false, null));
//		statictest.addTile(new Tile(TileType.TREE_BOTTOM, new Location(264, 264, 64, 64, 0), false, null));
		statictest.addEntity(slash);
		statictest.addEntity(player);
		statictest.addPlayer(player);
		
//		statictest.addEntity(new Entity(EntityType.SLASH_RUBY, new Location(400, 400, 64, 64), 0, true, null, "SLASH"));
//		statictest.addEntity(new Entity(EntityType.SWORD_RUBY, new Location(400, 400, 64, 64), 0, true, null, "SWORD"));
//		statictest.addEntity(new Entity(EntityType.CLOUD, new Location(336, 336, 64, 64), 0, true, null, "CLOUD"));
		statictest.addCamera(playerCamera);
		statictest.setMainCamera(playerCamera);
		statictest.sortToChunks();
		worlds.add(statictest);
		
		fpsFont = new Font();
		fpsFont.getTextLocation().setWidth(128);
		
		world = Maps.LOBBY.getMap().load();
		world = addBasics(world);
		worlds.add(world);
		Handler.loadHandlers(this, worlds);
		TEMPStartClientServer();
	}
	
	public World addBasics(World world) {
		//ENTITIES
		world.addEntity(slash);
		world.addEntity(weapon);
		world.addEntity(player);
		world.addPlayer(player);
		
		//CAMERAS
		world.addCamera(playerCamera);
		world.setMainCamera(playerCamera);
		for(Entity e : multiWorld.getEntities()) {
			world.addEntity(e);
		}
		for(Player p : multiWorld.getPlayers()) {
			world.addPlayer(p);
		}
		//CHUNKS
		//world.sortToChunks();
		
		return world;
	}
	
	public synchronized void TEMPStartClientServer() {
		String username;
		String ui = JOptionPane.showInputDialog("Would you like to start a server(y/n)", "TEMP Start server...");
		if(ui == null) {
			ui = "";
		}
		multiplayer = true;
		String localIP = "";
		try {
			localIP = InetAddress.getLocalHost().toString();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(ui.equalsIgnoreCase("y")) {
			server = new Server(this, 5006);
			client = new Client(this, localIP.split("/")[1], 5006);
			lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 64, 256, 64)));
			lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.ADMIN_BUTTON, new Location(0, 64, 256, 64)));
			lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 650 - 64, 256, 64)));
			lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.START_BUTTON, new Location(0, 650 - 64, 256, 64)));
			lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 650 - 64, 256, 64)));
			lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.START_BUTTON, new Location(0, 650 - 64, 256, 64)));
			lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 650 - 64, 256, 64)));
			lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.START_BUTTON, new Location(0, 650 - 64, 256, 64)));
		}
		else {
			client = new Client(this, localIP.split("/")[1], 5006);
		}
		username = JOptionPane.showInputDialog("What would you like your username to be", "TEMP Start server...");
		player.setName(username);
		weapon.setName("w_" + username);
		slash.setName("s_" + username);
		if(server != null) {
			server.start();
		}
		client.start();
		//NAME, TYPE ID, TEAM ID, WEAPON TYPE ID, SLASH TYPE ID 
		client.sendData((Packets.LOGIN.getID() + "✂" + username + "✂" + build).getBytes());
		client.sendData((Packets.PLAYER_DATA.getID() + "✂" + username + "✂" + player.getType().getID() + "✂" + player.getTeam().getID() + 
				"✂" + player.getEquiped().getType().getID() + "✂" + slash.getType().getID()).getBytes());
	}
	
	public void stop() throws IOException {
		//Map.generateFile("Lobby", world);
		running = false;
		if(client != null && server == null) {
			client.sendData((Packets.DISCONNECT.getID() + "✂" + player.getName()).getBytes());
		}
		else if(server != null) {
			for(String s : server.users) {
				Packets.sendPacketToUser(s, Packets.KICKED.getID() + "✂" + s + "✂Server Closed...", server);
			}
		}
		try {
			thread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 5) {
				ticks++;
				if(focused < 2) {
					tick();
				}
				delta = delta - 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(shouldRender) {
				frames++;
				if(focused < 2) {
					render();
				}
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				if(showFPS || debug) {
					System.out.println(frames + " fps, " + ticks + " tps");
				}
				fps = frames;
				tps = ticks;
				ticks = 0;
				frames = 0;
			}
		}
		
	}
	
	public void tick() {
		tickCount++;
		if(world != null) {
			Player p;
			Location sl;
			for(Entity e : world.getEntities()) {
				if(e.getControls() != null) {
					if(PlayerControls.UP.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.moveUp(world);
						if(!PlayerControls.USE.isPressed()) {
							e.setActiveAnimationNum(1);
						}
					}
					if(PlayerControls.DOWN.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.moveDown(world);
						if(!PlayerControls.USE.isPressed()) {
							e.setActiveAnimationNum(0);
						}
					}
					if(PlayerControls.RIGHT.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.moveRight(world);
						if(!PlayerControls.USE.isPressed()) {
							e.setActiveAnimationNum(2);
						}
					}
					if(PlayerControls.LEFT.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.moveLeft(world);
						if(!PlayerControls.USE.isPressed()) {
							e.setActiveAnimationNum(3);
						}
					}
					if((e.getActiveAnimationNum() == 1 || e.getActiveAnimationNum() == 5) && PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.setActiveAnimationNum(5);
						e.getEquiped().setLocation(new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270));
						e.setTopEquip(true);
						slash.setLocation(new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270));
						attackLoc = new Location(e.getLocation().getX(), e.getLocation().getY() - 64, 64, 64, 0);
					}
					else if((e.getActiveAnimationNum() == 0 || e.getActiveAnimationNum() == 4) && PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.setActiveAnimationNum(4);
						e.getEquiped().setLocation(new Location(0 + e.getLocation().getX(), 48 + e.getLocation().getY(), 64, 64, 90));
						e.setTopEquip(false);
						slash.setLocation(new Location(0 + e.getLocation().getX(), 48 + e.getLocation().getY(), 64, 64, 90));
						attackLoc = new Location(e.getLocation().getX(), e.getLocation().getY() + e.getLocation().getHeight(), 64, 64, 0);
					}
					else if((e.getActiveAnimationNum() == 2 || e.getActiveAnimationNum() == 6) && PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.setActiveAnimationNum(6);
						e.getEquiped().setLocation(new Location(32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 0));
						e.setTopEquip(true);
						slash.setLocation(new Location(32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 0));
						attackLoc = new Location(e.getLocation().getX() + e.getLocation().getWidth(), e.getLocation().getY(), 64, 64, 0);
					}
					else if((e.getActiveAnimationNum() == 3 || e.getActiveAnimationNum() == 7) && PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.setActiveAnimationNum(7);
						e.getEquiped().setLocation(new Location(-32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 180));
						e.setTopEquip(true);
						slash.setLocation(new Location(-32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 180));
						attackLoc = new Location(e.getLocation().getX(), e.getLocation().getY() - 64, 64, 64, 0);
					}
					else if(!PlayerControls.USE.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.getEquiped().setLocation(new Location(1000, 1000, 0, 0, 90));
						e.setTopEquip(false);
						e.setWalkspeed(5);
						slash.setLocation(new Location(1000, 1000, 0, 0, 90));
						if(e.getActiveAnimationNum() > 3 && e.getActiveAnimationNum() < 8) {
							e.setActiveAnimationNum(e.getActiveAnimationNum() - 4);
						}
					}
					if(attackLoc != null && PlayerControls.USE.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						if(e.getWalkspeed() != 0) {
							e.setWalkspeed(2);
							if(e instanceof Player) {
								((Player) e).attack(this, attackLoc);
							}
						}
					}
					if(e instanceof Player) {
						if(((Player) e).getHealth() < 0.0) {
							if(e.getActiveAnimationNum() != 9) {
								e.setActiveAnimationNum(8);
							}
							if(e.getActiveAnimationNum() == 8 && e.getCurrentAnimation().getFrame() >= 4) {
								e.setActiveAnimationNum(9);
								e.setWalkspeed(0);
								e.setSlowWalkspeed(0);
								hud.setOpen(false);
								overlayHud.setOpen(false);
								respawn.setOpen(true);
								overlayRespawn.setOpen(true);
								world.setMainCamera(killCam);
							}
						}
					}
				}
				e.getCurrentAnimation().run();
			}
			for(Entity e : world.getEntities()) {
				e.getCurrentAnimation().setRan(false);
				if(e instanceof Player) {
					p = (Player) e;
					if(client != null && client.packetCache != null) {
						for(int i = 0; i < client.packetCache.size(); i++) {
							packetArgs = Packets.packetArgs(client.packetCache.get(i));
							if(Packets.trimToID(client.packetCache.get(i)).equalsIgnoreCase(Packets.MOVE.getID())) {
								if(packetArgs[1].trim().equalsIgnoreCase(p.getName().trim())) {
									p.getLocation().setX(stringtoint(packetArgs[2].trim()));
									p.getLocation().setY(stringtoint(packetArgs[3].trim()));
									if(e.getActiveAnimationNum() == 4) {
										e.getEquiped().setLocation(new Location(0 + e.getLocation().getX(), 48 + e.getLocation().getY(), 64, 64, 90));
										e.setTopEquip(false);
										sl = new Location(0 + e.getLocation().getX(), 48 + e.getLocation().getY(), 64, 64, 90);
									}
									else if(e.getActiveAnimationNum() == 5) {
										e.getEquiped().setLocation(new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270));
										e.setTopEquip(true);
										sl = new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270);
									}
									else if(e.getActiveAnimationNum() == 6) {
										e.getEquiped().setLocation(new Location(32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 0));
										e.setTopEquip(true);
										sl = new Location(32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 0);
									}
									else if(e.getActiveAnimationNum() == 7) {
										e.getEquiped().setLocation(new Location(-32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 180));
										e.setTopEquip(true);
										sl = new Location(-32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 180);
									}
									else {
										e.getEquiped().setLocation(new Location(1000, 1000, 0, 0, 90));
										//e.setTopEquip(false);
										sl = new Location(1000, 1000, 0, 0, 90);
									}
									for(Entity s : world.getEntities()) {
										if(s.getName().equalsIgnoreCase("s_" + packetArgs[1].trim())) {
											s.setLocation(sl);
										}
									}
									client.packetCache.remove(i);
									i--;
								}
							}
							else if(Packets.trimToID(client.packetCache.get(i)).equalsIgnoreCase(Packets.ANIMATION.getID())) {
								if(packetArgs[1].trim().equalsIgnoreCase(p.getName().trim())) {
									p.setGhostActiveAnimationNum(stringtoint(packetArgs[2].trim()));
									if(e.getActiveAnimationNum() == 4) {
										e.getEquiped().setLocation(new Location(0 + e.getLocation().getX(), 48 + e.getLocation().getY(), 64, 64, 90));
										e.setTopEquip(false);
										sl = new Location(0 + e.getLocation().getX(), 48 + e.getLocation().getY(), 64, 64, 90);
									}
									else if(e.getActiveAnimationNum() == 5) {
										e.getEquiped().setLocation(new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270));
										e.setTopEquip(true);
										sl = new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270);
									}
									else if(e.getActiveAnimationNum() == 6) {
										e.getEquiped().setLocation(new Location(32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 0));
										e.setTopEquip(true);
										sl = new Location(32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 0);
									}
									else if(e.getActiveAnimationNum() == 7) {
										e.getEquiped().setLocation(new Location(-32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 180));
										e.setTopEquip(true);
										sl = new Location(-32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 180);
									}
									else {
										e.getEquiped().setLocation(new Location(1000, 1000, 0, 0, 90));
										e.setTopEquip(false);
										sl = new Location(1000, 1000, 0, 0, 90);
									}
									for(Entity s : world.getEntities()) {
										if(s.getName().equalsIgnoreCase("s_" + packetArgs[1].trim())) {
											s.setLocation(sl);
										}
									}
									client.packetCache.remove(i);
									i--;
								}
							}
							else if(Packets.trimToID(client.packetCache.get(i)).equalsIgnoreCase(Packets.PLAYER_DATA.getID())) {
								if(packetArgs[1].trim().equalsIgnoreCase(p.getName().trim())) {
									p.setType(EntityType.findByID(stringtoint(packetArgs[2].trim())));
									p.setTeam(Team.findByID(stringtoint(packetArgs[3].trim())));
									p.getEquiped().setType(EntityType.findByID(stringtoint(packetArgs[4].trim())));
									for(Entity s : world.getEntities()) {
										if(s.getName().equalsIgnoreCase("s_" + packetArgs[1].trim())) {
											s.setType(EntityType.findByID(stringtoint(packetArgs[5].trim())));
										}
									}
									client.packetCache.remove(i);
									i--;
								}
							}
							else if(Packets.trimToID(client.packetCache.get(i)).equalsIgnoreCase(Packets.DAMAGE.getID())) {
								for(Entity s : world.getEntities()) {
									if(s.getName().equalsIgnoreCase(packetArgs[3].trim())) {
										killCam.setEntity(s);
									}
								}
								client.packetCache.remove(i);
								i--;
							}
							else if(Packets.trimToID(client.packetCache.get(i)).equalsIgnoreCase(Packets.SHRINE_CAP.getID())) {
								for(Shrine s : world.getShrines()) {
									if(s.getID() == stringtoint(packetArgs[1].trim())) {
										s.update(Team.findByID(stringtoint(packetArgs[2].trim())));
										s.setCount(s.getResetCount());
									}
								}
								client.packetCache.remove(i);
								i--;
							}
						}
					}
					for(Shrine s : world.getShrines()) {
						if(s.getLocation().collidesWith(p.getLocation())) {
							if(p.getTeam() == Team.BLUE) {
								s.subtractCount(1.0);
							}
							else if(p.getTeam() == Team.RED) {
								s.addCount(1.0);
							}
						}
					}
				}
			}
		}
		
		for(Shrine s : world.getShrines()) {
			overlayHud.getMenuItems().set(s.getID(), Shrine.getHUDShrineItem(s, 16));
			overlayRespawn.getMenuItems().set(s.getID(), Shrine.getHUDShrineItem(s, 650 / 2 + 650 / 8));
			s.setIncreasing(0);
		}
		hud.getText().set(0, player.getHealth() + " / " + player.getMaxHealth());
		if(player.getHealth() / player.getMaxHealth() > 51.0) {
			hud.getMenuItems().get(hud.getMenuItems().size() - 1).setType(MenuItemType.HEALTH_FULL); 
		}
		else if(player.getHealth() / player.getMaxHealth() > 1.0) {
			hud.getMenuItems().get(hud.getMenuItems().size() - 1).setType(MenuItemType.HEALTH_HALF); 
		}
		else {
			hud.getMenuItems().get(hud.getMenuItems().size() - 1).setType(MenuItemType.HEALTH_EMPTY);
		}
		WIDTH = getWidth();
		HEIGHT = getHeight();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D ctx = (Graphics2D) bs.getDrawGraphics();
		
		ctx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		ctx.clearRect(0, 0, getWidth(), getHeight());
		if(world != null) {
			ctx = world.renderWorld(ctx);
		}
		if(showFPS || debug) {
			ctx.drawString(fps + " fps, " + tps + " tps", 20, 20);
			//ctx = fpsFont.renderString(ctx, fps + " fps, " + tps + " tps");
		}
		
		if(debug && selector != null) {
			//if(selectTrans == null)  {
			selectTrans = new AffineTransform();
			selectTrans.translate(650 - 64, 0);
			selectTrans.scale(64 / selector.type.getLocation().getWidth(), 64 / selector.type.getLocation().getHeight());
			selectTrans.rotate(Math.toRadians(selector.rotation), 64 / (selector.type.getImage().getLocation().getWidth() / 2), 64 / (selector.type.getImage().getLocation().getHeight() / 2));
			//}
			ctx.setColor(Color.WHITE);
			ctx.fillRect(650 - 64, 0, 64, 94);
			ctx.setColor(Color.BLACK);
			ctx.drawImage(selector.type.getImage().getTile(), selectTrans, null);
			ctx.drawString("r = "+ selector.rotation, 650 - 64, 74);
			ctx.drawString("c = " + selector.collide, 650 - 64, 84);
			ctx.setColor(Color.RED);
			//loc = new Location(engine.player.getLocation().getX() / 64 * 64, engine.player.getLocation().getY() / 64 * 64, 64, 64, rotation);
			ctx.drawRect((player.getLocation().getX() / 64 * 64) - (32) + world.getMainCamera().getLocation().getX(), (player.getLocation().getY() / 64 * 64) - (32) + world.getMainCamera().getLocation().getY(), 64, 64);
		}
		String line;
		for(Menu menu : menus) {
			if(menu.isOpen()) {
				ctx.drawImage(menu.getMenuImage().getMenuImage(), menu.getLocation().getX(), menu.getLocation().getY(), menu.getLocation().getWidth(), menu.getLocation().getHeight(), null);
				for(MenuItem item : menu.getMenuItems()) {
					ctx.drawImage(item.getType().getMenuImage().getMenuImage(), item.getClickLocation().getX(), item.getClickLocation().getY(), item.getClickLocation().getWidth(), item.getClickLocation().getHeight(), null);
					if(item.isTextOpen()) {
						for(int i = 0; i < item.getType().getDescription().size(); i++) {
							line = item.getType().getDescription().get(i);
							ctx.drawString(line, item.getTextLocation().getX(), item.getTextLocation().getY() + (item.getLineSpace() * i));
						}
					}
				}
				for(int i = 0; i < menu.getText().size(); i++) {
					ctx.drawString(menu.getText().get(i), menu.getTextLocation().get(i).getX(), menu.getTextLocation().get(i).getY());
				}
			}
		}
		ctx.setColor(Color.BLACK);
		
		//ctx.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
		//ctx.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
		
		
		ctx.dispose();
		bs.show();
		
	}
	
	public static int stringtoint(String s) {
		return Integer.parseInt(s);
	}
	
	public static double stringtodouble(String s) {
		return Double.parseDouble(s);
	}
}
