package org.doublejava.transference.actors;

import org.doublejava.transference.stages.Level1;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {
   public Obstacles getObstacles() {
      return ((Level1) getStage()).obstacles;
   }
}
