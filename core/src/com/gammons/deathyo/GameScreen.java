package com.gammons.deathyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gammons.deathyo.entities.GameMap;
import com.gammons.deathyo.view.DeathView;
import com.gammons.deathyo.view.KillBallView;

public class GameScreen implements Screen {

  private OrthogonalTiledMapRenderer renderer;
  private OrthographicCamera camera;

  private Animation walkAnimation;
  private SpriteBatch spriteBatch;
  private float stateTime;

  private DeathView deathView;
  private GameInputProcessor input;
  private GameMap map = new GameMap();
  private ShapeRenderer shapeRenderer = new ShapeRenderer();
  private KillBallView killBallView;
  private boolean killBallDeployed;

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

    handleInput(delta);

    renderer.setView(camera);
    renderer.render();

    camera.update();
    // debugShapes();
    renderer.getSpriteBatch().begin();
    deathView.draw(renderer.getSpriteBatch());
    if (killBallDeployed)
      renderKillBall(renderer.getSpriteBatch());

    renderer.getSpriteBatch().end();

  }

  private void renderKillBall(Batch spriteBatch) {
    killBallView.update(Gdx.graphics.getDeltaTime());
    killBallView.render(spriteBatch);
  }

  public void shootKillBall(Vector2 coords) {
    if (!killBallDeployed) {
      killBallDeployed = true;
      Vector2 center = new Vector2();
      deathView.rectangle().getCenter(center);

      killBallView = new KillBallView(center);
      killBallView.shootToward(coords.x, coords.y);
    }
  }

  private void debugShapes() {
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(ShapeType.Line);
    shapeRenderer.setColor(1, 1, 0, 1);
    Rectangle rect = new Rectangle(deathView.getX(), deathView.getY(),
        deathView.getWidth(), deathView.getHeight());
    shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
    shapeRenderer.end();
  }

  private void handleInput(float delta) {
    if (input.isDownPressed() && !map.willCollideDown(deathView.rectangle()))
      deathView.moveDown(delta);
    if (input.isLeftPressed() && !map.willCollideLeft(deathView.rectangle()))
      deathView.moveLeft(delta);
    if (input.isRightPressed() && !map.willCollideRight(deathView.rectangle()))
      deathView.moveRight(delta);
    if (input.isUpPressed() && !map.willCollideUp(deathView.rectangle()))
      deathView.moveUp(delta);
    if (input.isMousePressed()) {
      shootKillBall(input.getMouseCoords(camera));
    }
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
    deathView.setPosition(0, 32);
  }

}
