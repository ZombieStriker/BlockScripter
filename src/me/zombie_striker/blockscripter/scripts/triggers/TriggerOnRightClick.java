package me.zombie_striker.blockscripter.scripts.triggers;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class TriggerOnRightClick extends ScriptTrigger {

	public TriggerOnRightClick(String internalName) {
		super("onRightClick", internalName);
	}

	@Override
	public boolean isTriggeredBy(Object e) {
		return (e instanceof PlayerInteractEvent) && ((PlayerInteractEvent) e).getAction() == Action.RIGHT_CLICK_BLOCK;
	}

	@Override
	public TargetType[] getValidTargetTypes() {
		return new TargetType[] { TargetType.ENTITY, TargetType.BLOCK, TargetType.CANCELABLE_EVENT };
	}

}
