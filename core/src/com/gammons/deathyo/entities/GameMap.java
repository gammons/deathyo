package com.gammons.deathyo.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameMap {
  private TiledMap map;

  public GameMap() {
    TmxMapLoader loader = new TmxMapLoader();
    map = loader.load("maps/map.tmx");

  }

  public TiledMap getMap() {
    return map;
  }

  public void dispose() {
    map.dispose();
  }
}
