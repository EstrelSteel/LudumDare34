package com.estrelsteel.engine1.entitiy;

import java.util.ArrayList;

import com.estrelsteel.engine1.world.Location;

public enum EntityType {
	UNKOWN(-1, "UNKNOWN", "/com/estrelsteel/engine1/res/texture.png", new Location(0 * 16, 0 * 16, 16, 16)),
	PLAYER(0, "PLAYER", "/com/estrelsteel/engine1/res/texture.png", new Location(1 * 16, 0 * 16, 16, 16)),
	TEMP_HEART(-2, "TEMP_HEART", "/com/estrelsteel/engine1/res/hud.png", new Location(3 * 16, 0 * 16, 16, 16)),
	WALPOLE(1, "WALPOLE", "/com/estrelsteel/engine1/res/spriteSheet.png", new Location(0 * 16, 0 * 16, 19, 21));
	
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
