package me.zombie_striker.blockscripter.scripts.actions;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ActionSendTitleMessage extends ScriptAction {

	public ActionSendTitleMessage(String name) {
		super("sendMessage",name, new TargetType[] { TargetType.ENTITY});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length < 2)
			return;
		if(target.getEntity() instanceof Player) {
			((Player)target.getEntity()).sendTitle(ChatColor.translateAlternateColorCodes('&',(String)objects[0]),ChatColor.translateAlternateColorCodes('&',(String)objects[1]));
		}
	}

	@Override
	public int getParameterAmount() {
		return 2;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] {"Default Message",null};
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] {ParamTypes.STRING, ParamTypes.STRING};
	}
	@Override
	public String[] getParameterTitles() {
		return new String[] {"Title", "SubTitle"};
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
