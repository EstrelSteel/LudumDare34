package com.estrelsteel.engine1.handler;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.world.World;

public  class Level1ButtonHandler extends Handler {
	
	private Engine1 engine;
	private Tile tile;
	
	public Level1ButtonHandler(String name, Engine1 engine, Tile tile) {
		super(name);
		this.engine = engine;
		this.tile = tile;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		World world = engine.world;
		int x = world.getMainCamera().getLocation().getX();
		int y = world.getMainCamera().getLocation().getY();
		int displayX;
		int displayY;
		if(world.getMainCamera().getFollowX()) {
			displayX = tile.getLocation().getX() - (tile.getLocation().getWidth() / 2) + x;
		}
		else {
			displayX = tile.getLocation().getX() + x;
		}
		if(world.getMainCamera().getFollowY()) {
			displayY = tile.getLocation().getY() - (tile.getLocation().getHeight() / 2) + y;
		}
		else {
			displayY = tile.getLocation().getY() + y;
		}
		if(e.getX() >= displayX && e.getX() <= displayX + tile.getLocation().getWidth() && e.getY() >= displayY && e.getY() <= displayY + tile.getLocation().getHeight()) {
			Entity entity;
			ArrayList<Entity> entities = new ArrayList<Entity>();
			entities = engine.slvl1.getEntities();
			for(int i = 0; i < entities.size(); i++) {
				entity = entities.get(i);
				if(entity.getType().getName().startsWith("FLOWER") || entity.getType() == EntityType.STONE_FLOWER) {
					entities.remove(i);
					i--;
				}
			}
			engine.slvl1.setEntities(entities);
			engine.world = engine.slvl1;
			engine.player.getLocation().setX(0);
			engine.player.getLocation().setY(0);
			engine.hud.setOpen(true);
			engine.passCount = 20;
			engine.passPercent = 10.0;
			engine.passWithoutStone = false;
			engine.wiltChance = 1000;
			Engine1.twoButtonControl = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void setTile(Tile tile) {
		this.tile = tile;
		return;
	}
}
