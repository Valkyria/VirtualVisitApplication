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

/* Le contrôleur du joueur définit l'état de l'objet joueur en temps réel.
 * Il récupère les évènements asynchrones (appui sur une touche, etc...) et transforme les fonctions en "état du joueur".
 * La fonction render() de WorldRenderer se base sur un état du joueur pour le dessiner, et le contrôleur modifie simplement l'état.
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
		/* Ici on va changer les propriétés de l'objet player en fonction
		 * des inputs, des detections de collisions etc...
		 */
		Vector2 oldPosition = player.GetPosition();
		player.SetPosition(processInputs(delta)); //Gère les actions d'input pour donner la position du joueur à la frame d'après.
		//Si la position du joueur à changé, on check les collisions.
		if (!oldPosition.epsilonEquals(player.GetPosition(), 0.01f)){
			player.SetPosition(processCollision(oldPosition)); //Gère les collisions
		}	
	}

	private Vector2 processInputs(float delta){
		Vector2 position = new Vector2();
		/*
		 * Explication de l'instruction :
		 * "player.getMovementDirection().cpy().scl(Player.SPEED)"
		 * On prend la direction du mouvement, multiplié par la vitesse du mouvement 
		 * ce qui nous donne la différence de position entre le joueur à la frame d'avant, et le joueur dans une seconde
		 * ".scl(delta)"
		 * Ensuite, on multiplie ça par delta, pour avoir la différence de position entre le joueur à la frame d'avant,
		 * et le joueur dans delta secondes (à la frame en cours)
		 * ".add(player.GetPosition()"
		 * Finalement, on ajoute cette différence à la position initiale (frame d'avant) pour nous donner
		 * la position finale (frame en cours).
		 * On définit la vitesse par seconde et on utilise le delta pour permettre d'avoir une vitesse constante
		 * qui ne soit pas liée au FPS.
		 */
		position = player.getMovementDirection().cpy().scl(Player.SPEED).scl(delta).add(player.GetPosition());
		
		//Techniquement la variable newPosition est accessible à l'ensemble de la classe, 
		//mais pour des raison de clarté dans la methode update, je le renvoie quand même.
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
	 * Les fonctions suivantes servent à changer une information événementielle asynchrone en un état statique utilisable par la méthode render.
	 * Dans le programme, il y a une boucle qui appelle update() puis render() en permanence, et une boucle qui surveille les input. 
	 * La récupération d'un input n'est pas synchronisée avec la methode update(), mais il faut passer les informations de l'une à l'autre.
	 * Donc à chaque fois qu'un input est récupéré, on écrit dans cette classe l'action qui lui correspond, et qui ne sera modifiée que par d'autres input.
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
