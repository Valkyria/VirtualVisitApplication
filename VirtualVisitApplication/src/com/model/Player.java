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
		this.HitBox = new Rectangle();
		this.direction = Direction.FACING_DOWN;
		this.state = State.IDLE;
	}
	
	public void SetPosition(Vector2 position){
		this.position = position;
		this.HitBox.setPosition(position);
	}
	
	public void SetPosition(float x, float y){
		this.position.add(x, y);
		this.HitBox.setPosition(x, y);
	}
	
	public Vector2 GetPosition(){
		return position;
	}
	public Rectangle getHitBox(){
		return this.HitBox;
	}
	public void SetDirection(Direction direction){
		this.direction = direction;
	}
	public Direction GetDirection(){
		return this.direction;
	}
	public State GetStatus(){
		return this.state;
	}
	public void SetStatus(State state){
		this.state = state;
	}
}
