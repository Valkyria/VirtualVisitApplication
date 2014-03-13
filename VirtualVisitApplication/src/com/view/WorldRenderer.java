package com.view;

import com.model.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class WorldRenderer {
	
	//Constantes concernant la camera
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	
	private World world;
	private OrthographicCamera cam;
	
	public WorldRenderer(World world){
		this.world = world;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		cam = new OrthographicCamera(1, h/w);
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
	}
}
