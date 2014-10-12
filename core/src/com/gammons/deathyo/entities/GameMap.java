package com.gammons.deathyo.entities;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameMap {
  private TiledMap map;
  private TiledMapTileLayer collisionLayer;
  private float tileWidth;
  private float tileHeight;
  private final String BLOCKED_KEY = "blocked";
  private int width;
  private int height;

  private class GameCell {
    public int id;
    public boolean collision;
    public Rectangle gameCoords;
  }

  private GameCell[][] cells;

  public GameMap() {
    TmxMapLoader loader = new TmxMapLoader();
    map = loader.load("maps/map.tmx");
    collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
    tileHeight = collisionLayer.getTileHeight();
    tileWidth = collisionLayer.getTileWidth();
    width = collisionLayer.getWidth();
    height = collisionLayer.getHeight();
    System.out.println(width);
    System.out.println(height);
    cells = new GameCell[width][height];
    // debugMap();
    processMap();
  }

  private void processMap() {
    Iterator<MapLayer> i = map.getLayers().iterator();
    while (i.hasNext()) {
      System.out.println("######### LAYER ##########");

      TiledMapTileLayer tl = (TiledMapTileLayer) i.next();
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          Cell cell = tl.getCell(x, y);
          if (cell == null)
            continue;
          if (cells[x][y] == null) {
            cells[x][y] = new GameCell();
            cells[x][y].gameCoords = new Rectangle(x * tileWidth, y
                * tileHeight, tileWidth, tileHeight);
          }
          cells[x][y].id = cell.getTile().getId();
          cells[x][y].collision = cell.getTile().getProperties()
              .containsKey(BLOCKED_KEY);

        }
      }
    }
  }

  public TiledMap getMap() {
    return map;
  }

  public void dispose() {
    map.dispose();
  }

  public boolean willCollideLeft(Rectangle entityPosition) {
    Iterator<GameCell> i = getCollidingTiles(entityPosition).iterator();
    Vector2 tileCenter = new Vector2();
    Vector2 entityCenter = new Vector2();
    GameCell c;
    while (i.hasNext()) {
      c = (GameCell) i.next();

      if (c.collision) {
        c.gameCoords.getCenter(tileCenter);
        entityPosition.getCenter(entityCenter);
        if ((tileCenter.x < entityCenter.x)
            && (Math.abs(tileCenter.y - entityCenter.y) < tileHeight / 2))
          return true;
      }
    }
    return false;
  }

  public boolean willCollideUp(Rectangle entityPosition) {
    Iterator<GameCell> i = getCollidingTiles(entityPosition).iterator();
    Vector2 tileCenter = new Vector2();
    Vector2 entityCenter = new Vector2();
    GameCell c;
    while (i.hasNext()) {
      c = (GameCell) i.next();

      if (c.collision) {
        c.gameCoords.getCenter(tileCenter);
        entityPosition.getCenter(entityCenter);
        if ((tileCenter.y > entityCenter.y)
            && (Math.abs(tileCenter.x - entityCenter.x) < tileWidth / 2))
          return true;
      }
    }
    return false;
  }

  public boolean willCollideDown(Rectangle entityPosition) {
    Iterator<GameCell> i = getCollidingTiles(entityPosition).iterator();
    Vector2 tileCenter = new Vector2();
    Vector2 entityCenter = new Vector2();
    GameCell c;
    while (i.hasNext()) {
      c = (GameCell) i.next();

      if (c.collision) {
        c.gameCoords.getCenter(tileCenter);
        entityPosition.getCenter(entityCenter);
        if ((tileCenter.y < entityCenter.y)
            && (Math.abs(tileCenter.x - entityCenter.x) < tileWidth / 2))
          return true;
      }
    }
    return false;
  }

  public boolean willCollideRight(Rectangle entityPosition) {
    Iterator<GameCell> i = getCollidingTiles(entityPosition).iterator();
    Vector2 tileCenter = new Vector2();
    Vector2 entityCenter = new Vector2();
    GameCell c;
    while (i.hasNext()) {
      c = (GameCell) i.next();

      if (c.collision) {
        c.gameCoords.getCenter(tileCenter);
        entityPosition.getCenter(entityCenter);
        if ((tileCenter.x > entityCenter.x)
            && (Math.abs(tileCenter.y - entityCenter.y) < tileHeight / 2))
          return true;

      }
    }
    return false;
  }

  public boolean willCollideAny(Rectangle entityPosition) {
    Iterator<GameCell> i = getCollidingTiles(entityPosition).iterator();
    GameCell c;
    Vector2 tileCenter = new Vector2();
    Vector2 entityCenter = new Vector2();
    while (i.hasNext()) {
      c = (GameCell) i.next();

      if (c.collision)
        c.gameCoords.getCenter(tileCenter);
      entityPosition.getCenter(entityCenter);
      if ((Math.abs(tileCenter.x - entityCenter.x) < tileWidth / 2)
          && (Math.abs(tileCenter.y - entityCenter.y) < tileHeight / 2))
        return true;

    }
    return false;
  }

  private ArrayList<GameCell> getCollidingTiles(Rectangle entityPosition) {
    ArrayList<GameCell> collidingTiles = new ArrayList<GameCell>();
    for (int x = 0; x < cells.length; x++) {
      for (int y = 0; y < cells.length; y++) {
        if (Intersector.intersectRectangles(entityPosition,
            cells[x][y].gameCoords, new Rectangle())) {
          collidingTiles.add(cells[x][y]);
        }
      }
    }
    return collidingTiles;
  }

  private void debugMap() {
    Iterator<MapLayer> i = map.getLayers().iterator();
    while (i.hasNext()) {
      System.out.println("######### LAYER ##########");

      TiledMapTileLayer tl = (TiledMapTileLayer) i.next();
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          Cell cell = tl.getCell(x, y);
          System.out.println("Cell at (" + x + "," + y + ") is "
              + cell.getTile().getId());
        }
      }
    }
  }

}
