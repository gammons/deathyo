package com.gammons.deathyo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gammons.deathyo.entities.Death;

public class DeathView {
  public Death death;

  private Animation currentAnimation;
  private Animation walkUpAnimation;
  private Animation walkDownAnimation;
  private Animation walkLeftAnimation;
  private Animation walkRightAnimation;

  private float stateTime;
  private boolean isWalking;
  private TextureRegion currentTexture;
  private final int TEXTURE_ROWS = 3;
  private final int TEXTURE_COLS = 2;

  private boolean killBallDeployed;

  private KillBallView killBall;

  public DeathView() {
    death = new Death();
    Texture walkSheet = new Texture(Gdx.files.internal("art/death.png"));
    TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()
        / TEXTURE_COLS, walkSheet.getHeight() / TEXTURE_ROWS);

    walkRightAnimation = new Animation(0.15f, tmp[0]);
    walkLeftAnimation = new Animation(0.15f, tmp[1]);
    walkUpAnimation = new Animation(0.15f, tmp[2]);
    walkDownAnimation = walkRightAnimation;
    currentAnimation = walkRightAnimation;
    killBallDeployed = false;

  }

  public void render(SpriteBatch spriteBatch) {
    if (isWalking)
      stateTime += Gdx.graphics.getDeltaTime();

    TextureRegion texture = currentAnimation.getKeyFrame(stateTime, true);

    spriteBatch.draw(texture, death.position.x, death.position.y);
    renderKillBall(spriteBatch);

    isWalking = false;

  }

  public void renderKillBall(SpriteBatch spriteBatch) {
    if (killBallDeployed) {
      killBall.update(Gdx.graphics.getDeltaTime());
      killBall.render(spriteBatch);

    }
  }

  public void moveUp() {
    isWalking = true;
    currentAnimation = walkUpAnimation;
    death.moveUp();
  }

  public void moveDown() {
    isWalking = true;
    currentAnimation = walkDownAnimation;
    death.moveDown();
  }

  public void moveLeft() {
    isWalking = true;
    currentAnimation = walkLeftAnimation;
    death.moveLeft();
  }

  public void moveRight() {
    isWalking = true;
    currentAnimation = walkRightAnimation;
    death.moveRight();
  }

  public Vector2 position() {
    return death.position;
  }

  public void shootKillBall(Vector2 coords) {
    killBallDeployed = true;
    killBall = new KillBallView(new Vector2(death.position.x, death.position.y));
    killBall.shootToward(coords.x, coords.y);
  }

}
