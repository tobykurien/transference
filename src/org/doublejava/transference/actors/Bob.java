package org.doublejava.transference.actors;

import org.doublejava.transference.actions.GravityAction;
import org.doublejava.transference.actions.JumpAction;

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

   Texture texture;
   Animation walk;
   Animation walkBack;
   float stateTime = 0;
   boolean forward = true;
   int state = STATE_STOPPED;
   Rectangle bounds;
   JumpAction jumpAction;
   GravityAction gravityAction;
   
   public Bob() {
      texture = new Texture(Gdx.files.internal("data/bob_walk.png"));
      TextureRegion[][] regions = new TextureRegion(texture, texture.getWidth(), texture.getHeight()).split(16, 16);
      walk = new Animation(0.05f, regions[0]);
      TextureRegion[][] regions2 = new TextureRegion(texture, texture.getWidth(), texture.getHeight()).split(16, 16);
      for (TextureRegion tr: regions2[0]) tr.flip(true, false);
      walkBack = new Animation(0.05f, regions2[0]);
      bounds = new Rectangle(0, 0, 16, 16);
      jumpAction = new JumpAction(this);
      gravityAction = new GravityAction(this);
   }

   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      TextureRegion frame = (forward ? 
               walk.getKeyFrame(stateTime, true) : 
               walkBack.getKeyFrame(stateTime, true) );
      batch.draw(frame, getX(), getY());
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
         translate(1, 0);
      } else if (Gdx.input.isKeyPressed(Keys.A)) {
         state = STATE_WALKING;
         forward = false;
         stateTime += deltaTime;
         translate(-1, 0);
      } else {
         state = STATE_STOPPED;
         stateTime = 0;
      } 

      if (!jumpAction.act(deltaTime)) {
         gravityAction.act(deltaTime);
      }

      if (Gdx.input.isKeyPressed(Keys.W) && !jumpAction.isJumping()) {
         stateTime = 0;
         jumpAction.jump(forward);
      }
   }
}
