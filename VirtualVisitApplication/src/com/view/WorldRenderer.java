package com.view;

import com.model.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class WorldRenderer {
	
	/* Constantes concernant la camera
	 * Comme on utilise mapRenderer, on est obligé de multiplier par 32 la taille de la camera.
	 * Lorsqu'on dessinera nous même les layers, il faudra utiliser juste les valeurs 10 et 7, et les ppuX et les ppuY.
	 */
	private static final float CAMERA_WIDTH = (16*10); //Ici on décide du nombre de tiles affichées à l'écran.
	private static final float CAMERA_HEIGHT = (16*7); //Dans ce cas, on affiche 10*7 tiles.
	
	private World world;
	private OrthographicCamera cam;
	private OrthogonalTiledMapRenderer mapRenderer;
	private SpriteBatch batch;
	
	private int width;
	private int height;
	//Ces deux valeurs n'ont aucun sens lorsqu'on utilise le mapRenderer.
	private float ppuX;	// pixels par unité (par case) sur l'axe X
	private float ppuY;	// pixels par unité (par case) sur l'axe Y
	
	public WorldRenderer(World world){
		this.world = world;
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();
		//cam = new OrthographicCamera(1, h/w);
		
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.mapRenderer = new OrthogonalTiledMapRenderer(world.getMap());
		this.batch = new SpriteBatch();
	}
	
	public void render(){
		//On dessine la map grâce à la methode de mapRenderer.
		//Decoupage des layers et placement du perso entre les couches
		
		cam.position.set(world.getPlayer().GetPosition().x, world.getPlayer().GetPosition().y, 0);
		cam.update();
		mapRenderer.setView(cam);
		
		// affichage des couches inferieures (par index)
		mapRenderer.render(new int[]{0,1,2});

		// affichage du perso
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		batch.end();

		// affichage de la couche supperieure (par index)
		mapRenderer.render(new int[]{3});
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.ppuX = (float)width / CAMERA_WIDTH;
		this.ppuY = (float)height / CAMERA_HEIGHT;
	}
}
