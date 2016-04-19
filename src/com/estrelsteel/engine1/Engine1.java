package com.estrelsteel.engine1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.contract.Contract;
import com.estrelsteel.engine1.contract.RequireData;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.entitiy.block.Block;
import com.estrelsteel.engine1.entitiy.block.BlockStatus;
import com.estrelsteel.engine1.entitiy.block.BlockType;
import com.estrelsteel.engine1.estrelian.Estrelian;
import com.estrelsteel.engine1.handler.CoreHandler;
import com.estrelsteel.engine1.handler.GameHandler;
import com.estrelsteel.engine1.handler.GameHandler.GameControls;
import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.maps.Gamemode;
import com.estrelsteel.engine1.maps.Map.Maps;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuImage;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.menu.MenuItem.MenuItemType;
import com.estrelsteel.engine1.menu.MenuText;
import com.estrelsteel.engine1.menu.controller.ContractController;
import com.estrelsteel.engine1.menu.controller.FinshedContractController;
import com.estrelsteel.engine1.menu.controller.HudController;
import com.estrelsteel.engine1.menu.controller.MainMenuController;
import com.estrelsteel.engine1.saves.Profile;
import com.estrelsteel.engine1.sound.Effects;
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
	public GameHandler playerHandler = new GameHandler("PLAYER");
	
	public String title = "ShapeCrafter";
	public String version = "v0.1b";
	public static int build = 9;
	public long time = System.currentTimeMillis();
	public static String filesPath = "";
	private boolean appendEstrel = false;
	
	ArrayList<World> worlds = new ArrayList<World>();
	public World world;
	public Camera camera = new Camera(new Location(0, 0, 0, 0));
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	public World multiWorld = new World(WIDTH * SCALE, HEIGHT * SCALE);
	private Maps devMap = Maps.DEV;
	
	@SuppressWarnings("unused")
	private Estrelian es2 = new Estrelian();
	public Gamemode gm = Gamemode.CLASSIC;
	public Maps map = Maps.DEV;
	public boolean canWin = false;
	
	public GameHandler gameHandler = new GameHandler("GAME_HANDLER");
	
	public Profile profile = new Profile();
	private int profileNum = 0;
	
	public double score = 0.0;
	public long startTime = 0;
	public long expireTime = 10000;
	
	public Block activeBlock = new Block(BlockType.getRandomBlockType(), new Location(98, 500, 64, 64, 0));
	public Entity claw = new Entity(EntityType.CLAW, new Location(98, 468, 128, 128));
	public Entity claw_arm	= new Entity(EntityType.CLAW_ARM, new Location(226, 468, 300, 128));
	
	public Menu hud = new Menu("HUD", new Location(0, 0, 16, 16), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0, 0, 16, 16)));
	private HudController hudController = new HudController(hud, this);
	
	public Menu mainMenu = new Menu("MAIN_MENU", new Location(0, 0, 16, 16), new MenuImage(Engine1.filesPath + "/assets/res/img/menuHud.png", new Location(0, 0, 256, 256)));
	private MainMenuController mainMenuController = new MainMenuController(mainMenu, this);
	
	public Menu contract = new Menu("CONTRACT", new Location(0, 0, 16, 16), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 4 * 16, 32, 32)));
	private ContractController contractController = new ContractController(contract, this);
	
	public Menu victory = new Menu("VICTORY", new Location(0, 0, 16, 16), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 4 * 16, 32, 32)));
	private FinshedContractController victoryController = new FinshedContractController(victory, this);
	
	public Menu defeat = new Menu("DEFEAT", new Location(0, 0, 16, 16), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 4 * 16, 32, 32)));
	private FinshedContractController defeatController = new FinshedContractController(defeat, this);
	
	public Menu over = new Menu("OVER", new Location(0, 0, 16, 16), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(2 * 16, 4 * 16, 32, 32)));
	private FinshedContractController overController = new FinshedContractController(over, this);
	
	public Menu hudContract = new Menu("HUD", new Location(0, 0, 16, 16), new MenuImage(Engine1.filesPath + "/assets/res/img/texture.png", new Location(0, 0, 16, 16)));
	
	public ArrayList<Contract> contracts = new ArrayList<Contract>();
	public int contractPos = 0;
	
	public void start() {
		File f = new File(System.getProperty("java.class.path"));
		File dir = f.getAbsoluteFile().getParentFile();
		filesPath = dir.toString();
		if(appendEstrel) {
			filesPath = filesPath + "_estrelsteel";
		}
		profileNum = 1;
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
		contractPos = profile.getPosition();
		try {
			FileReader fr = new FileReader(filesPath + "/assets/contracts.cu1");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			ArrayList<String> lines = new ArrayList<String>();
			String line = br.readLine();
			while(line != null) {
				lines.add(line);
				line = br.readLine();
			}
			for(String s : lines) {
				contracts.add(Contract.create(s));
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		addFocusListener(coreHandler);
		
		camera.setFollowX(false);
		camera.setFollowY(false);
		camera.getLocation().setX(325 - 128);
		camera.getLocation().setY(50);
		
		claw.setCollide(false);
		claw.setWalkspeed(10);
		
		claw_arm.setCollide(false);
		claw_arm.setWalkspeed(10);
		
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

		hud.addMenuItem(new MenuText(" seconds", new Location(56, 56), new Font("Menlo", Font.BOLD, 16), Color.BLACK));
		hud.addMenuItem(new MenuText("0", new Location(56, 86), new Font("Menlo", Font.BOLD, 16), Color.BLACK));
		hud.addMenuItem(new MenuItem(MenuItemType.CLOCK, new Location(16, 32, 32, 32)));
		hud.addMenuItem(new MenuItem(MenuItemType.CREDIT, new Location(16, 64, 32, 32)));
		hud.addMenuItem(new MenuItem(MenuItemType.PAINT, new Location(16, 570, 64, 64)));
		hud.setController(hudController);
		hud.setOpen(false, this);
		menus.add(hud);
		
		mainMenu.addMenuItem(new MenuText("Start", new Location(84, 370), new Font("Menlo", Font.BOLD, 48), new Color(0, 102, 0)));
		mainMenu.addMenuItem(new MenuText("Quit", new Location(435, 370), new Font("Menlo", Font.BOLD, 48), new Color(102, 0, 0)));
		mainMenu.addMenuItem(new MenuItem(MenuItemType.START, new Location(53, 310, 216, 86)));
		mainMenu.addMenuItem(new MenuItem(MenuItemType.QUIT, new Location(386, 310, 216, 86)));
		mainMenu.addMenuItem(new MenuText("Restart Progress", new Location(16, 610, 200, 32), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		mainMenu.setController(mainMenuController);
		mainMenu.setOpen(true, this);
		menus.add(mainMenu);
		
		
		contract.setController(contractController);
		contract.setOpen(false, this);
		menus.add(contract);
		
		victory.addMenuItem(new MenuText("Contract Completed!", new Location(125, 90), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		victory.addMenuItem(new MenuText("Next", new Location(125, 270, 160, 32), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		victory.setController(victoryController);
		victory.setOpen(false, this);
		menus.add(victory);
		
		defeat.addMenuItem(new MenuText("Contract Failed!", new Location(125, 90), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		defeat.addMenuItem(new MenuText("Next", new Location(125, 270, 160, 32), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		defeat.setController(defeatController);
		defeat.setOpen(false, this);
		menus.add(defeat);
		
		over.addMenuItem(new MenuText("All Contracts Complete!", new Location(125, 90), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		over.addMenuItem(new MenuText("By: EstrelSteel", new Location(140, 130), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		over.addMenuItem(new MenuText("For: Ludum Dare 35", new Location(140, 170), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		over.addMenuItem(new MenuText("Theme: Shapeshift", new Location(140, 210), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		over.addMenuItem(new MenuText("Thank you for playing!", new Location(125, 290), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		over.addMenuItem(new MenuText("Exit", new Location(125, 380, 160, 32), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		over.setController(overController);
		over.setOpen(false, this);
		menus.add(over);
		
		updateContractMenu();
		hudContract.setOpen(false, this);
		menus.add(hudContract);
		
		world = addBasics(world);
		worlds.add(world);
		Handler.loadHandlers(this, worlds);
		EntityType.updateSRC(filesPath);
		//TileType.updateSRC(filesPath);
		MenuItemType.updateSRC(filesPath);
		//Effects.updateSRC(filesPath);
		for(Menu menu : menus) {
			menu.getMenuImage().setSRC(filesPath + menu.getMenuImage().getSRC());
		}
		running = true;
		thread = new Thread(this, title + version + "_main");
		thread.start();
	}
	
	public void updateContractMenu() {
		contract.setMenuItems(new ArrayList<MenuItem>());
		hudContract.setMenuItems(new ArrayList<MenuItem>());
		contract.addMenuItem(new MenuText("Contract: " + contracts.get(contractPos).getName(), new Location(125, 90), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		contract.addMenuItem(new MenuText("Requirements:", new Location(125, 130), new Font("Menlo", Font.BOLD, 16), Color.BLACK));
		hudContract.addMenuItem(new MenuItem(MenuItemType.PAPER, new Location(16, 300, 256, 256)));
		hudContract.addMenuItem(new MenuText("Requirements:", new Location(75, 320), new Font("Menlo", Font.PLAIN, 8), Color.BLACK));
		for(int i = 0; i < contracts.get(contractPos).getRequirements().size(); i++) {
			contract.addMenuItem(new MenuText("- " + contracts.get(contractPos).getRequirements().get(i), new Location(140, 130 + (20 * (i + 1))), new Font("Menlo", Font.BOLD, 16), Color.BLACK));
			hudContract.addMenuItem(new MenuText("- " + contracts.get(contractPos).getRequirements().get(i), new Location(50, 320 + (12 * (i + 1))), new Font("Menlo", Font.PLAIN, 8), Color.BLACK));
		}
		contract.addMenuItem(new MenuText("Begin", new Location(125, 540, 160, 32), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
		contract.addMenuItem(new MenuText("Exit", new Location(125, 580, 160, 32), new Font("Menlo", Font.BOLD, 32), Color.BLACK));
	}
	
	public World addBasics(World world) {
		//CAMERAS
		world.addEntity(activeBlock);
		world.addEntity(claw);
		world.addEntity(claw_arm);
		
		world.addCamera(camera);
		world.setMainCamera(camera);
		
		return world;
	}
	
	public void stop() throws IOException {
		if(debug) {
			//Map.generateFile(map.name().substring(0, 1) + map.name().substring(1).toLowerCase(), world);
		}
		running = false;
		profile.setPosition(contractPos);
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
		tickCount++;
		if(!contract.isOpen() && !mainMenu.isOpen() && !victory.isOpen() && !defeat.isOpen()) {
			if(System.currentTimeMillis() - startTime >= expireTime) {
				startTime = System.currentTimeMillis();
				for(Entity e : world.getEntities()) {
					if(e instanceof Block) {
						if(((Block) e).getStatus() == BlockStatus.PINNED) {
							score = score + ((Block) e).getCollideType(world.getCollideMaps().get(world.getCollideMapPosition())).getScore();
						}
					}
				}
				((MenuText) hud.getMenuItems().get(1)).setText("" + score);
				if(world.getCollideMapPosition() + 1 >= world.getCollideMaps().size()) {
					world.setCollideMapPosition(0);
					hud.setOpen(false, this);
					hudContract.setOpen(false, this);
					if(contracts.get(contractPos).checkFinished(new RequireData(world, score), true)) {
						contractPos = contractPos + 1;
						
						if(contractPos >= contracts.size()) {
							over.setOpen(true, this);
							contractPos = 0;
						}
						else {
							Effects.VICTORY.getSound().play();
							updateContractMenu();
							victory.setOpen(true, this);
						}
					}
					else {
						Effects.DEFEAT.getSound().play();
						defeat.setOpen(true, this);
					}
				}
				else {
					if(!contracts.get(contractPos).checkFinished(new RequireData(world, score), false)) {
						Effects.DEFEAT.getSound().play();
						hud.setOpen(false, this);
						hudContract.setOpen(false, this);
						defeat.setOpen(true, this);
					}
					world.setCollideMapPosition(world.getCollideMapPosition() + 1);
	
				}
				world.setEntities(new ArrayList<Entity>());
				world.addEntity(activeBlock);
				world.addEntity(claw);
				world.addEntity(claw_arm);
				
			}
		}
		((MenuText) hud.getMenuItems().get(0)).setText((int) ((expireTime - (System.currentTimeMillis() - startTime)) / 1000)+ " seconds");
		if(GameControls.USE.isPressed()) {
			Effects.PLACE.getSound().play();
			activeBlock.setStatus(BlockStatus.values()[activeBlock.getStatus().ordinal() + 1]);
			GameControls.USE.setPressed(false);
		}
		if(activeBlock.getStatus() == BlockStatus.PINNED) {
			activeBlock = new Block(BlockType.getRandomBlockType(), new Location(98, 500, 64, 64, 0));
			world.addEntity(activeBlock);
			claw.setLocation(new Location(98, 468, 128, 128));
			claw_arm.setLocation(new Location(226, 468, 300, 128));
		}
		if(world != null) {
			world.getCollideMaps().get(world.getCollideMapPosition()).getLocation().setRotation(
					world.getCollideMaps().get(world.getCollideMapPosition()).getLocation().getRotation() + 1);
			for(Entity e : world.getEntities()) {
				e.getCurrentAnimation().run();
				if(e instanceof Block) {
					if(((Block) e).getStatus() == BlockStatus.PINNED) {
						if(tickCount % 1 == 0) {
							e.getLocation().setRotation(e.getLocation().getRotation() + 1);
						}
					}
					if(((Block) e).getStatus() == BlockStatus.MOVING) {
						e.moveUp(world);
						claw.moveUp(world);
						claw_arm.moveUp(world);
					}
				}
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
		ctx.drawImage(EntityType.BACK.getDefaultImage().getEntity(), 0, 0, WIDTH, HEIGHT, null);
		
		ctx.setFont(new Font("Menlo", Font.BOLD, 16));
		if(world != null) {
			ctx = world.renderWorld(ctx);
		}
		if((showFPS || debug) && !hideDebugHud) {
			ctx.drawString(fps + " fps, " + tps + " tps", 20, 20);
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
