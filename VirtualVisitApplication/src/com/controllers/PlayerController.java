package com.controllers;

import java.util.HashMap;
import java.util.Map;

import com.model.Player;
import com.model.Player.Direction;
import com.model.Player.State;
import com.model.World;

/* Le contr�leur du joueur d�finit l'�tat de l'objet joueur en temps r�el.
 * Il r�cup�re les �v�nements asynchrones (appui sur une touche, etc...) et transforme les fonctions en "�tat du joueur".
 * La fonction render() de WorldRenderer se base sur un �tat du joueur pour le dessiner, et le contr�leur modifie simplement l'�tat.
 */
public class PlayerController {
	
	private World world;
	private Player player;
	
	enum Keys {
		LEFT, RIGHT, UP, DOWN
	}
	
	//Abstraction des actions possibles du joueur (tous les inputs vont se r�sumer � right, left, up ou down)
	static Map<Keys, Boolean> keys = new HashMap<PlayerController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	};
	
	public PlayerController(World world){
		this.world = world;
		this.player = world.getPlayer();
	}
	
	public void update(float delta) {
		/* Ici on va changer les propri�t�s de l'objet player en fonction
		 * des inputs, des detections de collisions etc...
		 */
		if (keys.get(Keys.RIGHT)){
			player.GetPosition().set(player.GetPosition().x + 1, player.GetPosition().y);
			player.SetDirection(Direction.FACING_RIGHT);
		}
		if (keys.get(Keys.LEFT)){
			player.GetPosition().set(player.GetPosition().x - 1, player.GetPosition().y);
			player.SetDirection(Direction.FACING_LEFT);
		}
		if (keys.get(Keys.UP)){
			player.GetPosition().set(player.GetPosition().x, player.GetPosition().y + 1);
			player.SetDirection(Direction.FACING_UP);
		}
		if (keys.get(Keys.DOWN)){
			player.GetPosition().set(player.GetPosition().x, player.GetPosition().y - 1);
			player.SetDirection(Direction.FACING_DOWN);
		}
		
		//update de la hitBox + position d�s qu'un mouvement est effectu�, a am�liorer: 
		//GetPosition() ne renvois pas distinctement un X et un Y mais un vecteur
		//et vu que tu le set par player.GetPosition().x et player.GetPosition().y 
		//je recupere 2 vecteurs au lieu de deux floats
		if (keys.get(Keys.RIGHT) || keys.get(Keys.LEFT) || keys.get(Keys.UP) || keys.get(Keys.DOWN)){
			player.SetPosition(player.GetPosition());
			player.SetStatus(State.WALKING);
		}
		else{
			player.SetPosition(player.GetPosition());
			player.SetStatus(State.IDLE);
		}
	}

	/*
	 * Les fonctions suivantes servent � changer une information �v�nementielle asynchrone en un �tat statique utilisable par la m�thode render.
	 * Dans le programme, il y a une boucle qui appelle update() puis render() en permanence, et une boucle qui surveille les input. 
	 * La r�cup�ration d'un input n'est pas synchronis�e avec la methode update(), mais il faut passer les informations de l'une � l'autre.
	 * Donc � chaque fois qu'un input est r�cup�r�, on �crit dans cette classe l'action qui lui correspond, et qui ne sera modifi�e que par d'autres input.
	 * En revanche, la methode update() va lire ces actions et changer l'objet Player en fonction de celles-ci.
	 */
	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
		
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
		
	}

	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
		
	}

	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
		
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
		
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
		
	}

	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));
		
	}

	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
		
	}
}
