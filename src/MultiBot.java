
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Timer;

import autobot.behavior.*;

@Manifest(authors = { "ekux" }, name = "SuperHeatBot")
public class MultiBot extends ActiveScript {

	Behavior[] b = {new Fletcher(), new Superheater()};
	int[] bActs = {100/14, 150/5};
	int bCurrent = 0;
	
	
	int maxTimeInMinutes = 240;
	Timer t = new Timer(60L*1000L*maxTimeInMinutes);
	
	
	
	@Override
	public void onStart() {
		b[bCurrent].prepare();
		
	}

	@Override
	public int loop() {
		if (t.isRunning()&&bActs[bCurrent]>0 && b[bCurrent].canAct()){
			b[bCurrent].act();
			bActs[bCurrent]--;
			
			//Randomly switch behaviors
			if(Math.random()<.5){
				bCurrent = (int) (Math.random()*b.length);
				b[bCurrent].prepare();
				
				System.out.println("switced to :"+ b[bCurrent].getClass().getName());
			}
			//Make sure doing behavior with more acts if possible
			if(bActs[bCurrent] == 0)
			{
				int totalRemaining = 0;
				for(int i : bActs)
					totalRemaining+=i;
				if(totalRemaining>0){
					do{
						bCurrent = (int) (Math.random()*b.length);
					}while (bActs[bCurrent]<=0);
				}
				b[bCurrent].prepare();
			}
		}
		else{
			System.out.println("shutdown");
			System.out.println("timeRemaining " + t.getRemaining());
			System.out.println("actsRemaining" + b[bCurrent].getClass().getName()+ "  " +bActs[bCurrent]);
			
			shutdown();
		}
		return 0;
	}

	@Override
	public void onStop() {
	}
}