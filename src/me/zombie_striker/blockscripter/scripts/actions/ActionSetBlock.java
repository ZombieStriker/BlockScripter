package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionSetBlock extends ScriptAction {

	public ActionSetBlock(String name) {
		super("setMaterial", name, new TargetType[] { TargetType.BLOCK});
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length < 1 || objects[0]==null)
			return;
		target.getLocation().getBlock().setType((Material)objects[0]);
	}

	@Override
	public int getParameterAmount() {
		return 1;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { Material.DIRT};
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.MATERIAL};
	}
	@Override
	public String[] getParameterTitles() {
		return new String[] {"Material"};
	}
	@Override
	public Material getOverrideMaterial() {
		return Material.STONE;
	}
}
