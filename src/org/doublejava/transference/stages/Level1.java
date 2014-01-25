package org.doublejava.transference.stages;

import org.doublejava.transference.actors.Background;
import org.doublejava.transference.actors.Bob;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level1 extends Stage {

   public Level1() {
      addActor(new Background("data/level1.png"));
      
      Bob bob = new Bob();
      bob.translate(50, 50);
      addActor(bob);
   }
   
}
