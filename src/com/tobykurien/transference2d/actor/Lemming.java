package com.tobykurien.transference2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Lemming extends Actor {
	Texture texture;
	Animation walk;
	Animation walkBack;
	float stateTime = 0;
	boolean forward = true;
	boolean isSelected = false;
   private Body body;

	public Lemming(Platform platform) {
		texture = new Texture(Gdx.files.internal("data/platform_walk.png"));
		
		// grab frames for walking forward and create Animation
		TextureRegion[][] regions = new TextureRegion(texture,
				texture.getWidth(), texture.getHeight()).split(16, 16);
		walk = new Animation(0.05f, regions[0]);
		
		// flip frames for walking backwards and create Animation
		TextureRegion[][] regions2 = new TextureRegion(texture,
				texture.getWidth(), texture.getHeight()).split(16, 16);
		for (TextureRegion tr : regions2[0])
			tr.flip(true, false);
		walkBack = new Animation(0.05f, regions2[0]);

		// set our bounds within the frame
		setWidth(12);
		setHeight(16);
		
		addToPlatform(platform);
	}

	/**
	 * Create a Box2d model and add to the Box2d world inside Platform object
	 * @param platform
	 */
   private void addToPlatform(Platform platform) {
      // 1. Create a BodyDef, as usual.
      BodyDef bd = new BodyDef();
      bd.type = BodyType.DynamicBody;

      // Create our body in the world using our body definition
      body = platform.addBody(bd);
      body.setFixedRotation(true);
      body.setUserData(this);
      
      // Create a circle shape and set its radius to 6
      PolygonShape shape = new PolygonShape();
      shape.set(new float[]{
         // bounding box around sprite
         4*Platform.WORLD_TO_BOX,0,
         4*Platform.WORLD_TO_BOX,16*Platform.WORLD_TO_BOX,
         12*Platform.WORLD_TO_BOX,16*Platform.WORLD_TO_BOX,
         12*Platform.WORLD_TO_BOX,0
      });

      // Create a fixture definition to apply our shape to
      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.shape = shape;
      fixtureDef.density = 1f;
      fixtureDef.friction = 0f;
      fixtureDef.restitution = 0f;

      // Create our fixture and attach it to the body
      body.createFixture(fixtureDef);

      // Remember to dispose of any shapes after you're done with them!
      // BodyDef and FixtureDef don't need disposing, but shapes do.
      shape.dispose();      
   }
   
   @Override
   public void setPosition(float x, float y) {
      //super.setPosition(x, y);
      body.setTransform(x * Platform.WORLD_TO_BOX, y * Platform.WORLD_TO_BOX, 0);
   }

   @Override
   public void translate(float x, float y) {
      //super.translate(x, y);
      Vector2 curVel = body.getLinearVelocity();
      body.setLinearVelocity(
               (x != 0 ? x  * Platform.WORLD_TO_BOX : curVel.x), 
               (y != 0 ? y * Platform.WORLD_TO_BOX : curVel.y));
   }
   
   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      TextureRegion frame = (forward ? walk.getKeyFrame(stateTime, true)
            : walkBack.getKeyFrame(stateTime, true));
      batch.draw(frame, 
         body.getPosition().x * Platform.BOX_TO_WORLD, 
         body.getPosition().y * Platform.BOX_TO_WORLD);
   }

   public void turn() {
      forward = !forward;
   }
   
	@Override
	public void act(float deltaTime) {
      Vector2 curVel = body.getLinearVelocity();

      if (curVel.x == 0) {
         // we've stopped moving, assume collision and turn around
         turn();
      }
      
      if (forward) {
         translate(Bob.WALK_SPEED, 0);
      } else {
         translate(-Bob.WALK_SPEED, 0);
      }
	}
}
