
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Timer;

import autobot.behavior.*;

@Manifest(authors = { "ekux" }, name = "SuperHeatBot")
public class FletchBot extends ActiveScript {

	Behavior b = new Fletcher();
	int maxTimeInMinutes = 240;
	Timer t = new Timer(60L*1000L*maxTimeInMinutes);
	
	@Override
	public void onStart() {
		b.prepare();
	}

	@Override
	public int loop() {
		if (t.isRunning() && b.canAct())
			b.act();
		else{
			System.out.println("shutdown");
			System.out.println("timeRemaining " + t.getRemaining());
			shutdown();
		}
		return 0;
	}

	@Override
	public void onStop() {
	}
}