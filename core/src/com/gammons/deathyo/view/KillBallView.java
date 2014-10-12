package com.gammons.deathyo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gammons.deathyo.entities.GameMap;

public class KillBallView {
  private Vector2 velocity = new Vector2();
  private float speedMax = 300;
  private Sprite killBall;
  private GameMap map;
  private boolean collided;

  public KillBallView(Vector2 origin, GameMap _map) {
    map = _map;
    Texture ballTexture = new Texture(Gdx.files.internal("art/ball.png"));
    killBall = new Sprite(ballTexture);
    killBall.setPosition(origin.x, origin.y);
    killBall.setSize(8, 8);

  }

  public void shootToward(float targetX, float targetY) {
    /*
     * Get the normalized direction vector from our position to the target. Then
     * scale that value to our desired speed. In this particular example, we are
     * using the distance of the target from the current position to determine
     * how fast we will shoot the ball, and limiting to a maximum speed. We will
     * apply velocity in the update method.
     */
    // velocity.set(targetX - position.x, targetY -
    // position.y).nor().scl(Math.min(position.dst(targetX, targetY),
    // speedMax));
    velocity.set(targetX - killBall.getX(), targetY - killBall.getY()).nor()
        .scl(speedMax);
  }

  public void update(float deltaTime) {
    Vector2 v = new Vector2(killBall.getX(), killBall.getY());
    v.add(velocity.x * deltaTime, velocity.y * deltaTime);
    killBall.setX(v.x);
    killBall.setY(v.y);
    if (map.willCollideAny(killBall.getBoundingRectangle())) {
      collided = true;
    }
  }

  public boolean collided() {
    return collided;
  }

  public void render(Batch batch) {
    killBall.draw(batch);
  }

}
