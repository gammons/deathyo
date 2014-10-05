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
  public final int TEXTURE_ROWS = 3;
  public final int TEXTURE_COLS = 2;
  
  public DeathView() {
    death = new Death();
    Texture walkSheet = new Texture(Gdx.files.internal("art/death.png"));
    TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/TEXTURE_COLS, walkSheet.getHeight()/TEXTURE_ROWS);
    
/*    TextureRegion[] walkFrames = new TextureRegion[TEXTURE_COLS * TEXTURE_ROWS];
    int index = 0;
    for (int i = 0; i < 1; i++) {
        for (int j = 0; j < 2; j++) {
            walkFrames[index++] = tmp[i][j];
        }
    }*/
    walkRightAnimation = new Animation(0.15f, tmp[0]);
    walkLeftAnimation = new Animation(0.15f, tmp[1]);
    walkUpAnimation = new Animation(0.15f, tmp[2]);
    walkDownAnimation = walkRightAnimation;
    
    currentAnimation = walkRightAnimation;

  }
  
  
  public void render(SpriteBatch spriteBatch) {
    if (isWalking)
      stateTime += Gdx.graphics.getDeltaTime();

    TextureRegion texture = currentAnimation.getKeyFrame(stateTime, true);
    spriteBatch.begin();
    spriteBatch.draw(texture, death.position.x, death.position.y);             // #17
    spriteBatch.end();
    isWalking = false;

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

}
