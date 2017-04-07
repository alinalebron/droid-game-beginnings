package atm.rocketguardian.entities;

import com.badlogic.gdx.physics.box2d.Body;

public interface Zombie {
	public void setUserData(Object s);
	public Object getUserData();
	public Boolean isAlive();
	public void detach();
	public void setAlive(Boolean b);
	public Body getBody();
}
