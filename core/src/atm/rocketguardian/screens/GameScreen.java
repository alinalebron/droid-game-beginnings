package atm.rocketguardian.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import atm.rocketguardian.RocketGuardian;
import atm.rocketguardian.helpers.Constants;
import atm.rocketguardian.helpers.GameContactListener;
import atm.rocketguardian.helpers.MapGenerator;
import atm.rocketguardian.helpers.ScreenShaker;

public class GameScreen extends BaseScreen {

	public Stage stage;
	private World world;
	private OrthographicCamera camera;
	private Viewport gameport;
	private MapGenerator map;
	// Objects for testing purposes
	// private OrthographicCamera debugcam;
	// private Box2DDebugRenderer debug;
	public static ScreenShaker shaker;
	public static Hud hud;

	public GameScreen(final RocketGuardian game) {
		super(game);
		//AssetLoader.gameMusic.play();
		camera = new OrthographicCamera();
		gameport = new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, camera);
		stage = new Stage(gameport);
		world = new World(new Vector2(0, -9.81f), true);

		// Objects for testing purposes
		// debug = new Box2DDebugRenderer();
		// debugcam = new OrthographicCamera(63, 30);

		world.setContactListener(new GameContactListener());

		// This object will handle screen shaking
		shaker = new ScreenShaker(camera);

		// HUD
		hud = new Hud(game.batch);
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);
	}

	@Override
	public void show() {
		map = new MapGenerator(world, stage);
		stage.addActor(map);
	}

	@Override
	public void hide() {
		map.hide();
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(3 / 255f, 6 / 255f, 43 / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shaker.update(camera, delta);
		game.batch.setProjectionMatrix(camera.combined);
		stage.act();
		world.step(delta, 6, 2);
		stage.draw();

		// debug.render(world, debugcam.combined);

		camera.update();
		hud.update(delta);
		checkGameOver();

	}

	private void checkGameOver() {
		if (!MapGenerator.rocket.isAlive()) {
			if (hud.getScore() > RocketGuardian.previousHighScore) {
				RocketGuardian.setHighScore(hud.getScore());
			}
			endgameDelay();
		}
	}

	@Override
	public void dispose() {
		// stage.dispose();
		world.dispose();
		hud.dispose();
	}

	private void endgameDelay() {
		Runnable setGameOverScreen = new Runnable() {
			@Override
			public void run() {
				//AssetLoader.gameMusic.stop();
				game.setScreen(new GameOverScreen(game));
			}
		};

		Runnable stopObjects = new Runnable() {
			@Override
			public void run() {
				map.stopVelocity();
			}
		};

		// Play the actions
		stage.addAction(
				Actions.sequence(Actions.run(stopObjects), Actions.delay(1.2f), Actions.run(setGameOverScreen)));

	}
}
