package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionSetFireTicks extends ScriptAction {

	public ActionSetFireTicks(String name) {
		super("setFireTicks", name, new TargetType[] { TargetType.ENTITY,TargetType.PROJECTILE });
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
		if (target.getEntity() instanceof Damageable) {
			target.getEntity().setFireTicks((int) h);
		}
	}

	@Override
	public int getParameterAmount() {
		return 1;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { 0 };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.NUMBER };
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] { "Fire-Ticks" };
	}
	@Override
	public Material getOverrideMaterial() {
		return Material.LAVA_BUCKET;
	}
}
