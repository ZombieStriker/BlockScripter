package me.zombie_striker.blockscripter.scripts.actions;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class ActionBroadcastHelloWorld extends ScriptAction {

	public ActionBroadcastHelloWorld(String name) {
		super("broadcastHelloWorld",name, new TargetType[] { TargetType.ENTITY});
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		Bukkit.broadcastMessage("Hello World");
	}

	@Override
	public int getParameterAmount() {
		return 0;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return null;
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return null;
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
