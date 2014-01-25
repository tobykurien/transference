package org.doublejava.transference.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstacles extends Actor {
   List<Rectangle> obstacles = new ArrayList<Rectangle>();
   
   public Obstacles() {
      // Level 1 fixed obstacles
      obstacles.add(new Rectangle(0,0,90,162));
      obstacles.add(new Rectangle(264,0,18,90));
      obstacles.add(new Rectangle(354,0,18,162));
      obstacles.add(new Rectangle(372,0,18*4,18*8));
      obstacles.add(new Rectangle(444,0,18,18*9));
      obstacles.add(new Rectangle(462,0,18,18*8));
   }
   
   public float getMinY(Actor actor) {
      Rectangle actorBounds = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
      float x = actor.getX() - (actor.getWidth()/2f);
      for (Rectangle r : obstacles) {
         if (r.contains(x, 0)) {
            return r.height;
         }
      }
      
      return 72f;
   }
   
   public float getMaxX(Actor actor) {
      Rectangle actorBounds = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
      for (Rectangle r : obstacles) {
         if (r.overlaps(actorBounds)) {
            return r.getX();
         }
      }
      
      return actor.getX();
   }

   public float getMinX(Actor actor) {
      Rectangle actorBounds = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
      for (Rectangle r : obstacles) {
         if (r.overlaps(actorBounds)) {
            return r.getX() + r.getWidth();
         }
      }
      
      return actor.getX();
   }

   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      ShapeRenderer renderer = new ShapeRenderer();
      renderer.setColor(Color.BLUE);
      renderer.begin(ShapeType.Line);
      for (Rectangle r : obstacles) {
         renderer.rect(r.getX()*2, r.getY()*2, r.getWidth()*2, r.getHeight()*2);
      }
      renderer.end();
   }
}
