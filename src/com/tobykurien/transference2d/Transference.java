package com.tobykurien.transference2d;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.tobykurien.transference2d.stage.Level1;

/**
 * Re-implementation of Transference level1 using Scene2d and Box2d
 * @author toby
 *
 */
public class Transference extends Game {
   private Level1 level1;
   
   @Override
   public void create() {
      level1 = new Level1();
      Gdx.input.setInputProcessor(level1);
   }

   @Override
   public void dispose() {
      level1.dispose();
   }

   @Override
   public void pause() {
   }

   @Override
   public void render() {
      Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
      level1.act(Gdx.graphics.getDeltaTime());
      level1.draw();
   }

   @Override
   public void resize(int width, int height) {
      level1.setViewport(480, 320, true);
      level1.getCamera().translate(-level1.getGutterWidth(), -level1.getGutterHeight(), 0);
   }

   @Override
   public void resume() {
   }

}
