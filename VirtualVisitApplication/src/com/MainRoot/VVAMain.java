package com.MainRoot;

import com.screens.GameScreen;
import com.badlogic.gdx.Game;

//https://dpk.net/2011/05/01/libgdx-box2d-tiled-maps-full-working-example-part-1/

public class VVAMain extends Game {
	
	@Override
	public void create() {
		//Création du GameScreen (appel automatique)
		setScreen(new GameScreen());
	}
}
