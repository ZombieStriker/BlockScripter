package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Location;
import org.bukkit.Material;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionTeleportTo extends ScriptAction {

	public ActionTeleportTo(String name) {
		super("teleportTo", name, new TargetType[] { TargetType.ENTITY});
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length ==0 || objects[0]==null)
			return;
		Location to = ((ScriptedBlock)objects[0]).getBlock().getLocation().add(0.5,1,0.5);
		to.setDirection(target.getEntity().getLocation().getDirection());
		target.getEntity().teleport(to);
	}

	@Override
	public int getParameterAmount() {
		return 1;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] {sb};
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.SPECIFIC_BLOCK};
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] { "Teleport Player To"};
	}
	@Override
	public Material getOverrideMaterial() {
		return Material.ENDER_PEARL;
	}
}
