package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionProjectileBounce extends ScriptAction {

	public ActionProjectileBounce(String name) {
		super("bounce", name, new TargetType[] { TargetType.PROJECTILE });
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if (objects.length == 0 || objects[0] == null)
			return;
		double h = 0;
		if (objects[0] instanceof Integer) {
			h = ((Integer) objects[0]);
		} else if (objects[0] instanceof Double) {
			h = ((Double) objects[0]);
		}
		Bukkit.broadcastMessage("stiff");
		target.getProjectile().teleport(target.getProjectile().getLocation().add(0, 12, 0));
		target.getProjectile().setVelocity(target.getProjectile().getVelocity().multiply(-h));
	}

	@Override
	public int getParameterAmount() {
		return 1;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { 1 };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.NUMBER };
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] { "Velocity multiplier" };
	}
	@Override
	public Material getOverrideMaterial() {
		return Material.SLIME_BALL;
	}
}
