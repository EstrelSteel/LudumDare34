package com.estrelsteel.engine1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;

import com.estrelsteel.engine1.camera.Camera;
import com.estrelsteel.engine1.camera.TestCameraControl;
import com.estrelsteel.engine1.entitiy.Animation;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityImage;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.estrelian.Estrelian;
import com.estrelsteel.engine1.handler.CoreHandler;
import com.estrelsteel.engine1.handler.Handler;
import com.estrelsteel.engine1.handler.PlayerHandler;
import com.estrelsteel.engine1.handler.PlayerHandler.PlayerControls;
import com.estrelsteel.engine1.menu.Menu;
import com.estrelsteel.engine1.menu.MenuItem;
import com.estrelsteel.engine1.online.Client;
import com.estrelsteel.engine1.online.Server;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.tile.Tile;
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
	private boolean showFPS = true;
	public int fps;
	public int tps;
	public int focused = 0;
	
	private Thread thread; 
	public CoreHandler coreHandler;
	public PlayerHandler playerHandler = new PlayerHandler("PLAYER");
	
	public String title = "Engine1";
	public String version = "v0.1a";
	public int build = 0;
	public long time = System.currentTimeMillis();
	
	public World world = new World(WIDTH * SCALE, HEIGHT * SCALE);
	public World staticWorld = world;
	public Entity player = new Entity(EntityType.WALPOLE, new Location(0, 0, 64, 64), 5, true, playerHandler, "PLAYER");
	public Camera playerCamera = new Camera(new Location(0, 0, 0, 0), player);
	public TestCameraControl camControlTest = new TestCameraControl(playerCamera);
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	
	private Estrelian es2 = new Estrelian();
	public Server server;
	public Client client;
	
	public synchronized void start() {
		running = true;

		addFocusListener(coreHandler);
		
		thread = new Thread(this, title + version + "_main");
		thread.start();
		
		playerCamera.setFollowX(true);
		playerCamera.setFollowY(true);
		playerCamera.setCameraController(camControlTest);
		staticWorld.addTile(new Tile(TileType.WALL_DARK, new Location(100, 100, 64, 64), true, null));
		staticWorld.addTile(new Tile(TileType.WALL_LIGHT, new Location(165, 100, 64, 64), true, null));
		staticWorld.addEntity(player);
		staticWorld.addCamera(playerCamera);
		staticWorld.setMainCamera(playerCamera);
		/*
		try {
			staticWorld = es2.loadWorld("U:/testTile.es1", staticWorld);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		*/
		//Forward
		EntityType.WALPOLE.getAnimations().get(0).setMaxWait(15);
		EntityType.WALPOLE.getAnimations().get(0).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/spriteSheet.png", new Location(2 * 16, 0 * 16, 19, 21)));
		//Backward
		EntityType.WALPOLE.getAnimations().add(new Animation(15));
		EntityType.WALPOLE.getAnimations().get(1).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/spriteSheet.png", new Location(0 * 16, 2 * 16, 19, 21)));
		EntityType.WALPOLE.getAnimations().get(1).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/spriteSheet.png", new Location(2 * 16, 2 * 16, 19, 21)));
		//Right
		EntityType.WALPOLE.getAnimations().add(new Animation(15));
		EntityType.WALPOLE.getAnimations().get(2).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/spriteSheet.png", new Location(0 * 16, 4 * 16, 19, 21)));
		EntityType.WALPOLE.getAnimations().get(2).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/spriteSheet.png", new Location(2 * 16, 4 * 16, 19, 21)));
		//Left
		EntityType.WALPOLE.getAnimations().add(new Animation(15));
		EntityType.WALPOLE.getAnimations().get(3).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/spriteSheet.png", new Location(0 * 16, 6 * 16, 19, 21)));
		EntityType.WALPOLE.getAnimations().get(3).getImages().add(new EntityImage("/com/estrelsteel/engine1/res/spriteSheet.png", new Location(2 * 16, 6 * 16, 19, 21)));
		
		world = staticWorld;
		Handler.loadHandlers(this, world);
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
			e.getCurrentAnimation().run();
			
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
		
		Graphics ctx = bs.getDrawGraphics();
		ctx.clearRect(0, 0, getWidth(), getHeight());
		ctx = world.renderWorld(ctx);
		if(showFPS) {
			ctx.drawString(fps + " fps, " + tps + " tps", 20, 20);
		}
		
		ctx.setColor(Color.BLACK);
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
