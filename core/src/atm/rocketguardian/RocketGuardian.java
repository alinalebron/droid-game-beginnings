package atm.rocketguardian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import atm.rocketguardian.entities.Backgrounds;
import atm.rocketguardian.helpers.AssetLoader;
import atm.rocketguardian.helpers.ScoreSharer;
import atm.rocketguardian.screens.MainMenuScreen;

public class RocketGuardian extends Game {

	public SpriteBatch batch;
	public AssetLoader assets;
	public static Backgrounds menuBackground;
	public static int previousHighScore;
	public static Preferences prefs;
	public ScoreSharer sharer;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		AssetLoader.load();
		
		loadPreferences();
		previousHighScore = getHighScore();
		
		menuBackground = new Backgrounds(0);
		setScreen(new MainMenuScreen(this));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
		batch.dispose();
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	public void setScoreSharer(ScoreSharer sharer) {
		this.sharer = sharer;
	}
	
	public void loadPreferences() {
		prefs = Gdx.app.getPreferences("RocketGuardian");
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
	}
	
	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}
	
	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}
}
