package me.zombie_striker.blockscripter.scripts.triggers;


import me.zombie_striker.blockscripter.guis.PromptGUI;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class TriggerOnPromptReject extends ScriptTrigger {

	public TriggerOnPromptReject(String internalName) {
		super("onPromptReject",internalName);
	}

	@Override
	public boolean isTriggeredBy(Object e) {
		return (e instanceof PromptGUI) && !((PromptGUI)e).getResponse();
	}

	@Override
	public TargetType[] getValidTargetTypes() {
		return new TargetType[] {TargetType.BLOCK, TargetType.ENTITY};
	}

}
