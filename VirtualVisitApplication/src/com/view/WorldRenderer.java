package com.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.model.Menu;
import com.model.Player;
import com.model.Player.Direction;
import com.model.Player.State;
import com.model.World;
import com.MainRoot.VVAMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {
	
	/* Constantes concernant la camera
	 * Comme on utilise mapRenderer, on est obligé de multiplier par 32 la taille de la camera.
	 * Lorsqu'on dessinera nous meme les layers, il faudra utiliser juste les valeurs 10 et 7, et les ppuX et les ppuY.
	 */
	private static final float CAMERA_WIDTH = (16*32); //Ici on décide du nombre de tiles affichées à l'écran.
	private static final float CAMERA_HEIGHT = (16*18); //Dans ce cas, on affiche 10*7 tiles.
	private static int camX = (int) Math.round(CAMERA_WIDTH/ 2f);
	private static int camY = (int) Math.round(CAMERA_HEIGHT/ 2f);
	private static final float WALKING_FRAME_DURATION = 0.1f;
	private static final float fontSize = 0.5f;
	private static final String panel = "panel";
	private static final String transition = "transition";
	private static final String message = "message";
	private static final String map = "map";
	private static final String mainBat = "mainBat";
	private static final String boutique = "boutique";
	private static final String infor = "infor";
	private static final String cantine = "cantine";
	private static final String multi = "multi";
	private static final String npc_A1 = "npc_A1";
	private static final String npc_A2 = "npc_A2";
	private static final HAlignment align = HAlignment.CENTER;
	private VVAMain game;
			
	private World world;
	private OrthographicCamera cam;
	private OrthogonalTiledMapRenderer mapRenderer;
	private SpriteBatch batch;
	private AtlasRegion[] PlayerUpRegion, PlayerDwRegion, PlayerLeRegion, PlayerRiRegion;
	private Animation PlayerUpAnimation, PlayerDwAnimation, PlayerLeAnimation, PlayerRiAnimation, currentAnimation, 
		PalyerUpStop, PalyerDwStop, PalyerLeStop, PalyerRiStop;
	private Sprite currentSprite;
	private BitmapFont font;
	private TextureRegion bubbleBot, bubbleTop, bubbleMid;
	
	private MapObject currentObject;
	
	private float imgWidth, imgHeight, StateTime, heightMessage, widthMessage;
	
	public WorldRenderer(World world, VVAMain g){
		this.world = world;
		this.game = g;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(camX, camY, 0);
		this.cam.update();
		this.mapRenderer = new OrthogonalTiledMapRenderer(world.getMap());
		this.batch = new SpriteBatch();
		
		loadTextures();
	}
	
	public void render(){
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		//On dessine la map grace à la methode de mapRenderer.
		//Decoupage des layers et placement du perso entre les couches
		cam.position.set(world.getPlayer().GetPosition().x, world.getPlayer().GetPosition().y, 0);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		mapRenderer.setView(cam);
		
		// affichage des couches inferieures (par index)
		mapRenderer.render(new int[]{0,1,2});

		// affichage du perso
		batch.begin();
		this.drawChar();
		batch.end();

		// affichage de la couche supperieure (par index)
		mapRenderer.render(new int[]{3});
		if(Gdx.input.isTouched()){
		}
		if(this.isColideEvent()){
			MapProperties props = this.getCurrentObject().getProperties();
			if(props.get("type").toString().equals(panel)){
				this.drawMessage(props);
			}
			if(props.get("type").toString().equals(transition)){
				world.setHouse();
				this.resetView();
			}
			if(props.get("type").toString().equals(map)){
				world.setMap();
				this.resetView();
			}
			if(props.get("type").toString().equals(mainBat)){
				world.setMainBat();
				this.resetView();
			}
			if(props.get("type").toString().equals(infor)){
				world.setInfor();
				this.resetView();
			}
			if(props.get("type").toString().equals(boutique)){
				world.setBoutique();
				this.resetView();
			}
			if(props.get("type").toString().equals(cantine)){
				world.setCantine();
				this.resetView();
			}
			if(props.get("type").toString().equals(multi)){
				world.setMultimedia();
				this.resetView();
			}
			
			if(props.get("type").toString().equals(npc_A1)){
				String value ="";
				String joursBeg, joursEnd ="";
				int TotDiffBeg=0;
				int TotDiffEnd=0;
				if(game.currentUser.getDtFin() != null){
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					
					int diffyear = Integer.parseInt(game.currentUser.getDtFin().substring(0, 4))-Integer.parseInt(format.format(new Date()).substring(0, 4));
					int diffmonth = Integer.parseInt(game.currentUser.getDtFin().substring(5, 7))-Integer.parseInt(format.format(new Date()).substring(5, 7));
					int diffday = Integer.parseInt(game.currentUser.getDtFin().substring(8, 10))-Integer.parseInt(format.format(new Date()).substring(8, 10));
					TotDiffEnd = diffday+diffmonth*30+diffyear*365;
					
					diffyear = Integer.parseInt(format.format(new Date()).substring(0, 4))-Integer.parseInt(game.currentUser.getDtDebut().substring(0, 4));
					diffmonth = Integer.parseInt(format.format(new Date()).substring(5, 7))-Integer.parseInt(game.currentUser.getDtDebut().substring(5, 7));
					diffday = Integer.parseInt(format.format(new Date()).substring(8, 10))-Integer.parseInt(game.currentUser.getDtDebut().substring(8, 10));
					TotDiffBeg = diffday+diffmonth*30+diffyear*365;
				}
				if(game.currentUser.getDtDebut() == null || game.currentUser.getDtFin() == null)
				{
					value = "Bonjour "+game.currentUser.getPrenom();
				}
				else{
					if(TotDiffBeg<0){
						joursBeg="Votre sejour n a pas\n encore debute";
						joursEnd="";
					}
					else{
						joursBeg="Vous etes ici \ndepuis "+TotDiffBeg+" jours\n";
						joursEnd="Vous nous quitterez\ndans "+TotDiffEnd+" jours\n";
					}
					value = "Bonjour "+game.currentUser.getPrenom()+"\n"+joursBeg+joursEnd;
				}
				props.put("message", value);
				this.drawMessage(props);
			}
			
			if(props.get("type").toString().equals(npc_A2)){
				String value = "";
				if(game.ListRepasUser.isEmpty() != true)
				{
					value="Vous avez reserve\nles repas suivants:\n";
					for(Menu menu : game.ListRepasUser){
						value=value+menu.getNom()+"\n";
					}
				}
				else{
					value = "Bonjour "+game.currentUser.getPrenom();
				}
				props.put("message", value);
				this.drawMessage(props);
			}
		}
	}

	/*______________________________________________________________________
	     _                     _   _______        _                       
 		| |                   | | |__   __|      | |                      
 		| |     ___   __ _  __| |    | | _____  _| |_ _   _ _ __ ___  ___ 
 		| |    / _ \ / _` |/ _` |    | |/ _ \ \/ / __| | | | '__/ _ \/ __|
 		| |___| (_) | (_| | (_| |    | |  __/>  <| |_| |_| | | |  __/\__ \
 		|______\___/ \__,_|\__,_|    |_|\___/_/\_\\__|\__,_|_|  \___||___/
	 _______________________________________________________________________*/
	
	private void loadTextures() {
		font = new BitmapFont(Gdx.files.internal("data/font/SantasSleighFull.fnt"));
		font.setScale(fontSize);
		TextureAtlas bubbleAtlas = new TextureAtlas(Gdx.files.internal("data/map/tilesets/bubble.atlas"));
		bubbleTop = bubbleAtlas.findRegion("bubble_tot");
		bubbleBot = bubbleAtlas.findRegion("bubble_bot");
		bubbleMid = bubbleAtlas.findRegion("bubble_mid");
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/char/MainChar.atlas"));
		
		//Up
		PlayerUpRegion = new AtlasRegion [4];
		for(int i=0; i<4; i++){
			PlayerUpRegion[i] = atlas.findRegion("up"+(i));
        }
		PlayerUpAnimation = new Animation(WALKING_FRAME_DURATION, PlayerUpRegion);
		PalyerUpStop =  new Animation(WALKING_FRAME_DURATION, atlas.findRegion("up1"));
		
		//Down
		PlayerDwRegion = new AtlasRegion [4];
		for(int i=0; i<4; i++){
			PlayerDwRegion[i] = atlas.findRegion("dw"+(i));
        }
		PlayerDwAnimation = new Animation(WALKING_FRAME_DURATION, PlayerDwRegion);
		PalyerDwStop =  new Animation(WALKING_FRAME_DURATION, atlas.findRegion("dw1"));
		
		//Left
		PlayerLeRegion = new AtlasRegion [4];
		for(int i=0; i<4; i++){
			PlayerLeRegion[i] = atlas.findRegion("le"+(i));
        }
		PlayerLeAnimation = new Animation(WALKING_FRAME_DURATION, PlayerLeRegion);
		PalyerLeStop =  new Animation(WALKING_FRAME_DURATION, atlas.findRegion("le1"));
		
		//Right
		PlayerRiRegion = new AtlasRegion [4];
		for(int i=0; i<4; i++){
			PlayerRiRegion[i] = atlas.findRegion("ri"+(i));
        }
		PlayerRiAnimation = new Animation(WALKING_FRAME_DURATION, PlayerRiRegion);
		PalyerRiStop =  new Animation(WALKING_FRAME_DURATION, atlas.findRegion("ri1"));
		
		//HitBox size ! need test
		imgWidth = PlayerRiAnimation.getKeyFrame(WALKING_FRAME_DURATION).getRegionHeight();
		imgHeight = PlayerRiAnimation.getKeyFrame(WALKING_FRAME_DURATION).getRegionWidth();
		world.getPlayer().getHitBox().setSize(imgWidth, (imgHeight/2));
	}
	
	/*__________________________________________________________________________
	   _____                           __  __      _   _               _     
 	  |  __ \                         |  \/  |    | | | |             | |    
 	  | |  | |_ __ __ ___      _____  | \  / | ___| |_| |__   ___   __| |___ 
 	  | |  | | '__/ _` \ \ /\ / / __| | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
 	  | |__| | | | (_| |\ V  V /\__ \ | |  | |  __/ |_| | | | (_) | (_| \__ \
 	  |_____/|_|  \__,_| \_/\_/ |___/ |_|  |_|\___|\__|_| |_|\___/ \__,_|___/
	 ____________________________________________________________________________*/
	
	private void drawChar() {
		Player player = world.getPlayer();
		Direction direction = player.GetDirection();
		State statut = player.GetStatus();
		
		if(direction == Direction.FACING_UP){
			currentAnimation = statut == State.WALKING ? PlayerUpAnimation : PalyerUpStop;
		}
		if(direction == Direction.FACING_DOWN){
			currentAnimation = statut == State.WALKING ? PlayerDwAnimation : PalyerDwStop;
		}
		if(direction == Direction.FACING_LEFT){
			currentAnimation = statut == State.WALKING ? PlayerLeAnimation : PalyerLeStop;
		}
		if(direction == Direction.FACING_RIGHT){
			currentAnimation = statut == State.WALKING ? PlayerRiAnimation : PalyerRiStop;
		}
		
		currentSprite = new Sprite(currentAnimation.getKeyFrame(this.StateTime, true));
		batch.draw(currentSprite, player.GetPosition().x, player.GetPosition().y);
	}
	
	private void drawMessage(MapProperties props){
		RectangleMapObject rectangleObject = (RectangleMapObject)(this.getCurrentObject());
		heightMessage = font.getWrappedBounds(props.get(message).toString(), rectangleObject.getRectangle().width*7).height;
		widthMessage = font.getWrappedBounds(props.get(message).toString(), rectangleObject.getRectangle().width*7).width;
		
		batch.begin();
		this.drawBulle(props, rectangleObject);
		this.drawText(props, rectangleObject);
		batch.end();
	}
	
	public void drawBulle(MapProperties props, RectangleMapObject rectangleObject){
		
		batch.draw(bubbleTop, 
				rectangleObject.getRectangle().x-widthMessage/2,
				rectangleObject.getRectangle().y+heightMessage + rectangleObject.getRectangle().height+(heightMessage/3),
				rectangleObject.getRectangle().width*7,
				heightMessage/5);
		
		batch.draw(bubbleMid, 
				rectangleObject.getRectangle().x-widthMessage/2,
				rectangleObject.getRectangle().y + rectangleObject.getRectangle().height+(heightMessage/3),
				rectangleObject.getRectangle().width*7,
				heightMessage);
		
		batch.draw(bubbleBot, 
				rectangleObject.getRectangle().x-widthMessage/2,
				rectangleObject.getRectangle().y+ rectangleObject.getRectangle().height,
				rectangleObject.getRectangle().width*7,
				heightMessage/3);
	}
	
	public void drawText(MapProperties props, RectangleMapObject rectangleObject){
		font.drawWrapped(batch, props.get(message).toString(),
				rectangleObject.getRectangle().x - (widthMessage/2), 
				rectangleObject.getRectangle().y+ rectangleObject.getRectangle().height+heightMessage+(heightMessage/3),
				rectangleObject.getRectangle().width*7, align);
	}
	
	/*_____________________________________________________________
	 _____         _ _       _                             
    / ____|       (_) |     | |                            
   | (_____      ___| |_ ___| |__    _ __ ___   __ _ _ __  
    \___ \ \ /\ / / | __/ __| '_ \  | '_ ` _ \ / _` | '_ \ 
    ____) \ V  V /| | || (__| | | | | | | | | | (_| | |_) |
   |_____/ \_/\_/ |_|\__\___|_| |_| |_| |_| |_|\__,_| .__/ 
                                                    | |    
                                                    |_|  
     ______________________________________________________________*/
	
	public void resetView(){
		cam.position.set(world.getPlayer().GetPosition().x, world.getPlayer().GetPosition().y, 0);
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(camX, camY, 0);
		this.cam.update();
		this.mapRenderer = new OrthogonalTiledMapRenderer(world.getMap());
		this.batch = new SpriteBatch();
		loadTextures();
	}
	
	/*_____________________________________________________________________
	    ____  _   _                 __  __      _   _               _     
  	   / __ \| | | |               |  \/  |    | | | |             | |    
 	  | |  | | |_| |__   ___ _ __  | \  / | ___| |_| |__   ___   __| |___ 
 	  | |  | | __| '_ \ / _ \ '__| | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
 	  | |__| | |_| | | |  __/ |    | |  | |  __/ |_| | | | (_) | (_| \__ \
  	   \____/ \__|_| |_|\___|_|    |_|  |_|\___|\__|_| |_|\___/ \__,_|___/
	 _______________________________________________________________________*/
	
	public void SetStateTime(float StateTime){
		this.StateTime = StateTime;
	}
	
	public float GetStateTime(){
		return this.StateTime;
	}
	
	public MapObject getCurrentObject(){
		return this.currentObject;
	}
	
	public void setCurrentObject(MapObject mapObj){
		this.currentObject = mapObj;
	}
	
	public boolean isColideEvent() {
		boolean isColidePlayer = false;
		MapObject clearEvent = new MapObject();
		this.setCurrentObject(clearEvent);
		
		for(MapObject objectEvent: this.world.getEventLayer().getObjects()) {
			RectangleMapObject rectangleObject = (RectangleMapObject)(objectEvent);
			Rectangle eventRectangle = rectangleObject.getRectangle();
			if(this.world.getPlayer().getHitBox().overlaps(eventRectangle))
			{
				isColidePlayer = true;
				this.setCurrentObject(objectEvent);
			}
		}
		return isColidePlayer;
	}
}
