package autobot.behavior;

import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;

/** Sample Behavior that heats mith@author Eric Kuxhausen **/
public class Fletcher extends Behavior {

	private boolean canAccomplish;
	public int errorCount;

	public final int unstrungBow=62, string=1777; //66 for unstrung yew shieldbow // 64 for maple shortbow // 62 for maple shieldbow

	@Override
	/** this assumes near bank, knife in toolbar **/
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
			string();
		} catch (Exception e) {
			errorCount++;
		}
	}

	private void string() throws Exception {
		System.out.println("attempting to string");
		Inventory.getItemAt(1).getWidgetChild().click(true);
		Time.sleep(Random.nextInt(500, 800));
		Inventory.getItemAt(15).getWidgetChild().click(true);
		Time.sleep(Random.nextInt(1000, 1300));
		Mouse.click(390+Random.nextInt(5, 20), 360+Random.nextInt(0, 15), true);
		System.out.println("Started stringing");
		Time.sleep(Random.nextInt(14000, 15000));
		
		for(int i = 0; i<5; i++){
			if(!Inventory.contains(unstrungBow)){
				i=10;				
			}
			else{
				i++;
				Time.sleep(3000);
				System.out.println("Waiting to finish stringing...");
			}
		System.out.println("Finished Stringing");
		}
	}

	private void bank() throws Exception {
		Bank.open(); System.out.println("opened bank"); /*open bank*/
		Time.sleep(Random.nextInt(1000, 1300));
		Bank.depositInventory(); System.out.println("deposited inventory"); /*deposit strung bows*/
		Bank.withdraw(unstrungBow, 14); System.out.println("withdrew bows"); /*withdraw unstrung bows*/
		Bank.withdraw(string, 14); System.out.println("withdrew strings"); /*withdraw string*/
		Bank.close(); System.out.println("closing bank"); /*close*/
		sleep(Times.BANK);
	}
}
