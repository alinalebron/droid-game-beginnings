package atm.rocketguardian.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import atm.rocketguardian.RocketGuardian;
import atm.rocketguardian.helpers.AssetLoader;
import atm.rocketguardian.helpers.Constants;

public class MainMenuScreen extends BaseScreen {
	private Viewport viewport;
	private Stage stage;
	private Button playButton, creditsButton, howToPlayButton;

	public MainMenuScreen(final RocketGuardian game) {
		super(game);
		AssetLoader.menuMusic.play();
		viewport = new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);

		// Create buttons with their respective labels

		// Play button
		playButton = new Button(AssetLoader.skin);
		Label playLabel = new Label("PLAY", AssetLoader.skin);
		playLabel.setFontScale(0.5f);
		playButton.add(playLabel);
		playButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetLoader.clickSound.play();
				game.setScreen(new GameScreen(game));
				AssetLoader.menuMusic.stop();
				dispose();
			}
		});

		// Credits button
		creditsButton = new Button(AssetLoader.skin);
		Label creditsLabel = new Label("CREDITS", AssetLoader.skin);
		creditsLabel.setFontScale(0.5f);
		creditsButton.add(creditsLabel);
		creditsButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetLoader.clickSound.play();
				game.setScreen(new CreditsScreen(game));
				dispose();
			}
		});

		// How to play button
		howToPlayButton = new Button(AssetLoader.skin);
		Label howToPlayLabel = new Label("HOW TO PLAY", AssetLoader.skin);
		howToPlayLabel.setFontScale(0.5f);
		howToPlayButton.add(howToPlayLabel);
		howToPlayButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetLoader.clickSound.play();
				game.setScreen(new HowToPlayScreen(game));
				dispose();
			}
		});

		// Logo image
		Image gameLogo = new Image(AssetLoader.logo);
		
		//High score Label
		Label highScoreLabel = new Label("HIGH SCORE: " + RocketGuardian.getHighScore(), AssetLoader.skin);

		// This table contains the buttons
		Table buttonsTable = new Table();
		buttonsTable.center();

		buttonsTable.add(playButton).expandX().size(163, 60);
		buttonsTable.add(howToPlayButton).expandX().size(163, 60).padLeft(20).padRight(20);
		buttonsTable.add(creditsButton).expandX().size(163, 60);

		// This is the main table
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		table.add(gameLogo).size(195 * 2, 80 * 2).expandX().padTop(40);
		table.row();
		table.add(buttonsTable).padTop(20);
		table.row();
		table.add(highScoreLabel).padTop(15);

//		table.debug();
//		buttonsTable.debug();
		stage.addActor(RocketGuardian.menuBackground);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(3 / 255f, 6 / 255f, 43 / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
