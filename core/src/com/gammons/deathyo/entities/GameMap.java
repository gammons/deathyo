package com.gammons.deathyo.entities;

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
    // debugMap();
    processMap();
  }

  public void debugMap() {
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

  public void processMap() {
    Iterator<MapLayer> i = map.getLayers().iterator();
    while (i.hasNext()) {
      System.out.println("######### LAYER ##########");

      TiledMapTileLayer tl = (TiledMapTileLayer) i.next();
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          Cell cell = tl.getCell(x, y);
          GameCell gc = new GameCell();
          gc.id = cell.getTile().getId();
          gc.gameCoords = new Rectangle(x * width, y * height, width, height);
          gc.collision = cell.getTile().getProperties()
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
    // gather a list of cells that touch the top left and bottom right
    // cells are rectangles too!
    /*
     * 1. Create polygons out of our cells. can be done beforehand. 2. Find the
     * cells that intersect the left line of entityPosition
     * (intersectLinePolygon) 3. determine of those cells are collision cells.
     */
    Intersector i = new Intersector();
    // i.intersectRectangles(position, rectangle2, null);
    return false;
  }

  public boolean willCollideRight(Vector2 position) {
    Vector2 currentCell = positionToCell(position);
    return isCellBlocked(new Vector2(currentCell.x + 1, currentCell.y));
  }

  public boolean willCollideUp(Vector2 position) {
    Vector2 currentCell = positionToCell(position);
    return isCellBlocked(new Vector2(currentCell.x, currentCell.y + 1));
  }

  public boolean willCollideDown(Vector2 position) {
    Vector2 currentCell = positionToCell(position);
    return isCellBlocked(new Vector2(currentCell.x, currentCell.y - 1));
  }

  private Vector2 positionToCell(Vector2 position) {

    Vector2 v = new Vector2((int) (position.x / (tileWidth * 2)),
        (int) (position.y / (tileHeight * 2)));

    // reverse height, as our tile starts at top left
    // v.y = (height - v.y);

    return v;
  }

  private boolean isCellBlocked(Vector2 currentCell) {
    System.out.println("looking at cell at (" + currentCell + ")");
    Iterator<MapLayer> i = map.getLayers().iterator();
    while (i.hasNext()) {
      TiledMapTileLayer tl = (TiledMapTileLayer) i.next();
      Cell cell = tl.getCell((int) (currentCell.x), (int) currentCell.y);
      boolean collides = cell != null && cell.getTile() != null
          && cell.getTile().getProperties().containsKey(BLOCKED_KEY);
      if (collides) {

        System.out.println("collides is true, tileId = "
            + cell.getTile().getId());
        return true;
      }
    }
    return false;
  }

}
