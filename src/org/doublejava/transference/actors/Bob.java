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

public class Bob extends Actor {
	final int STATE_STOPPED = 0;
	final int STATE_WALKING = 1;
	
	final float WALK_SPEED = 2.5f;

	Texture texture;
	Animation walk;
	Animation walkBack;
	float stateTime = 0;
	boolean forward = true;
	int state = STATE_STOPPED;
	JumpAction jumpAction;
	GravityAction gravityAction;

	public Bob() {
		texture = new Texture(Gdx.files.internal("data/bob_walk.png"));
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
		if (Gdx.input.isKeyPressed(Keys.D)) {
			state = STATE_WALKING;
			forward = true;
			stateTime += deltaTime;
			if (getX() < (getStage().getWidth() - getWidth() / 2)) {
				translate(WALK_SPEED, 0);
			}
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			state = STATE_WALKING;
			forward = false;
			stateTime += deltaTime;
			if (getX() > 0) {
				translate(-WALK_SPEED, 0);
			}
		} else {
			state = STATE_STOPPED;
			stateTime = 0;
		}

	   jumpAction.act(deltaTime);
	   gravityAction.act(deltaTime);

		if (Gdx.input.isKeyPressed(Keys.W) && !jumpAction.isJumping()) {
			stateTime = 0;
			jumpAction.jump(forward);
		}

		if (Gdx.input.isKeyPressed(Keys.ALT_RIGHT)) {
			Gdx.app.debug("bob", "Position: " + getX() + "," + getY() + ", " + getWidth() + "," + getHeight());
		}

      Obstacles obstacles = ((Level1)getStage()).obstacles;
		if (startX < getX()) {
		   // moving to the right
		   float maxX = obstacles.getMaxX(this);
			if (getX() > maxX) {
				translate(maxX - getX(), 0);
			}
		} else {
		   // moving to the left
         float minX = obstacles.getMinX(this);
			if (getX() < minX) {
            translate(minX - getX(), 0);
			}
		}
	}
}
