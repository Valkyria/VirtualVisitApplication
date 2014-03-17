package com.view;

import com.model.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class WorldRenderer {
	
	/* Constantes concernant la camera
	 * Comme on utilise mapRenderer, on est obligé de multiplier par 32 la taille de la camera.
	 * Lorsqu'on dessinera nous même les layers, il faudra utiliser juste les valeurs 10 et 7, et les ppuX et les ppuY.
	 */
	private static final float CAMERA_WIDTH = (16*32); //Ici on décide du nombre de tiles affichées à l'écran.
	private static final float CAMERA_HEIGHT = (16*18); //Dans ce cas, on affiche 10*7 tiles.
	private static final float WALKING_FRAME_DURATION = 0.06f;
	
	private World world;
	private OrthographicCamera cam;
	private OrthogonalTiledMapRenderer mapRenderer;
	private SpriteBatch batch;
	private AtlasRegion[] PlayerUpRegion, PlayerDwRegion, PlayerLeRegion, PlayerRiRegion;
	private Animation PlayerUpAnimation, PlayerDwAnimation, PlayerLeAnimation, PlayerRiAnimation;
	
	private int width;
	private int height;
	//Ces deux valeurs n'ont aucun sens lorsqu'on utilise le mapRenderer.
	private float ppuX;	// pixels par unité (par case) sur l'axe X
	private float ppuY;	// pixels par unité (par case) sur l'axe Y
	private float imgWidth, imgHeight;
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
		loadTextures();
	}
	
	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/char/MainChar.atlas"));
		
		//Up
		PlayerUpRegion = new AtlasRegion [4];
		for(int i=0; i<4; i++){
			PlayerUpRegion[i] = atlas.findRegion("up"+(i));
        }
		PlayerUpAnimation = new Animation(WALKING_FRAME_DURATION, PlayerUpRegion);
		
		//Down
		PlayerDwRegion = new AtlasRegion [4];
		for(int i=0; i<4; i++){
			PlayerDwRegion[i] = atlas.findRegion("dw"+(i));
        }
		PlayerDwAnimation = new Animation(WALKING_FRAME_DURATION, PlayerDwRegion);
		
		//Left
		PlayerLeRegion = new AtlasRegion [4];
		for(int i=0; i<4; i++){
			PlayerLeRegion[i] = atlas.findRegion("le"+(i));
        }
		PlayerLeAnimation = new Animation(WALKING_FRAME_DURATION, PlayerLeRegion);
		
		//Right
		PlayerRiRegion = new AtlasRegion [4];
		for(int i=0; i<4; i++){
			PlayerRiRegion[i] = atlas.findRegion("ri"+(i));
        }
		PlayerRiAnimation = new Animation(WALKING_FRAME_DURATION, PlayerRiRegion);
		
		//HitBox size ! need test
		imgWidth = PlayerRiAnimation.getKeyFrame(WALKING_FRAME_DURATION).getRegionHeight();
		imgHeight = PlayerRiAnimation.getKeyFrame(WALKING_FRAME_DURATION).getRegionWidth();
		world.getPlayer().getHitBox().setSize(imgWidth, imgHeight);
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
		this.drawChar();
		batch.end();

		// affichage de la couche supperieure (par index)
		mapRenderer.render(new int[]{3});
	}

	private void drawChar() {
		/*Bob bob = world.getBob();
		bobFrame = bob.isFacingLeft() ? bobIdleLeft : bobIdleRight;
		if(bob.getState().equals(State.WALKING)) {
			bobFrame = bob.isFacingLeft() ? 
				walkLeftAnimation.getKeyFrame(bob.getStateTime(), true) : 
				walkRightAnimation.getKeyFrame(bob.getStateTime(), true);
		} else if (bob.getState().equals(State.JUMPING)) {
			if (bob.getVelocity().y > 0) {
				bobFrame = bob.isFacingLeft() ? bobJumpLeft : bobJumpRight;
			} else {
				bobFrame = bob.isFacingLeft() ? bobFallLeft : bobFallRight;
			}
		}
		spriteBatch.draw(bobFrame, 
						 bob.getPosition().x * ppuX,
						 bob.getPosition().y * ppuY, 
						 Bob.SIZE * ppuX, 
						 Bob.SIZE * ppuY);*/
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.ppuX = (float)width / CAMERA_WIDTH;
		this.ppuY = (float)height / CAMERA_HEIGHT;
	}
}
