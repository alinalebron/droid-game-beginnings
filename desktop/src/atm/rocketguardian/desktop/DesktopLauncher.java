package atm.rocketguardian.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import atm.rocketguardian.RocketGuardian;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Rocket Guardian";
		config.width = 640 * 2;
		config.height = 360 * 2;
		config.resizable = false;
		RocketGuardian game = new RocketGuardian();
		game.setScoreSharer(new DesktopScoreSharer());
		new LwjglApplication(game, config);
	}
}
