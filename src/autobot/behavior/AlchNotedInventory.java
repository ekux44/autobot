package autobot.behavior;

import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

import autobot.behavior.Behavior.Times;

/** currently high alchs everything in inventory except natures, indifferent of noted **/
public class AlchNotedInventory extends Behavior {

	private boolean canAccomplish;
	public int errorCount;
	
	public final int natureRunes = 561;

	/** this assumes near bank, fire staff equipped, inventory tab open, 
	 * and highalch bound to 2 on the open action bar **/
	public AlchNotedInventory(){
		canAccomplish = true;
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
			alchEverythingButNatureOnce();
		} catch (Exception e) {
			System.out.println("Caught error: "+e);
			errorCount++;
		}
	}
	public void alchEverythingButNatureOnce(){
		Item[] items = Inventory.getItems();
		for(Item i : items){
			if(i.getId()!=natureRunes){
				Keyboard.sendKey((char) '2', getTime(Times.KEYPRESS));
				sleep(Times.KEYPRESS);
				i.getWidgetChild().click(true);
				sleep(Times.HIGHALCH);
			}
		}
	}
}
