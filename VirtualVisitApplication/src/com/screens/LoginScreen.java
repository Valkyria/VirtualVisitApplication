package com.screens;


import java.util.ArrayList;

import com.model.User;
import com.model.Webservice;
import com.MainRoot.VVAMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.Application.ApplicationType;

public class LoginScreen implements Screen {
	
	private SpriteBatch spriteBatch;
    private Texture Background;
    private VVAMain game;
    private TextureRegion BackgroundRegion;
    private Stage stage;
    private TextButton Connexion, Ignorer;
    private Music music;
    private Webservice Webservice;
    private ArrayList<User> ListUser;
    private TextField login, mdp;
    private ShapeRenderer shapeRenderer;
    private BitmapFont buttonFont;
    private String status;
    
	public LoginScreen(VVAMain game, ArrayList<User> ListUser){
		//Initialisation des sprites � utiliser sur les boutons
		this.game = game;
		this.ListUser = ListUser;
		this.Webservice = new Webservice();
		new Vector2();
		this.shapeRenderer = new ShapeRenderer();
		Background = new Texture("data/img/bg_login.png");
		game.ListMenus = Webservice.getAllMenu();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(BackgroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        buttonFont.draw(spriteBatch, status, Gdx.graphics.getHeight()/20*2, Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/40));
        spriteBatch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        if(Connexion.isPressed()){
        	String strLog =login.getText();
        	String strMdp =mdp.getText();
        	for(User usr: ListUser){
        		if (usr.getId().equals(strLog) && usr.getMdp().equals(strMdp)){
        			game.currentUser = usr;
        			game.ListRepasUser = Webservice.getUserMenu(usr.getId());
        			this.game.setScreen(new MenuScreen(game));
        		}
        	}
        }
        if(Ignorer.isPressed()){
        	game.currentUser = new User(null, null, null, "Invite", null, null, null, null);
        	this.game.setScreen(new MenuScreen(game));
        }
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.circle(Gdx.graphics.getHeight()/20, Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/20),Gdx.graphics.getHeight()/70);
        shapeRenderer.end();
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
        buttonFont= new BitmapFont(Gdx.files.internal("data/font/SantasSleighFull.fnt"));
        
        Skin skin;
        skin = new Skin(Gdx.files.internal("ui/defaultskin.json"));
        
        login =new TextField("Identifiant",skin);
        mdp =new TextField("Mot de passe",skin);
        table.padTop(Gdx.graphics.getHeight()/10);
        
        if(game.Type.equals(ApplicationType.Android)){
        	 skin.getFont("default-font").setScale(2);
        	 
        	 table.row().pad(Gdx.graphics.getHeight()/20, 0, 0, 0);
        	 table.add(login).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/800);
        	 
        	 table.row().pad(Gdx.graphics.getHeight()/20, 0, 0, 0);
             table.add(mdp).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/800);
        	 
        	 Connexion = new TextButton("Connexion", skin);
             table.row().pad(Gdx.graphics.getHeight()/20, 0, 0, 0);
             table.add(Connexion).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/800);
             
             Ignorer = new TextButton("Ignorer", skin);
             table.row().pad(Gdx.graphics.getHeight()/20, 0, 0, 0);
             table.add(Ignorer).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/800);
        }
        else{
        	table.row().pad(0, 0, 0, 0);
        	table.add(login);
        	
        	table.row().pad(10, 0, 0, 0);
            table.add(mdp);
            
            skin.getFont("default-font").setScale(1f);
       	    Connexion = new TextButton("Connexion", skin);
            table.row().pad(20, 0, 0, 0);
            table.add(Connexion).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/400);
            
            Ignorer = new TextButton("Ignorer", skin);
            table.row().pad(15, 0, 0, 0);
            table.add(Ignorer).fill((float)Gdx.graphics.getWidth()/1000, (float)Gdx.graphics.getHeight()/400);
       }
        
        music = Gdx.audio.newMusic(Gdx.files.internal("data/song/Sneaky Snitch.mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
        
        if(game.Type.equals(ApplicationType.Android)){
       	 	buttonFont.setScale(1.8f);
        }
        else{
        	buttonFont.setScale(0.8f);
        }
        
        if(this.ListUser.isEmpty()){
        	shapeRenderer.setColor(Color.RED);
        	status = "Server Offline";
        }
        else{
        	shapeRenderer.setColor(Color.GREEN);
        	status = "Server Online";
        }
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
