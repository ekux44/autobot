package autobot.behavior;

import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;

import autobot.behavior.Behavior.Times;

public class Burrier extends Behavior {

	private boolean canAccomplish;
	public int errorCount;

	public final int bigBones = 532, babyDragonBones = 534;

	/**
	 * Assumes key 9 bound to burry babyDragonBones and key 0 bound to burry
	 * bigBones. Inventory tab open
	 **/
	@Override
	public void prepare() {
		// TODO reduce assumptions
		try {
			Bank.depositInventory();
			canAccomplish = true;

		} catch (Exception e) {
			canAccomplish = false;
		}
	}

	@Override
	public boolean canAct() {
		if (errorCount > 5) // arbitrary number of acceptable errors
			canAccomplish = false;
		// TODO add more checking
		return canAccomplish;
	}

	@Override
	public void act() {
		try {
			bank();
			for (int i = 0; i < 28; i++)
				burry();
		} catch (Exception e) {
			errorCount++;
		}
	}

	private void burry() throws Exception {
		switch (Inventory.getItemAt(27).getId()) {
		case bigBones:
			Keyboard.sendKey((char) '0', getTime(Times.BONEBURRY));
			sleep(Times.BONEBURRY);
			break;
		case babyDragonBones:
			Keyboard.sendKey((char) '9', getTime(Times.BONEBURRY));
			sleep(Times.BONEBURRY);
			break;
		default:
			errorCount++;
		}
	}

	private void bank() throws Exception {
		Bank.open();
		sleep(Times.NORMAL);
		Bank.depositInventory();
		sleep(Times.NORMAL);
		Bank.withdraw(bigBones, 28);
		sleep(Times.NORMAL);
		Bank.withdraw(babyDragonBones, 28);
		sleep(Times.NORMAL);
		Bank.close();
		sleep(Times.BANK);
	}
}
