package atm.rocketguardian;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import atm.rocketguardian.RocketGuardian;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useImmersiveMode = true;
		RocketGuardian game = new RocketGuardian();
		game.setScoreSharer(new AndroidScoreSharer(this));
		initialize(game, config);
	}
}
