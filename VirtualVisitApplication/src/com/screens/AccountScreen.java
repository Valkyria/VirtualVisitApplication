package com.screens;

import java.text.DecimalFormat;

import com.MainRoot.VVAMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class AccountScreen implements Screen
{
        private SpriteBatch spriteBatch;
        private Texture Background;
        private VVAMain game;
        private TextureRegion BackgroundRegion;
        private TextureRegion[] ButtonStyle;
        private int bgsizex, bgsizey;
        private Stage stage;
        private BitmapFont buttonFont;
        private TextureAtlas atlas;
        private TextButton Menu;
		private float ratio;
        /**
         * Constructor for the splash screen
         * @param g Game which called this splash screen.
         */
        public AccountScreen(VVAMain g)
        {
                this.game = g;
        }
        
        @Override
		public void show()
        {
			bgsizex=720;
			bgsizey=1280;
			spriteBatch = new SpriteBatch();
	        Background = game.assets.get("images/Backgrounds/Stats.png");
	        BackgroundRegion=new TextureRegion(Background,0, Background.getHeight()-bgsizey, bgsizex, bgsizey);
	        stage = new Stage();
	        
	        Gdx.input.setInputProcessor(stage);
	        Table table = new Table();
	        table.setFillParent(true);
	        stage.addActor(table);
	        
	        atlas = game.assets.get("images/Sprites/buttons.atlas");
	        ButtonStyle = new AtlasRegion [2];
	        ButtonStyle[0] = atlas.findRegion("void-0");
	        ButtonStyle[1] = atlas.findRegion("void-1");
	        buttonFont= new BitmapFont();
	        
	        
	        TextButtonStyle style = new TextButtonStyle();
	        style.up = new TextureRegionDrawable(ButtonStyle[0]);
	        style.down = new TextureRegionDrawable(ButtonStyle[1]);
	        style.font = buttonFont;
	        table.row().padTop(280);
	        table.add();
	        Menu = new TextButton("Menu", style);
	        table.row().padTop(Gdx.graphics.getHeight()/20);
	        table.add(Menu).fill(Gdx.graphics.getWidth()/900f, (Gdx.graphics.getHeight()/1000f));
	        
        }
		@Override
		public void render(float delta) {
			
			
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            spriteBatch.begin();
            spriteBatch.draw(BackgroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            
            buttonFont.draw(spriteBatch, "Total win: "+"", 
            		(Gdx.graphics.getWidth()/3f), (float)((Gdx.graphics.getHeight()/1.45f)));
            
            spriteBatch.end();
            stage.act(Gdx.graphics.getDeltaTime());
            
            stage.draw();
            
            if(Menu.isPressed()){
           		this.game.setScreen(new MenuScreen(game));
            }
            

		}

		@Override
		public void resize(int width, int height) {
			// TODO Auto-generated method stub
			//stage.setViewport(width, height, true);
			
		}

		@Override
		public void hide() {
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
			stage.dispose();
			
		}
}
