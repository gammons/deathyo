package com.gammons.deathyo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class KillBallView {
  private Vector2 position;
  private Vector2 velocity = new Vector2();
  private float speedMax = 1000;
  private Texture ballTexture;

  public KillBallView(Vector2 origin) {
    ballTexture = new Texture(Gdx.files.internal("art/ball.png"));
    position = origin;
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
    velocity.set(targetX - position.x, targetY - position.y).nor()
        .scl(speedMax);
  }

  public void update(float deltaTime) {
    position.add(velocity.x * deltaTime, velocity.y * deltaTime);
    // velocity.scl(1 - (0.98f * deltaTime)); // Linear dampening, otherwise the
    // ball will keep going at the original velocity forever
  }

  public void render(SpriteBatch spriteBatch) {
    spriteBatch.draw(ballTexture, position.x, position.y);
  }

}
