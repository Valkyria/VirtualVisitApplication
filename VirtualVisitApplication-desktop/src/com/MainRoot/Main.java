package com.MainRoot;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "VirtualVisitApplication";
		cfg.useGL20 = false;
		cfg.width = 720;
		cfg.height = 405;
		
		new LwjglApplication(new VVAMain(), cfg);
	}
}
