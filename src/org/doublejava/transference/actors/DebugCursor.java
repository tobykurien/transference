package org.doublejava.transference.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DebugCursor extends Actor {
   
   public DebugCursor() {
   }
   
   @Override
   public void act(float deltaTime) {
      if (Gdx.input.isKeyPressed(Keys.ALT_RIGHT)) {
         Gdx.app.debug("cursor", "Position: " + Gdx.input.getX() + "," + Gdx.input.getY());
      }
   }
}
