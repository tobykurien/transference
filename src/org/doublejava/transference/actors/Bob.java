package org.doublejava.transference.actors;

import org.doublejava.transference.actions.GravityAction;
import org.doublejava.transference.actions.JumpAction;

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

	Texture texture;
	Animation walk;
	Animation walkBack;
	float stateTime = 0;
	boolean forward = true;
	int state = STATE_STOPPED;
	JumpAction jumpAction;
	GravityAction gravityAction;
	Obstacles obstacles;

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
		this.obstacles = new Obstacles();
		gravityAction = new GravityAction(this, obstacles);

		setWidth(16);
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
				translate(1, 0);
			}
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			state = STATE_WALKING;
			forward = false;
			stateTime += deltaTime;
			if (getX() > 0) {
				translate(-1, 0);
			}
		} else if (Gdx.input.isKeyPressed(Keys.ALT_RIGHT)) {
			Gdx.app.debug("bob", "Position: " + getX() + "," + getY());
		} else {
			state = STATE_STOPPED;
			stateTime = 0;
		}

		if (!jumpAction.act(deltaTime)) {
			gravityAction.act(deltaTime);
		}

		if (Gdx.input.isKeyPressed(Keys.W) && !jumpAction.isJumping()) {
			stateTime = 0;
			jumpAction.jump(forward);
		}

		if (Gdx.input.isKeyPressed(Keys.ALT_RIGHT)) {
			Gdx.app.debug("bob", "Position: " + getX() + "," + getY() + ", " + getWidth() + "," + getHeight());
			Gdx.app.debug("bob_intersect", "Position: " + obstacles.getMinX(this) + "," + obstacles.getMaxX(this));
		}

		if (startX < getX()) {
		   // moving to the right
		   float maxX = obstacles.getMaxX(this);
			if (getX() > maxX) {
				setX(maxX);
			}
		} else {
		   // moving to the left
         float minX = obstacles.getMinX(this);
			if (getX() < minX) {
				setX(minX);
			}
		}
	}
}
