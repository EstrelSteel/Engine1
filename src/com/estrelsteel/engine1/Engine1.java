package com.estrelsteel.engine1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.camera.TestCameraControl;
import com.estrelsteel.engine1.entitiy.AlarmTrap;
import com.estrelsteel.engine1.entitiy.Animation;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityImage;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.player.Player;
import com.estrelsteel.engine1.estrelian.Estrelian;
import com.estrelsteel.engine1.handler.CoreHandler;
import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.handler.PlayerHandler;
import com.estrelsteel.engine1.handler.PlayerHandler.PlayerControls;
import com.estrelsteel.engine1.handler.Selector;
import com.estrelsteel.engine1.maps.Gamemode;
import com.estrelsteel.engine1.maps.Map;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.maps.Victory;
import com.estrelsteel.engine1.menu.EndController;
import com.estrelsteel.engine1.menu.LobbyMainController;
import com.estrelsteel.engine1.menu.LobbyMapController;
import com.estrelsteel.engine1.menu.LobbyModeController;
import com.estrelsteel.engine1.menu.LobbyVoteController;
import com.estrelsteel.engine1.menu.MainMenuController;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuImage;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.menu.RespawnController;
import com.estrelsteel.engine1.menu.WinController;
import com.estrelsteel.engine1.online.Client;
import com.estrelsteel.engine1.online.Packets;
import com.estrelsteel.engine1.online.PendingPacket;
import com.estrelsteel.engine1.online.Server;
import com.estrelsteel.engine1.saves.Profile;
import com.estrelsteel.engine1.sound.Effects;
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
	public boolean debug = false;
	private boolean hideDebugHud = false;
	private boolean showFPS = false;
	public int fps;
	public int tps;
	public int focused = 0;
	
	private Thread thread; 
	public CoreHandler coreHandler;
	public PlayerHandler playerHandler = new PlayerHandler("PLAYER");
	
	public String title = "Minotaur";
	public String version = "v1.2a";
	public static int build = 43;
	public long time = System.currentTimeMillis();
	public static String filesPath = "";
	
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
	public AlarmTrap alarmTrap = new AlarmTrap(new Location(-10000, -10000, 0, 0, 0), "ALARM_TRAP", this);
	public Location attackLoc;
	public World multiWorld = new World(WIDTH * SCALE, HEIGHT * SCALE);
	private Maps devMap = Maps.SAND_BAR;
	
	@SuppressWarnings("unused")
	private Estrelian es2 = new Estrelian();
	public static Server server;
	public static Client client;
	public static boolean multiplayer = false;
	public static Maps vote = Maps.INVALID;
	public static Gamemode gmVote = Gamemode.CLASSIC;
	private PendingPacket packet;
	public Gamemode gm = Gamemode.CLASSIC;
	public Maps map = Maps.INVALID;
	public boolean canWin = false;
	
	public Selector selector = new Selector("SELECTOR", this);
	private AffineTransform selectTrans;
	
	public Profile profile = new Profile();
	private int profileNum = 0;
	
	//public Font fpsFont;
	
	public Menu hud = new Menu("hud", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0, 0, 16, 16)));
	public Menu overlayHud = new Menu("overlayHud", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0, 0, 16, 16)));
	public Menu respawn = new Menu("respawn", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/respawn_back.png", new Location(0, 0, 65, 65)));
	public Menu overlayRespawn = new Menu("overlayRespawn", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0, 0, 16, 16)));
	public Menu lobbyMainHud = new Menu("lobbyMainHud", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0, 0, 16, 16)));
	public Menu lobbyVoteHud = new Menu("lobbyVoteHud", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0, 0, 16, 16)));
	public Menu lobbyMapHud = new Menu("lobbyMapHud", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0, 0, 16, 16)));
	public Menu lobbyModeHud = new Menu("lobbyModeHud", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0, 0, 16, 16)));
	public RespawnController respawnHandler = new RespawnController(overlayRespawn, "RespawnHandler", this);
	public Menu victory =  new Menu("victory", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/respawn_back.png", new Location(0, 0, 65, 65)));
	public Menu defeat =  new Menu("defeat", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/respawn_back.png", new Location(0, 0, 65, 65)));
	public Menu vic1text = new Menu("vic1text", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0, 0, 16, 16)));
	public Menu vic2text = new Menu("vic2text", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0, 0, 16, 16)));
	public Menu mainMenu = new Menu("mainMenu", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/lobby_hud.png", new Location(0 * 16, 0, 16, 16)));
	public Menu alarmMenu = new Menu("alarmMenu", new Location(0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0 * 16, 0, 16, 16)));
	
	public EndController victoryHandler = new EndController(victory, "VictoryHandler", this);
	public EndController defeatHandler = new EndController(defeat, "DefeatHandler", this);
	public LobbyMainController lobbyMainHandler = new LobbyMainController(lobbyMainHud, "LobbyMainHandler", this);
	public LobbyVoteController lobbyVoteHandler = new LobbyVoteController(lobbyVoteHud, "LobbyVoteHandler", this);
	public LobbyMapController lobbyMapHandler = new LobbyMapController(lobbyMapHud, "LobbyMapHandler", this);
	public LobbyModeController lobbyModeHandler = new LobbyModeController(lobbyModeHud, "LobbyMapHandler", this);
	public WinController vic1Handler = new WinController(vic1text, "vic1Handler");
	public WinController vic2Handler = new WinController(vic2text, "vic1Handler");
	public MainMenuController mainMenuController = new MainMenuController(mainMenu, "MainMenuController", this);
	
	public void start() {
		if(System.getProperty("os.name").startsWith("Windows")) {
			filesPath = System.getProperty("user.home") + "/AppData/Roaming/Minotaur";
		}
		else if(System.getProperty("os.name").startsWith("Mac")) {
			filesPath = System.getProperty("user.home") + "/Library/Application Support/Minotaur";
		}
		else if(System.getProperty("os.name").startsWith("Linux")) {
			filesPath = System.getProperty("user.home") + "/Minotaur";
		}
		String profileChoice = JOptionPane.showInputDialog("Load profile number(1-3)", "");
		if(profileChoice == null) {
			try {
				stop();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			profileNum = stringtoint(profileChoice);
		}
		catch(NumberFormatException e) {
			try {
				stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		if(profileNum > 3 || profileNum < 1) {
			System.out.println("PROFILE OUT OF BOUNDS");
			profileNum = 1;
		}
		try {
			FileReader fr = new FileReader(filesPath + "/saves/profile" + profileNum + ".cu1");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			ArrayList<String> lines = new ArrayList<String>();
			String line = br.readLine();
			while(line != null) {
				lines.add(line);
				line = br.readLine();
			}
			profile.load(lines);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		addFocusListener(coreHandler);
		
		
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
		EntityType.WALPOLE.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(2 * 16, 0 * 16, 19, 21)));
		EntityType.JOHN_SNOW.getAnimations().get(0).setMaxWait(15);
		EntityType.JOHN_SNOW.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(2 * 16, 0 * 16, 19, 21)));
		EntityType.MINOTAUR.getAnimations().get(0).setMaxWait(15);
		EntityType.MINOTAUR.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(2 * 16, 0 * 16, 19, 21)));
		EntityType.CLOUD.getAnimations().get(0).setMaxWait(7);
		EntityType.SLASH.getAnimations().get(0).setMaxWait(3);
		EntityType.SLASH_GOLD.getAnimations().get(0).setMaxWait(3);
		EntityType.SLASH_RUBY.getAnimations().get(0).setMaxWait(3);
		
		EntityType.SWORD_DIAMOND.getAnimations().get(0).setMaxWait(3);
		EntityType.SWORD_DIAMOND.getAnimations().get(0).setSoundEffect(Effects.SWORD_SWING_1);
		EntityType.SWORD_GOLD.getAnimations().get(0).setMaxWait(3);
		EntityType.SWORD_GOLD.getAnimations().get(0).setSoundEffect(Effects.SWORD_SWING_1);
		EntityType.SWORD_RUBY.getAnimations().get(0).setMaxWait(3);
		EntityType.SWORD_RUBY.getAnimations().get(0).setSoundEffect(Effects.SWORD_SWING_2);
		
		EntityType.WAR_AXE_DIAMOND.getAnimations().get(0).setMaxWait(3);
		EntityType.WAR_AXE_DIAMOND.getAnimations().get(0).setSoundEffect(Effects.AXE_SWING_1);
		EntityType.WAR_AXE_GOLD.getAnimations().get(0).setMaxWait(3);
		EntityType.WAR_AXE_GOLD.getAnimations().get(0).setSoundEffect(Effects.AXE_SWING_1);
		EntityType.WAR_AXE_RUBY.getAnimations().get(0).setMaxWait(3);
		EntityType.WAR_AXE_RUBY.getAnimations().get(0).setSoundEffect(Effects.AXE_SWING_2);
		
		EntityType.SPEAR.getAnimations().get(0).setMaxWait(3);
		EntityType.SPEAR.getAnimations().get(0).setSoundEffect(Effects.SPEAR_JAB_1);
		EntityType.BOW.getAnimations().get(0).setMaxWait(3);
		EntityType.BOW.getAnimations().get(0).setSoundEffect(Effects.BOW_SHOOT);
		EntityType.LEVER.getAnimations().get(0).setMaxWait(5);
		EntityType.LEVER.getAnimations().get(0).setSoundEffect(Effects.LEVER);
		for(int i = 0; i < 10; i++) {
			if(i > 0 && i < 4) {
				EntityType.WALPOLE.getAnimations().add(new Animation(15));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(0 * 16, 2 * i * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(2 * 16, 2 * i * 16, 19, 21)));
				
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(15));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(0 * 16, 2 * i * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(2 * 16, 2 * i * 16, 19, 21)));
				
				EntityType.MINOTAUR.getAnimations().add(new Animation(15));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(0 * 16, 2 * i * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(2 * 16, 2 * i * 16, 19, 21)));
			
			}
			if(i > 3 && i < 5) {
				EntityType.WALPOLE.getAnimations().add(new Animation(24));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 0 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().add(new Animation(24));
				EntityType.WALPOLE.getAnimations().get(i + 1).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 2 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().add(new Animation(24));
				EntityType.WALPOLE.getAnimations().get(i + 2).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 4 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().add(new Animation(24));
				EntityType.WALPOLE.getAnimations().get(i + 3).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 6 * 16, 19, 21)));
				
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(24));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 0 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(24));
				EntityType.JOHN_SNOW.getAnimations().get(i + 1).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 2 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(24));
				EntityType.JOHN_SNOW.getAnimations().get(i + 2).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 4 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(24));
				EntityType.JOHN_SNOW.getAnimations().get(i + 3).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 6 * 16, 19, 21)));
				
				EntityType.MINOTAUR.getAnimations().add(new Animation(24));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 0 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().add(new Animation(24));
				EntityType.MINOTAUR.getAnimations().get(i + 1).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 2 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().add(new Animation(24));
				EntityType.MINOTAUR.getAnimations().get(i + 2).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 4 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().add(new Animation(24));
				EntityType.MINOTAUR.getAnimations().get(i + 3).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 6 * 16, 19, 21)));
			}
			if(i > 7 && i < 9) {
				EntityType.WALPOLE.getAnimations().add(new Animation(5));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(5));
				EntityType.MINOTAUR.getAnimations().add(new Animation(5));
				
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(0 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(2 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(4 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(6 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));

				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(0 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(2 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(4 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(6 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(0 * 16, 8 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(2 * 16, 8 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(4 * 16, 8 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(6 * 16, 8 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
			}
			if(i > 8 && i < 10) {
				EntityType.WALPOLE.getAnimations().add(new Animation(5));
				EntityType.JOHN_SNOW.getAnimations().add(new Animation(5));
				EntityType.MINOTAUR.getAnimations().add(new Animation(5));
				
				EntityType.WALPOLE.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/robert_walpole_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));

				EntityType.JOHN_SNOW.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/john_snow_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
				
				EntityType.MINOTAUR.getAnimations().get(i).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/minotaur_sheet.png", new Location(8 * 16, 8 * 16, 19, 21)));
			}
			if(i > -1 && i < 5) {
				EntityType.CLOUD.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/particle.png", new Location(i * 16, 0 * 16, 16, 16)));
			}
			if(i > 0 && i < 7) {
				EntityType.LEVER.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/aeris.png", new Location(i * 16, 0 * 16, 16, 16)));	
			}
			if(i > 0 && i < 8) {
				EntityType.SWORD_DIAMOND.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/weapon.png", new Location(i * 16, 0 * 16, 16, 16)));
				EntityType.SWORD_GOLD.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/weapon.png", new Location(i * 16, 1 * 16, 16, 16)));
				EntityType.SWORD_RUBY.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/weapon.png", new Location(i * 16, 2 * 16, 16, 16)));
				EntityType.WAR_AXE_DIAMOND.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/weapon.png", new Location(i * 16, 3 * 16, 16, 16)));
				EntityType.WAR_AXE_GOLD.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/weapon.png", new Location(i * 16, 4 * 16, 16, 16)));
				EntityType.WAR_AXE_RUBY.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/weapon.png", new Location(i * 16, 5 * 16, 16, 16)));
				
				EntityType.BOW.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/weapon.png", new Location(i * 16, 7 * 16, 16, 16)));
				
				EntityType.SLASH.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/particle.png", new Location(i * 16, 1 * 16, 16, 16)));
				EntityType.SLASH_GOLD.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/particle.png", new Location(i * 16, 2 * 16, 16, 16)));
				EntityType.SLASH_RUBY.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/particle.png", new Location(i * 16, 3 * 16, 16, 16)));
			
				EntityType.SPEAR.getAnimations().get(0).getImages().add(new EntityImage(Engine1.filesPath + "/assets/res/img/weapon.png", new Location(i * 32, 6 * 16, 32, 16)));
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
		
		//hud.addMenuItem(new MenuItem(MenuItemType.MOUSE, new Location(0, 650 - 192, 128, 256)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_ONE, new Location(144 + (60) * 0, 650 - 140, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_TWO, new Location(144 + (60) * 1, 650 - 140, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_THREE, new Location(144 + (60) * 2, 650 - 140, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_FOUR, new Location(144 + (60) * 3, 650 - 140, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_FIVE, new Location(144 + (60) * 4, 650 - 140, 64, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.KEY_SPACE, new Location(144 + (60) * 0, 650 - 80, 300, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.SHRINE_METER, new Location(37, 16, 576, 64)));
		hud.addMenuItem(new MenuItem(MenuItemType.HEALTH_FULL, new Location(144 + (60) * 5, 650 - 80, 64, 64)));
		hud.addText("health", new Location(144 + (60) * 5 + 64, 650 - 40, 0, 0));
		hud.setOpen(false, this);
		menus.add(hud);
		
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_B, new Location(37 + (128 * 0), 16, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_B, new Location(37 + (128 * 1), 16, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_N, new Location(37 + (128 * 2), 16, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_R, new Location(37 + (128 * 3), 16, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SHRINE_R, new Location(37 + (128 * 4), 16, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.SWORD_DIAMOND_HUD, new Location(144 + (60) * 0 + 150 - 32, 650 - 80, 64, 64)));
		overlayHud.addMenuItem(new MenuItem(MenuItemType.ALARM_TRAP_BUTTON, new Location(144 + (60) * 0 + 16, 650 - 140 + 4, 48, 48)));
		overlayHud.setOpen(false, this);
		menus.add(overlayHud);
		
		respawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_METER, new Location(37, 650 / 2 + 650 / 8, 576, 64)));
		respawn.addMenuItem(new MenuItem(MenuItemType.RESPAWN_TEXT, new Location((650 - 512) / 2, 650 / 2 + 650 / 4, 512, 32)));
		respawn.addMenuItem(new MenuItem(MenuItemType.YOU_DIED_TEXT, new Location((650  - 512) / 2, 650 / 3, 512, 64)));
		respawn.setOpen(false, this);
		menus.add(respawn);
		
		alarmMenu.addMenuItem(new MenuItem(MenuItemType.ALARM_CLOCK_ICON, new Location(32, 96, 64, 64, 0)));
		alarmMenu.setOpen(false, this);
		menus.add(alarmMenu);

		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_B, new Location(37 + (128 * 0), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_B, new Location(37 + (128 * 1), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_N, new Location(37 + (128 * 2), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_R, new Location(37 + (128 * 3), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.addMenuItem(new MenuItem(MenuItemType.SHRINE_R, new Location(37 + (128 * 4), 650 / 2 + 650 / 8, 64, 64)));
		overlayRespawn.setController(respawnHandler);
		overlayRespawn.setOpen(false, this);
		menus.add(overlayRespawn);
		
		victory.addMenuItem(new MenuItem(MenuItemType.VICTORY_TEXT, new Location((650  - 512) / 2, 650 / 3, 512, 64)));
		victory.addMenuItem(new MenuItem(MenuItemType.LOBBY_TEXT, new Location((650 - 160) / 2, 650 / 2 + 650 / 8, 160, 32)));
		victory.addMenuItem(new MenuItem(MenuItemType.QUIT_TEXT, new Location((650 - 160) / 2, 650 / 2 + 650 / 4, 160, 32)));
		victory.setController(victoryHandler);
		victory.setOpen(false, this);
		menus.add(victory);
		
		defeat.addMenuItem(new MenuItem(MenuItemType.DEFEAT_TEXT, new Location((650  - 512) / 2, 650 / 3, 512, 64)));
		defeat.addMenuItem(new MenuItem(MenuItemType.LOBBY_TEXT, new Location((650 - 160) / 2, 650 / 2 + 650 / 8, 160, 32)));
		defeat.addMenuItem(new MenuItem(MenuItemType.QUIT_TEXT, new Location((650 - 160) / 2, 650 / 2 + 650 / 4, 160, 32)));
		defeat.setController(defeatHandler);
		defeat.setOpen(false, this);
		menus.add(defeat);
		
		lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 0, 256, 64)));
		lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.VOTE_BUTTON, new Location(0, 0, 256, 64)));
		lobbyMainHud.setController(lobbyMainHandler);
		lobbyMainHud.setOpen(false, this);
		menus.add(lobbyMainHud);
		
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 0, 256, 64)));
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.BACK_BUTTON, new Location(0, 0, 256, 64)));
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 64, 256, 64)));
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.MAPS_BUTTON, new Location(0, 64, 256, 64)));
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 128, 256, 64)));
		lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.MODE_BUTTON, new Location(0, 128, 256, 64)));
		lobbyVoteHud.setController(lobbyVoteHandler);
		lobbyVoteHud.setOpen(false, this);
		menus.add(lobbyVoteHud);
		
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 0, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BACK_BUTTON, new Location(0, 0, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 64, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.MINES_BUTTON, new Location(0, 64, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 128, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.ISLAND_BUTTON, new Location(0, 128, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(256, 128, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.ISLAND_LOOP_BUTTON, new Location(256, 128, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 192, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.SAND_BAR_BUTTON, new Location(0, 192, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 256, 256, 64)));
		lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.ZIG_ZAG_BUTTON, new Location(0, 256, 256, 64)));
		lobbyMapHud.setController(lobbyMapHandler);
		lobbyMapHud.setOpen(false, this);
		menus.add(lobbyMapHud);
		
		lobbyModeHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 0, 256, 64)));
		lobbyModeHud.addMenuItem(new MenuItem(MenuItemType.BACK_BUTTON, new Location(0, 0, 256, 64)));
		lobbyModeHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 64, 256, 64)));
		lobbyModeHud.addMenuItem(new MenuItem(MenuItemType.CLASSIC_BUTTON, new Location(0, 64, 256, 64)));
		lobbyModeHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 128, 256, 64)));
		lobbyModeHud.addMenuItem(new MenuItem(MenuItemType.REVERSE_BUTTON, new Location(0, 128, 256, 64)));
		lobbyModeHud.setController(lobbyModeHandler);
		lobbyModeHud.setOpen(false, this);
		menus.add(lobbyModeHud);
		
		vic1text.addMenuItem(new MenuItem(MenuItemType.TEAM, new Location((650 - 512) / 2, (650 / 6), 512, 32)));
		vic1text.addMenuItem(new MenuItem(MenuItemType.TEAM_BLUE_COLOUR, new Location((650 - 256) / 2, (650 / 6) + 64, 256, 64)));
		vic1text.addMenuItem(new MenuItem(MenuItemType.VIC_1_TEXT, new Location((650 - 512) / 2, (650 / 2), 512, 96)));
		vic1text.addMenuItem(new MenuItem(MenuItemType.PRESS_ENTER, new Location((650 - 352) / 2, 650 - (650 / 8), 352, 32)));
		vic1text.setController(vic1Handler);
		vic1text.setOpen(false, this);
		menus.add(vic1text);
		
		vic2text.addMenuItem(new MenuItem(MenuItemType.TEAM, new Location((650 - 512) / 2, (650 / 6), 512, 32)));
		vic2text.addMenuItem(new MenuItem(MenuItemType.TEAM_RED_COLOUR, new Location((650 - 256) / 2, (650 / 6) + 64, 256, 64)));
		vic2text.addMenuItem(new MenuItem(MenuItemType.VIC_2_TEXT, new Location((650 - 512) / 2, (650 / 2), 512, 64)));
		vic2text.addMenuItem(new MenuItem(MenuItemType.PRESS_ENTER, new Location((650 - 352) / 2, 650 - (650 / 8), 352, 32)));
		vic2text.setController(vic2Handler);
		vic2text.setOpen(false, this);
		menus.add(vic2text);
		
		mainMenu.addMenuItem(new MenuItem(MenuItemType.MINOTAUR_TITLE, new Location((WIDTH - 576) / 2, 64, 576, 64)));
		mainMenu.addMenuItem(new MenuItem(MenuItemType.MULTIPLAYER_BUTTON, new Location(40, (HEIGHT) / 3, 352, 32)));
		mainMenu.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(80, (HEIGHT) / 3 + 48, 288, 32)));
		mainMenu.addMenuItem(new MenuItem(MenuItemType.HOST_BUTTON, new Location(80, (HEIGHT) / 3 + 48, 288, 32)));
		mainMenu.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(80, (HEIGHT) / 3 + 96, 288, 32)));
		mainMenu.addMenuItem(new MenuItem(MenuItemType.CONNECT_BUTTON, new Location(80, (HEIGHT) / 3 + 96, 288, 32)));
		mainMenu.addMenuItem(new MenuItem(MenuItemType.ESTREL_ICON, new Location(WIDTH - 80, HEIGHT - 80, 64, 64)));
		mainMenu.setController(mainMenuController);
		mainMenu.setOpen(true, this);
		menus.add(mainMenu);
		
		player.setEquiped(weapon);
		statictest.addEntity(slash);
		statictest.addEntity(player);
		statictest.addPlayer(player);
		
		statictest.setMainCamera(playerCamera);
		statictest.sortToChunks();
		worlds.add(statictest);
		
		player = profile.configPlayer(player);
		weapon.setName("w_" + player.getName());
		slash.setName("s_" + player.getName());
		alarmTrap.setName("at_" + player.getName());
		alarmTrap.setParent(player);
		
		//fpsFont = new Font();
		//fpsFont.getTextLocation().setWidth(128);
		if(!debug) {
			world = Maps.LOBBY.getMap().load();
		}
		else {
			world = devMap.getMap().load();
			map = devMap;
			for(Menu m : menus) {
				m.setOpen(false);
			}
		}
		world = addBasics(world);
		worlds.add(world);
		Handler.loadHandlers(this, worlds);
		//TEMPStartClientServer();
		EntityType.updateSRC(filesPath);
		TileType.updateSRC(filesPath);
		MenuItemType.updateSRC(filesPath);
		//Effects.updateSRC(filesPath);
		for(Menu menu : menus) {
			menu.getMenuImage().setSRC(filesPath + menu.getMenuImage().getSRC());
		}
		running = true;
		thread = new Thread(this, title + version + "_main");
		thread.start();
	}
	
	public World addBasics(World world) {
		//ENTITIES
		world.addEntity(alarmTrap);
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
	
	public void updateHelpMenu() {
		for(int i = 0; i < vic1text.getMenuItems().size(); i++) {
			if(vic1text.getMenuItems().get(i).getType().getID() >= 44 && vic1text.getMenuItems().get(i).getType().getID() <= 47) {
				if(player.getTeam() == Team.BLUE) {
					vic1text.getMenuItems().get(i).setType(MenuItemType.TEAM_BLUE_COLOUR);
					break;
				}
				else if(player.getTeam() == Team.RED) {
					vic1text.getMenuItems().get(i).setType(MenuItemType.TEAM_RED_COLOUR);
					break;
				}
			}
		}
		for(int i = 0; i < vic2text.getMenuItems().size(); i++) {
			if(vic2text.getMenuItems().get(i).getType().getID() >= 44 && vic2text.getMenuItems().get(i).getType().getID() <= 47) {
				if(player.getTeam() == Team.BLUE) {
					vic2text.getMenuItems().get(i).setType(MenuItemType.TEAM_BLUE_COLOUR);
					break;
				}
				else if(player.getTeam() == Team.RED) {
					vic2text.getMenuItems().get(i).setType(MenuItemType.TEAM_RED_COLOUR);
					break;
				}
			}
		}
	}
	
	public void StartClientServer(boolean startServer) {
		multiplayer = true;
		String ip = "";
		String portStr = "";
		int port = 5006;
		if(!startServer) {
			ip = JOptionPane.showInputDialog("Enter the IP Address", "0.0.0.0");
		}
		if(ip == null) {
			return;
		}
		portStr = JOptionPane.showInputDialog("Enter the port", "5006");
		if(portStr == null) {
			return;
		}
		if(!portStr.equalsIgnoreCase("")) {
			try {
				port = Integer.parseInt(portStr);
			}
			catch(NumberFormatException e) {
				return;
			}
			if(port < 0) {
				port = 5006;
			}
		}
		else {
			port = 5006;
		}
		if(ip.equalsIgnoreCase("")|| ip.equalsIgnoreCase("0.0.0.0") || ip.equalsIgnoreCase("localhost")) {
			try {
				ip = InetAddress.getLocalHost().toString();
				ip = ip.split("/")[1];
			}
			catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		if(startServer) {
			server = new Server(this, port);
			client = new Client(this, ip, port);
			lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 64, 256, 64)));
			lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.ADMIN_BUTTON, new Location(0, 64, 256, 64)));
			lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 650 - 64, 256, 64)));
			lobbyMainHud.addMenuItem(new MenuItem(MenuItemType.START_BUTTON, new Location(0, 650 - 64, 256, 64)));
			lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 650 - 64, 256, 64)));
			lobbyVoteHud.addMenuItem(new MenuItem(MenuItemType.START_BUTTON, new Location(0, 650 - 64, 256, 64)));
			lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 650 - 64, 256, 64)));
			lobbyMapHud.addMenuItem(new MenuItem(MenuItemType.START_BUTTON, new Location(0, 650 - 64, 256, 64)));
			lobbyModeHud.addMenuItem(new MenuItem(MenuItemType.BUTTON_NOT_SELECTED, new Location(0, 650 - 64, 256, 64)));
			lobbyModeHud.addMenuItem(new MenuItem(MenuItemType.START_BUTTON, new Location(0, 650 - 64, 256, 64)));
		}
		else {
			client = new Client(this, ip, port);
		}
		if(server != null) {
			server.start();
		}
		client.start();
		client.sendData((Packets.LOGIN.getID() + Packets.SPLIT.getID() + player.getName() + Packets.SPLIT.getID() + build).getBytes());
		client.sendData((Packets.PLAYER_DATA.getID() + Packets.SPLIT.getID() + player.getName() + Packets.SPLIT.getID() + player.getType().getID() + Packets.SPLIT.getID() + player.getTeam().getID() + 
				Packets.SPLIT.getID() + player.getEquiped().getType().getID() + Packets.SPLIT.getID() + slash.getType().getID()).getBytes());
	}
	
	public void stop() throws IOException {
		if(debug) {
			Map.generateFile(map.name().substring(0, 1) + map.name().substring(1).toLowerCase(), world);
			System.out.println(map.name().substring(0, 1) + map.name().substring(1).toLowerCase());
		}
		running = false;
		if(client != null && server == null) {
			client.sendData((Packets.DISCONNECT.getID() + Packets.SPLIT.getID() + player.getName()).getBytes());
		}
		else if(server != null) {
			for(String s : server.users) {
				Packets.sendPacketToUser(s, Packets.KICKED.getID() + Packets.SPLIT.getID() + s + Packets.SPLIT.getID() + "Server Closed...", server);
			}
		}
		try {
			FileWriter fw = new FileWriter(filesPath + "/saves/profile" + profileNum + ".cu1");
			BufferedWriter bw = new BufferedWriter(fw);
			ArrayList<String> lines = profile.save();
			for(String line : lines) {
				bw.write(line + "\n");
			}
			bw.flush();
			bw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(thread != null) {
			try {
				thread.join();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
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
		boolean moved = false;
		@SuppressWarnings("unused")
		int lastAnimation = -1;
		
		tickCount++;
		if(world != null) {
			Team vicTeam = Victory.checkVicotry(world, gm);
			if(vicTeam != Team.OFF && client != null) {
				if(map != Maps.INVALID && map != Maps.LOBBY && !victory.isOpen() && !defeat.isOpen() && !vic1text.isOpen() && !vic2text.isOpen() && canWin) {
					client.sendData((Packets.VICTORY.getID() + Packets.SPLIT.getID() + vicTeam.getID()).getBytes());
				}
			}
			Location sl;
			for(Entity e : world.getEntities()) {
				if(e instanceof AlarmTrap) {
					((AlarmTrap) e).act(world);
				}
				lastAnimation = e.getActiveAnimationNum();
				if(e.getControls() != null) {
					if(PlayerControls.UP.isPressed() && e.getActiveAnimationNum() != 9  && e.getActiveAnimationNum() != 8 && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						if(!moved) {
							moved = e.moveUp(world);
						}
						else {
							e.moveUp(world);
						}
						if(!PlayerControls.USE.isPressed()) {
							e.setActiveAnimationNum(1);
						}
					}
					if(PlayerControls.DOWN.isPressed() && e.getActiveAnimationNum() != 9  && e.getActiveAnimationNum() != 8 && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						if(!moved) {
							moved = e.moveDown(world);
						}
						else {
							e.moveDown(world);
						}
						if(!PlayerControls.USE.isPressed()) {
							e.setActiveAnimationNum(0);
						}
					}
					if(PlayerControls.RIGHT.isPressed() && e.getActiveAnimationNum() != 9  && e.getActiveAnimationNum() != 8 && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						if(!moved) {
							moved = e.moveRight(world);
						}
						else {
							e.moveRight(world);
						}
						if(!PlayerControls.USE.isPressed()) {
							e.setActiveAnimationNum(2);
						}
					}
					if(PlayerControls.LEFT.isPressed() && e.getActiveAnimationNum() != 9  && e.getActiveAnimationNum() != 8 && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						if(!moved) {
							moved = e.moveLeft(world);
						}
						else {
							e.moveLeft(world);
						}
						if(!PlayerControls.USE.isPressed()) {
							e.setActiveAnimationNum(3);
						}
					}
					if(PlayerControls.ONE.isPressed() && hud.isOpen()) {
						alarmTrap.setTeam(player.getTeam());
						alarmTrap.moveLocation(player.getLocation());
						Effects.ALARM_TRAP_PLACE.getSound().play();
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
						if(e instanceof Player) {
							e.setWalkspeed(((Player) e).getNormalWalkspeed());
						}
						else {
							e.setWalkspeed(5);
						}
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
						if(e.getEquiped() != null && e.getEquiped().getCurrentAnimation().getFrame() == 0 && e.getEquiped().getCurrentAnimation().getSoundEffect() != null
								&& e.getActiveAnimationNum() >= 4 && e.getActiveAnimationNum() <= 7) {
							e.getEquiped().getCurrentAnimation().getSoundEffect().getSound().play();
						}
						if(((Player) e).getHealth() < 0.0) {
							if(e.getActiveAnimationNum() != 9) {
								e.setActiveAnimationNum(8);
							}
							if(e.getActiveAnimationNum() == 8 && e.getCurrentAnimation().getFrame() >= 4) {
								e.setActiveAnimationNum(9);
								e.setWalkspeed(0);
								e.setSlowWalkspeed(0);
								world.setMainCamera(killCam);
								hud.setOpen(false, this);
								overlayHud.setOpen(false, this);
								if(player.getType() == EntityType.MINOTAUR && map != Maps.INVALID && map != Maps.LOBBY && !victory.isOpen() && !defeat.isOpen() && !vic1text.isOpen() && !vic2text.isOpen() && canWin) {
									client.sendData((Packets.VICTORY.getID() + Packets.SPLIT.getID() + Team.getOpposedTeam(player.getTeam()).getID()).getBytes());
								}
								else {
									respawn.setOpen(true, this);
									overlayRespawn.setOpen(true, this);
								}
							}
						}
					}
				}
				e.getCurrentAnimation().run();
			}
			for(Entity e : world.getEntities()) {
				e.getCurrentAnimation().setRan(false);
				if(client != null && client.packetCache != null) {
					for(int i = 0; i < client.packetCache.size(); i++) {
						packet = client.packetCache.get(i);
						if(packet == null) {
							packet = new PendingPacket(Packets.INVALID.getID(), true, 0);
						}
						if(packet.shouldDelete()) {
							client.packetCache.remove(i);
							i--;
						}
						if(Packets.trimToID(packet.getMessage()).equalsIgnoreCase(Packets.MOVE.getID())) {
							if(packet.getPacketArgs()[1].trim().equalsIgnoreCase(e.getName().trim())) {
								e.getLocation().setX(stringtoint(packet.getPacketArgs()[2].trim()));
								e.getLocation().setY(stringtoint(packet.getPacketArgs()[3].trim()));
								sl = null;
								if(e instanceof Player) {
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
								}
								for(Entity s : world.getEntities()) {
									if(sl != null && s.getName().equalsIgnoreCase("s_" + packet.getPacketArgs()[1].trim())) {
										s.setLocation(sl);
									}
									else if(s.getName().equalsIgnoreCase(packet.getPacketArgs()[1].trim().substring(3))) {
										if(e instanceof AlarmTrap && s instanceof Player && ((AlarmTrap) e) != alarmTrap) {
											((AlarmTrap) e).setTeam(((Player) s).getTeam());
											((AlarmTrap) e).ghostMoveLocation(((Player) s).getLocation());
										}
									}
								}
								client.packetCache.remove(i);
								i--;
							}
						}
						else if(Packets.trimToID(packet.getMessage()).equalsIgnoreCase(Packets.ANIMATION.getID())) {
							if(e instanceof Player) {
								if(packet.getPacketArgs()[1].trim().equalsIgnoreCase(e.getName().trim())) {
									((Player) e).setGhostActiveAnimationNum(stringtoint(packet.getPacketArgs()[2].trim()));
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
										if(s.getName().equalsIgnoreCase("s_" + packet.getPacketArgs()[1].trim())) {
											s.setLocation(sl);
										}
									}
									client.packetCache.remove(i);
									i--;
								}
							}
						}
						else if(Packets.trimToID(packet.getMessage()).equalsIgnoreCase(Packets.PLAYER_DATA.getID())) {
							if(e instanceof Player) {
								if(packet.getPacketArgs()[1].trim().equalsIgnoreCase(e.getName().trim()) && !e.getName().equalsIgnoreCase(player.getName())) {
									e.setType(EntityType.findByID(stringtoint(packet.getPacketArgs()[2].trim())));
									((Player) e).setTeam(Team.findByID(stringtoint(packet.getPacketArgs()[3].trim())));
									e.getEquiped().setType(EntityType.findByID(stringtoint(packet.getPacketArgs()[4].trim())));
									updateHelpMenu();
									for(Entity s : world.getEntities()) {
										if(s.getName().equalsIgnoreCase("s_" + packet.getPacketArgs()[1].trim())) {
											s.setType(EntityType.findByID(stringtoint(packet.getPacketArgs()[5].trim())));
										}
									}
									if(e.getName().equalsIgnoreCase(player.getName())) {
										if(player.getEquiped().getType().getMenuItemType() != MenuItemType.UNKNOWN) {
											overlayHud.getMenuItems().get(5).setType(player.getEquiped().getType().getMenuItemType());
										}
									}
									client.packetCache.remove(i);
									i--;
								}
							}
						}
						else if(Packets.trimToID(packet.getMessage()).equalsIgnoreCase(Packets.DAMAGE.getID())) {
							for(Entity s : world.getEntities()) {
								if(s.getName().equalsIgnoreCase(packet.getPacketArgs()[3].trim())) {
									killCam.setEntity(s);
								}
							}
							client.packetCache.remove(i);
							i--;
						}
						else if(Packets.trimToID(packet.getMessage()).equalsIgnoreCase(Packets.SHRINE_CAP.getID())) {
							for(Shrine s : world.getShrines()) {
								if(s.getID() == stringtoint(packet.getPacketArgs()[1].trim())) {
									s.update(Team.findByID(stringtoint(packet.getPacketArgs()[2].trim())));
									s.setCount(s.getResetCount());
								}
							}
							client.packetCache.remove(i);
							i--;
						}
						else if(Packets.trimToID(packet.getMessage()).equalsIgnoreCase(Packets.MAP.getID())) {
							if(e instanceof Player) {
								canWin = true;
								map = Maps.findByID(Engine1.stringtoint(packet.getPacketArgs()[1].trim()));
								world = map.getMap().load();
								world = addBasics(world);
								gm = Gamemode.findByID(stringtoint(packet.getPacketArgs()[2].trim()));
								if(gm == Gamemode.REVERSE) {
									if(Maps.findByID(Engine1.stringtoint(packet.getPacketArgs()[1].trim())) != Maps.LOBBY) {
										updateHelpMenu();
										if(player.getTeam() == Team.BLUE) {
											vic2text.setOpen(true, this);
										}
										else if(player.getTeam() == Team.RED) {
											vic1text.setOpen(true, this);
										}
									}
								}
								else {
									if(Maps.findByID(Engine1.stringtoint(packet.getPacketArgs()[1].trim())) != Maps.LOBBY) {
										if(player.getTeam() == Team.BLUE) {
											vic1text.setOpen(true, this);
										}
										else if(player.getTeam() == Team.RED) {
											vic2text.setOpen(true, this);
										}
									}
								}
								if(player.getTeam() == Team.RED) {
									player.setHealth(player.getMaxHealth());
									player.setActiveAnimationNum(0);
									player.getLocation().setX(world.getShrines().get(4).getLocation().getX());
									player.getLocation().setY(world.getShrines().get(4).getLocation().getY());
									player.moveDown(world);
									player.setWalkspeed(5);
									player.setSlowWalkspeed(1);
								}
								else if(player.getTeam() == Team.BLUE) {
									player.setHealth(player.getMaxHealth());
									player.setActiveAnimationNum(0);
									player.getLocation().setX(world.getShrines().get(0).getLocation().getX());
									player.getLocation().setY(world.getShrines().get(0).getLocation().getY());
									player.moveDown(world);
									player.setWalkspeed(5);
									player.setSlowWalkspeed(1);
								}
								else {
									player.setHealth(player.getMaxHealth());
									player.setActiveAnimationNum(0);
									player.getLocation().setX(world.getShrines().get(2).getLocation().getX());
									player.getLocation().setY(world.getShrines().get(2).getLocation().getY());
									player.moveDown(world);
									player.setWalkspeed(5);
									player.setSlowWalkspeed(1);
								}
								client.packetCache.remove(i);
								i--;
							}
						}
					}
				}
				for(Shrine s : world.getShrines()) {
					if(e instanceof Player) {
						if(s.getLocation().collidesWith(e.getLocation())) {
							if(((Player) e).getActiveAnimationNum() != 9) {
								if(((Player) e).getTeam() == Team.BLUE) {
									s.subtractCount(1.0);
								}
								else if(((Player) e).getTeam() == Team.RED) {
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
		}
		
		hud.getText().set(0, (int) player.getHealth() + "/" + (int) player.getMaxHealth());
		if(player.getHealth() / player.getMaxHealth() < .1) {
			hud.getMenuItems().get(hud.getMenuItems().size() - 1).setType(MenuItemType.HEALTH_EMPTY); 
		}
		else if(player.getHealth() / player.getMaxHealth() < .51) {
			hud.getMenuItems().get(hud.getMenuItems().size() - 1).setType(MenuItemType.HEALTH_HALF); 
		}
		else {
			hud.getMenuItems().get(hud.getMenuItems().size() - 1).setType(MenuItemType.HEALTH_FULL);
		}
		WIDTH = getWidth();
		HEIGHT = getHeight();
		if(moved && multiplayer) {
			client.sendData((Packets.MOVE.getID() + Packets.SPLIT.getID() + player.getName() + Packets.SPLIT.getID() + player.getLocation().getX() + Packets.SPLIT.getID() + player.getLocation().getY()).getBytes());
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D ctx = (Graphics2D) bs.getDrawGraphics();
		ctx.setFont(new Font("Menlo", Font.BOLD, 16));
		ctx.setColor(Color.RED);
		
		ctx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		ctx.clearRect(0, 0, getWidth(), getHeight());
		if(world != null) {
			ctx = world.renderWorld(ctx);
		}
		if((showFPS || debug) && !hideDebugHud) {
			ctx.drawString(fps + " fps, " + tps + " tps", 20, 20);
			//ctx = fpsFont.renderString(ctx, fps + " fps, " + tps + " tps");
		}
		
		if(debug && selector != null && !hideDebugHud) {
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
