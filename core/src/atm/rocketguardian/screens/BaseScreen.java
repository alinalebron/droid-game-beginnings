package atm.rocketguardian.screens;

import com.badlogic.gdx.Screen;

import atm.rocketguardian.RocketGuardian;

public abstract class BaseScreen implements Screen {

	protected RocketGuardian game;

	public BaseScreen(final RocketGuardian game) {
		this.game = game;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
