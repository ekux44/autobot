package autobot.behavior;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.Path.TraversalOption;
import org.powerbot.game.api.wrappers.map.TilePath;

import autobot.behavior.Behavior.Times;

public class ToGrandExchange extends Behavior {

	private boolean canAccomplish;
	public int errorCount;

	// public static final int;
	private TilePath tp1;

	/** this assumes Varrock Loadstone Teleport Active and bound to "=" **/
	public ToGrandExchange() {
		// TODO assumes at fishing spot
		try {
			Tile[] t1 = { new Tile(3210, 3390, 0), new Tile(3210, 3407, 0),
					new Tile(3210, 3424, 0), new Tile(3195, 3429, 0),
					new Tile(3178, 3429, 0), new Tile(3176, 3443, 0),
					new Tile(3169, 3457, 0), new Tile(3164, 3473, 0),
					new Tile(3177, 3478, 0) };
			tp1 = new TilePath(t1);
			canAccomplish = true;
			System.out.println("Prepare Completed");

		} catch (Exception e) {
			canAccomplish = false;
		}
	}

	@Override
	public boolean canAct() {
		if (errorCount > 5) // arbitrary number of acceptable errors
		{
			System.out.println("Shutting down");
			canAccomplish = false;
		}
		// TODO add more checking
		return canAccomplish;
	}

	@Override
	public void act() {
		try {
			sleep(Times.NORMAL);
			teleport();
			walkToExchange();
		} catch (Exception e) {
			errorCount++;
			System.out.println("Caught an error: " + e);
		}
	}

	private void teleport() throws Exception {
		Keyboard.sendKey((char) '=', getTime(Times.KEYPRESS));
		sleep(Times.KEYPRESS);
		sleep(Times.VERYSHORT);

		Mouse.click(Random.nextInt(360, 380), Random.nextInt(215, 245), true);
		sleep(Times.NORMAL);
		sleep(Times.NORMAL);
		sleep(Times.NORMAL);
		sleep(Times.NORMAL);
		sleep(Times.NORMAL);
	}

	private void walkToExchange() throws Exception {
		while (tp1.validate()) {
			tp1.traverse();
			sleep(Times.SHORT);
		}
	}

}