package org.doublejava.transference.stages;

import org.doublejava.transference.actors.Background;
import org.doublejava.transference.actors.Bob;
import org.doublejava.transference.actors.DebugCursor;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level1 extends Stage {

   public Level1() {
      addActor(new Background("data/level1.png"));
      
      Bob bob = new Bob();
      bob.translate(0, 300);
      addActor(bob);
      addActor(new DebugCursor());
   }
   
}
