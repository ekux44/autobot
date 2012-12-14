
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;

import autobot.behavior.*;

@Manifest(authors = { "ekux" }, name = "SuperHeatBot")
public class AlchBot extends ActiveScript {

	Behavior b = new AlchNotedInventory();

	@Override
	public void onStart() {
		b.prepare();
	}

	@Override
	public int loop() {
		if (b.canAct())
			b.act();
		else
			shutdown();
		return 0;
	}

	@Override
	public void onStop() {
	}
}