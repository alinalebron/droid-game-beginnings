package atm.rocketguardian.helpers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import atm.rocketguardian.entities.Human;
import atm.rocketguardian.entities.Zombie;
import atm.rocketguardian.screens.GameScreen;

public class GameContactListener implements ContactListener {

	private boolean haveCollided(Contact contact, Object userA, Object userB) {
		Object userDataA = contact.getFixtureA().getUserData();
		Object userDataB = contact.getFixtureB().getUserData();
		Boolean res = null;
		if (userDataA == null || userDataB == null) {
			res = false;
		}
		res = (userDataA.equals(userA) && userDataB.equals(userB))
				|| (userDataA.equals(userB) && userDataB.equals(userA));
		return res;
	}

	@Override
	public void beginContact(Contact contact) {
		if (haveCollided(contact, "rocketguard", "floor")
				|| haveCollided(contact, "rocketguard", "floatingplatform")) {
			// We get the player object and handle its jumping flags.
			MapGenerator.rocket.setJumping(false);
		}
		for (Zombie zombie : MapGenerator.getZombies()) {
			if (haveCollided(contact, "rocketguard", zombie.getUserData()))	{
				AssetLoader.hitSound.play();
				zombie.setAlive(false);
				GameScreen.shaker.shake(0.25f);
				GameScreen.hud.addScore();
			}
		}
		for (Human human : MapGenerator.getHumans()) {
			if (haveCollided(contact, "floor", human.getUserData()) || haveCollided(contact, "floatingplatform", human.getUserData())) {
				human.setIsOnFloor(true);
			}
			if (haveCollided(contact, "rocketguard", human.getUserData())) {
				AssetLoader.hitSound.play();
				human.setAlive(false);
				GameScreen.shaker.shake(0.25f);
				MapGenerator.rocket.removeLife();
			}
		}	
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {


	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
