package com.estrelsteel.engine1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;

import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.camera.TestCameraControl;
import com.estrelsteel.engine1.entitiy.Animation;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityImage;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.estrelian.Estrelian;
import com.estrelsteel.engine1.font.Font;
import com.estrelsteel.engine1.handler.CoreHandler;
import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.handler.PlayerHandler;
import com.estrelsteel.engine1.handler.PlayerHandler.PlayerControls;
import com.estrelsteel.engine1.handler.Selector;
import com.estrelsteel.engine1.maps.Map;
import com.estrelsteel.engine1.maps.Mine;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuImage;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.online.Client;
import com.estrelsteel.engine1.online.Server;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.world.Chunk;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class Engine1 extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	public static final double SCALE = 1.625;
	public static int WIDTH = 400;
	public static final int startWidth = (int) (WIDTH * SCALE);
	public static int HEIGHT = 400;
	public static final int startHeight = (int) (HEIGHT * SCALE);
	public static Dimension dimension = new Dimension((int) (WIDTH * Engine1.SCALE), (int) (HEIGHT * Engine1.SCALE));
	
	public boolean running = false;
	public boolean applet = false;
	
	public int tickCount = 0;
	public int frames;
	public boolean debug = true;
	private boolean showFPS = false;
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
	public Entity player = new Entity(EntityType.WALPOLE, new Location(0, 0, 64, 64), 5, true, playerHandler, "PLAYER");
	public Camera playerCamera = new Camera(new Location(0, 0, 0, 0), player);
	public TestCameraControl camControlTest = new TestCameraControl(playerCamera);
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	public Entity weapon = new Entity(EntityType.SWORD_DIAMOND, new Location(-1000, -1000, 0, 0, 0), 5, false, null, "WEAPON");
	public Entity slash = new Entity(EntityType.SLASH, new Location(-1000, -1000, 0, 0, 0), 5, false, null, "SLASH");
	
	public World staticMines = new World(WIDTH * SCALE, HEIGHT * SCALE);
	public World mines = staticMines;
	
	@SuppressWarnings("unused")
	private Estrelian es2 = new Estrelian();
	public Server server;
	public Client client;
	
	public Selector selector = new Selector("SELECTOR", this);
	private AffineTransform selectTrans;
	
	public Font fpsFont;
	
	public Mine mine = new Mine();
	
	public Menu hud = new Menu("hud", new Location(0, 0, 650, 650), new MenuImage("/com/estrelsteel/engine1/res/texture.png", new Location(0, 0, 16, 16)));
	
	public void start() {
		running = true;

		addFocusListener(coreHandler);
		thread = new Thread(this, title + version + "_main");
		thread.start();
		
		playerCamera.setFollowX(true);
		playerCamera.setFollowY(true);
		playerCamera.setCameraController(camControlTest);
		player.setSlowWalkspeed(1);
		
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
		for(int i = 0; i < 8; i++) {
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
		hud.setOpen(false);
		menus.add(hud);
		
		player.setEquiped(weapon);
//		statictest.addTile(new Tile(TileType.TREE_PINE_TOP, new Location(200, 200, 64, 64, 0), false, null));
//		statictest.addTile(new Tile(TileType.TREE_PINE_BOTTOM, new Location(200, 264, 64, 64, 0), false, null));
//		statictest.addTile(new Tile(TileType.TREE_TOP, new Location(264, 200, 64, 64, 0), false, null));
//		statictest.addTile(new Tile(TileType.TREE_BOTTOM, new Location(264, 264, 64, 64, 0), false, null));
		statictest.addEntity(slash);
		statictest.addEntity(player);
		
//		statictest.addEntity(new Entity(EntityType.SLASH_RUBY, new Location(400, 400, 64, 64), 0, true, null, "SLASH"));
//		statictest.addEntity(new Entity(EntityType.SWORD_RUBY, new Location(400, 400, 64, 64), 0, true, null, "SWORD"));
//		statictest.addEntity(new Entity(EntityType.CLOUD, new Location(336, 336, 64, 64), 0, true, null, "CLOUD"));
		statictest.addCamera(playerCamera);
		statictest.setMainCamera(playerCamera);
		statictest.sortToChunks();
		worlds.add(statictest);
		
		fpsFont = new Font();
		fpsFont.getTextLocation().setWidth(128);
		
		world = mine.load();
		world = addBasics(world);
		Handler.loadHandlers(this, worlds);
	}
	
	public World addBasics(World world) {
		//ENTITIES
		world.addEntity(slash);
		world.addEntity(weapon);
		world.addEntity(player);
		
		//CAMERAS
		world.addCamera(playerCamera);
		world.setMainCamera(playerCamera);
		
		//CHUNKS
		//world.sortToChunks();
		
		return world;
	}
	
	public void stop() throws IOException {
		Map.generateFile("Mine", world);
		running = false;
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
		
		for(Entity e : world.getEntities()) {
			if(e.getControls() != null) {
				if(PlayerControls.UP.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
					e.moveUp(world);
					e.setActiveAnimationNum(1);
				}
				if(PlayerControls.DOWN.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
					e.moveDown(world);
					e.setActiveAnimationNum(0);
				}
				if(PlayerControls.RIGHT.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
					e.moveRight(world);
					e.setActiveAnimationNum(2);
				}
				if(PlayerControls.LEFT.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
					e.moveLeft(world);
					e.setActiveAnimationNum(3);
				}
				
				if(e.getActiveAnimationNum() == 1 && PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
					e.setActiveAnimationNum(5);
					e.getEquiped().setLocation(new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270));
					e.setTopEquip(true);
					slash.setLocation(new Location(0 + e.getLocation().getX(), -32 + e.getLocation().getY(), 64, 64, 270));
				}
				else if(e.getActiveAnimationNum() == 0 && PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
					e.setActiveAnimationNum(4);
					e.getEquiped().setLocation(new Location(0 + e.getLocation().getX(), 48 + e.getLocation().getY(), 64, 64, 90));
					e.setTopEquip(false);
					slash.setLocation(new Location(0 + e.getLocation().getX(), 48 + e.getLocation().getY(), 64, 64, 90));
				}
				else if(e.getActiveAnimationNum() == 2 && PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
					e.setActiveAnimationNum(6);
					e.getEquiped().setLocation(new Location(32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 0));
					e.setTopEquip(true);
					slash.setLocation(new Location(32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 0));
				}
				else if(e.getActiveAnimationNum() == 3 && PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
					e.setActiveAnimationNum(7);
					e.getEquiped().setLocation(new Location(-32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 180));
					e.setTopEquip(true);
					slash.setLocation(new Location(-32 + e.getLocation().getX(), 16 + e.getLocation().getY(), 64, 64, 180));
				}
				else if(!PlayerControls.USE.isPressed() && e.getEquiped() != null && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
					e.getEquiped().setLocation(new Location(1000, 1000, 0, 0, 90));
					e.setTopEquip(false);
					slash.setLocation(new Location(1000, 1000, 0, 0, 90));
					if(e.getActiveAnimationNum() > 3 && e.getActiveAnimationNum() < 8) {
						e.setActiveAnimationNum(e.getActiveAnimationNum() - 4);
					}
				}
			}
			e.getCurrentAnimation().run();
		}
		for(Entity e : world.getEntities()) {
			e.getCurrentAnimation().setRan(false);
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
}
