import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Timer;

import autobot.behavior.Behavior;
import autobot.behavior.HerbCleaner;

@Manifest(authors = { "ekux" }, name = "HerbCleanBot", description = "")
public class HerbCleanBot extends ActiveScript {

	Behavior b;
	int maxTimeInMinutes = 290+(int)(10*Math.random()); // up to 5 hours
	Timer t = new Timer(60L*1000L*maxTimeInMinutes);
	
	@Override
	public int loop() {
		if(b==null)
			b = new HerbCleaner();
		if (t.isRunning() && b.canAct())
			b.act();
		else{
			System.out.println("shutdown");
			System.out.println("timeRemaining " + t.getRemaining());
			shutdown();
		}
		return 0;
	}
}
