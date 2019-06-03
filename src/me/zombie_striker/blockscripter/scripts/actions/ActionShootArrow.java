package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionShootArrow extends ScriptAction {

	public ActionShootArrow(String name) {
		super("shootArrow", name, new TargetType[] { TargetType.BLOCK });
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if (objects.length < 4 || objects[0] == null)
			return;
		double x = (Double.parseDouble(objects[1] + ""));
		double y = (Double.parseDouble(objects[2] + ""));
		double z = (Double.parseDouble(objects[3] + ""));

		target.getLocation().getWorld().spawnEntity(
				target.getLocation().getBlock().getRelative((BlockFace) objects[0]).getLocation().add(0.5, 0.5, 0.5),
				EntityType.ARROW).setVelocity(new Vector(x, y, z));
		;// .dropItem(target.getLocation().getBlock().getRelative((BlockFace)
			// objects[1]).getLocation().add(0.5,0,0.5), dropped);
	}

	@Override
	public int getParameterAmount() {
		return 4;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { BlockFace.UP, 0, 1, 0 };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.BLOCKFACE_OFFSET, ParamTypes.NUMBER, ParamTypes.NUMBER,
				ParamTypes.NUMBER };
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] { "Offset", "Velocity-X", "Velocity-Y", "Velocity-Z" };
	}
	@Override
	public Material getOverrideMaterial() {
		return Material.ARROW;
	}
}
