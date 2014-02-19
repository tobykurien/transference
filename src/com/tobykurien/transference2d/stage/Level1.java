package com.tobykurien.transference2d.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tobykurien.transference2d.actor.Background;
import com.tobykurien.transference2d.actor.Bob;
import com.tobykurien.transference2d.actor.Lemming;
import com.tobykurien.transference2d.actor.Platform;

public class Level1 extends Stage {
   Platform platform;
   
   public Level1() {
      platform = new Platform();
      addActor(platform);
      addActor(new Background(platform, "data/level1.png"));
      
      Bob bob = new Bob(platform);
      bob.setPosition(0, 300);
      addActor(bob);

      Lemming lem = new Lemming(platform);
      lem.setPosition(200, 300);
      addActor(lem);
   }
   
   @Override
   public void draw() {
      super.draw();
      platform.render(getSpriteBatch());
   }
}
