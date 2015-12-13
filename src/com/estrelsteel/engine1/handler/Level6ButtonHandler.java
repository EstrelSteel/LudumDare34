package com.estrelsteel.engine1.handler;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.estrelsteel.engine1.Engine1;
import com.estrelsteel.engine1.entitiy.Entity;
import com.estrelsteel.engine1.entitiy.EntityType;
import com.estrelsteel.engine1.tile.Tile;
import com.estrelsteel.engine1.tile.TileType;
import com.estrelsteel.engine1.world.World;

public  class Level6ButtonHandler extends Handler {
	
	private Engine1 engine;
	private Tile tile;
	
	public Level6ButtonHandler(String name, Engine1 engine, Tile tile) {
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
			ArrayList<Entity> list = new ArrayList<Entity>();
			list = engine.slvl6.getEntities();
			for(int i = 0; i < list.size(); i++) {
				entity = list.get(i);
				if(entity.getType().getName().startsWith("FLOWER") || entity.getType() == EntityType.STONE_FLOWER) {
					list.remove(i);
					i--;
				}
			}
			ArrayList<Tile> listTile = new ArrayList<Tile>();
			Tile tile;
			listTile = engine.slvl6.getTiles();
			for(int i = 0; i < listTile.size(); i++) {
				tile = listTile.get(i);
				if(tile.getType() == TileType.BUTTON_BLUE && engine.lvl6Finished != 2) {
					listTile.remove(i);
					i--;
				}
			}
			engine.slvl6.setEntities(list);
			engine.slvl6.setTiles(listTile);
			engine.world = engine.slvl6;
			engine.player.getLocation().setX(0);
			engine.player.getLocation().setY(0);
			engine.hud.setOpen(true);
			engine.passCount = 150;
			engine.passPercent = 70.0;
			engine.passWithoutStone = true;
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
