package org.doublejava.transference.actions;

import org.doublejava.transference.actors.Obstacles;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GravityAction extends Action {
   Actor actor;
   Obstacles obstacles;
   int minY;
   
   public GravityAction(Actor actor, Obstacles obstacles) {
      this.actor = actor;
      this.obstacles = obstacles;
   }
   
   @Override
   public boolean act(float deltaTime) {
      minY = (int) obstacles.getMinY(actor.getX() + (actor.getWidth()/2));
      if (actor.getY() > minY) {
         actor.translate(0, -10);
         return true;
      } else if (actor.getY() < minY) {
         actor.translate(0, minY - actor.getY());
      }
      return false;
   }

}
