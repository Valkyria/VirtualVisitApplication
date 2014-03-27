package com.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.Vector2;
import com.model.Player.Direction;

public class World {
	private TiledMap map;
	private Player player;
	private MapLayer collision, event;
	private TmxMapLoader loader;
	private Parameters params;
	private final int ppu = 16;
	
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
		this.loader = new TmxMapLoader();
		this.params = new Parameters();
		this.params.textureMinFilter = TextureFilter.Nearest;
		this.params.textureMagFilter = TextureFilter.Nearest;
		
		this.player = new Player(new Vector2(496,192));
		this.player.setPrevMap(0);
		Texture.setEnforcePotImages(false);
		this.player.SetDirection(Direction.FACING_UP);
		this.map = this.loader.load("data/map/map.tmx", this.params);
		this.setLayers();
	}
	
	public void setLayers(){

		collision = new MapLayer();
		event = new MapLayer();
		
		//Recherche et enregistrement du calque de collision et event
		//on le supprime ensuite pour qu'il soit invisible
		for(int i=0;i<map.getLayers().getCount();i++){
			if(map.getLayers().get(i).getName().equals("collision")){
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
		this.map = this.loader.load("data/map/house.tmx", this.params);
		
		this.setLayers();
	}
	
	public void setMap(){
		Texture.setEnforcePotImages(false);
		if(this.player.getPrevMap() == 1){
			this.player.getPrevPosition().set(31*ppu,27*ppu);
			this.player.setPrevMap(0);
		}
		
		this.player.SetPosition(this.player.getPrevPosition().x, 
				this.player.getPrevPosition().y-this.player.getHitBox().getHeight());
		
		this.player.SetDirection(Direction.FACING_DOWN);
		this.map = this.loader.load("data/map/map.tmx", this.params);
		this.setLayers();
	}
	
	public void setMainBat(){
		Texture.setEnforcePotImages(false);
		
		this.map = this.loader.load("data/map/main_c1.tmx", this.params);
		
		if(this.player.getPrevMap() == 0){
			this.player.SetPosition(9*ppu,ppu*2);
			this.player.setPrevMap(1);
			this.player.SetDirection(Direction.FACING_UP);
		}
		else{
			if(this.player.getPrevPosition().x > Float.parseFloat(map.getProperties().get("width").toString())*ppu/2){
				this.player.SetPosition(this.player.getPrevPosition().x-this.player.getHitBox().getWidth()/2, this.player.getPrevPosition().y);
				this.player.SetDirection(Direction.FACING_LEFT);
			}
			else{
				this.player.SetPosition(this.player.getPrevPosition().x+this.player.getHitBox().getWidth()/2, this.player.getPrevPosition().y);
				this.player.SetDirection(Direction.FACING_RIGHT);
			}
		}
		
		
		this.setLayers();
	}
	
	public void setInfor(){
		Texture.setEnforcePotImages(false);
		this.player.getPrevPosition().set(this.player.GetPosition());
		this.player.SetPosition(2*ppu+this.player.getHitBox().getWidth(),6*ppu);
		this.player.SetDirection(Direction.FACING_RIGHT);
		this.map = this.loader.load("data/map/infor.tmx", this.params);
		
		this.setLayers();
	}
	
	public void setBoutique(){
		Texture.setEnforcePotImages(false);
		this.player.getPrevPosition().set(this.player.GetPosition());
		this.player.SetPosition(14*ppu-this.player.getHitBox().getWidth()/2,2*ppu);
		
		this.player.SetDirection(Direction.FACING_LEFT);
		this.map = this.loader.load("data/map/boutique.tmx", this.params);
		
		this.setLayers();
	}
	
	public void setCantine(){
		Texture.setEnforcePotImages(false);
		this.player.getPrevPosition().set(this.player.GetPosition());
		this.player.SetPosition(1*ppu+this.player.getHitBox().getWidth()/2,18.5f*ppu);
		
		this.player.SetDirection(Direction.FACING_RIGHT);
		this.map = this.loader.load("data/map/cantine.tmx", this.params);
		
		this.setLayers();
	}
	public void setMultimedia(){
		Texture.setEnforcePotImages(false);
		this.player.getPrevPosition().set(this.player.GetPosition());
		this.player.SetPosition(17*ppu-this.player.getHitBox().getWidth()/2,9*ppu);
		
		this.player.SetDirection(Direction.FACING_LEFT);
		this.map = this.loader.load("data/map/multimedia.tmx", this.params);
		
		this.setLayers();
	}
}
