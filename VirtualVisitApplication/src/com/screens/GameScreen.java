/**
 * 
 */
package com.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.controllers.PlayerController;
import com.model.World;
import com.view.WorldRenderer;

/**
 * @author Ligier, Tran-Van-Loc
 *
 */
public class GameScreen implements Screen, InputProcessor {
	
	private World world;
	private WorldRenderer renderer;
	private PlayerController playerController;
	private int width, height;
	/* 
	 * Appel� quand on veut afficher le screen pour la premi�re fois.
	 * Par exemple sous Android, quand on lance l'application, la methode show() est appel�e, mais
	 * quand on verrouille le t�l�phone avec l'appli au premier plan, puis qu'on le d�verrouille, elle n'est pas appel�e, 
	 * de m�me avec la touche home suivi du relancement de l'application.
	 * Cependant, quand l'appli est quitt�e via la touche retour, ou que, lorsqu'elle est en arri�re plan on vide la m�moire, 
	 * alors la m�thode show() est appel�e � nouveau au lancement.
	 */
	@Override
	public void show() {
		//Ajouter un nouveau world, un nouveau worldRenderer, et un nouveau PlayerController
		World world = new World();
		renderer = new WorldRenderer(world);
		playerController = new PlayerController(world);
		Gdx.input.setInputProcessor(this);
	}
	
	/* 
	 * Appel� � chaque fois que l'�cran se redessine.
	 * delta correspond au temps �coul� entre deux appels de render.
	 */
	@Override
	public void render(float delta) {
		//On update l'�tat du joueur
		playerController.update(delta);
		//On redessine le monde (WorldRenderer a acc�s � la map et au joueur, il peut donc tout dessiner).
		renderer.render();
	}

	/* 
	 * Appel� lorsque l'�cran est redimensionn�.
	 */
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}



	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		//Arr�te la surveillance des input quand l'appli est en arri�re plan
		Gdx.input.setInputProcessor(null);

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	/* 
	 * Appel� quand libgdx d�truit les ressources utilis�es.
	 */
	@Override
	public void dispose() {
		//Arr�te la surveillance des input quand l'appli se ferme.
		Gdx.input.setInputProcessor(null);
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		//Appell�e lorsqu'une touche du clavier physique est press�e.
		if (keycode == Keys.LEFT)
			playerController.leftPressed();
		if (keycode == Keys.RIGHT)
			playerController.rightPressed();
		if (keycode == Keys.UP)
			playerController.upPressed();
		if (keycode == Keys.DOWN)
			playerController.downPressed();
		//La valeur de retour informe l'InputProcessor qu'on a g�r� cet input.
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			playerController.leftReleased();
		if (keycode == Keys.RIGHT)
			playerController.rightReleased();
		if (keycode == Keys.UP)
			playerController.upReleased();
		if (keycode == Keys.DOWN)
			playerController.downReleased();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
