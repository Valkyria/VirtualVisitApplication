package com.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.model.Player.Direction;

public class World {
	private TiledMap map;
	private Player player;
	private MapLayer collision, event;
	
	public TiledMap getMap() {
		return map;
	}
	public Player getPlayer() {
		return player;
	}
	public MapLayer getColisionLayer(){
		return this.collision;
	}
	public MapLayer getEventLayer(){
		return this.event;
	}
	
	public World(){
		this.player = new Player(new Vector2(496,192));
		Texture.setEnforcePotImages(false);
		this.map = new TmxMapLoader().load("data/map/map.tmx");
		this.setLayers();
	}
	
	public void setLayers(){

		collision = new MapLayer();
		event = new MapLayer();
		
		//Recherche et enregistrement du calque de collision et event
		//on le supprime ensuite pour qu'il soit invisible
		for(int i=0;i<map.getLayers().getCount();i++){
			if(map.getLayers().get(i).getName().equals("colision")){
				this.collision=map.getLayers().get(i);
				this.map.getLayers().remove(i);
			}
			if(map.getLayers().get(i).getName().equals("events")){
				this.event=map.getLayers().get(i);
				this.map.getLayers().remove(i);
			}
		}
	}
	
	public void setHouse(){
		Texture.setEnforcePotImages(false);
		this.player.getPrevPosition().set(this.player.GetPosition());
		this.player.SetPosition(144,60);
		this.player.SetDirection(Direction.FACING_UP);
		this.map = new TmxMapLoader().load("data/map/house.tmx");
		
		this.setLayers();
	}
	
	public void setMap(){
		Texture.setEnforcePotImages(false);
		this.player.SetPosition(this.player.getPrevPosition().x, this.player.getPrevPosition().y-this.player.getHitBox().getHeight());
		this.player.SetDirection(Direction.FACING_DOWN);
		this.map = new TmxMapLoader().load("data/map/map.tmx");
		
		this.setLayers();
	}
}
