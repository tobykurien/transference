package org.doublejava.transference.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class JumpAction extends Action {
   Actor actor;

   boolean jumping = false;
   boolean forward = true;

   int jumpTime = 0;
   float[] jumpX = new float[]{
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1
   };
   float[] jumpY = new float[]{
      1, 1, 1, 1, 1, -1, -1, -1, -1, -1
   };
   
   public JumpAction(Actor actor) {
      this.actor = actor;
   }

   @Override
   public boolean act(float delta) {
      if (!jumping) return false;
      int dir = (forward ? 1 : -1);
      actor.translate(jumpX[jumpTime] * 5 * dir, jumpY[jumpTime] * 15);
      jumpTime += 1;
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
