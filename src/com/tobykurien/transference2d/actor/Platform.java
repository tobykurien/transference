package com.tobykurien.transference2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Platform extends Actor {
   public static float WORLD_TO_BOX = 0.01f;
   public static float BOX_TO_WORLD = 100f;   
   
   private Box2DDebugRenderer debugRenderer;
   World world;

   public Platform() {
      world = new World(new Vector2(0, -0.25f*BOX_TO_WORLD), true);
      debugRenderer = new Box2DDebugRenderer();     
      world.setContactListener(new ContactListener() {
         @Override
         public void preSolve(Contact arg0, Manifold arg1) {
         }
         
         @Override
         public void postSolve(Contact arg0, ContactImpulse arg1) {
         }
         
         @Override
         public void endContact(Contact arg0) {
         }
         
         @Override
         public void beginContact(Contact contact) {
            if (collisionBetween(contact, Lemming.class, Bob.class)) {
               // Bob and Lemming collided either horizontally or vertically
               // if Bob is standing on Lemming, Bob needs to follow Lemming
            }
         }
      });
   }
   
   /**
    * Returns true if the collision was between instances of the specified classes
    * @param contact
    * @param class1
    * @param class2
    * @return
    */
   protected boolean collisionBetween(Contact contact, Class class1, Class class2) {
      if (class1.isInstance(contact.getFixtureA().getBody().getUserData()) &&
               class2.isInstance(contact.getFixtureB().getBody().getUserData()) ) {
         return true;
      }

      if (class2.isInstance(contact.getFixtureA().getBody().getUserData()) &&
               class1.isInstance(contact.getFixtureB().getBody().getUserData()) ) {
         return true;
      }

      return false;
   }

   @Override
   public void act(float delta) {
      world.step(delta, 6, 2);
   }
   
   public Body addBody(BodyDef bodydef) {
      return world.createBody(bodydef);
   }
   
   public void render(SpriteBatch batch) {
      debugRenderer.render(world, batch.getProjectionMatrix().scl(BOX_TO_WORLD));
   }
}
