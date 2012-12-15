import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Timer;

import autobot.behavior.*;

/**
 * Randomly runs behaviors listed below. Will switch occasionally switch
 * behaviors and will stop trying a behavior if it is unsuccessful
 **/
@Manifest(authors = { "ekux" }, name = "MultiBot")
public class MultiBot extends ActiveScript {

	Behavior b;
	Class<?>[] behaviors = { Superheater.class, Fletcher.class , HerbCleaner.class};
	boolean[] canStillAct = { true, true, true};
	int bCurrent = 0;

	int maxTimeInMinutes = 240;
	Timer t = new Timer(60L * 1000L * maxTimeInMinutes);

	public boolean somethingCanStillAct() {
		boolean result = false;
		for (boolean b : canStillAct)
			result |= b;
		return result;
	}

	@Override
	public int loop() {
		while (b == null && somethingCanStillAct()) {
			bCurrent = (int) (Math.random() * behaviors.length);
			if (canStillAct[bCurrent])
				try {
					b = (Behavior) behaviors[bCurrent].newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					System.out.println("Behavior creation error");
				}

		}
		if (!t.isRunning() || b == null) {
			System.out.println("shutdown");
			System.out.println("timeRemaining " + t.getRemaining());
			shutdown();
		} else if (b.canAct()) {
			b.act();
			if (Math.random() < .2f)// randomly switch occasionally
				b = null;
		} else {
			canStillAct[bCurrent] = false;
			b = null;
		}

		return 0;
	}

}