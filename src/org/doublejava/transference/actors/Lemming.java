package org.doublejava.transference.actors;

import org.doublejava.transference.actions.GravityAction;
import org.doublejava.transference.actions.JumpAction;
import org.doublejava.transference.stages.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Lemming extends Actor {
	Texture texture;
	Animation walk;
	Animation walkBack;
	float stateTime = 0;
	boolean forward = true;
	boolean isSelected = false;
	JumpAction jumpAction;
	GravityAction gravityAction;

	public Lemming() {
		texture = new Texture(Gdx.files.internal("data/platform_walk.png"));
		TextureRegion[][] regions = new TextureRegion(texture,
				texture.getWidth(), texture.getHeight()).split(16, 16);
		walk = new Animation(0.05f, regions[0]);
		TextureRegion[][] regions2 = new TextureRegion(texture,
				texture.getWidth(), texture.getHeight()).split(16, 16);
		for (TextureRegion tr : regions2[0])
			tr.flip(true, false);
		walkBack = new Animation(0.05f, regions2[0]);

		jumpAction = new JumpAction(this);
		gravityAction = new GravityAction(this);

		setWidth(12);
		setHeight(16);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		TextureRegion frame = (forward ? walk.getKeyFrame(stateTime, true)
				: walkBack.getKeyFrame(stateTime, true));
		batch.draw(frame, getX(), getY());
	}

	@Override
	public void act(float deltaTime) {
		float startX = getX();

      if (forward) {
         stateTime += deltaTime;
         if (getX() < (getStage().getWidth() - getWidth() / 2)) {
            translate(1, 0);
         }
      } else {
         forward = false;
         stateTime += deltaTime;
         if (getX() > 0) {
            translate(-1, 0);
         }
      }		
		
	   jumpAction.act(deltaTime);
		gravityAction.act(deltaTime);
		   
		if (isSelected && Gdx.input.isKeyPressed(Keys.W) && !jumpAction.isJumping()) {
			stateTime = 0;
			jumpAction.jump(forward);
		}

      Obstacles obstacles = ((Level1)getStage()).obstacles;
      if (startX < getX()) {
         // moving to the right
         float maxX = obstacles.getMaxX(this);
         if (getX() > maxX) {
            translate(maxX - getX(), 0);
            forward = false;
         }
      } else {
         // moving to the left
         float minX = obstacles.getMinX(this);
         if (getX() < minX) {
            translate(minX - getX(), 0);
            forward = true;
         }
      }
	}
}
