package org.doublejava.transference.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bob extends Actor {
   final int STATE_STOPPED = 0;
   final int STATE_WALKING = 1;
   final int STATE_WALKING_BACK = 2;

   Texture texture;
   Animation walk;
   Animation walkBack;
   float stateTime = 0;
   int state = STATE_STOPPED;
   int x = 0, y = 0;

   public Bob() {
      texture = new Texture(Gdx.files.internal("data/bob_walk.png"));
      TextureRegion[][] regions = new TextureRegion(texture, texture.getWidth(), texture.getHeight()).split(16, 16);
      walk = new Animation(0.05f, regions[0]);
      TextureRegion[][] regions2 = new TextureRegion(texture, texture.getWidth(), texture.getHeight()).split(16, 16);
      for (TextureRegion tr: regions2[0]) tr.flip(true, false);
      walkBack = new Animation(0.05f, regions2[0]);
   }

   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      TextureRegion frame = (state==STATE_WALKING_BACK ? 
               walkBack.getKeyFrame(stateTime, true) : 
               walk.getKeyFrame(stateTime, true) );
      batch.draw(frame, x, y);
   }

   @Override
   public void act(float deltaTime) {
      if (Gdx.input.isKeyPressed(Keys.D)) {
         state = STATE_WALKING;
         stateTime += deltaTime;
         x += 1;
      } else if (Gdx.input.isKeyPressed(Keys.A)) {
         state = STATE_WALKING_BACK;
         stateTime += deltaTime;
         x -= 1;
      } else {
         state = STATE_STOPPED;
         stateTime = 0;
      }
   }
}
