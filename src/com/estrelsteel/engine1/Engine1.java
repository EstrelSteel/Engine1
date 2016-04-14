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
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.camera.TestCameraControl;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.player.Player;
import com.estrelsteel.engine1.entitiy.player.Team;
import com.estrelsteel.engine1.entitiy.weapon.Weapon;
import com.estrelsteel.engine1.entitiy.weapon.WeaponType;
import com.estrelsteel.engine1.estrelian.Estrelian;
import com.estrelsteel.engine1.handler.CoreHandler;
import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.handler.PlayerHandler;
import com.estrelsteel.engine1.handler.PlayerHandler.PlayerControls;
import com.estrelsteel.engine1.handler.Selector;
import com.estrelsteel.engine1.maps.Gamemode;
import com.estrelsteel.engine1.maps.Map;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.menu.MenuText;
import com.estrelsteel.engine1.saves.Profile;
import com.estrelsteel.engine1.tile.TileType;
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
	private boolean hideDebugHud = false;
	private boolean showFPS = false;
	public int fps;
	public int tps;
	public int focused = 0;
	
	private Thread thread; 
	public CoreHandler coreHandler;
	public PlayerHandler playerHandler = new PlayerHandler("PLAYER");
	
	public String title = "Engine1";
	public String version = "v0.1g";
	public static int build = 7;
	public long time = System.currentTimeMillis();
	public static String filesPath = "";
	private boolean appendEstrel = false;
	
	ArrayList<World> worlds = new ArrayList<World>();
	public World world;
	public Player player = new Player(EntityType.UNKNOWN, new Location(0, 0, 64, 64), 10, true, playerHandler, "PLAYER");
	public Camera playerCamera = new Camera(new Location(0, 0, 0, 0), player);
	public Camera killCam = new Camera(new Location(0, 0, 0, 0), player);
	public TestCameraControl camControlTest = new TestCameraControl(playerCamera);
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	public WeaponType weapon = WeaponType.UNKNOWN;
	public Entity slash = new Entity(EntityType.UNKNOWN, new Location(-1000, -1000, 0, 0, 0), 10, false, null, "SLASH");
	public Location attackLoc;
	public World multiWorld = new World(WIDTH * SCALE, HEIGHT * SCALE);
	private Maps devMap = Maps.DEV;
	
	@SuppressWarnings("unused")
	private Estrelian es2 = new Estrelian();
	public Gamemode gm = Gamemode.CLASSIC;
	public Maps map = Maps.DEV;
	public boolean canWin = false;
	
	public Selector selector = new Selector("SELECTOR", this);
	private AffineTransform selectTrans;
	
	public Profile profile = new Profile();
	private int profileNum = 0;
	
	public void start() {
		if(System.getProperty("os.name").startsWith("Windows")) {
			filesPath = System.getProperty("user.home") + "/AppData/Roaming/" + title;
		}
		else if(System.getProperty("os.name").startsWith("Mac")) {
			filesPath = System.getProperty("user.home") + "/Library/Application Support/" + title;
		}
		else if(System.getProperty("os.name").startsWith("Linux")) {
			filesPath = System.getProperty("user.home") + "/" + title;
		}
		if(appendEstrel) {
			filesPath = filesPath + "_estrelsteel";
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
		
		player.setEquiped(weapon.getWeapon());
		
		player = profile.configPlayer(player);
		weapon.getWeapon().setName("w_" + player.getName());
		slash.setName("s_" + player.getName());
		
		if(!debug) {
			world = Maps.DEV.getMap().load();
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
		world.addEntity(slash);
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
	
	public void stop() throws IOException {
		if(debug) {
			Map.generateFile(map.name().substring(0, 1) + map.name().substring(1).toLowerCase(), world);
			System.out.println(map.name().substring(0, 1) + map.name().substring(1).toLowerCase());
		}
		running = false;
		
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
			for(Entity e : world.getEntities()) {
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
					if((e.getActiveAnimationNum() == 1 || e.getActiveAnimationNum() == 5) && PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
						e.setActiveAnimationNum(5);
						e.getEquiped().setLocation(new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270));
						e.setTopEquip(true);
						slash.setLocation(new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270));
						attackLoc = new Location(e.getLocation().getX(), e.getLocation().getY() - e.getLocation().getHeight(), 64, 64, 0);
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
						attackLoc = new Location(e.getLocation().getX() - e.getLocation().getWidth(), e.getLocation().getY() - 64, 64, 64, 0);
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
							if(e.getEquiped() instanceof Weapon) {
								e.setWalkspeed(2 + ((Weapon) e.getEquiped()).getWeight());
							}
							else {
								e.setWalkspeed(2);
							}
							if(e instanceof Player) {
								if(((Player) e).getEquiped() instanceof Weapon) {
									((Weapon) (((Player) e).getEquiped())).attack(this, attackLoc);
								}
							}
						}
					}
					if(e instanceof Player) {
						if(e.getEquiped() != null && e.getEquiped().getCurrentAnimation().getFrame() == 0 && e.getEquiped().getCurrentAnimation().getSoundEffect() != null
								&& e.getActiveAnimationNum() >= 4 && e.getActiveAnimationNum() <= 7) {
							e.getEquiped().getCurrentAnimation().getSoundEffect().getSound().play();
						}
						if(((Player) e).getHealth() < 0.0) {
							if(map == Maps.DEV || map == Maps.INVALID) {
								((Player) e).setHealth(((Player) e).getMaxHealth());
							}
							else {
								if(e.getActiveAnimationNum() != 9) {
									e.setActiveAnimationNum(8);
								}
								if(e.getActiveAnimationNum() == 8 && e.getCurrentAnimation().getFrame() >= 4) {
									e.setActiveAnimationNum(9);
									e.setWalkspeed(0);
									e.setSlowWalkspeed(0);
									world.setMainCamera(killCam);
								}
							}
						}
					}
				}
				e.getCurrentAnimation().run();
			}
			for(WeaponType t : WeaponType.values()) {
				t.getWeapon().getCurrentAnimation().run();
			}
			for(WeaponType t : WeaponType.values()) {
				t.getWeapon().getCurrentAnimation().setRan(false);
			}
			for(Entity e : world.getEntities()) {
				e.getCurrentAnimation().setRan(false);
			}
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
		ctx.setColor(Color.RED);
		
		ctx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		ctx.clearRect(0, 0, getWidth(), getHeight());
		
		ctx.setFont(new Font("Menlo", Font.BOLD, 16));
		if(world != null) {
			ctx = world.renderWorld(ctx);
		}
		if((showFPS || debug) && !hideDebugHud) {
			ctx.drawString(fps + " fps, " + tps + " tps", 20, 20);
		}
		if(debug && selector != null && !hideDebugHud) {
			selectTrans = new AffineTransform();
			selectTrans.translate(650 - 64, 0);
			selectTrans.scale(64 / selector.type.getLocation().getWidth(), 64 / selector.type.getLocation().getHeight());
			selectTrans.rotate(Math.toRadians(selector.rotation), 64 / (selector.type.getImage().getLocation().getWidth() / 2), 64 / (selector.type.getImage().getLocation().getHeight() / 2));
			ctx.setColor(Color.WHITE);
			ctx.fillRect(650 - 64, 0, 64, 94);
			ctx.setColor(Color.BLACK);
			ctx.drawImage(selector.type.getImage().getTile(), selectTrans, null);
			ctx.drawString("r = "+ selector.rotation, 650 - 64, 74);
			ctx.drawString("c = " + selector.collide, 650 - 64, 94);
			ctx.setColor(Color.RED);
			//loc = new Location(engine.player.getLocation().getX() / 64 * 64, engine.player.getLocation().getY() / 64 * 64, 64, 64, rotation);
			ctx.drawRect((player.getLocation().getX() / 64 * 64) - (32) + world.getMainCamera().getLocation().getX(), (player.getLocation().getY() / 64 * 64) - (32) + world.getMainCamera().getLocation().getY(), 64, 64);
		}
		String line;
		for(Menu menu : menus) {
			if(menu.isOpen()) {
				ctx.drawImage(menu.getMenuImage().getMenuImage(), menu.getLocation().getX(), menu.getLocation().getY(), menu.getLocation().getWidth(), menu.getLocation().getHeight(), null);
				for(MenuItem item : menu.getMenuItems()) {
					if(item instanceof MenuText) {
						ctx.setFont(((MenuText) item).getFont());
						ctx.setColor(((MenuText) item).getColour());
						ctx.drawString(((MenuText) item).getText(), item.getClickLocation().getX(), item.getClickLocation().getY() + item.getClickLocation().getHeight());
					}
					else {
						ctx.drawImage(item.getType().getMenuImage().getMenuImage(), item.getClickLocation().getX(), item.getClickLocation().getY(), item.getClickLocation().getWidth(), item.getClickLocation().getHeight(), null);
						if(item.isTextOpen()) {
							for(int i = 0; i < item.getType().getDescription().size(); i++) {
								line = item.getType().getDescription().get(i);
								ctx.drawString(line, item.getTextLocation().getX(), item.getTextLocation().getY() + (item.getLineSpace() * i));
							}
						}
					}
				}
			}
		}
		ctx.setColor(Color.BLACK);
		
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
