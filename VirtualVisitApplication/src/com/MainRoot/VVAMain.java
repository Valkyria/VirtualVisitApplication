package com.MainRoot;

import com.screens.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

//https://dpk.net/2011/05/01/libgdx-box2d-tiled-maps-full-working-example-part-1/

public class VVAMain extends Game {
	public AssetManager assets;
	
	@Override
	public void create() {
		//Cr�ation du GameScreen (appel automatique)
		setScreen(new LoadingScreen(this));
	}
}
