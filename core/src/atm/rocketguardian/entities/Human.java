package atm.rocketguardian.entities;

import com.badlogic.gdx.physics.box2d.Body;

public interface Human {
	public void setUserData(Object s);
	public Object getUserData();
	public Boolean isAlive();
	public Boolean isOnFloor();
	public void setIsOnFloor(Boolean b);
	public void detach();
	public void setAlive(Boolean b);
	public Body getBody();
	public void randomizeAnimation();
	public	float getX();
	public float getY();
}
