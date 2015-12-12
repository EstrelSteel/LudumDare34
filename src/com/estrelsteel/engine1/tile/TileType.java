package com.estrelsteel.engine1.tile;

import com.estrelsteel.engine1.world.Location;

public enum TileType {
	UNKOWN(-1, "UNKOWN", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	AIR(0, "AIR", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 0 * 16, 16, 16)),
	WALL_DARK(1, "WALL_DARK", "/com/estrelsteel/engine1/res/texture.png", new Location(2 * 16, 0 * 16, 16, 16)),
	WALL_LIGHT(2, "WALL_LIGHT", "/com/estrelsteel/engine1/res/texture.png", new Location(3 * 16, 0 * 16, 16, 16));
	
	private int id;
	private String name;
	private TileImage img;
	
	TileType() {
		this.id = -1;
		this.name = "UNKOWN";
	}
	
	TileType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	TileType(int id, String name, String src, Location loc) {
		this.id = id;
		this.name = name;
		this.img = new TileImage(src, loc);
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public TileImage getImage() {
		return img;
	}
	
	public static TileType findByName(String name) {
		for(TileType type : TileType.values()) {
			if(type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return TileType.UNKOWN;
	}
}
