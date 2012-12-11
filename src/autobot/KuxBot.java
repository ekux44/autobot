package autobot;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;

import autobot.behavior.*;

@Manifest(authors = { "ekux" }, name = "KuxBot")
public class KuxBot extends ActiveScript {

	Behavior b = new Superheater();

	@Override
	public void onStart() {
		System.out.println("Starting up Kuxscript");
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
		System.out.println("Stopping Kuxscript");
	}
}
