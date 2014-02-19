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

public class Bob extends Actor {
   final int STATE_STOPPED = 0;
   final int STATE_WALKING = 1;
   
   public static final float WALK_SPEED = 100f;

   Texture texture;
   Animation walk;
   Animation walkBack;
   float stateTime = 0;
   boolean forward = true;
   int state = STATE_STOPPED;
   Body body;

   public Bob(Platform platform) {
      texture = new Texture(Gdx.files.internal("data/bob_walk.png"));

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
      body.setUserData(this);

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

   @Override
   public void act(float deltaTime) {
      Vector2 curVel = body.getLinearVelocity();
      float startX = body.getPosition().x;
      if (Gdx.input.isKeyPressed(Keys.D)) {
         state = STATE_WALKING;
         forward = true;
         stateTime += deltaTime;
         if (startX < (getStage().getWidth() - getWidth() / 2)) {
            translate(WALK_SPEED, 0);
         }
      } else if (Gdx.input.isKeyPressed(Keys.A)) {
         state = STATE_WALKING;
         forward = false;
         stateTime += deltaTime;
         if (startX > 0) {
            translate(-WALK_SPEED, 0);
         }
      } else {
         state = STATE_STOPPED;
         stateTime = 0;
         body.setLinearVelocity(0, curVel.y);
      }


      if (Gdx.input.isKeyPressed(Keys.W) && curVel.y < 0.1f && curVel.y >= 0) {
         // TODO: this can trigger at top of jump too
         stateTime = 0;
         // big upward push
         body.setLinearVelocity(0, 650*Platform.WORLD_TO_BOX);
      }

      if (Gdx.input.isKeyPressed(Keys.ALT_RIGHT)) {
         Gdx.app.debug("bob", "Position: " + getX() + "," + getY() + ", " + getWidth() + "," + getHeight());
      }
   }
}
