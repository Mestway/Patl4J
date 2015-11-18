package rulesEngine.listener;

import rulesEngine.behavior.SerenoaSessionBehavior;

public class SerenoaListener
	    implements FileListener
	  {
		
		SerenoaSessionBehavior serenoaSessionBehavior;
		
		public SerenoaListener(SerenoaSessionBehavior serenoaSessionBehavior){
			this.serenoaSessionBehavior = serenoaSessionBehavior;
		}
	
		@Override
	    public void fileChanged ()
	    {
			serenoaSessionBehavior.updateModels();
	    }
		
		

		
	  }