package com.estrelsteel.engine1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.camera.TestCameraControl;
import com.estrelsteel.engine1.entitiy.Animation;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityImage;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.estrelian.Estrelian;
import com.estrelsteel.engine1.handler.CoreHandler;
import com.estrelsteel.engine1.handler.EmptyHandler;
import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.handler.Level1ButtonHandler;
import com.estrelsteel.engine1.handler.Level2ButtonHandler;
import com.estrelsteel.engine1.handler.Level3ButtonHandler;
import com.estrelsteel.engine1.handler.Level4ButtonHandler;
import com.estrelsteel.engine1.handler.Level5ButtonHandler;
import com.estrelsteel.engine1.handler.Level6ButtonHandler;
import com.estrelsteel.engine1.handler.Level7ButtonHandler;
import com.estrelsteel.engine1.handler.MainMenuButtonHandler;
import com.estrelsteel.engine1.handler.PlayerHandler;
import com.estrelsteel.engine1.handler.PlayerHandler.PlayerControls;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuImage;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.meter.HorizontalMeter;
import com.estrelsteel.engine1.online.Client;
import com.estrelsteel.engine1.online.Server;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.world.Location;
import com.estrelsteel.engine1.world.World;

public class Engine1 extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

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
	private boolean showFPS = false;
	public int fps;
	public int tps;
	public int focused = 0;
	
	private Thread thread; 
	public CoreHandler coreHandler;
	public PlayerHandler playerHandler = new PlayerHandler("PLAYER");
	
	public String title = "Flower";
	public String version = "v0.1a";
	public int build = 1;
	public long time = System.currentTimeMillis();
	
	public World mainMenu1 = new World("MAIN_MENU", WIDTH * SCALE, HEIGHT * SCALE);
	public World smainMenu1 = mainMenu1;
	public World lvl1 = new World("LEVEL1", WIDTH * SCALE, HEIGHT * SCALE);
	public World slvl1 = lvl1;
	public World lvl2 = new World("LEVEL2", WIDTH * SCALE, HEIGHT * SCALE);
	public World slvl2 = lvl2;
	public World lvl3 = new World("LEVEL3", WIDTH * SCALE, HEIGHT * SCALE);
	public World slvl3 = lvl3;
	public World lvl4 = new World("LEVEL4", WIDTH * SCALE, HEIGHT * SCALE);
	public World slvl4 = lvl4;
	public World lvl5 = new World("LEVEL5", WIDTH * SCALE, HEIGHT * SCALE);
	public World slvl5 = lvl5;
	public World lvl6 = new World("LEVEL6", WIDTH * SCALE, HEIGHT * SCALE);
	public World slvl6 = lvl6;
	public World lvl7 = new World("LEVEL7", WIDTH * SCALE, HEIGHT * SCALE);
	public World slvl7 = lvl7;
	
	public ArrayList<World> worlds = new ArrayList<World>();
	public World world;
	public Entity player = new Entity(EntityType.JOHN_SNOW, new Location(64, 0, 64, 64), 5, true, playerHandler, "PLAYER");
	public Camera playerCamera = new Camera(new Location(0, 0, 0, 0), player);
	public TestCameraControl camControlTest = new TestCameraControl(playerCamera);
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	
	@SuppressWarnings("unused")
	private Estrelian es2 = new Estrelian();
	public Server server;
	public Client client;
	private int flowerCount = 0;
	private int wiltedCount = 0;
	private int witheredCount = 0;
	private int stoneCount = 0;
	private int goldCount = 0;
	private int allFlowerCount = 1;
	private HorizontalMeter flowerMeter;
	private HorizontalMeter wiltedMeter;
	private HorizontalMeter witheredMeter;
	private HorizontalMeter stoneMeter;
	private HorizontalMeter goldMeter;
	private Location meterLocation;
	public double wiltChance = 1000;
	public double passCount = 200;
	public double passPercent = 10.0;
	public boolean passWithoutStone = false;
	public static boolean twoButtonControl = false;
	public static int moveDirection = 0;
	public static boolean reduceUsage = false;
	
	public int lvl1Finished = 0;
	public int lvl2Finished = 0;
	public int lvl3Finished = 0;
	public int lvl4Finished = 0;
	public int lvl5Finished = 0;
	public int lvl6Finished = 0;
	public int lvl7Finished = 0;
	
	public Menu hud;
	private Menu finishLvl;
	private Menu failedLvl;
	
	public Level1ButtonHandler lvl1ButtonHandler = new Level1ButtonHandler("LEVEL1_BUTTON", this, null);
	public Level2ButtonHandler lvl2ButtonHandler = new Level2ButtonHandler("LEVEL2_BUTTON", this, null);
	public Level3ButtonHandler lvl3ButtonHandler = new Level3ButtonHandler("LEVEL3_BUTTON", this, null);
	public Level4ButtonHandler lvl4ButtonHandler = new Level4ButtonHandler("LEVEL4_BUTTON", this, null);
	public Level5ButtonHandler lvl5ButtonHandler = new Level5ButtonHandler("LEVEL5_BUTTON", this, null);
	public Level6ButtonHandler lvl6ButtonHandler = new Level6ButtonHandler("LEVEL6_BUTTON", this, null);
	public Level7ButtonHandler lvl7ButtonHandler = new Level7ButtonHandler("LEVEL7_BUTTON", this, null);
	public MainMenuButtonHandler mmButtonHandler = new MainMenuButtonHandler("MAIN_MENU_BUTTON", this, null);
	
	public synchronized void start() {
		running = true;

		addFocusListener(coreHandler);
		
		thread = new Thread(this, title + version + "_main");
		thread.start();
		
		playerCamera.setFollowX(true);
		playerCamera.setFollowY(true);
		playerCamera.setCameraController(camControlTest);
		
		
		
		/*
		try {
			staticWorld = es2.loadWorld("U:/testTile.es1", staticWorld);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		*/
		//Forward
		EntityType.JOHN_SNOW.getAnimations().get(0).setMaxWait(15);
		EntityType.JOHN_SNOW.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(2 * 16, 0 * 16, 19, 21)));
		//Backward
		EntityType.JOHN_SNOW.getAnimations().add(new Animation(15));
		EntityType.JOHN_SNOW.getAnimations().get(1).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(0 * 16, 2 * 16, 19, 21)));
		EntityType.JOHN_SNOW.getAnimations().get(1).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(2 * 16, 2 * 16, 19, 21)));
		//Right
		EntityType.JOHN_SNOW.getAnimations().add(new Animation(15));
		EntityType.JOHN_SNOW.getAnimations().get(2).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(0 * 16, 4 * 16, 19, 21)));
		EntityType.JOHN_SNOW.getAnimations().get(2).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(2 * 16, 4 * 16, 19, 21)));
		//Left
		EntityType.JOHN_SNOW.getAnimations().add(new Animation(15));
		EntityType.JOHN_SNOW.getAnimations().get(3).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(0 * 16, 6 * 16, 19, 21)));
		EntityType.JOHN_SNOW.getAnimations().get(3).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(2 * 16, 6 * 16, 19, 21)));
		
		int yFlower = 0;
		int yPedal = 0;
		for(EntityType type : EntityType.values()) {
			if(type.getName().startsWith("FLOWER")) {
				type.getAnimations().get(0).setMaxWait(30);
				type.getAnimations().get(0).addImage(new EntityImage("/com/estrelsteel/engine1/res/flower_sheet.png", new Location(1 * 16, yFlower * 16, 16, 16)));
				type.getAnimations().get(0).addImage(new EntityImage("/com/estrelsteel/engine1/res/flower_sheet.png", new Location(2 * 16, yFlower * 16, 16, 16)));
				type.getAnimations().get(0).addImage(new EntityImage("/com/estrelsteel/engine1/res/flower_sheet.png", new Location(1 * 16, yFlower * 16, 16, 16)));
				yFlower = yFlower + 1;
			}
			else if(type.getName().startsWith("PEDAL")) {
				type.getAnimations().get(0).setMaxWait(30);
				type.getAnimations().get(0).addImage(new EntityImage("/com/estrelsteel/engine1/res/flower_sheet.png", new Location(3 * 16, yPedal * 16, 16, 16)));
				type.getAnimations().get(0).addImage(new EntityImage("/com/estrelsteel/engine1/res/flower_sheet.png", new Location(4 * 16, yPedal * 16, 16, 16)));
				yPedal = yPedal + 1;
			}
		}
		
		EntityType.STONE_FLOWER.getAnimations().get(0).setMaxWait(30);
		EntityType.STONE_FLOWER.getAnimations().get(0).addImage(new EntityImage("/com/estrelsteel/engine1/res/flower_sheet.png", new Location(6 * 16, 0 * 16, 16, 16)));
		EntityType.STONE_FLOWER.getAnimations().get(0).addImage(new EntityImage("/com/estrelsteel/engine1/res/flower_sheet.png", new Location(7 * 16, 0 * 16, 16, 16)));
		EntityType.STONE_FLOWER.getAnimations().get(0).addImage(new EntityImage("/com/estrelsteel/engine1/res/flower_sheet.png", new Location(6 * 16, 0 * 16, 16, 16)));
		
		Camera menuCam = new Camera(new Location(0, 0, 0, 0), player);
		menuCam.setFollowX(false);
		menuCam.setFollowY(true);
		smainMenu1.addCamera(menuCam);
		smainMenu1.setMainCamera(menuCam);
		
		smainMenu1.addTile(new Tile(TileType.UNKOWN, new Location(64, -128, 64, 64), true, null));
		smainMenu1.addTile(new Tile(TileType.UNKOWN, new Location(64, 16 * 64, 64, 64), true, null));
		int random = (int) (Math.random() * 100);
		for(int x = -15; x <= 15; x++) {
			for(int y = -15; y <= 21; y++) {
				if(x >= 0 && x <= 10 && y >= -6 && y <= 21) {
					smainMenu1.addTile(new Tile(TileType.GRASS, new Location(x * 64, y * 64, 64, 64), false, null));
				}
				if(y >= -1 && y <= 15) {
					smainMenu1.addTile(new Tile(TileType.UNKOWN, new Location(-10, y * 64, 64, 64), true, null));
					smainMenu1.addTile(new Tile(TileType.UNKOWN, new Location(138, y * 64, 64, 64), true, null));
				}
				if(x >= -9 && x <= 9 && y >= -9 && y <= 9) {
					random = (int) (Math.random() * 100);
					if(!reduceUsage && random == 3) {
						slvl1.addTile(new Tile(TileType.DIRT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl2.addTile(new Tile(TileType.DIRT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl3.addTile(new Tile(TileType.DIRT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl4.addTile(new Tile(TileType.DIRT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl5.addTile(new Tile(TileType.DIRT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl6.addTile(new Tile(TileType.DIRT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl7.addTile(new Tile(TileType.DIRT, new Location(x * 64, y * 64, 64, 64), false, null));
						
						slvl1.addTile(new Tile(TileType.GRASS_OVERLAY, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl2.addTile(new Tile(TileType.GRASS_OVERLAY, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl3.addTile(new Tile(TileType.GRASS_OVERLAY, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl4.addTile(new Tile(TileType.GRASS_OVERLAY, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl5.addTile(new Tile(TileType.GRASS_OVERLAY, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl6.addTile(new Tile(TileType.GRASS_OVERLAY, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl7.addTile(new Tile(TileType.GRASS_OVERLAY, new Location(x * 64, y * 64, 64, 64), false, null));
					}
					else {
						slvl1.addTile(new Tile(TileType.GRASS, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl2.addTile(new Tile(TileType.GRASS, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl3.addTile(new Tile(TileType.GRASS, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl4.addTile(new Tile(TileType.GRASS, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl5.addTile(new Tile(TileType.GRASS, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl6.addTile(new Tile(TileType.GRASS, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl7.addTile(new Tile(TileType.GRASS, new Location(x * 64, y * 64, 64, 64), false, null));
					}
				}
				if(x >= -10 && x < -9 && y >= -10 && y <= 10) {
					slvl1.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl2.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl3.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl4.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl5.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl6.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl7.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					if(!reduceUsage) {
						slvl1.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl2.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl3.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl4.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl5.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl6.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl7.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
					}
				}
				if(x >= 10 && x < 11 && y >= -10 && y <= 10) {
					slvl1.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl2.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl3.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl4.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl5.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl6.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl7.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					if(!reduceUsage) {
						slvl1.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl2.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl3.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl4.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl5.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl6.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl7.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
					}
				}
				if(x >= -10 && x <= 10 && y >= -10 && y < -9) {
					slvl1.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl2.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl3.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl4.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl5.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl6.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl7.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					if(!reduceUsage) {
						slvl1.addTile(new Tile(TileType.WATER_OVERLAY_UP, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl2.addTile(new Tile(TileType.WATER_OVERLAY_UP, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl3.addTile(new Tile(TileType.WATER_OVERLAY_UP, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl4.addTile(new Tile(TileType.WATER_OVERLAY_UP, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl5.addTile(new Tile(TileType.WATER_OVERLAY_UP, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl6.addTile(new Tile(TileType.WATER_OVERLAY_UP, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl7.addTile(new Tile(TileType.WATER_OVERLAY_UP, new Location(x * 64, y * 64, 64, 64), false, null));
					}
				}
				if(x >= -10 && x <= 10 && y >= 10 && y < 11) {
					slvl1.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl2.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl3.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl4.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl5.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl6.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl7.addTile(new Tile(TileType.SAND, new Location(x * 64, y * 64, 64, 64), false, null));
					if(!reduceUsage) {
						slvl1.addTile(new Tile(TileType.WATER_OVERLAY_DOWN, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl2.addTile(new Tile(TileType.WATER_OVERLAY_DOWN, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl3.addTile(new Tile(TileType.WATER_OVERLAY_DOWN, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl4.addTile(new Tile(TileType.WATER_OVERLAY_DOWN, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl5.addTile(new Tile(TileType.WATER_OVERLAY_DOWN, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl6.addTile(new Tile(TileType.WATER_OVERLAY_DOWN, new Location(x * 64, y * 64, 64, 64), false, null));
						slvl7.addTile(new Tile(TileType.WATER_OVERLAY_DOWN, new Location(x * 64, y * 64, 64, 64), false, null));
					}
				}
				if(!reduceUsage && ((x == -10 && y == -10) || (x == -10 && y == 10))) {
					slvl1.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl2.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl3.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl4.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl5.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl6.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl7.addTile(new Tile(TileType.WATER_OVERLAY_LEFT, new Location(x * 64, y * 64, 64, 64), false, null));
				}
				if(!reduceUsage && ((x == 10 && y == -10) || (x == 10 && y == 10))) {
					slvl1.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl2.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl3.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl4.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl5.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl6.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
					slvl7.addTile(new Tile(TileType.WATER_OVERLAY_RIGHT, new Location(x * 64, y * 64, 64, 64), false, null));
				}
				else if((x >= -15 && x <= 15 && y >= -15 && y <= 15) && !(x >= -10 && x <= 10 && y >= -10 && y <= 10)){
					slvl1.addTile(new Tile(TileType.WATER, new Location(x * 64, y * 64, 64, 64), true, null));
					slvl2.addTile(new Tile(TileType.WATER, new Location(x * 64, y * 64, 64, 64), true, null));
					slvl3.addTile(new Tile(TileType.WATER, new Location(x * 64, y * 64, 64, 64), true, null));
					slvl4.addTile(new Tile(TileType.WATER, new Location(x * 64, y * 64, 64, 64), true, null));
					slvl5.addTile(new Tile(TileType.WATER, new Location(x * 64, y * 64, 64, 64), true, null));
					slvl6.addTile(new Tile(TileType.WATER, new Location(x * 64, y * 64, 64, 64), true, null));
					slvl7.addTile(new Tile(TileType.WATER, new Location(x * 64, y * 64, 64, 64), true, null));
				}
			}
		}
		
		smainMenu1.addEntity(player);
		Tile tile = new Tile(TileType.BUTTON_RED, new Location(192, 0, 64, 64), false, null);
		tile.setControls(lvl1ButtonHandler);
		lvl1ButtonHandler.setTile(tile);
		smainMenu1.addTile(tile);
		smainMenu1.addTile(new Tile(TileType.BOARD_LEVEL1, new Location(266, 0, 240, 96)));
		smainMenu1.addTile(new Tile(TileType.BOARD_TUTORIAL, new Location(266, -160, 240, 96)));
		smainMenu1.addTile(new Tile(TileType.BOARD_CREDITS, new Location(10, -178, 144, 168)));
		
		tile = new Tile(TileType.BUTTON_RED, new Location(192, 160, 64, 64), false, null);
		tile.setControls(lvl2ButtonHandler);
		lvl2ButtonHandler.setTile(tile);
		smainMenu1.addTile(tile);
		smainMenu1.addTile(new Tile(TileType.BOARD_LEVEL2, new Location(266, 160, 240, 96)));
		
		tile = new Tile(TileType.BUTTON_RED, new Location(192, 320, 64, 64), false, null);
		tile.setControls(lvl3ButtonHandler);
		lvl3ButtonHandler.setTile(tile);
		smainMenu1.addTile(tile);
		smainMenu1.addTile(new Tile(TileType.BOARD_LEVEL3, new Location(266, 320, 240, 96)));
		
		tile = new Tile(TileType.BUTTON_RED, new Location(192, 480, 64, 64), false, null);
		tile.setControls(lvl4ButtonHandler);
		lvl4ButtonHandler.setTile(tile);
		smainMenu1.addTile(tile);
		smainMenu1.addTile(new Tile(TileType.BOARD_LEVEL4, new Location(266, 480, 240, 96)));
		
		tile = new Tile(TileType.BUTTON_RED, new Location(192, 640, 64, 64), false, null);
		tile.setControls(lvl5ButtonHandler);
		lvl5ButtonHandler.setTile(tile);
		smainMenu1.addTile(tile);
		smainMenu1.addTile(new Tile(TileType.BOARD_LEVEL5, new Location(266, 640, 240, 96)));
		
		tile = new Tile(TileType.BUTTON_YELLOW, new Location(192, 800, 64, 64), false, null);
		tile.setControls(lvl6ButtonHandler);
		lvl6ButtonHandler.setTile(tile);
		smainMenu1.addTile(tile);
		smainMenu1.addTile(new Tile(TileType.BOARD_LEVEL6, new Location(266, 800, 240, 96)));
		
		tile = new Tile(TileType.BUTTON_YELLOW, new Location(192, 960, 64, 64), false, null);
		tile.setControls(lvl7ButtonHandler);
		lvl7ButtonHandler.setTile(tile);
		smainMenu1.addTile(tile);
		smainMenu1.addTile(new Tile(TileType.BOARD_LEVEL7, new Location(266, 960, 240, 120)));
		
		//slvl1.addTile(new Tile(TileType.WATER, new Location(3 * 64, 1 * 64, 64, 64), true, null));
		
//		slvl1.addEntity(new Entity(EntityType.FLOWER_RED, new Location(1 * 64, 1 * 64, 24, 24), 5, false, new EmptyHandler(), "FLOWER_RED"));
//		slvl1.addEntity(new Entity(EntityType.FLOWER_PINK, new Location(0 * 64, 0 * 64, 24, 24), 5, false, new EmptyHandler(), "FLOWER_PINK"));
//		slvl1.addEntity(new Entity(EntityType.FLOWER_WHITE, new Location(0 * 64, 1 * 64, 24, 24), 5, false, new EmptyHandler(), "FLOWER_WHITE"));
		
		slvl1.addEntity(player);
		slvl1.addCamera(playerCamera);
		slvl1.setMainCamera(playerCamera);
		
		slvl2.addEntity(player);
		slvl2.addCamera(playerCamera);
		slvl2.setMainCamera(playerCamera);
		
		slvl3.addEntity(player);
		slvl3.addCamera(playerCamera);
		slvl3.setMainCamera(playerCamera);
		
		slvl4.addEntity(player);
		slvl4.addCamera(playerCamera);
		slvl4.setMainCamera(playerCamera);
		
		slvl5.addEntity(player);
		slvl5.addCamera(playerCamera);
		slvl5.setMainCamera(playerCamera);
		
		slvl6.addEntity(player);
		slvl6.addCamera(playerCamera);
		slvl6.setMainCamera(playerCamera);
		
		slvl7.addEntity(player);
		slvl7.addCamera(playerCamera);
		slvl7.setMainCamera(playerCamera);
		
		meterLocation = new Location(20, 578, 610, 30);
		goldMeter = new HorizontalMeter(meterLocation, allFlowerCount, goldCount, false);
		
		
		goldMeter.setFrontColour(new Color(255, 191, 0));
		flowerMeter = new HorizontalMeter(meterLocation, allFlowerCount, flowerCount + goldCount, false);
		random = (int) (Math.random() * 4);
		if(random == 0) {
			flowerMeter.setFrontColour(new Color(255, 29, 0));
		}
		else if(random == 1) {
			flowerMeter.setFrontColour(new Color(255, 153, 255));
		}
		else if(random == 2) {
			flowerMeter.setFrontColour(new Color(238, 238, 238));
		}
		else {
			flowerMeter.setFrontColour(new Color(191, 0, 255));
		}
		
		wiltedMeter = new HorizontalMeter(meterLocation, allFlowerCount, wiltedCount + flowerCount + goldCount, false);
		wiltedMeter.setFrontColour(new Color(187, 87, 0));
		witheredMeter = new HorizontalMeter(meterLocation, allFlowerCount, witheredCount + wiltedCount + flowerCount + goldCount, false);
		witheredMeter.setFrontColour(new Color(140, 140, 0));
		stoneMeter = new HorizontalMeter(meterLocation, allFlowerCount, stoneCount + witheredCount + wiltedCount + flowerCount + goldCount, false);
		stoneMeter.setBackColour(Color.BLACK);
		stoneMeter.setFrontColour(new Color(119, 119, 119));
		
		hud = new Menu("FLOWER_HUD", new Location(5, 554, 640, 96), new MenuImage("/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 0 * 16, 128, 16)));
		finishLvl = new Menu("LEVEL_CLEAR", new Location((getWidth() / 2) - (384 / 2), (getHeight() / 2) - (144 / 2), 384, 144), new MenuImage("/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 1 * 16, 128, 48)));
		failedLvl = new Menu("LEVEL_FAIL", new Location((getWidth() / 2) - (336 / 2), (getHeight() / 2) - (48 / 2), 336, 46), new MenuImage("/com/estrelsteel/engine1/res/hud.png", new Location(0 * 16, 4 * 16, 112, 16)));
		
		hud.setOpen(false);
		
		player.getLocation().setY(1000);
		player.getLocation().setY(500);
		player.getLocation().setY(0);
		
		lvl1 = slvl1;
		mainMenu1 = smainMenu1;
		worlds.add(smainMenu1);
		worlds.add(slvl1);
		world = slvl1;
		world = smainMenu1;
		Handler.loadHandlers(this, lvl1);
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*
		try {
			es2.saveWorld("U:/test.es1", staticWorld);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		*/
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
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
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
				if(showFPS) {
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
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = i + tickCount;
		}
		Entity entity = null;
		boolean collide = false;
		for(Entity e : world.getEntities()) {
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
			
			if(PlayerControls.SPACE.isPressed() && e.getControls().getName().equalsIgnoreCase("PLAYER")) {
				PlayerControls.SPACE.setPressed(false);
				int random = (int) (Math.random() * 4);
				
				if(random == 0) {
					entity = new Entity(EntityType.FLOWER_RED, new Location(e.getLocation().getX() + 1, e.getLocation().getY() + 1, 24, 24), 5, false, new EmptyHandler(), "FLOWER_RED");
				}
				else if(random == 1) {
					entity = new Entity(EntityType.FLOWER_PINK, new Location(e.getLocation().getX() + 1, e.getLocation().getY() + 1, 24, 24), 5, false, new EmptyHandler(), "FLOWER_PINK");
				}
				else if(random == 2) {
					entity = new Entity(EntityType.FLOWER_WHITE, new Location(e.getLocation().getX() + 1, e.getLocation().getY() + 1, 24, 24), 5, false, new EmptyHandler(), "FLOWER_WHITE");
				}
				else {
					entity = new Entity(EntityType.FLOWER_PURPLE, new Location(e.getLocation().getX() + 1, e.getLocation().getY() + 1, 24, 24), 5, false, new EmptyHandler(), "FLOWER_PURPLE");
				}
				
				for(Entity e1 : world.getEntities()) {
					if(e1.getLocation().collidesWith(entity.getLocation()) && e1.getType().getName().startsWith("FLOWER")) {
						if(e1.getType() == EntityType.FLOWER_WILTED || e1.getType() == EntityType.FLOWER_WITHERED) {
							e1.setType(EntityType.FLOWER_GOLD);
						}
						collide = true;
					}
				}
			}
			e.getCurrentAnimation().run();
		}
		if(entity != null && !collide) {
			world.getEntities().add(world.getEntities().size() - 1, entity);
		}
		double random = 0.0;
		flowerCount = 0;
		wiltedCount = 0;
		witheredCount = 0;
		stoneCount = 0;
		goldCount = 0;
		allFlowerCount = 0;
		for(Entity e : world.getEntities()) {
			random = Math.random() * wiltChance;
			if((int) random == 1) {
				if(e.getType().getName().startsWith("FLOWER") && e.getType() != EntityType.FLOWER_GOLD && e.getType() != EntityType.FLOWER_PURE_GOLD) {
					if(e.getType() == EntityType.FLOWER_WILTED) {
						e.setType(EntityType.FLOWER_WITHERED);
					}
					else if(e.getType() == EntityType.FLOWER_WITHERED) {
						e.setType(EntityType.STONE_FLOWER);
					}
					else {
						e.setType(EntityType.FLOWER_WILTED);
					}
				}
			}
			e.getCurrentAnimation().setRan(false);
			if(e.getType() == EntityType.FLOWER_RED || e.getType() == EntityType.FLOWER_PINK
					|| e.getType() == EntityType.FLOWER_WHITE || e.getType() == EntityType.FLOWER_PURPLE) {
				flowerCount++;
				allFlowerCount++;
			}
			else if(e.getType() == EntityType.FLOWER_GOLD || e.getType() == EntityType.FLOWER_PURE_GOLD) {
				goldCount++;
				allFlowerCount++;
			}
			else if(e.getType() == EntityType.FLOWER_WILTED) {
				wiltedCount++;
				allFlowerCount++;
			}
			else if(e.getType() == EntityType.FLOWER_WITHERED) {
				witheredCount++;
				allFlowerCount++;
			}
			else if(e.getType() == EntityType.STONE_FLOWER) {
				stoneCount++;
				allFlowerCount++;
			}
		}
		stoneMeter.setValue(stoneCount + witheredCount + wiltedCount + flowerCount + goldCount);
		stoneMeter.setMax(allFlowerCount);
		witheredMeter.setValue(witheredCount + wiltedCount + flowerCount + goldCount);
		witheredMeter.setMax(allFlowerCount);
		wiltedMeter.setValue(wiltedCount + flowerCount + goldCount);
		wiltedMeter.setMax(allFlowerCount);
		flowerMeter.setValue(flowerCount + goldCount);
		flowerMeter.setMax(allFlowerCount);
		goldMeter.setValue(goldCount);
		goldMeter.setMax(allFlowerCount);
		//System.setMax.println(stoneCount + ", " + witheredCount + ", " + wiltedCount + ", " + flowerCount + ", " + goldCount + ", " + allFlowerCount);
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
		if(!reduceUsage) {
			ctx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		ctx.clearRect(0, 0, getWidth(), getHeight());
		if(world != null) {
			ctx = world.renderWorld(ctx);
		}
		
		if(showFPS) {
			ctx.drawString(fps + " fps, " + tps + " tps", 20, 20);
		}
		
		ctx.setColor(Color.BLACK);
		if(hud != null && !hud.getMenuImage().isImageLoaded() && hud.isOpen()); {
			hud.getMenuImage().loadImage();
		}
		if(hud.isOpen()) {
			ctx.drawImage(hud.getMenuImage().getMenuImage(), hud.getLocation().getX(), hud.getLocation().getY(), hud.getLocation().getWidth(), hud.getLocation().getHeight(), null);
			ctx = stoneMeter.drawMeter(ctx);
			ctx = witheredMeter.drawMeterWithoutBack(ctx);
			ctx = wiltedMeter.drawMeterWithoutBack(ctx);
			ctx = flowerMeter.drawMeterWithoutBack(ctx);
			ctx = goldMeter.drawMeterWithoutBack(ctx);
			ctx.drawString("Total Flowers: " + (witheredCount + wiltedCount + flowerCount + goldCount), 40, 625);
			ctx.drawString("Mininum Flower Pass: " + passCount, 415, 625);
			ctx.drawString("Mininum Percentage Pass: " + passPercent, 415, 640);
		}
		ctx.setColor(Color.BLACK);
		
		if(passWithoutStone && stoneCount > 0) {
			ctx.drawImage(failedLvl.getMenuImage().getMenuImage(), failedLvl.getLocation().getX(), failedLvl.getLocation().getY(), failedLvl.getLocation().getWidth(), failedLvl.getLocation().getHeight(), null);
			for(Entity e : world.getEntities()) {
				if(e.getType().getName().startsWith("FLOWER") || e.getType() == EntityType.STONE_FLOWER) {
					e.setType(EntityType.STONE_FLOWER);
				}
			}
			Tile tile = new Tile(TileType.BUTTON_BLUE, new Location(0, 0, 64, 64), false, null);
			mmButtonHandler.setTile(tile);
			tile.setControls(mmButtonHandler);
			world.addTile(tile);
			if(twoButtonControl) {
				twoButtonControl = false;
				PlayerControls.UP.setPressed(false);
				PlayerControls.DOWN.setPressed(false);
				PlayerControls.RIGHT.setPressed(false);
				PlayerControls.LEFT.setPressed(false);
			}
		}
		if(lvl1Finished == 1 && world.getName().equals("MAIN_MENU"))  {
			smainMenu1.addTile(new Tile(TileType.CHECKMARK, new Location(516, 0, 64, 64), false, null));
			world.addTile(new Tile(TileType.CHECKMARK, new Location(516, 0, 64, 64), false, null));
			lvl1Finished = 2;
		}
		if(lvl2Finished == 1 && world.getName().equals("MAIN_MENU")) {
			smainMenu1.addTile(new Tile(TileType.CHECKMARK, new Location(516, 160, 64, 64), false, null));
			world.addTile(new Tile(TileType.CHECKMARK, new Location(516, 160, 64, 64), false, null));
			lvl2Finished = 2;
		}
		if(lvl3Finished == 1 && world.getName().equals("MAIN_MENU")) {
			smainMenu1.addTile(new Tile(TileType.CHECKMARK, new Location(516, 320, 64, 64), false, null));
			world.addTile(new Tile(TileType.CHECKMARK, new Location(516, 320, 64, 64), false, null));
			lvl3Finished = 2;
		}
		if(lvl4Finished == 1 && world.getName().equals("MAIN_MENU")) {
			smainMenu1.addTile(new Tile(TileType.CHECKMARK, new Location(516, 480, 64, 64), false, null));
			world.addTile(new Tile(TileType.CHECKMARK, new Location(516, 480, 64, 64), false, null));
			lvl4Finished = 2;
		}
		if(lvl5Finished == 1 && world.getName().equals("MAIN_MENU")) {
			smainMenu1.addTile(new Tile(TileType.CHECKMARK, new Location(516, 640, 64, 64), false, null));
			world.addTile(new Tile(TileType.CHECKMARK, new Location(516, 640, 64, 64), false, null));
			lvl5Finished = 2;
		}
		if(lvl6Finished == 1 && world.getName().equals("MAIN_MENU")) {
			smainMenu1.addTile(new Tile(TileType.CHECKMARK, new Location(516, 800, 64, 64), false, null));
			world.addTile(new Tile(TileType.CHECKMARK, new Location(516, 800, 64, 64), false, null));
			lvl6Finished = 2;
		}
		if(lvl7Finished == 1 && world.getName().equals("MAIN_MENU")) {
			smainMenu1.addTile(new Tile(TileType.CHECKMARK, new Location(516, 960, 64, 64), false, null));
			world.addTile(new Tile(TileType.CHECKMARK, new Location(516, 960, 64, 64), false, null));
			lvl7Finished = 2;
		}
		if((witheredCount + wiltedCount + flowerCount + goldCount) >= passCount && (flowerMeter.getPercentage() * 100 >= passPercent || (witheredMeter.getPercentage() * 100 >= passPercent && passWithoutStone))) {
			ctx.drawImage(finishLvl.getMenuImage().getMenuImage(), finishLvl.getLocation().getX(), finishLvl.getLocation().getY(), finishLvl.getLocation().getWidth(), finishLvl.getLocation().getHeight(), null);
			for(Entity e : world.getEntities()) {
				if(e.getType().getName().startsWith("FLOWER") || e.getType() == EntityType.STONE_FLOWER) {
					e.setType(EntityType.FLOWER_PURE_GOLD);
				}
			}
			Tile tile = new Tile(TileType.BUTTON_BLUE, new Location(0, 0, 64, 64), false, null);
			mmButtonHandler.setTile(tile);
			tile.setControls(mmButtonHandler);
			world.addTile(tile);
			if(twoButtonControl) {
				twoButtonControl = false;
				PlayerControls.UP.setPressed(false);
				PlayerControls.DOWN.setPressed(false);
				PlayerControls.RIGHT.setPressed(false);
				PlayerControls.LEFT.setPressed(false);
			}
			
			if(world.getName().equals("LEVEL1")) {
				lvl1Finished = 1;
			}
			else if(world.getName().equals("LEVEL2")) {
				lvl2Finished = 1;
			}
			else if(world.getName().equals("LEVEL3")) {
				lvl3Finished = 1;
			}
			else if(world.getName().equals("LEVEL4")) {
				lvl4Finished = 1;
			}
			else if(world.getName().equals("LEVEL5")) {
				lvl5Finished = 1;
			}
			else if(world.getName().equals("LEVEL6")) {
				lvl6Finished = 1;
			}
			else if(world.getName().equals("LEVEL7")) {
				lvl7Finished = 1;
			}
		}
		//ctx.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
		//ctx.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
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
		if(focused == 1) {
			ctx.setColor(Color.GRAY);
			ctx.fillRect(0, 0, getWidth(), getHeight());
			ctx.setColor(Color.BLACK);
			//System.out.println("drawing");
			ctx.drawString("The Game is Paused!", 30, 30);
			ctx.drawString("If you would like to continue, please re-focus the window.", 30, 100);
			focused = 2;
		}
		
		ctx.dispose();
		bs.show();
		
	}
	
	public static int stringtoint(String s) {
		return Integer.parseInt(s);
	}
}
