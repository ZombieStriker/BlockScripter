package me.zombie_striker.blockscripter.scripts.triggers;

import org.bukkit.event.player.PlayerMoveEvent;

import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class TriggerOnPlayerWalkOn extends ScriptTrigger {

	public TriggerOnPlayerWalkOn(String internalName) {
		super("onPlayerWalkOn",internalName);
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
