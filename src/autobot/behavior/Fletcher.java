package autobot.behavior;

import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;

import autobot.behavior.Behavior.Times;

/** Sample Behavior that heats mith@author Eric Kuxhausen **/
public class Fletcher extends Behavior {

	private boolean canAccomplish;
	public int errorCount;

	public final int unstrungBow = 62, string = 1777; // 66 for unstrung yew
														// shieldbow // 64 for
														// maple shortbow // 62
														// for maple shieldbow

	/** this assumes near bank, knife in toolbar **/
	public Fletcher() {
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
		return canAccomplish;
	}

	@Override
	public void act() {
		try {
			bank();
			string();
		} catch (Exception e) {
			errorCount++;
		}
	}

	private void string() throws Exception {
		System.out.println("attempting to string");
		Inventory.getItemAt(0).getWidgetChild().click(true);
		sleep(Times.NORMAL);
		Inventory.getItemAt(15).getWidgetChild().click(true);
		sleep(Times.NORMAL);
		Mouse.click(390 + Random.nextInt(5, 20), 360 + Random.nextInt(0, 15),
				true);
		System.out.println("Started stringing");
		Time.sleep(Random.nextInt(14000, 15000));

		for (int i = 0; i < 5; i++) {
			if (!Inventory.contains(unstrungBow)) {
				i = 10;
			} else {
				i++;
				Time.sleep(3000);
				System.out.println("Waiting to finish stringing...");
			}
			System.out.println("Finished Stringing");
		}
	}

	private void bank() throws Exception {
		if (Bank.open())
			System.out.println("opened bank");
		else {
			sleep(Times.SHORT);
			Bank.open();
		}
		sleep(Times.SHORT);
		Bank.depositInventory(); /* deposit strung bows */
		System.out.println("deposited inventory");
		Bank.withdraw(unstrungBow, 14); /* withdraw unstrung bows */
		System.out.println("withdrew bows");
		Bank.withdraw(string, 14); /* withdraw string */
		System.out.println("withdrew strings");
		Bank.close(); /* close */
		System.out.println("closing bank");
		sleep(Times.BANK);
	}
}
