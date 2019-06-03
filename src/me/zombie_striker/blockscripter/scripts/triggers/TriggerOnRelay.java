package me.zombie_striker.blockscripter.scripts.triggers;

import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class TriggerOnRelay extends ScriptTrigger {

	public TriggerOnRelay(String internalName) {
		super("onLeftClick",internalName);
	}

	@Override
	public boolean isTriggeredBy(Object e) {
		return (e instanceof String) && ((String)e).equals("relay");
	}

	@Override
	public TargetType[] getValidTargetTypes() {
		return new TargetType[] {TargetType.BLOCK};
	}

}
