package org.doublejava.transference.actions;

import org.doublejava.transference.actors.ObstaclesLevel1;
import org.doublejava.transference.stages.Level1;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GravityAction extends Action {
   Actor actor;
   int minY;
   
   public GravityAction(Actor actor) {
      this.actor = actor;
   }
   
   @Override
   public boolean act(float deltaTime) {
      ObstaclesLevel1 obstacles = ((Level1)actor.getStage()).obstacles;
      minY = (int) obstacles.getMinY(actor);
      if (actor.getY() > minY) {
         actor.translate(0, -10);
         return true;
      } else if (actor.getY() < minY) {
         actor.translate(0, minY - actor.getY());
      }
      return false;
   }

}
