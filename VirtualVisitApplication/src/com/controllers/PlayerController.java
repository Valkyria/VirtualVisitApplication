package com.controllers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
	//private Vector2 newPosition;
	private Rectangle collisionContainer;
	
	enum Keys {
		LEFT, RIGHT, UP, DOWN
	}
	
	public PlayerController(World world){
		this.world = world;
		this.player = world.getPlayer();
		//this.newPosition = world.getPlayer().GetPosition();
		this.collisionContainer = new Rectangle();
	}
	
	public void update(float delta) {
		/* Ici on va changer les propri�t�s de l'objet player en fonction
		 * des inputs, des detections de collisions etc...
		 */
		Vector2 oldPosition = player.GetPosition();
		player.SetPosition(processInputs(delta)); //G�re les actions d'input pour donner la position du joueur � la frame d'apr�s.
		//Si la position du joueur � chang�, on check les collisions.
		if (!oldPosition.epsilonEquals(player.GetPosition(), 0.01f)){
			player.SetPosition(processCollision(oldPosition)); //G�re les collisions
		}	
	}

	private Vector2 processInputs(float delta){
		Vector2 position = new Vector2();
		/*
		 * Explication de l'instruction :
		 * "player.getMovementDirection().cpy().scl(Player.SPEED)"
		 * On prend la direction du mouvement, multipli� par la vitesse du mouvement 
		 * ce qui nous donne la diff�rence de position entre le joueur � la frame d'avant, et le joueur dans une seconde
		 * ".scl(delta)"
		 * Ensuite, on multiplie �a par delta, pour avoir la diff�rence de position entre le joueur � la frame d'avant,
		 * et le joueur dans delta secondes (� la frame en cours)
		 * ".add(player.GetPosition()"
		 * Finalement, on ajoute cette diff�rence � la position initiale (frame d'avant) pour nous donner
		 * la position finale (frame en cours).
		 * On d�finit la vitesse par seconde et on utilise le delta pour permettre d'avoir une vitesse constante
		 * qui ne soit pas li�e au FPS.
		 */
		position = player.getMovementDirection().cpy().scl(Player.SPEED).scl(delta).add(player.GetPosition());
		
		//Techniquement la variable newPosition est accessible � l'ensemble de la classe, 
		//mais pour des raison de clart� dans la methode update, je le renvoie quand m�me.
		return position;
	}
	
	private Vector2 processCollision(Vector2 oldPosition){
		Cell currentCell;
		int tileSize = (Integer) this.world.getMap().getProperties().get("tilewidth");
		this.collisionContainer.height = tileSize;
		this.collisionContainer.width = tileSize;
		for (int i = (int)(player.GetPosition().x/tileSize) - 1; i <= (int)(player.GetPosition().x/tileSize) + 1; i++ ){
			for (int j = (int)(player.GetPosition().y/tileSize) - 1; j <= (int)(player.GetPosition().y/tileSize) + 1; j++ ){
				currentCell = ((TiledMapTileLayer)this.world.getColisionLayer()).getCell(i, j);
				if(currentCell != null){
					this.collisionContainer.x = i*16;
					this.collisionContainer.y = j*16;
					if (player.getHitBox().overlaps(this.collisionContainer)){
						//System.out.println("collision");
						return oldPosition;
					}
				}
			}
		}
		return player.GetPosition();
	}

	/*
	 * Les fonctions suivantes servent � changer une information �v�nementielle asynchrone en un �tat statique utilisable par la m�thode render.
	 * Dans le programme, il y a une boucle qui appelle update() puis render() en permanence, et une boucle qui surveille les input. 
	 * La r�cup�ration d'un input n'est pas synchronis�e avec la methode update(), mais il faut passer les informations de l'une � l'autre.
	 * Donc � chaque fois qu'un input est r�cup�r�, on �crit dans cette classe l'action qui lui correspond, et qui ne sera modifi�e que par d'autres input.
	 * En revanche, la methode update() va lire ces actions et changer l'objet Player en fonction de celles-ci.
	 */
	public void leftPressed() {
		this.player.setMovementDirectionX(-1);
	}

	public void rightPressed() {
		this.player.setMovementDirectionX(1);
	}

	public void upPressed() {
		this.player.setMovementDirectionY(1);
	}

	public void downPressed() {
		this.player.setMovementDirectionY(-1);
	}

	public void leftReleased() {
		this.player.setMovementDirectionX(0);
	}

	public void rightReleased() {
		this.player.setMovementDirectionX(0);
	}

	public void upReleased() {
		this.player.setMovementDirectionY(0);
	}

	public void downReleased() {
		this.player.setMovementDirectionY(0);
	}
}
