package org.doublejava.transference.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bob extends Actor {
   Texture texture;
   Animation animation;
   float stateTime = 0;
   
   public Bob() {
      texture = new Texture(Gdx.files.internal("data/bob_walk.png"));
      TextureRegion[][] regions = new TextureRegion(texture, texture.getWidth(), texture.getHeight()).split(16, 16);
      animation = new Animation(0.1f, regions[0]);
   }
   
   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      batch.draw(animation.getKeyFrame(stateTime), 0, 0);
   }
   
   @Override
   public void act(float deltaTime) {
      stateTime += deltaTime;
   }
}
