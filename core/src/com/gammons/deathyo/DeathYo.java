package com.gammons.deathyo;

import com.badlogic.gdx.Game;

public class DeathYo extends Game {
	
  @Override
	public void create () {
    setScreen(new GameScreen());
	}

	@Override
	public void render () {
	  super.render();
	}
}
