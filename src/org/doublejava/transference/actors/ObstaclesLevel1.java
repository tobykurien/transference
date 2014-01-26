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

public class ObstaclesLevel1 extends Obstacles {
   
   public ObstaclesLevel1() {
      // Level 1 fixed obstacles
      obstacles.add(new Rectangle(0,0,94,162));
      obstacles.add(new Rectangle(274,0,18,90));
      obstacles.add(new Rectangle(364,0,18,162));
      obstacles.add(new Rectangle(382,0,18*4,18*8));
      obstacles.add(new Rectangle(454,0,18,18*9));
      obstacles.add(new Rectangle(472,0,18,18*8));
   }

}
