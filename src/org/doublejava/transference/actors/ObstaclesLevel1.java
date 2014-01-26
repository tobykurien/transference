package org.doublejava.transference.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ObstaclesLevel1 extends Actor {
   List<Rectangle> obstacles = new ArrayList<Rectangle>();
   List<Rectangle> actorRects = new ArrayList<Rectangle>();
   private ShapeRenderer renderer = new ShapeRenderer();
   
   public ObstaclesLevel1() {
      // Level 1 fixed obstacles
      obstacles.add(new Rectangle(0,0,94,162));
      obstacles.add(new Rectangle(274,0,18,90));
      obstacles.add(new Rectangle(364,0,18,162));
      obstacles.add(new Rectangle(382,0,18*4,18*8));
      obstacles.add(new Rectangle(454,0,18,18*9));
      obstacles.add(new Rectangle(472,0,18,18*8));
   }
   
   public void updateActors(Array<Actor> actors) {
      actorRects.clear();
      for (Actor a : actors) {
         if (a != this)
            actorRects.add(new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight()));
      }
   }

   public List<Rectangle> getRects() {
      List<Rectangle> rects = new ArrayList<Rectangle>();
      rects.addAll(obstacles);
      rects.addAll(actorRects);
      return rects;
   }
   
   public float getMinY(Actor actor) {
      float x = actor.getX();
      for (Rectangle r : getRects()) {
         if (r.contains(x, 0)) {
            return r.height;
         }
      }
      
      return 72f;
   }
   
   public float getMaxX(Actor actor) {
      Rectangle actorBounds = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
      for (Rectangle r : getRects()) {
         if (r.getX() + r.getWidth() > actor.getX() && r.overlaps(actorBounds)) {
//         	Gdx.app.debug("actorbounds", "GETMAXX  Position: " + actorBounds.getX() + "," + actorBounds.getY());
//         	Gdx.app.debug("r.overlaps bounds", "GETMAXX  Position: " + r.getX() + "," + r.getY());
            return r.getX() - actor.getWidth();
         }
      }
      
      return actor.getX();
   }

   public float getMinX(Actor actor) {
      Rectangle actorBounds = new Rectangle(
               actor.getX() - actor.getWidth(), 
               actor.getY(), 
               actor.getWidth(), 
               actor.getHeight());
      for (Rectangle r : getRects()) {
         if (r.overlaps(actorBounds)) {
//         	Gdx.app.debug("actorbounds", "GETMINX  Position: " + actorBounds.getX() + "," + actorBounds.getY());
//         	Gdx.app.debug("r.overlaps bounds", "GETMINX  Position: " + r.getX() + "," + r.getY());
            return r.getX() + r.getWidth() + 2f;
         }
      }
      
      return actor.getX();
   }

   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      renderer.setProjectionMatrix(batch.getProjectionMatrix());
      renderer.setColor(Color.BLUE);
      renderer.begin(ShapeType.Line);
      for (Rectangle r : getRects()) {
         renderer.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
      }
      renderer.end();
   }
}
