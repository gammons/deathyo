package com.gammons.deathyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.gammons.deathyo.entities.Death;
import com.gammons.deathyo.view.DeathView;

public class GameScreen implements Screen {
  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;
  private OrthographicCamera camera;
  

  private Animation walkAnimation;
  private SpriteBatch spriteBatch;
  private float stateTime;
  
  private DeathView deathView;
  
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0,0,0,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    handleInput();
    
    renderer.setView(camera);
    camera.update();
    
    renderer.render();
    deathView.render(spriteBatch);
    

  }

  private void handleInput() {
    if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
      deathView.moveLeft();
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
      deathView.moveRight();
    if(Gdx.input.isKeyPressed(Input.Keys.UP))
      deathView.moveUp();
    if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
      deathView.moveDown();
    
    
  }

  @Override
  public void resize(int width, int height) {
    camera.viewportWidth = width / 2.0f;
    camera.viewportHeight = height / 2.0f;
    camera.update();

  }

  @Override
  public void show() {
    TmxMapLoader loader = new TmxMapLoader();
    map = loader.load("maps/map.tmx");
    renderer = new OrthogonalTiledMapRenderer(map);
    camera = new OrthographicCamera();
    camera.position.set(new Vector2(160,160), 0);
    spriteBatch = new SpriteBatch();
    stateTime = 0f;
    addDeath();
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
