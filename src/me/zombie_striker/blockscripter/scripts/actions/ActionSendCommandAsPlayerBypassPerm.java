package me.zombie_striker.blockscripter.scripts.actions;

import me.zombie_striker.blockscripter.BlockScripter;
import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ActionSendCommandAsPlayerBypassPerm extends ScriptAction {

	public ActionSendCommandAsPlayerBypassPerm(String name) {
		super("sendCommandAsPlayerBypassPerm",name, new TargetType[] { TargetType.ENTITY});
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length < 2 || objects[0]==null)
			return;
		if(target.getEntity() instanceof Player) {
			BlockScripter.bypassPermissionCommand(((Player)target.getEntity()), (String)objects[0], (String)objects[1]);
		}
	}

	@Override
	public int getParameterAmount() {
		return 2;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] {"say Default Message","permission.temp"};
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] {ParamTypes.STRING,ParamTypes.STRING};
	}
	@Override
	public String[] getParameterTitles() {
		return new String[] {"Command","Permission to bypass"};
	}
	@Override
	public Material getOverrideMaterial() {

		try {
			return Material.COMMAND_BLOCK;
		}catch (Error|Exception e3){return Material.STONE;}
	}
}
