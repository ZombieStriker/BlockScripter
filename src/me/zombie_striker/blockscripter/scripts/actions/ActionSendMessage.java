package me.zombie_striker.blockscripter.scripts.actions;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ActionSendMessage extends ScriptAction {

	public ActionSendMessage(String name) {
		super("sendMessage",name, new TargetType[] { TargetType.ENTITY});
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length < 1 || objects[0]==null)
			return;
		if(target.getEntity() instanceof Player) {
			((Player)target.getEntity()).sendMessage(ChatColor.translateAlternateColorCodes('&',(String)objects[0]));
		}
	}

	@Override
	public int getParameterAmount() {
		return 1;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] {"Default Message"};
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] {ParamTypes.STRING};
	}
	@Override
	public String[] getParameterTitles() {
		return new String[] {"Message"};
	}
	@Override
	public Material getOverrideMaterial() {
		try {
		return Material.OAK_SIGN;
		}catch(Error|Exception e4) {
			return Material.valueOf("SIGN");			
		}
	}
}
