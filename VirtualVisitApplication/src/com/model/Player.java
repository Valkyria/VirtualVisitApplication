package com.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	
	//L'état du joueur (pour déterminer l'animation)
	public enum State {
        IDLE, WALKING
    }
	
	//La direction dans laquelle il est tourné
	public enum Direction {
		FACING_RIGHT, FACING_LEFT, FACING_UP, FACING_DOWN
	}
	
	public static final float SIZE = 1f; //La taille du joueur par rapport à la camera (Ici, une case de haut et de large).
	
	State state = State.IDLE;
	Direction direction = Direction.FACING_UP;
	
	private Vector2 position;
	private Rectangle HitBox;
	
	public Player(Vector2 position){
		this.position = position;
	}
	
	//a appeler dans le player ctrler
	public void SetPosition(Vector2 position){
		this.position = position;
		this.HitBox.setPosition(position);
	}
	
	public Vector2 GetPosition(){
		return position;
	}
	public void setHitBox(float width, float height, Vector2 position){
		this.HitBox.width=width;
		this.HitBox.height=height;
		this.HitBox.setPosition(position);
	}
	public Rectangle getHitBox(){
		return this.HitBox;
	}
}
