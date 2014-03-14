package com.model;

import com.badlogic.gdx.math.Vector2;

public class Player {
	
	//L'�tat du joueur (pour d�terminer l'animation)
	public enum State {
        IDLE, WALKING
    }
	
	//La direction dans laquelle il est tourn�
	public enum Direction {
		FACING_RIGHT, FACING_LEFT, FACING_UP, FACING_DOWN
	}
	
	public static final float SIZE = 1f; //La taille du joueur par rapport � la camera (Ici, une case de haut et de large).
	
	State state = State.IDLE;
	Direction direction = Direction.FACING_UP;
	
	private Vector2 position;
	
	public Player(Vector2 position){
		this.position = position;
	}
	
	public Vector2 GetPosition(){
		return position;
	}
}
