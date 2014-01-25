package org.doublejava.transference.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bob extends Actor {
   final int STATE_STOPPED = 0;
   final int STATE_WALKING = 1;
   final int STATE_JUMP = 3;

   Texture texture;
   Animation walk;
   Animation walkBack;
   float stateTime = 0;
   boolean forward = true;
   int state = STATE_STOPPED;
   int x = 0, y = 0;
   Rectangle bounds;
   private float velocity;

   int jumpTime = 0;
   float[] jumpX = new float[]{
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1
   };
   float[] jumpY = new float[]{
      1, 1, 1, 1, 1, -1, -1, -1, -1, -1
   };
   
   public Bob(int initX, int initY) {
      texture = new Texture(Gdx.files.internal("data/bob_walk.png"));
      TextureRegion[][] regions = new TextureRegion(texture, texture.getWidth(), texture.getHeight()).split(16, 16);
      walk = new Animation(0.05f, regions[0]);
      TextureRegion[][] regions2 = new TextureRegion(texture, texture.getWidth(), texture.getHeight()).split(16, 16);
      for (TextureRegion tr: regions2[0]) tr.flip(true, false);
      walkBack = new Animation(0.05f, regions2[0]);
      x = initX;
      y = initY;
      bounds = new Rectangle(0, 0, 16, 16);
   }

   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      TextureRegion frame = (forward ? 
               walk.getKeyFrame(stateTime, true) : 
               walkBack.getKeyFrame(stateTime, true) );
      batch.draw(frame, x, y);
   }
   
   @Override
   public Actor hit(float x, float y, boolean touchable) {
      if (bounds.contains(x, y)) {
         return this;
      } else {
         return null;
      }
   }

   @Override
   public void act(float deltaTime) {
      if (Gdx.input.isKeyPressed(Keys.D)) {
         state = STATE_WALKING;
         forward = true;
         stateTime += deltaTime;
         x += 1;
      } else if (Gdx.input.isKeyPressed(Keys.A)) {
         state = STATE_WALKING;
         forward = false;
         stateTime += deltaTime;
         x -= 1;
      } else if (jumpTime == 0) {
         state = STATE_STOPPED;
         stateTime = 0;
      } 

      if (Gdx.input.isKeyPressed(Keys.W) && state != STATE_JUMP) {
         state = STATE_JUMP;
         stateTime = 0;
         jumpTime = 0;
      }
      
      if (state == STATE_JUMP) {
         int dir = (forward ? 1 : -1);
         x += jumpX[jumpTime] * 5 * dir;
         y += jumpY[jumpTime] * 15;
         jumpTime += 1;
         if (jumpTime >= jumpX.length) {
            state = STATE_STOPPED;
         }
      }
   }
}
