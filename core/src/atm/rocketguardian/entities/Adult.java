package atm.rocketguardian.entities;

import java.util.ArrayList;
import java.util.List;

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

public class Adult extends Actor implements Human {
	private World world;
	private Body body;
	private BodyDef bodyDef;
	private Fixture fixture;
	private Sprite sprite;
	private float stateTime;
	private Boolean isAlive;
	private List<Animation<TextureAtlas.AtlasRegion>> animationsList;
	private int currentAnimation;
	private Boolean isOnFloor;


	public Adult(World world, Vector2 position) {

		// Create the list with the available animations
		animationsList = createAnimationsList();
		currentAnimation = MathUtils.random(animationsList.size() - 1);
		// Assign world and other properties
		this.world = world;
		// At first, the Adult is created with a random animation. It will be
		// reassigned every time the adult respawns.
		// this.sprite = new
		// Sprite(animationsList.get(MathUtils.random(animationsList.size() -
		// 1)).getKeyFrame(0));
		this.sprite = new Sprite(animationsList.get(currentAnimation).getKeyFrame(0));

		this.isOnFloor = false;
		this.isAlive = true;

		// Initialize Box2D objects
		bodyDef = new BodyDef();

		// set body properties
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);

		// Adult sprite dimensions are 0.72x1.80 meters. Box2D requires
		// half-width
		// and half-height to set box dimensions, so we divide it by 2.
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.31f, 0.85f);

		// Create the actual body.
		body = world.createBody(bodyDef);
		fixture = body.createFixture(shape, 10);
		fixture.setUserData("human");

		// Dispose the PolygonShape since it isn't needed anymore.
		shape.dispose();

		// Scene2D Actor settings
		// We set the size of the actual actor. It must be the same as the body
		// box, but in pixels.
		setSize(0.31f * 2 * Constants.PPM, 0.85f * 2 * Constants.PPM);
		// The actor position will be set in the draw() method, since it needs
		// to be updated constantly.
	}

	@Override
	public void act(float delta) {
		stateTime += delta;

		// We start by setting the actor position, that will be the same as the
		// Box2D body minus half the width / height (in pixels).
		// This is because the setPosition() method sets its arguments as bottom
		// left corner coordinates of the actor. The Box2D
		// getPosition() method returns the position in meters, so we multiply
		// it by PPM to convert it in pixels.
		setPosition((body.getPosition().x - 0.31f) * Constants.PPM, (body.getPosition().y - 0.85f) * Constants.PPM);

		// Set the sprite coordinates and dimensions to match its Box2D body.
		// Add 10 to make the sprite look nice while its hitbox is slightly less
		// wide.
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth() + 10, getHeight());
		sprite.setRegion(animationsList.get(currentAnimation).getKeyFrame(stateTime));
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setRotation(body.getAngle() * (360 / (2 * MathUtils.PI)));

		// fix the angle
		if (body.getAngle() != 0) {
			body.setTransform(body.getPosition(), 0);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
	}

	@Override
	public void detach() {
		// We detach the body to save resources when the game isn't running.
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

	private List<Animation<TextureAtlas.AtlasRegion>> createAnimationsList() {
		List<Animation<TextureAtlas.AtlasRegion>> res = new ArrayList<Animation<TextureAtlas.AtlasRegion>>();
		res.add(AssetLoader.girlAnimation);
		res.add(AssetLoader.whiteGuyAnimation);
		res.add(AssetLoader.whiteGuyGreenAnimation);
		res.add(AssetLoader.blackGuyAnimation);
		return res;
	}

	public void randomizeAnimation() {
		currentAnimation = MathUtils.random(animationsList.size() - 1);
	}

	@Override
	public void setUserData(Object s) {
		fixture.setUserData(s);
	}

	@Override
	public Object getUserData() {
		return fixture.getUserData();
	}

	@Override
	public Boolean isAlive() {
		return isAlive;
	}

	@Override
	public void setAlive(Boolean b) {
		this.isAlive = b;
	}

	public Boolean isOnFloor() {
		return isOnFloor;
	}

	public void setIsOnFloor(Boolean isOnFloor) {
		this.isOnFloor = isOnFloor;
	}


	@Override
	public Body getBody() {
		return body;
	}
}
