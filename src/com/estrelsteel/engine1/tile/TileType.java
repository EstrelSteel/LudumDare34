package com.estrelsteel.engine1.tile;

import com.estrelsteel.engine1.world.Location;

public enum TileType {
	UNKOWN(-1, "UNKOWN", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	AIR(0, "AIR", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 0 * 16, 16, 16)),
	WALL_DARK(1, "WALL_DARK", "/com/estrelsteel/engine1/res/texture.png", new Location(2 * 16, 0 * 16, 16, 16)),
	WALL_LIGHT(2, "WALL_LIGHT", "/com/estrelsteel/engine1/res/texture.png", new Location(3 * 16, 0 * 16, 16, 16)),
	GRASS(3, "GRASS", "/com/estrelsteel/engine1/res/texture.png", new Location(4 * 16, 0 * 16, 16, 16)),
	WATER(4, "WATER", "/com/estrelsteel/engine1/res/texture.png", new Location(5 * 16, 0 * 16, 16, 16)),
	BUTTON_RED(5, "BUTTON_RED", "/com/estrelsteel/engine1/res/texture.png", new Location(6 * 16, 0 * 16, 16, 16)),
	BUTTON_YELLOW(6, "BUTTON_YELLOW","/com/estrelsteel/engine1/res/texture.png", new Location(7 * 16, 0 * 16, 16, 16)),
	BUTTON_BLUE(7, "BUTTON_BLUE", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 1 * 16, 16, 16)),
	BUTTON_GREEN(8, "BUTTON_GREEN", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 1 * 16, 16, 16)),
	CHECKMARK(9, "CHECKMARK", "/com/estrelsteel/engine1/res/texture.png", new Location(2 * 16, 1 * 16, 16, 16)),
	BOARD_TUTORIAL(10, "BOARD_TUTORIAL", "/com/estrelsteel/engine1/res/message.png",new Location(0 * 16, 0 * 16, 80, 32)),
	BOARD_CREDITS(11, "BOARD_CREDITS", "/com/estrelsteel/engine1/res/message.png",new Location(5 * 16, 0 * 16, 48, 56)),
	BOARD_LEVEL1(12, "BOARD_LEVEL1", "/com/estrelsteel/engine1/res/message.png",new Location(0 * 16, 2 * 16, 80, 32)),
	BOARD_LEVEL2(13, "BOARD_LEVEL2", "/com/estrelsteel/engine1/res/message.png",new Location(0 * 16, 4 * 16, 80, 32)),
	BOARD_LEVEL3(14, "BOARD_LEVEL3", "/com/estrelsteel/engine1/res/message.png", new Location(0 * 16, 6 * 16, 80, 32)),
	BOARD_LEVEL4(15, "BOARD_LEVEL4", "/com/estrelsteel/engine1/res/message.png", new Location(0 * 16, 8 * 16, 80, 32)),
	BOARD_LEVEL5(16, "BOARD_LEVEL5", "/com/estrelsteel/engine1/res/message.png", new Location(0 * 16, 10 * 16, 80, 32)),
	BOARD_LEVEL6(17, "BOARD_LEVEL6", "/com/estrelsteel/engine1/res/message.png", new Location(0 * 16, 12 * 16, 80, 32)),
	BOARD_LEVEL7(18, "BOARD_LEVEL7", "/com/estrelsteel/engine1/res/message.png", new Location(0 * 16, 14 * 16, 80, 40)),
	SAND(19, "SAND", "/com/estrelsteel/engine1/res/texture.png", new Location(3 * 16, 1 * 16, 16, 16)),
	DIRT(20, "DIRT", "/com/estrelsteel/engine1/res/texture.png", new Location(4 * 16, 1 * 16, 16, 16)),
	GRASS_OVERLAY(21, "GRASS_OVERLAY", "/com/estrelsteel/engine1/res/texture.png", new Location(5 * 16, 1 * 16, 16, 16)),
	WATER_OVERLAY_LEFT(22, "WATER_OVERLAY_LEFT", "/com/estrelsteel/engine1/res/texture.png", new Location(6 * 16, 1 * 16, 16, 16)),
	WATER_OVERLAY_RIGHT(23, "WATER_OVERLAY_RIGHT", "/com/estrelsteel/engine1/res/texture.png", new Location(7 * 16, 1 * 16, 16, 16)),
	WATER_OVERLAY_UP(24, "WATER_OVERLAY_UP", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 2 * 16, 16, 16)),
	WATER_OVERLAY_DOWN(25, "WATER_OVERLAY_DOWN", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 2 * 16, 16, 16));
	
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
