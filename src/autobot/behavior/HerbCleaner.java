package autobot.behavior;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;

import autobot.behavior.Behavior.Times;

/** Behavior that cleans herbs @author Eric Kuxhausen **/
public class HerbCleaner  extends Behavior {

	private boolean canAccomplish;
	public int errorCount;

	public final int grimyHerb = 2485; 
	// 211 for grimy avantoe
	// 2485 for grimy lantadyme
	// 217 for grimy dwarf weed
	
	/** this assumes near bank **/
	public HerbCleaner() {
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
		Inventory.getItemAt(0).getWidgetChild().click(true);
		sleep(Times.NORMAL);
		Mouse.click(390 + Random.nextInt(5, 20), 360 + Random.nextInt(0, 15),
				true);
		System.out.println("Started Cleaning");
		Time.sleep(Random.nextInt(14000, 15000));
		
		int wait = 0;
		while (Inventory.contains(grimyHerb)){
			Time.sleep(1000);
			wait++;
			if(wait>15){
				//something went wrong
				throw new Exception();
			}
		}
		System.out.println("Finished Cleaning");
	}

	private void bank() throws Exception {
		if (Bank.open())
			System.out.println("opened bank");
		else {
			sleep(Times.SHORT);
			Bank.open();
		}
		sleep(Times.SHORT);
		Bank.depositInventory();
		sleep(Times.SHORT);
		Bank.withdraw(grimyHerb, 28); 
		sleep(Times.SHORT);
		Bank.close(); /* close */
		System.out.println("closing bank");
		sleep(Times.BANK);
	}
	
	public void asdf (Comparable c){
		
	}
}
