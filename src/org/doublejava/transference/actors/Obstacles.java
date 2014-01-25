package org.doublejava.transference.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstacles extends Actor {
   List<Rectangle> obstacles = new ArrayList<Rectangle>();
   
   public Obstacles() {
      obstacles.add(new Rectangle(0,0,90,162));
   }
   
   public float getMinY(float x) {
      for (Rectangle r : obstacles) {
         if (r.contains(x, 0)) {
            return r.height;
         }
      }
      
      return 72f;
   }
}
