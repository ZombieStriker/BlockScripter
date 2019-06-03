package me.zombie_striker.blockscripter.scripts.actions;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ActionSendMessageNearby extends ScriptAction {

	public ActionSendMessageNearby(String name) {
		super("sendMessageRadius", name, new TargetType[] { TargetType.ENTITY, TargetType.BLOCK });
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length < 2 || objects[0]==null)
			return;
		Location base = target.getLocation();
		if(line.getTarget().getTargetType()==TargetType.ENTITY) {
			base = target.getEntity().getLocation();
		}
		double d = 0;// (double) ((Integer) obj);
		if (objects[1] instanceof Double)
			d = ((Double) objects[1]);
		else if (objects[1] instanceof Integer)
			d = ((Integer) objects[1]);
		else if (objects[1] instanceof Float)
			d = ((Float) objects[1]);
		for (Player player : base.getWorld().getPlayers()) {
			if (player.getLocation().distanceSquared(target.getLocation()) <= d
					* d)
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',(String) objects[0]));
		}

	}

	@Override
	public int getParameterAmount() {
		return 2;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { "Default Message", 6 };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.STRING, ParamTypes.NUMBER };
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] { "Message", "radius" };
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
