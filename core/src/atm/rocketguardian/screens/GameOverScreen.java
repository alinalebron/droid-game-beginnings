package atm.rocketguardian.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import atm.rocketguardian.RocketGuardian;
import atm.rocketguardian.helpers.AssetLoader;
import atm.rocketguardian.helpers.Constants;

public class GameOverScreen extends BaseScreen {
	private Viewport viewport;
	private Stage stage;
	private Button shareButton, playAgainButton, mainMenuButton;

	public GameOverScreen(final RocketGuardian game) {
		super(game);
		AssetLoader.menuMusic.play();
		viewport = new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);
		
		// Buttons
		// Play again button
		playAgainButton = new Button(AssetLoader.skin);
		Label playAgainLabel = new Label("PLAY AGAIN", AssetLoader.skin);
		playAgainLabel.setFontScale(0.5f);
		playAgainButton.add(playAgainLabel);
		playAgainButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetLoader.clickSound.play();
				AssetLoader.menuMusic.stop();
				game.setScreen(new GameScreen(game));
				dispose();
			}
		});

		// Main menu button
		mainMenuButton = new Button(AssetLoader.skin);
		Label mainMenuLabel = new Label("MAIN MENU", AssetLoader.skin);
		mainMenuLabel.setFontScale(0.5f);
		mainMenuButton.add(mainMenuLabel);
		mainMenuButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetLoader.clickSound.play();
				game.setScreen(new MainMenuScreen(game));
				dispose();
			}
		});

		// Share button
		shareButton = new Button(AssetLoader.skin);
		Label shareLabel = new Label("SHARE SCORE", AssetLoader.skin);
		shareLabel.setFontScale(0.5f);
		shareButton.add(shareLabel);
		shareButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetLoader.clickSound.play();
				game.sharer.shareScore(GameScreen.hud.getScore());
			}
		});

		// Table used to display widgets
		Table table = new Table();
		table.bottom();
		table.setFillParent(true);

		// Table that contains the buttons
		Table buttonsTable = new Table();
		buttonsTable.center();

		buttonsTable.add(playAgainButton).size(163, 60);
		buttonsTable.add(shareButton).size(163, 60).padLeft(20).padRight(20);
		buttonsTable.add(mainMenuButton).size(163, 60);

		// Table that contains the score labels
		Table scoreTable = new Table();
		scoreTable.center();

		Label scoreLabel = new Label("SCORE: " + GameScreen.hud.getScore(), AssetLoader.skin);
		Label highScoreLabel = new Label("HIGH SCORE: " + RocketGuardian.getHighScore(), AssetLoader.skin);
		Label newRecordLabel = new Label("NEW RECORD!", AssetLoader.skin);
		scoreLabel.setColor(Color.FIREBRICK);
		highScoreLabel.setColor(Color.FIREBRICK);
		newRecordLabel.setColor(Color.GOLDENROD);
		newRecordLabel.setFontScale(1.5f);

		if (GameScreen.hud.getScore() == RocketGuardian.getHighScore() && RocketGuardian.getHighScore() != 0
				&& RocketGuardian.getHighScore() > RocketGuardian.previousHighScore) {
			AssetLoader.newRecordSound.play();
			scoreTable.add(newRecordLabel).padBottom(20);
			scoreTable.row();
		}
		scoreTable.add(scoreLabel);
		scoreTable.row();
		scoreTable.add(highScoreLabel).padTop(20);

		// Main table
		table.add(scoreTable).padBottom(30);
		table.row();
		table.add(buttonsTable).padBottom(60);

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
