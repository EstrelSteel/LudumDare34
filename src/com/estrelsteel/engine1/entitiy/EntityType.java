package com.estrelsteel.engine1.entitiy;

import java.util.ArrayList;

import com.estrelsteel.engine1.world.Location;

public enum EntityType {
	UNKOWN(-1, "UNKNOWN", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	PLAYER(0, "PLAYER", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 0 * 16, 16, 16)),
	WALPOLE(1, "WALPOLE", "/com/estrelsteel/engine1/res/spriteSheet.png", new Location(0 * 16, 0 * 16, 19, 21)),
	JOHN_SNOW(17, "JOHN_SNOW", "/com/estrelsteel/engine1/res/john_snow_sheet.png", new Location(0 * 16, 0 * 16, 19, 21)),
	
	FLOWER_RED(2, "FLOWER_RED", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(0 * 16, 0 * 16, 16, 16)),
	FLOWER_PINK(3, "FLOWER_PINK", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(0 * 16, 1 * 16, 16, 16)),
	FLOWER_WHITE(4, "FLOWER_WHITE", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(0 * 16, 2 * 16, 16, 16)),
	FLOWER_PURPLE(5, "FLOWER_PURPLE", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(0 * 16, 3 * 16, 16, 16)),
	FLOWER_WILTED(6, "FLOWER_WILTED", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(0 * 16, 4 * 16, 16, 16)),
	FLOWER_WITHERED(7, "FLOWER_WITHERED", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(0 * 16, 5 * 16, 16, 16)),
	FLOWER_GOLD(8, "FLOWER_GOLD", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(0 * 16, 6 * 16, 16, 16)),
	FLOWER_PURE_GOLD(9, "FLOWER_PURE_GOLD", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(0 * 16, 7 * 16, 16, 16)),
	STONE_FLOWER(10, "STONE_FLOWER", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(5 * 16, 0 * 16, 16, 16)),
	
	PEDAL_RED(11, "PEDAL_RED", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(3 * 16, 0 * 16, 16, 16)),
	PEDAL_PINK(12, "PEDAL_PINK", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(3 * 16, 1 * 16, 16, 16)),
	PEDAL_WHITE(13, "PEDAL_WHITE", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(3 * 16, 2 * 16, 16, 16)),
	PEDAL_PURPLE(14, "PEDAL_PINK", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(3 * 16, 3 * 16, 16, 16)),
	PEDAL_WILTED(15, "PEDAL_WILTED", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(3 * 16, 4 * 16, 16, 16)),
	PEDAL_GOLD(16, "PEDAL_GOLD", "/com/estrelsteel/engine1/res/flower_sheet.png", new Location(3 * 16, 5 * 16, 16, 16));
	
	private int id;
	private String name;
	private EntityImage defaultImage;
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	
	EntityType(int id, String name, String src, Location loc) {
		this.id = id;
		this.name = name;
		this.defaultImage = new EntityImage(src, loc);
		this.animations = new ArrayList<Animation>();
		this.animations.add(new Animation(30));
		this.animations.get(0).addImage(defaultImage);
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public EntityImage getDefaultImage() {
		return defaultImage;
	}
	
	public ArrayList<Animation> getAnimations() {
		return animations;
	}
	
	public void addAnimation(Animation animation) {
		animations.add(animation);
		return;
	}
	
	public void removeAnimation(Animation animation) {
		animations.remove(animation);
		return;
	}
	
	public void setAnimations(ArrayList<Animation> animations) {
		this.animations = animations;
		return;
	}
	
	public static EntityType findByName(String name) {
		for(EntityType type : EntityType.values()) {
			if(type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return EntityType.UNKOWN;
	}
}
