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
   final int STATE_WALKING_BACK = 2;
   final int STATE_JUMP = 3;

   Texture texture;
   Animation walk;
   Animation walkBack;
   float stateTime = 0;
   int state = STATE_STOPPED;
   int x = 0, y = 0;
   Rectangle bounds;
   private float velocity;

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
      TextureRegion frame = (state==STATE_WALKING_BACK ? 
               walkBack.getKeyFrame(stateTime, true) : 
               walk.getKeyFrame(stateTime, true) );
      batch.draw(frame, x, y);
   }
   
   @Override
   public Actor hit(float x, float y, boolean touchable) {
      // TODO Auto-generated method stub
      return super.hit(x, y, touchable);
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
      } else if (Gdx.input.isKeyPressed(Keys.W)) {
         state = STATE_JUMP;
         stateTime = 0;
         velocity = 10;
      } else if (velocity == 0){
         state = STATE_STOPPED;
         stateTime = 0;
      }
      
      if (state == STATE_JUMP) {
         velocity -= 0.1f;
         x += velocity * 0.5f;
         y += velocity * 0.5f;
      }
   }
}
