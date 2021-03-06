package com.gammons.deathyo.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class DeathView extends Sprite {

  private Animation currentAnimation;
  private Animation walkUpAnimation;
  private Animation walkDownAnimation;
  private Animation walkLeftAnimation;
  private Animation walkRightAnimation;

  private float stateTime = 0;
  private boolean isWalking;
  private final int TEXTURE_ROWS = 3;
  private final int TEXTURE_COLS = 2;

  private final int PIXEL_WIDTH = 16;
  private final int PIXEL_HEIGHT = 16;
  private final int speed = 100;

  public DeathView() {
    Texture walkSheet = new Texture(Gdx.files.internal("art/death.png"));
    TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()
        / TEXTURE_COLS, walkSheet.getHeight() / TEXTURE_ROWS);

    setupAnimations(tmp);
    setAnimation();
    setSize(PIXEL_WIDTH, PIXEL_HEIGHT);

  }

  @Override
  public void draw(Batch spriteBatch) {
    if (isWalking)
      stateTime += Gdx.graphics.getDeltaTime();

    setAnimation();
    super.draw(spriteBatch);
    isWalking = false;

  }

  public void moveUp(float delta) {
    isWalking = true;
    currentAnimation = walkUpAnimation;
    setY(getY() + speed * delta);

  }

  public void moveDown(float delta) {
    isWalking = true;
    currentAnimation = walkDownAnimation;
    setY(getY() - speed * delta);
  }

  public void moveLeft(float delta) {
    isWalking = true;
    currentAnimation = walkLeftAnimation;
    setX(getX() - speed * delta);
  }

  public void moveRight(float delta) {
    isWalking = true;
    currentAnimation = walkRightAnimation;
    setX(getX() + speed * delta);
  }

  public Rectangle rectangle() {
    return new Rectangle(getX(), getY(), getWidth(), getHeight());
  }

  private void setAnimation() {
    TextureRegion region = currentAnimation.getKeyFrame(stateTime, true);
    setRegion(region);
  }

  private void setupAnimations(TextureRegion[][] tmp) {
    walkRightAnimation = new Animation(0.15f, tmp[0]);
    walkLeftAnimation = new Animation(0.15f, tmp[1]);
    walkUpAnimation = new Animation(0.15f, tmp[2]);
    walkDownAnimation = walkRightAnimation;
    currentAnimation = walkRightAnimation;
  }

}
