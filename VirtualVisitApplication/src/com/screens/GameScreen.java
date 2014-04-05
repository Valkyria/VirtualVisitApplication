/**
 * 
 */
package com.screens;

import com.screens.MenuScreen;
import com.MainRoot.VVAMain;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
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
	private float CurrentStateTime;
	private VVAMain game;
	private Music music;
	private Vector2 initialTouch;
	private Vector2 currentTouch;
	private ShapeRenderer shapeRenderer;
	
	/*
	 * Appelé quand on veut afficher le screen pour la première fois. Par
	 * exemple sous Android, quand on lance l'application, la methode show() est
	 * appelée, mais quand on verrouille le téléphone avec l'appli au premier
	 * plan, puis qu'on le déverrouille, elle n'est pas appelée, de même avec la
	 * touche home suivi du relancement de l'application. Cependant, quand
	 * l'appli est quittée via la touche retour, ou que, lorsqu'elle est en
	 * arrière plan on vide la mémoire, alors la méthode show() est appelée à
	 * nouveau au lancement.
	 */

	public GameScreen(VVAMain g) {
		this.game = g;
	}

	@Override
	public void show() {
		// Ajouter un nouveau world, un nouveau worldRenderer, et un nouveau
		// PlayerController
		this.world = new World();
		this.renderer = new WorldRenderer(world, this.game);
		this.playerController = new PlayerController(world);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		this.initialTouch = new Vector2();
		this.currentTouch = new Vector2();
		this.shapeRenderer = new ShapeRenderer();
		
		music = Gdx.audio.newMusic(Gdx.files
				.internal("data/song/The Snow Queen.mp3"));
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();
	}

	public void retourMenu() {
		this.game.setScreen(new MenuScreen(this.game));
	}
	public void drawCircle(){
		shapeRenderer.setColor(Color.BLACK);
		if(game.Type.equals(ApplicationType.Android)){
			Gdx.gl10.glLineWidth(world.getPlayer().getHitBox().width/2);
			shapeRenderer.circle(this.initialTouch.x, this.initialTouch.y+Gdx.graphics.getHeight(), world.getPlayer().getHitBox().width*4);
			shapeRenderer.circle(this.initialTouch.x, this.initialTouch.y+Gdx.graphics.getHeight(), world.getPlayer().getHitBox().width*6);
		}
		else{
			Gdx.gl10.glLineWidth(world.getPlayer().getHitBox().width/5);
			shapeRenderer.circle(this.initialTouch.x, this.initialTouch.y+Gdx.graphics.getHeight(), world.getPlayer().getHitBox().width);
			shapeRenderer.circle(this.initialTouch.x, this.initialTouch.y+Gdx.graphics.getHeight(), world.getPlayer().getHitBox().width*2);
		}
		
		shapeRenderer.line(this.initialTouch.x, this.initialTouch.y+Gdx.graphics.getHeight(), Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());
	}
	/*
	 * Appelé à chaque fois que l'écran se redessine. delta correspond au temps
	 * écoulé entre deux appels de render.
	 */
	@Override
	public void render(float delta) {
		// On update l'état du joueur
		playerController.update(delta);
		// On redessine le monde (WorldRenderer a accès à la map et au joueur,
		// il peut donc tout dessiner).
		CurrentStateTime = renderer.GetStateTime();
		renderer.SetStateTime(CurrentStateTime += Gdx.graphics.getDeltaTime());
		renderer.render();
		if(Gdx.input.isTouched()){
			shapeRenderer.begin(ShapeType.Line);
				this.drawCircle();
			shapeRenderer.end();
		}
		

	}

	/*
	 * Appelé lorsque l'écran est redimensionné.
	 */
	@Override
	public void resize(int width, int height) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		// Arrête la surveillance des input quand l'appli est en arrière plan
		Gdx.input.setInputProcessor(null);
		music.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	/*
	 * Appelé quand libgdx détruit les ressources utilisées.
	 */
	@Override
	public void dispose() {
		// Arrête la surveillance des input quand l'appli se ferme.
		Gdx.input.setInputProcessor(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		// Appellée lorsqu'une touche du clavier physique est pressée.
		if (keycode == Keys.LEFT)
			playerController.leftPressed();
		if (keycode == Keys.RIGHT)
			playerController.rightPressed();
		if (keycode == Keys.UP)
			playerController.upPressed();
		if (keycode == Keys.DOWN)
			playerController.downPressed();

		// La valeur de retour informe l'InputProcessor qu'on a géré cet input.
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (keycode == Keys.ESCAPE)
			retourMenu();
		if (keycode == Keys.BACK)
			retourMenu();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.initialTouch.set(screenX, -screenY);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.world.getPlayer().setMovementDirectionX(0);
		this.world.getPlayer().setMovementDirectionY(0);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		this.currentTouch.set(screenX, -screenY);
		/*
		 * Ici, je soustrais le vecteur de la nouvelle position du doigt au
		 * vecteur de l'ancienne position puis je le normalise (sa norme
		 * (longueur) vaut 1) et j'en fais une copie. Ça me permet d'obtenir une
		 * information de direction (deux coordonées entre 0 et 1) mais pas de
		 * distance. La copie du vecteur est faite pour passer un nouvel objet
		 * vecteur au player, pour eviter de travailler sur un même objet ici et
		 * dans la classe PlayerController.
		 */
		this.world.getPlayer().setMovementDirection(
				currentTouch.sub(this.initialTouch).nor().cpy());
		// System.out.println(this.world.getPlayer().getMovementDirection());
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
