package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionPlaySound extends ScriptAction {

	public ActionPlaySound(String name) {
		super("playSound", name, new TargetType[] { TargetType.ENTITY, TargetType.BLOCK });
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if (objects.length < 3 || objects[0] == null)
			return;
		double volume = 0;
		if (objects[1] instanceof Integer) {
			volume = ((Integer) objects[1]);
		} else if (objects[1] instanceof Double) {
			volume = ((Double) objects[1]);
		}
		double pitch = 0;
		if (objects[2] instanceof Integer) {
			pitch = ((Integer) objects[2]);
		} else if (objects[2] instanceof Double) {
			pitch = ((Double) objects[2]);
		}

		String name = ((String) objects[0]);
		//if (name.startsWith("minecraft:"))
		//	name = name.split("minecraft:")[1];

		if (line.getTarget().getTargetType() == TargetType.ENTITY) {
			target.getEntity().getWorld().playSound(target.getLocation(), name, (float) volume, (float) pitch);
		} else {
			target.getLocation().getWorld().playSound(target.getLocation(), name, (float) volume, (float) pitch);
		}
	}

	@Override
	public int getParameterAmount() {
		return 3;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { "entity.experience_orb.pickup", 1, 1 };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.STRING, ParamTypes.NUMBER, ParamTypes.NUMBER };
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] { "Sound", "Volume", "Pitch" };
	}
	@Override
	public Material getOverrideMaterial() {
		return Material.NOTE_BLOCK;
	}
}
