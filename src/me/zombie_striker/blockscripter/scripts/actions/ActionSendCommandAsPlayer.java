package me.zombie_striker.blockscripter.scripts.actions;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ActionSendCommandAsPlayer extends ScriptAction {

	public ActionSendCommandAsPlayer(String name) {
		super("sendCommandAsPlayer",name, new TargetType[] { TargetType.ENTITY});
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length < 1 || objects[0]==null)
			return;
		if(target.getEntity() instanceof Player) {
			Bukkit.dispatchCommand(((Player)target.getEntity()), (String)objects[0]);
		}
	}

	@Override
	public int getParameterAmount() {
		return 1;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] {"say Default Message"};
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] {ParamTypes.STRING};
	}
	@Override
	public String[] getParameterTitles() {
		return new String[] {"Command"};
	}
	@Override
	public Material getOverrideMaterial() {
		return Material.COMMAND_BLOCK;
	}
}
