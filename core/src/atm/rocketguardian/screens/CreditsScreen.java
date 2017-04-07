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

public class CreditsScreen extends BaseScreen {
	private Viewport viewport;
	private Stage stage;
	private Button backButton, donateButton;
	private String creditsString;

	public CreditsScreen(final RocketGuardian game) {
		super(game);
		viewport = new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);
		
		creditsString = "DEVELOPED BY ANTONIO TORRES M." 
					+ "\n ART BY JAIME TORRES." 
					+ "\n LICENSED UNDER GPL3"
					+ "\n POWERED BY LIBGDX (APACHE 2.0)" 
					+ "\n\n MUSIC BY CODEMANU UNDER CC-BY-3.0."
					+ "\n SFX BY BART & LITTLE ROBOT SOUND FACTORY UNDER CC-BY-3.0."
					+ "\n BUTTONS BY @CAMTATZ UNDER CC-0."
					+ "\n FONT: 8-BIT PUSAB";
					
		Label creditsLabel = new Label(creditsString, AssetLoader.skin);
		creditsLabel.setColor(Color.FOREST);
		creditsLabel.setAlignment(0);
		creditsLabel.setFontScale(0.60f);
		// Buttons
		// Back to menu button
		// Back button
		backButton = new Button(AssetLoader.skin);
		Label backLabel = new Label("BACK", AssetLoader.skin);
		backLabel.setFontScale(0.5f);
		backButton.add(backLabel);
		backButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetLoader.clickSound.play();
				game.setScreen(new MainMenuScreen(game));
				dispose();
			}
		});

		// Donate button
		donateButton = new Button(AssetLoader.skin, "donate");
		Label donateLabel = new Label("DONATE", AssetLoader.skin);
		donateLabel.setFontScale(0.5f);
		donateButton.add(donateLabel);
		donateButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetLoader.clickSound.play();
				Gdx.net.openURI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=M7YKJ3W9E9BVY");
			}
		});

		// Table used to display widgets
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		table.add(creditsLabel).expandX();
		table.row();
		table.add(donateButton).size(163 * 0.75f, 60 * 0.75f).padTop(10);
		table.row();
		table.add(backButton).size(163, 60).padTop(10);
		
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
