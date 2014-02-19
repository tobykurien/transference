package com.tobykurien.transference2d.actor;

import java.lang.ref.WeakReference;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tobykurien.transference2d.BodyEditorLoader;

/**
 * Actual Level platform image with Box2d collision model (fixture)
 * @author toby
 */
public class Background extends Actor {
   Body body;
   
   public Background(Platform platform, String path) {
      float WIDTH = 480 * Platform.WORLD_TO_BOX;
      // Load the sprite
      Texture texture = new Texture(Gdx.files.internal(path));
      texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
      TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
      Sprite sprite = new Sprite(region);

      // 0. Create a loader for the file saved from the editor.
      BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/level1.json"));

      // 1. Create a BodyDef, as usual.
      BodyDef bd = new BodyDef();
      bd.position.set(0, 0);
      bd.type = BodyType.StaticBody; // DynamicBody;

      // 2. Create a FixtureDef, as usual.
      FixtureDef fd = new FixtureDef();
      fd.density = 1;
      fd.friction = 0f;
      fd.restitution = 0f;

      // 3. Create a Body, as usual.
      body = platform.addBody(bd);
      body.setUserData(sprite);

      // 4. Create the body fixture automatically by using the loader.
      loader.attachFixture(body, "Level1", fd, WIDTH);

      // Reference the origin of the model
      sprite.setSize(WIDTH * Platform.BOX_TO_WORLD, WIDTH * Platform.BOX_TO_WORLD
               * sprite.getHeight() / sprite.getWidth());
      sprite.setOrigin(0, 0);
      sprite.setPosition(0, 0);
   }   
   
   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      Sprite s = (Sprite) body.getUserData();
      s.draw(batch);
   }
}
