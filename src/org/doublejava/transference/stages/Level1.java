package org.doublejava.transference.stages;

import org.doublejava.transference.actors.Background;
import org.doublejava.transference.actors.Bob;
import org.doublejava.transference.actors.Lemming;
import org.doublejava.transference.actors.ObstaclesLevel1;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level1 extends Stage {
   public ObstaclesLevel1 obstacles;
   
   public Level1() {
      obstacles = new ObstaclesLevel1();
      addActor(new Background("data/level1.png"));
      Bob bob = new Bob();
      bob.translate(0, 300);
      addActor(bob);
      Lemming lem = new Lemming();
      lem.translate(250,162);
      addActor(lem);
   }
   
}
