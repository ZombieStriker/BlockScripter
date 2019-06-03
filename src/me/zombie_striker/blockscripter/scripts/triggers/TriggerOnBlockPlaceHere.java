package me.zombie_striker.blockscripter.scripts.triggers;

import org.bukkit.event.block.BlockPlaceEvent;

import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class TriggerOnBlockPlaceHere extends ScriptTrigger {

	public TriggerOnBlockPlaceHere(String internalName) {
		super("onBlockPlaceHere",internalName);
	}

	@Override
	public boolean isTriggeredBy(Object e) {
		return (e instanceof BlockPlaceEvent);
	}

	@Override
	public TargetType[] getValidTargetTypes() {
		return new TargetType[] {TargetType.ENTITY,TargetType.BLOCK,TargetType.CANCELABLE_EVENT};
	}

}
