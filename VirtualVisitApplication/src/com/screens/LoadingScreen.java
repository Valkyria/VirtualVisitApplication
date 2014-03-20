package com.screens;

import com.MainRoot.VVAMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LoadingScreen implements Screen {
	
	private VVAMain game;
	private BitmapFont font;
	private SpriteBatch batch;
	private Texture emptyT;
	private Texture fullT;
	private NinePatch empty;
	private NinePatch full;
	private Texture Background;
	private TextureRegion BackgroundRegion;
	
	public LoadingScreen(VVAMain pGame){
		game=pGame;
		game.assets=new AssetManager();
		
		game.assets.load("data/img/btt.atlas",TextureAtlas.class);
		
		//________________________________________________________________________
		
		game.assets.load("data/song/Sneaky Snitch.mp3",Music.class);
		game.assets.load("data/song/The Snow Queen.mp3",Music.class);
		
		//_________________________________________________________________________
		
		game.assets.load("data/img/bg_menu.png",Texture.class);
		
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		 batch.begin();
		 batch.draw(BackgroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		 empty.draw(batch, (Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/1.3f))/2, 225, Gdx.graphics.getWidth()/1.3f, 30);
		 full.draw(batch, (Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/1.3f))/2, 225, game.assets.getProgress()*(Gdx.graphics.getWidth()/1.3f), 30);
		 font.drawMultiLine(batch,(int)(game.assets.getProgress()*100)+"% loaded",Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/2f),247,0, BitmapFont.HAlignment.CENTER);
		 batch.end();
		 
		 if(game.assets.update()){
			game.setScreen(new MenuScreen(game));
			}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
				
		 font=new BitmapFont();
		 batch=new SpriteBatch();
		 emptyT=new Texture(Gdx.files.internal("data/img/empty.png"));
		 fullT=new Texture(Gdx.files.internal("data/img/full.png"));
		 empty=new NinePatch(new TextureRegion(emptyT,24,24),8,8,8,8);
		 full=new NinePatch(new TextureRegion(fullT,24,24),8,8,8,8);
		 Texture.setEnforcePotImages(false);
		 Background = new Texture(Gdx.files.internal("data/img/bg_menu.png"));
		 
	     BackgroundRegion=new TextureRegion(Background,0, 0, Background.getWidth(), Background.getHeight());
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
