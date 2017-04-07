package atm.rocketguardian.helpers;

import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class ScreenShaker {
	private float elapsed;
	private float duration;
	private float baseX;
	private float baseY;
	private Random random;
	
	public ScreenShaker(OrthographicCamera camera) {
		this.elapsed = 0;
		this.random = new Random();
		backupCameraPos(camera);
	}
	
	private void backupCameraPos(OrthographicCamera camera) {
		this.baseX = camera.position.x;
		this.baseY = camera.position.y;
	}
	
	public void shake(float duration) {
		this.elapsed = 0;
		this.duration = duration;
	}
	
	public void update(OrthographicCamera camera, float delta) {
		restoreCameraPos(camera);
		if (this.elapsed < this.duration) {
			float currentPower = camera.zoom * ((duration - this.elapsed) / this.duration);
			float x = (random.nextFloat() - 0.5f) * currentPower * 15;
			float y = (random.nextFloat() - 0.5f) * currentPower * 15;
			camera.translate(-x, -y);
			this.elapsed += delta;
		} 
	}
	
	private void restoreCameraPos(OrthographicCamera camera) {
		camera.position.x = this.baseX;
		camera.position.y = this.baseY;
	}
}
