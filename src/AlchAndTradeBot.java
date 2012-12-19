
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

import autobot.behavior.*;

@Manifest(authors = { "ekux" }, name = "PartialAlchAndTradeBot")
public class AlchAndTradeBot extends ActiveScript {
	
	//TODO implement
	
	@Override
	public int loop() {
		InGameGE.open();
		if(InGameGE.buy("nature rune", 0, 100)){
			InGameGE.close();
			shutdown();
		}
		shutdown();
		
		return 0;
	}
}