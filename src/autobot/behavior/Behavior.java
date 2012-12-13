package autobot.behavior;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.util.Random;

/** @author Eric Kuxhausen **/
public abstract class Behavior {

	/** prepare should be called before calling canAct() or act() **/
	public abstract void prepare();

	public abstract boolean canAct();

	/** Discrete unit of work. canAct() should be checked before any calls to act are made */
	public abstract void act();

	protected void sleep(Times type) {
		Task.sleep(getTime(type));
	}

	/**
	 * Returns randomized sleep time for the associated action. Care should be
	 * taken when shortening existing times as this may break existing Behaviors
	 **/
	protected int getTime(Times type) {
		// TODO shorten times
		switch (type) {
		case NORMAL:
			return Random.nextGaussian(875, 1120, 1000, 80);
		case SUPERHEAT:
			return Random.nextGaussian(1200, 1400, 1250, 30);//estimate
		case HIGHALCH:
			return Random.nextGaussian(3100, 3200, 3150, 30);//guess
		case BANK:
			return Random.nextGaussian(3200, 3400, 3250, 30);
		case KEYPRESS:
			return Random.nextGaussian(80, 130, 100, 15);
		case BONEBURRY:
			return Random.nextGaussian(1200, 1400, 1250, 30);//guess
		}
		return 0;
	}

	public enum Times {
		NORMAL, SUPERHEAT, BANK, KEYPRESS, HIGHALCH, BONEBURRY
	}
}
