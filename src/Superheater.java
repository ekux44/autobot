import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;

/** Sample Behavior that heats mith@author Eric Kuxhausen **/
public class Superheater extends Behavior {

	private boolean canAccomplish;
	public int errorCount;

	public final int mithrilOre = 447, mithBar = 2359, coalOre = 453,
			natureRunes = 561;

	@Override
	/** this assumes near bank, fire staff equipped, and inventory tab open **/
	public void prepare() {
		//TODO reduce assumptions
		try {
			Bank.depositInventory();
			canAccomplish = true;

		} catch (Exception e) {
			canAccomplish = false;
		}
	}

	@Override
	public boolean canAct() {
		if (errorCount > 5) //arbitrary number of acceptable errors
			canAccomplish = false;
		// TODO add more checking
		return canAccomplish;
	}

	@Override
	public void act() {
		try {
			bank();
			for (int i = 0; i < 5; i++)
				smelt();
		} catch (Exception e) {
			errorCount++;
		}
	}

	private void smelt() throws Exception {
		Keyboard.sendKey((char) '1', getTime(Times.KEYPRESS));
		sleep(Times.KEYPRESS);
		Inventory.getItemAt(25).getWidgetChild().click(true);
		sleep(Times.SUPERHEAT);
	}

	private void bank() throws Exception {
		Bank.open();
		sleep(Times.NORMAL);
		Bank.depositInventory();
		sleep(Times.NORMAL);
		Bank.withdraw(natureRunes, 5);
		sleep(Times.NORMAL);
		Bank.withdraw(coalOre, 20);
		sleep(Times.NORMAL);
		Bank.withdraw(mithrilOre, 5);
		sleep(Times.NORMAL);
		Bank.close();
		sleep(Times.BANK);

	}
}
