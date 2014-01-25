package org.doublejava.transference.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
   Texture texture;
   
   public Background(String path) {
      texture = new Texture(Gdx.files.internal(path));
   }
   
   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
   }
}
