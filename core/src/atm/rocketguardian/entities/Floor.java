package atm.rocketguardian.entities;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import atm.rocketguardian.helpers.AssetLoader;
import atm.rocketguardian.helpers.Constants;

public class Floor extends Actor {
	private World world;
	private Body body;
	private Fixture fixture;
	private BodyDef def;
	private AtlasRegion floor;
	private float width;
	private Array<Sprite> floorArray;
	private Iterator<Sprite> floorIterator;
	private float velocity;

	public Floor(World world) {

		// Assign world and other properties
		this.world = world;
		floor = AssetLoader.floor;
		width = 50;
		velocity = 5;

		// Initialize and define Box2D objects
		def = new BodyDef();

		// Body properties
		def.position.set(width / 2, 0);
		def.type = BodyType.StaticBody;
		body = world.createBody(def);

		// Body shape. We want our floor to cover the region it is given.
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, 0.70f);
		fixture = body.createFixture(shape, 0);
		fixture.setUserData("floor");
		shape.dispose();

		// Now we set the Scene2D Actor properties. It needs the dimensions in
		// pixels.
		setSize(Constants.GAME_WIDTH, 32);
		setPosition(0, 0);

		// Create the sprite array
		floorArray = new Array<Sprite>();
		Sprite sprite = new Sprite(floor);
		sprite.setSize(getWidth(), getHeight());
		sprite.setX(0);
		floorArray.add(sprite);
		sprite = new Sprite(floor);
		sprite.setSize(getWidth(), getHeight());
		sprite.setX(Constants.GAME_WIDTH);
		floorArray.add(sprite);

	}

	public void act(float delta) {
		// Iteration of the floor tiles
		floorIterator = floorArray.iterator();
		while (floorIterator.hasNext()) {
			Sprite sprite = floorIterator.next();
			sprite.setX(sprite.getX() - velocity);
			if (sprite.getX() < -sprite.getWidth()) {
				floorIterator.remove();
				generateNewSprite();
			}
		}
	}

	public void draw(Batch batch, float parentAlpha) {
		for (Sprite sprite : floorArray) {
			sprite.draw(batch);
		}
	}

	public void detach() {
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

	private void generateNewSprite() {
		Sprite sprite = new Sprite(floor);
		sprite.setSize(getWidth(), getHeight());
		sprite.setX(sprite.getWidth());
		floorArray.add(sprite);
	}
	
	public void setVelocity(Integer vel) {
		velocity = vel;
	}
}
