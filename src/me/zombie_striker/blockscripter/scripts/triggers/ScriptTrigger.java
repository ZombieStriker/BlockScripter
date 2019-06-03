package me.zombie_striker.blockscripter.scripts.triggers;

import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public abstract class ScriptTrigger {

	private String name;
	private String displayName;

	public ScriptTrigger(String internalName, String displayname) {
		name = internalName;
		this.displayName = displayname;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getName() {
		return name;
	}

	public abstract boolean isTriggeredBy(Object e);

	public abstract TargetType[] getValidTargetTypes();

	public boolean hasTargetType(TargetType type) {
		for (TargetType t : getValidTargetTypes()) {
			if (t == type)
				return true;
		}
		return false;
	}
}
