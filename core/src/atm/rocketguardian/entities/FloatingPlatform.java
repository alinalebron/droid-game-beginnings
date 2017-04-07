package atm.rocketguardian.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import atm.rocketguardian.helpers.AssetLoader;
import atm.rocketguardian.helpers.Constants;

public class FloatingPlatform extends Actor {
	private World world;
	private Body body;
	private Fixture fixture;
	private BodyDef def;
	private Sprite sprite;
	private float stateTime;

	public FloatingPlatform(World world, float x, float y) {
		// Assign world and other properties
		this.world = world;
		stateTime = 0;
		sprite = new Sprite(AssetLoader.platformAnimation.getKeyFrame(0));

		// Box2D objects
		def = new BodyDef();
		def.position.set(x, y);
		def.type = BodyType.KinematicBody;
		body = world.createBody(def);

		// Box2D body size and fixture creation
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(2f, 0.5f);

		fixture = body.createFixture(shape, 30);
		fixture.setUserData("floatingplatform");
		shape.dispose();

		// Scene2D actor properties
		setSize(4f * Constants.PPM, 1.5f * Constants.PPM);
	}

	@Override
	public void act(float delta) {
		stateTime += delta;
		
		setPosition((body.getPosition().x - 2f) * Constants.PPM, (body.getPosition().y - 1f) * Constants.PPM);
		// Set the sprite coordinates and dimensions to match its Box2D body.
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setRegion(AssetLoader.platformAnimation.getKeyFrame(stateTime));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
	}

	public Body getBody() {
		return body;
	}

	public void detach() {
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

}
