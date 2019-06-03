package me.zombie_striker.blockscripter.scripts.triggers;

import org.bukkit.event.player.PlayerMoveEvent;

import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class TriggerOnBlockBreak extends ScriptTrigger {

	public TriggerOnBlockBreak(String internalName) {
		super("onBlockBreak",internalName);
	}

	@Override
	public boolean isTriggeredBy(Object e) {
		return (e instanceof PlayerMoveEvent);
	}

	@Override
	public TargetType[] getValidTargetTypes() {
		return new TargetType[] {TargetType.ENTITY,TargetType.BLOCK,TargetType.CANCELABLE_EVENT};
	}

}
