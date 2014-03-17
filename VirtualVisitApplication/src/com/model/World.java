package com.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class World {
	private TiledMap map;
	private Player player;
	private MapLayer collision;
	
	public TiledMap getMap() {
		return map;
	}
	public Player getPlayer() {
		return player;
	}
	public MapLayer getColisionLayer(){
		return this.collision;
	}
	public World(){
		this.player = new Player(new Vector2(496,200));
		Texture.setEnforcePotImages(false);
		this.map = new TmxMapLoader().load("data/map/map.tmx");

		//Recherche et enregistrement du calque de collision
		//on le supprime ensuite pour qu'il soit invisible
		for(int i=0;i<map.getLayers().getCount();i++){
			if(map.getLayers().get(i).getName().equals("colision")){
				this.collision=map.getLayers().get(i);
				map.getLayers().remove(i);
				break;
			}
		}
	}
}
