package com.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class World {
	private TiledMap map;
	private Player player;
	
	public TiledMap getMap() {
		return map;
	}
	public Player getPlayer() {
		return player;
	}
	
	public World(){
		this.player = new Player(new Vector2(10,10));
		Texture.setEnforcePotImages(false);
		this.map = new TmxMapLoader().load("data/map/map.tmx");
	}
}
