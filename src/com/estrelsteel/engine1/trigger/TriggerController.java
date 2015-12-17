package com.estrelsteel.engine1.trigger;

public abstract class TriggerController {
	
	private Trigger trigger;
	
	public TriggerController(Trigger trigger) {
		this.trigger = trigger;
	}
	
	public boolean checkCollide() {
		if(trigger.getLocation().collidesWith(trigger.getEntity().getLocation())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public abstract void execute();
}
