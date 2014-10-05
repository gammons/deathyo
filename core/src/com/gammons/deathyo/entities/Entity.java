package com.gammons.deathyo.entities;

import com.badlogic.gdx.math.Vector2;

public class Entity {
  public Vector2 position;
  protected int speed;
  
  public Entity() {
    position = new Vector2();
  }
  
  public void moveRight() {
    position.x += speed;
  }
  public void moveLeft() {
    position.x -= speed;
  }
  
  public void moveUp() {
    position.y += speed; 
  }
  
  public void moveDown() {
    position.y -= speed;
  }
}
