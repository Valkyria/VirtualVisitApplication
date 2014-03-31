package com.screens;

import com.MainRoot.VVAMain;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class AccountScreen implements Screen
{
        private SpriteBatch spriteBatch;
        private Texture Background;
        private VVAMain game;
        private TextureRegion BackgroundRegion;
        private Stage stage;
        private BitmapFont buttonFont;
        private TextButton Menu;
		private float heightMessage, widthMessage;
		/**
         * Constructor for the splash screen
         * @param g Game which called this splash screen.
         */
        public AccountScreen(VVAMain g)
        {
             this.game = g;
             Background = new Texture("data/img/bg_info.png");
             buttonFont= new BitmapFont(Gdx.files.internal("data/font/SantasSleighFull.fnt"));
        }
        
        @Override
		public void show()
        {
        	spriteBatch = new SpriteBatch();
    		BackgroundRegion=new TextureRegion(Background,0, 0, Background.getWidth(), Background.getHeight());
            stage = new Stage();
            Gdx.input.setInputProcessor(stage);
            Table table = new Table();
            table.setFillParent(true);
            stage.addActor(table);
            
            heightMessage = buttonFont.getBounds(game.currentUser.toString()).height;
    		widthMessage = buttonFont.getBounds(game.currentUser.toString()).width;
    		System.out.println(widthMessage+" "+heightMessage+"\n"+Gdx.graphics.getWidth()+" "+Gdx.graphics.getHeight());
            Skin skin;
            skin = new Skin(Gdx.files.internal("ui/defaultskin.json"));
            
            table.padTop(Gdx.graphics.getHeight()/1.5f);
            
            if(game.Type.equals(ApplicationType.Android)){
           	 	skin.getFont("default-font").setScale(1.9f);
           	 	buttonFont.setScale(1.9f);
           	 
           	 	Menu = new TextButton("Retour", skin);
           	 	table.row().pad(Gdx.graphics.getHeight()/20, 0, 0, 0);
           	 	table.add(Menu).fill((float)Gdx.graphics.getWidth()/300, (float)Gdx.graphics.getHeight()/800);
            }
            else{
            	skin.getFont("default-font").setScale(0.9f);
            	buttonFont.setScale(0.9f);
            	
            	Menu = new TextButton("Retour", skin);
                table.row().pad(15, 0, 0, 0);
                table.add(Menu).fill((float)Gdx.graphics.getWidth()/200, (float)Gdx.graphics.getHeight()/400);
    	        
            }
	        
        }
		@Override
		public void render(float delta) {
            
			
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        spriteBatch.begin();
	        spriteBatch.draw(BackgroundRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        
	        if(game.Type.equals(ApplicationType.Android)){
	        	 buttonFont.drawMultiLine(spriteBatch, game.currentUser.toString(), Gdx.graphics.getWidth()/2-widthMessage/4.5f, Gdx.graphics.getHeight()/2+heightMessage*5f);
	        }
	        else{
	        	 buttonFont.drawMultiLine(spriteBatch, game.currentUser.toString(), Gdx.graphics.getWidth()/2-widthMessage/9.5f, Gdx.graphics.getHeight()/2+heightMessage*2f);
	        }
	       
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
