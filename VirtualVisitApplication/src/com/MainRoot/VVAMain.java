package com.MainRoot;

import java.util.ArrayList;

import com.model.Menu;
import com.model.User;
import com.screens.LoadingScreen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

//https://dpk.net/2011/05/01/libgdx-box2d-tiled-maps-full-working-example-part-1/

public class VVAMain extends Game {
	public AssetManager assets;
	public ApplicationType Type;
	public User currentUser;
	public ArrayList<Menu> ListMenus;
	public ArrayList<Menu> ListRepasUser;
	
	@Override
	public void create() {
		//Création du GameScreen (appel automatique)
		ListMenus = new ArrayList<Menu>();
		ListRepasUser = new ArrayList<Menu>();
		setScreen(new LoadingScreen(this));
		this.Type = Gdx.app.getType();
	}
}
