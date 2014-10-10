package com.gammons.deathyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.gammons.deathyo.entities.GameMap;
import com.gammons.deathyo.view.DeathView;

public class GameScreen implements Screen {

  private OrthogonalTiledMapRenderer renderer;
  private OrthographicCamera camera;

  private Animation walkAnimation;
  private SpriteBatch spriteBatch;
  private float stateTime;

  private DeathView deathView;
  private GameInputProcessor input;

  private GameMap map = new GameMap();

  public GameScreen(GameInputProcessor _input) {
    input = _input;
  }

  @Override
  public void show() {

    renderer = new OrthogonalTiledMapRenderer(map.getMap());
    camera = new OrthographicCamera();
    camera.position.set(new Vector2(160, 160), 0);
    spriteBatch = new SpriteBatch();
    stateTime = 0f;
    addDeath();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    handleInput();

    renderer.setView(camera);
    renderer.render();
    camera.update();

    spriteBatch.begin();

    deathView.render(spriteBatch);
    spriteBatch.end();

  }

  private void handleInput() {
    if (input.isDownPressed())
      deathView.moveDown();
    if (input.isLeftPressed())
      deathView.moveLeft();
    if (input.isRightPressed())
      deathView.moveRight();
    if (input.isUpPressed())
      deathView.moveUp();
    if (input.isMousePressed())
      deathView.shootKillBall(input.getMouseCoords());
  }

  @Override
  public void resize(int width, int height) {
    camera.viewportWidth = width / 2.0f;
    camera.viewportHeight = height / 2.0f;
    camera.update();
    input.setWindow(width, height);

  }

  @Override
  public void hide() {
    dispose();

  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub

  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub

  }

  @Override
  public void dispose() {
    map.dispose();
    renderer.dispose();

  }

  private void addDeath() {
    deathView = new DeathView();
    deathView.death.position.x = 50;
    deathView.death.position.y = 50;
  }

}
