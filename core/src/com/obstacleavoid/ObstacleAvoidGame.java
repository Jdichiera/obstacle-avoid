package com.obstacleavoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.obstacleavoid.screen.GameScreen;
import com.obstacleavoid.tossout.ShapeRendererSample;


// Extend game to allow for multiple screens
public class ObstacleAvoidGame extends Game {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new ShapeRendererSample());
	}
}
