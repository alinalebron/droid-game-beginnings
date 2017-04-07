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

public class HowToPlayScreen extends BaseScreen {
	private Viewport viewport;
	private Stage stage;
	private Button backButton;
	private String howToPlayText;

	public HowToPlayScreen(final RocketGuardian game) {
		super(game);

		viewport = new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);
		
		howToPlayText = "ZOMBIES ARE FALLING ALONG THE CITY!" + 
				"\nAS THE ROCKET GUARDIAN, DO YOUR DUTY \nAND DESTROY THEM." +
				"\n\n- TAP TO JUMP WITH YOUR ROCKET" + 
				"\n- HIT THE ZOMBIES WITH YOUR WEAPON\n   TO WIPE THEM OUT." +
				"\n- THE CITIZENS ARE NOT ZOMBIES, \n SO DON'T DESTROY THEM (YET)." + 
				"\n - IF YOU KILL 5 CITIZENS, YOU WILL GET \"FIRED\".";
		
		// Create label with the text
		Label textLabel = new Label(howToPlayText, AssetLoader.skin);
		textLabel.setFontScale(0.75f);
		textLabel.setColor(Color.FOREST);
		textLabel.setAlignment(0);

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
		
		// Table used to display widgets
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		table.add(textLabel).expandX();
		table.row();
		table.add(backButton).size(163, 60).padTop(20);

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
