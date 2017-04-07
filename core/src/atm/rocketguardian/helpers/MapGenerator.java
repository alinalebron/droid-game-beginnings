package atm.rocketguardian.helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

import atm.rocketguardian.entities.Adult;
import atm.rocketguardian.entities.Baby;
import atm.rocketguardian.entities.Backgrounds;
import atm.rocketguardian.entities.FloatingPlatform;
import atm.rocketguardian.entities.Floor;
import atm.rocketguardian.entities.Human;
import atm.rocketguardian.entities.MiniZombie;
import atm.rocketguardian.entities.RocketGuard;
import atm.rocketguardian.entities.Zombie;
import atm.rocketguardian.entities.ZombieGuy;

// The purpose of this class is to generate world objects, including enemies and platforms. It will handle their positions and their spawning. 

public class MapGenerator extends Actor {
	// Some of these objects are public static to use them in
	// GameContactListener class.
	public static MiniZombie mini1, mini2;
	public static ZombieGuy guy1, guy2;
	public static RocketGuard rocket;
	public static Adult adult;
	public static Baby baby;
	private static Floor floor;
	private static FloatingPlatform platform1, platform2;
	private static Backgrounds background;
	private Vector2 resetPos, velocity, forceToApplyOnHuman;
	private float[] platformY = { 2.7f, 5.5f };
	private float[] zombieY = { 10f, 11f, 12f };
	private long elapsedSecondsSinceStart, startTime;
	private Boolean readyToSpawn;
	private float velocityValue;

	public MapGenerator(World world, Stage stage) {
		guy1 = new ZombieGuy(world, new Vector2(35f, 12f));
		mini1 = new MiniZombie(world, new Vector2(42f, 11f));
		adult = new Adult(world, new Vector2(30f, 10f));
		baby = new Baby(world, new Vector2(50f, 10f));

		// Setting individual userData will help to detect collisions properly.
		guy1.setUserData("guy1");
		mini1.setUserData("mini1");
		baby.setUserData("baby");

		floor = new Floor(world);
		background = new Backgrounds(32);
		rocket = new RocketGuard(world, new Vector2(1.5f, 6f));
		platform1 = new FloatingPlatform(world, 28f, platformY[0]);
		platform2 = new FloatingPlatform(world, 35f, platformY[1]);
		
		// These objects will be used to update bodies position in order to
		// reuse them.
		resetPos = new Vector2();
		velocity = new Vector2();
		forceToApplyOnHuman = new Vector2();
		startTime = TimeUtils.millis();
		elapsedSecondsSinceStart = 0;
		// This flag will help to spawn enemies only when wanted (after x
		// seconds since start).
		readyToSpawn = false;

		// Velocity of the moving objects
		velocityValue = 6 * 1.15f;
	}

	@Override
	public void act(float delta) {
		// Calculate elapsed seconds since start
		elapsedSecondsSinceStart = (TimeUtils.timeSinceMillis(startTime) / 1000);

		if (elapsedSecondsSinceStart > 2) {
			readyToSpawn = true;
		}
		
		// Call each Actor act method
		for (Actor actor : getActors()) {
			if (actor instanceof FloatingPlatform) {
				updatePlatformPosition((FloatingPlatform) actor);
			} else if (actor instanceof Zombie) {
				updateZombiePosition((Zombie) actor);
			} else if (actor instanceof Human) {
				updateHumanPosition((Human) actor);
			}
			actor.act(delta);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// Call each Actor draw method
		for (Actor actor : getActors()) {
			actor.draw(batch, parentAlpha);
		}
	}

	public void hide() {
		for (Actor actor : getActors()) {
			actor.remove();
		}
		// detach objects with Box2D body
		mini1.detach();
		guy1.detach();
		rocket.detach();
		platform1.detach();
		platform2.detach();
		baby.detach();
	}

	private void updatePlatformPosition(FloatingPlatform platform) {
		velocity.set(-velocityValue, 0);
		if (readyToSpawn) {
			platform.getBody().setLinearVelocity(velocity);
		}
		// 2f is body's half-width
		if (platform.getBody().getPosition().x < -2f) {
			// The y coordinate must always be 2.5f or 5f.
			// We add 2f to x coordinate because that is body width.
			float offsetX = MathUtils.random(3);
			resetPos.set((Constants.GAME_WIDTH / Constants.PPM) + 2f + offsetX, platformY[MathUtils.random(1)]);
			platform.getBody().setTransform(resetPos, platform.getBody().getAngle());
		}
	}

	private void updateZombiePosition(Zombie zombie) {
		velocity.set(-velocityValue, 0);
		if (readyToSpawn) {
			zombie.getBody().setLinearVelocity(velocity);
		}
		zombie.getBody().applyForceToCenter(0, -2000, true);
		// 0.5f is Zombie half-width.
		if (zombie.getBody().getPosition().x < -0.5f || !zombie.isAlive() || zombie.getBody().getPosition().y < 0) {
			float offsetX = MathUtils.random(20);
			resetPos.set((Constants.GAME_WIDTH / Constants.PPM) + 0.5f + offsetX, zombieY[MathUtils.random(2)]);
			zombie.getBody().setTransform(resetPos, 0);
			zombie.setAlive(true);
		}
	}

	private void updateHumanPosition(Human human) {
		if (!human.isOnFloor()) {
			velocity.set(0, -velocityValue * 3);
		} else if (readyToSpawn) {
			velocity.set(-velocityValue, 0);
		}
		forceToApplyOnHuman.set(0, -2000);
		human.getBody().setLinearVelocity(velocity);
		human.getBody().applyForceToCenter(forceToApplyOnHuman, true);

		// 0.4f is adult's half-width. We add 1f to assure the body isn't
		// falling when it appears on screen.
		if (human.getBody().getPosition().x < -0.4f || !human.isAlive() || human.getBody().getPosition().y < 0) {
			float offsetX = MathUtils.random(2);
			if (human instanceof Baby) {
				offsetX = +10;
			}
			resetPos.set((Constants.GAME_WIDTH / Constants.PPM) + 1f + offsetX, 9);
			human.getBody().setTransform(resetPos, 0);
			human.setAlive(true);
			human.setIsOnFloor(false);
			human.randomizeAnimation();
		}
	}

	public static List<Actor> getActors() {
		List<Actor> res = new ArrayList<Actor>();
		res.add(background);
		res.add(floor);
		res.add(platform1);
		res.add(platform2);
		res.add(mini1);
		res.add(guy1);
		res.add(rocket);
		res.add(adult);
		res.add(baby);
		return res;
	}

	public static List<Zombie> getZombies() {
		List<Zombie> res = new ArrayList<Zombie>();
		for (Actor actor : getActors()) {
			if (actor instanceof Zombie) {
				res.add((Zombie) actor);
			}
		}
		return res;
	}

	public static List<Human> getHumans() {
		List<Human> res = new ArrayList<Human>();
		for (Actor actor : getActors()) {
			if (actor instanceof Human) {
				res.add((Human) actor);
			}
		}
		return res;
	}
	public void stopVelocity() {
		velocityValue = 0;
		background.setCityVelocity(0);
		background.setLightsVelocity(0);
		floor.setVelocity(0);
	}
}
