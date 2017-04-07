package atm.rocketguardian.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

public class RocketGuard extends Actor {
	private World world;
	private Body body;
	private BodyDef bodyDef;
	private Fixture fixture;
	private Sprite sprite;
	private float stateTime;
	private boolean isJumping;
	private boolean isAlive;
	private Vector2 position;
	private Integer lives;
	private float explosionY, boomTimer;
	private Animation<TextureAtlas.AtlasRegion> boomAnim;

	public RocketGuard(World world, Vector2 position) {
		// Assign world and other properties
		this.world = world;
		this.sprite = new Sprite(AssetLoader.guardianAnimation.getKeyFrame(0));
		this.position = position;

		// Boom animation
		boomAnim = AssetLoader.iceExplosionAnimation;
		explosionY = 0;
		boomTimer = 0;

		isJumping = false;
		isAlive = true;

		// Number of hits with humans permitted
		lives = 1;

		// Initialize Box2D body
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);

		// Body size
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1.14f, 0.64f);

		// Create the actual body.
		body = world.createBody(bodyDef);
		fixture = body.createFixture(shape, 10);
		fixture.setUserData("rocketguard");

		// Dispose the PolygonShape since it isn't needed anymore.
		shape.dispose();

		// Scene2D Actor settings
		// We set the size of the actual actor. It must be the same as the body
		// box, but in pixels.
		setSize(2.28f * Constants.PPM, 1.28f * Constants.PPM);
		// The actor position will be set in the draw() method, since it needs
		// to be updated constantly.
	}

	@Override
	public void act(float delta) {
		stateTime += delta;
		if (!isAlive) {
			boomTimer += delta;
			// play explosion sound
			AssetLoader.explosionSound.play();
		}
		if (Gdx.input.justTouched()) {
			jump();
		}
		if (isJumping) {
			// This force makes the jumping feel more natural
			body.applyForceToCenter(0, -450 * 1.3f, true);
		}
		// Fix the guardian at an x coordinate
		if (body.getPosition().x != this.position.x) {
			body.setTransform(this.position.x, body.getPosition().y, 0);
		}
		// fix the angle
		if (body.getAngle() != 0) {
			body.setTransform(body.getPosition(), 0);
		}

		// We start by setting the actor position, that will be the same as the
		// Box2D body minus half the width / height (in pixels).
		// This is because the setPosition() method sets its arguments as bottom
		// left corner coordinates of the actor. The Box2D
		// getPosition() method returns the position in meters, so we multiply
		// it by PPM to convert it in pixels.
		setPosition((body.getPosition().x - 1.14f) * Constants.PPM, (body.getPosition().y - 0.64f) * Constants.PPM);

		// Set the sprite coordinates and dimensions to match its Box2D body.
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setRegion(AssetLoader.guardianAnimation.getKeyFrame(stateTime));
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * (360 / (2 * MathUtils.PI)));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
		if (!isAlive) {
			drawExplosion(batch);
			// move the guardian
			getBody().setTransform(this.position.x, this.position.y * 100, 0);
		}
	}

	public void jump() {
		if (!isJumping && isAlive) {
			isJumping = true;
			Vector2 pos = body.getPosition();
			body.applyLinearImpulse(0, 450, pos.x, pos.y, true);
		}
	}

	public void detach() {
		// We detach the body to save resources when the game isn't running.
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

	public void setJumping(boolean bool) {
		isJumping = bool;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public Body getBody() {
		return body;
	}

	public Integer getLives() {
		return lives;
	}

	public void removeLife() {
		lives--;
	}

	public boolean isAlive() {
		if (lives <= 0) {
			isAlive = false;
		}
		return isAlive;
	}

	public void isAlive(Boolean b) {
		isAlive = b;
	}

	public void storeExplosionPosition(float y) {
		explosionY = y;
	}

	private void drawExplosion(Batch batch) {
		if (!boomAnim.isAnimationFinished(boomTimer)) {
			batch.draw(boomAnim.getKeyFrame(boomTimer), getX() + (getWidth() / 2), explosionY + (getHeight() / 2), 50,
					50);
		} else {
			storeExplosionPosition(Constants.GAME_HEIGHT + 100);
			boomTimer = 0;
		}
	}
}
