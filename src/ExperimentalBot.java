
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

import autobot.behavior.*;

@Manifest(authors = { "ekux" }, name = "PartialAlchAndTradeBot")
public class ExperimentalBot extends ActiveScript {
	
	Behavior b;
	int maxTimeInMinutes = 290+(int)(10*Math.random()); // up to 5 hours
	Timer t = new Timer(60L*1000L*maxTimeInMinutes);
	
	@Override
	public int loop() {
		//interactWithGrandExchange();
		b = new ToGrandExchange();
		b.act();
		
		return 0;
	}
	
	public void interactWithGrandExchange(){
		InGameGE.open();
		if(InGameGE.buy("nature rune", 0, 100)){
			InGameGE.close();
			shutdown();
		}
		shutdown();
	}
}