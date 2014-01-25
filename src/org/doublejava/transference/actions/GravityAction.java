package org.doublejava.transference.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GravityAction extends Action {
   Actor actor;
   
   int minY = 50;
   
   public GravityAction(Actor actor) {
      this.actor = actor;
   }
   
   @Override
   public boolean act(float deltaTime) {
      if (actor.getY() > minY) {
         actor.translate(0, -10);
         return true;
      } else if (actor.getY() < minY) {
         actor.translate(0, minY - actor.getY());
         
      }
      return false;
   }

}
