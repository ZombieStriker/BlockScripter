package me.zombie_striker.blockscripter.scripts.triggers;

import org.bukkit.event.entity.ProjectileHitEvent;

import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class TriggerOnProjectileHit extends ScriptTrigger {

	public TriggerOnProjectileHit(String internalName) {
		super("onProjectileHit",internalName);
	}

	@Override
	public boolean isTriggeredBy(Object e) {
		return (e instanceof ProjectileHitEvent);
	}

	@Override
	public TargetType[] getValidTargetTypes() {
		return new TargetType[] {TargetType.BLOCK, TargetType.ENTITY, TargetType.PROJECTILE};
	}

}
