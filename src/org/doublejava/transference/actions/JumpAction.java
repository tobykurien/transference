package org.doublejava.transference.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class JumpAction extends Action {
   Actor actor;

   boolean jumping = false;
   boolean forward = true;

   float jumpTime = 0;
   float[] jumpX = new float[]{
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1
   };
   float[] jumpY = new float[]{
      1, 1, 0.8f, 0.5f, 0.2f, -0.2f, -0.5f, -0.8f, -1, -1
   };
   
   public JumpAction(Actor actor) {
      this.actor = actor;
   }

   @Override
   public boolean act(float delta) {
      if (!jumping) return false;
      
      int dir = (forward ? 1 : -1);
      float deltaX = jumpX[(int)jumpTime] * 3 * dir;
      float deltaY = jumpY[(int)jumpTime] * 7;
      actor.translate(0, deltaY + GravityAction.GRAVITY); // overcome gravity
      jumpTime += delta * 20;
      if (jumpTime >= jumpX.length) {
         jumping = false;
      }

      return true;
   }

   public void jump(boolean forward) {
      this.forward = forward;
      jumping = true;
      jumpTime = 0;
   }

   public boolean isJumping() {
      return jumping;
   }

   public void setJumping(boolean jumping) {
      this.jumping = jumping;
   }
}
