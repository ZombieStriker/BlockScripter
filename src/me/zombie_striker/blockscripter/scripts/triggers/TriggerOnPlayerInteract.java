package me.zombie_striker.blockscripter.scripts.triggers;

import org.bukkit.event.player.PlayerInteractEvent;

import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class TriggerOnPlayerInteract extends ScriptTrigger {

	public TriggerOnPlayerInteract(String internalName) {
		super("onPlayerInteract",internalName);
	}

	@Override
	public boolean isTriggeredBy(Object e) {
		return (e instanceof PlayerInteractEvent);
	}

	@Override
	public TargetType[] getValidTargetTypes() {
		return new TargetType[] {TargetType.ENTITY,TargetType.BLOCK,TargetType.CANCELABLE_EVENT};
	}

}
