package atm.rocketguardian.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import atm.rocketguardian.helpers.AssetLoader;
import atm.rocketguardian.helpers.Constants;
import atm.rocketguardian.helpers.MapGenerator;

public class Hud implements Disposable {
	public Stage stage;
	private Viewport viewport;
	
	//Scene2D widgets
	private Label guardianLabel;
	private Label scoreLabel;
	private Label playerLabel;
	private Label scoreTextLabel;
	
	private Integer guardianLives;
	private Integer score;

	public Hud(SpriteBatch batch) {
		// Define variables
		guardianLives = 1;
		score = 0;
		
		// setup viewport and stage
		viewport = new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, batch);
		
		// define Table
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		Table iconsTable = new Table();
		
		// define the life icon
		Image lifeIcon = new Image(AssetLoader.lifeIcon);
		lifeIcon.setScale(1.25f);
		
		// define labels
		guardianLabel = new Label("x" + guardianLives.toString(), AssetLoader.skin);
		scoreLabel = new Label(score.toString(), AssetLoader.skin);
		playerLabel = new Label("PLAYER", AssetLoader.skin);
		scoreTextLabel = new Label("SCORE", AssetLoader.skin);
		
		// scale labels
		guardianLabel.setFontScale(0.75f);
		scoreLabel.setFontScale(0.75f);
		playerLabel.setFontScale(0.75f);
		scoreTextLabel.setFontScale(0.75f);
		
		iconsTable.add(lifeIcon).expandX();
		iconsTable.add(guardianLabel).padLeft(15).expandX();
		
		// add widgets to table
		table.add(playerLabel).align(Align.left).padLeft(15);
		table.add(scoreTextLabel).align(Align.right).padRight(15);
		
		table.row();
		
		table.add(iconsTable).expandX().align(Align.left).padLeft(15).padTop(0);
		table.add(scoreLabel).expandX().align(Align.right).padRight(15).padTop(0);
		
		stage.addActor(table);
		
	}
	
	public void update (float delta) {
		guardianLabel.setText("x" + MapGenerator.rocket.getLives());
		scoreLabel.setText(score.toString());
		stage.act();
		stage.draw();
	}
	
	public void addScore() {
		score++;
	}
	
	public int getScore() {
		return score;
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

}
