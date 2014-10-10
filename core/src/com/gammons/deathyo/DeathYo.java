package com.gammons.deathyo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class DeathYo extends Game {
	
  @Override
	public void create () {
    GameInputProcessor inputProcessor = new GameInputProcessor();
    Gdx.input.setInputProcessor(inputProcessor);
    setScreen(new GameScreen(inputProcessor));
	}

	@Override
	public void render () {
	  super.render();
	}
}
