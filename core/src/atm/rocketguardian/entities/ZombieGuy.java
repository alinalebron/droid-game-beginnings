package atm.rocketguardian.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import atm.rocketguardian.helpers.AssetLoader;
import atm.rocketguardian.helpers.Constants;

public class ZombieGuy extends Actor implements Zombie {
	private World world;
	private Body body;
	private BodyDef bodyDef;
	private Fixture fixture;
	private Sprite sprite;
	private float stateTime;
	private Boolean isAlive;

	public ZombieGuy(World world, Vector2 position) {

		// Assign world and other properties
		this.world = world;
		this.sprite = new Sprite(AssetLoader.zombieGuyAnimation.getKeyFrame(0));
		this.isAlive = true;

		// Initialize Box2D objects
		bodyDef = new BodyDef();

		// set body properties
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);

		// ZombieGuy sprite dimensions are 0.86x1.75 meters. Box2D requires
		// half-width
		// and half-height to set box dimensions, so we divide it by 2.
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.43f, 0.875f);

		// Create the actual body.
		body = world.createBody(bodyDef);
		fixture = body.createFixture(shape, 10);
		fixture.setUserData("zombieguy");

		// Dispose the PolygonShape since it isn't needed anymore.
		shape.dispose();

		// Scene2D Actor settings
		// We set the size of the actual actor. It must be the same as the body
		// box, but in pixels.
		setSize(0.86f * Constants.PPM, 1.75f * Constants.PPM);
		// The actor position will be set in the draw() method, since it needs
		// to be updated constantly.

	}

	public void act(float delta) {
		stateTime += delta;

		// We start by setting the actor position, that will be the same as the
		// Box2D body minus half the width / height (in pixels).
		// This is because the setPosition() method sets its arguments as bottom
		// left corner coordinates of the actor. The Box2D
		// getPosition() method returns the position in meters, so we multiply
		// it by PPM to convert it in pixels.
		setPosition((body.getPosition().x - 0.43f) * Constants.PPM, (body.getPosition().y - 0.875f) * Constants.PPM);

		// Set the sprite coordinates and dimensions to match its Box2D body.
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setRegion(AssetLoader.zombieGuyAnimation.getKeyFrame(stateTime));
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * (360 / (2 * MathUtils.PI)));
	}

	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
	}

	public void detach() {
		// We detach the body to save resources when the game isn't running.
		body.destroyFixture(fixture);
		world.destroyBody(body);

	}

	public Body getBody() {
		return body;
	}

	public Boolean isAlive() {
		return this.isAlive;
	}

	public void setAlive(Boolean b) {
		this.isAlive = b;
	}

	public void setUserData(Object s) {
		fixture.setUserData(s);
	}
	
	public Object getUserData() {
		return fixture.getUserData();
	}
}
