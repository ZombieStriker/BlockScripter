package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionBreakBlock extends ScriptAction {

	public ActionBreakBlock(String name) {
		super("breakNaturally", name, new TargetType[] { TargetType.BLOCK });
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		target.getLocation().getBlock().breakNaturally();
	}

	@Override
	public int getParameterAmount() {
		return 0;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] {};
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] {};
	}

	@Override
	public String[] getParameterTitles() {
		return null;
	}
	@Override
	public Material getOverrideMaterial() {
		return null;
	}
}
