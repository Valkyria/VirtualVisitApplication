package com.screens;


import com.screens.GameScreen;
import com.MainRoot.VVAMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.Application.ApplicationType;

public class MenuScreen implements Screen {
	
	private SpriteBatch spriteBatch;
    private Texture Background;
    private VVAMain game;
    private TextureRegion BackgroundRegion;
    private TextureRegion[] ButtonStyle;
    private Stage stage;
    private BitmapFont buttonFont;
    private TextureAtlas atlas;
    private TextButton Play, Quit, Info;
    private Music music;
    
	public MenuScreen(VVAMain game){
		//Initialisation des sprites � utiliser sur les boutons
		this.game = game;
		Background = new Texture("data/img/bg_menu.png");
		atlas = game.assets.get("data/img/btt.atlas");

		ButtonStyle = new AtlasRegion [2];
        ButtonStyle[0] = atlas.findRegion("menu_btt0");
        ButtonStyle[1] = atlas.findRegion("menu_btt1");
        
        buttonFont= new BitmapFont(Gdx.files.internal("data/font/SantasSleighFull.fnt"));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(BackgroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        buttonFont.draw(spriteBatch, "Bienvenu "+game.currentUser.getPrenom()+" !",0,Gdx.graphics.getHeight());
        spriteBatch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(Play.isPressed()){
       		this.game.setScreen(new GameScreen(game));
        }
        if(Quit.isPressed()){
        	Gdx.app.exit();
        }
        if(game.currentUser.getId() != null){
        	if (Info.isPressed()){
        		this.game.setScreen(new AccountScreen(game));
        	}
        }

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		BackgroundRegion=new TextureRegion(Background,0, 0, Background.getWidth(), Background.getHeight());
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        TextButtonStyle style = new TextButtonStyle();
        style.up = new TextureRegionDrawable(ButtonStyle[0]);
        style.down = new TextureRegionDrawable(ButtonStyle[1]);
        style.font = buttonFont;
        
        table.padTop(Gdx.graphics.getWidth()/5);

        if(game.Type.equals(ApplicationType.Android)){
           	 style.font.setScale(2);
           	 Play = new TextButton("Visiter", style);
             table.row().pad(Gdx.graphics.getHeight()*100/(float)Gdx.graphics.getHeight(), (float)Gdx.graphics.getWidth()/2, 0, 0);
             table.add(Play).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/800);
             
             if(game.currentUser.getId() != null){
            	 Info= new TextButton("Mon Compte", style);
                 table.row().pad(Gdx.graphics.getHeight()*100/(float)Gdx.graphics.getHeight(), (float)Gdx.graphics.getWidth()/2, 0, 0);
                 table.add(Info).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/800);
             }
             Quit = new TextButton("Quitter", style);
             table.row().pad(Gdx.graphics.getHeight()*100/(float)Gdx.graphics.getHeight(), (float)Gdx.graphics.getWidth()/2, 0, 0);
             table.add(Quit).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/800);
         }
        	
         else{
           	style.font.setScale(1f);
          	Play = new TextButton("Visiter", style);
            table.row().pad(0, (float)Gdx.graphics.getWidth()/2, 0, 0);
            table.add(Play).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/600);
            
            if(game.currentUser.getId() != null){
            	Info= new TextButton("Mon Compte", style);
                table.row().pad(0, (float)Gdx.graphics.getWidth()/2, 0, 0);
                table.add(Info).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/600);
            }
            
            Quit = new TextButton("Quitter", style);
            table.row().pad(0, (float)Gdx.graphics.getWidth()/2, 0, 0);
            table.add(Quit).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/600);
          }
        
        
        music = Gdx.audio.newMusic(Gdx.files.internal("data/song/Sneaky Snitch.mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
       
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		music.dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
