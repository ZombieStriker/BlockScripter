package me.zombie_striker.blockscripter.scripts.triggers;

import org.bukkit.event.block.BlockRedstoneEvent;

import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class TriggerOnRedstoneDePowered extends ScriptTrigger {

	public TriggerOnRedstoneDePowered(String internalName) {
		super("onRedstoneDepowered",internalName);
	}

	@Override
	public boolean isTriggeredBy(Object e) {
		return (e instanceof BlockRedstoneEvent) && ((BlockRedstoneEvent)e).getNewCurrent()==0 && ((BlockRedstoneEvent)e).getOldCurrent()>0; 
	}

	@Override
	public TargetType[] getValidTargetTypes() {
		return new TargetType[] {TargetType.BLOCK};
	}

}
