package atm.rocketguardian;

import android.app.Activity;
import android.content.Intent;
import atm.rocketguardian.helpers.ScoreSharer;

public class AndroidScoreSharer implements ScoreSharer  {

	Activity act;
	
	public AndroidScoreSharer (Activity act) {
		this.act = act;
	}
	
	@Override
	public void shareScore(Integer val) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT,"Just scored " + val + " points in #RocketGuardian! https://gitlab.com/atorresm/rocket-guardian");
		shareIntent.setType("text/plain");
		act.startActivity(Intent.createChooser(shareIntent, act.getResources().getString(R.string.send_to)));
	}

}
