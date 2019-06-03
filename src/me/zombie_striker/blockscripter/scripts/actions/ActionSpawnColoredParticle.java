package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.zombie_striker.blockscripter.ParticleHandlers;
import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionSpawnColoredParticle extends ScriptAction {

	public ActionSpawnColoredParticle(String name) {
		super("spawnColoredParticle", name, new TargetType[] { TargetType.BLOCK });
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if (objects.length < 4 || objects[0] == null)
			return;
		double x = (Double.parseDouble(objects[1] + ""));
		double y = (Double.parseDouble(objects[2] + ""));
		double z = (Double.parseDouble(objects[3] + ""));

		ParticleHandlers.spawnColoredParticle(x, y, z, 	target.getLocation().getBlock().getRelative((BlockFace) objects[0]).getLocation().add(0.5, 0.5, 0.5));
	}

	@Override
	public int getParameterAmount() {
		return 4;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { BlockFace.UP, 255, 255, 255 };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.BLOCKFACE_OFFSET, ParamTypes.NUMBER, ParamTypes.NUMBER,
				ParamTypes.NUMBER };
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] { "Offset", "Red(0-255)", "Green(0-255)", "Blue(0-255)" };
	}
	@Override
	public Material getOverrideMaterial() {
		return Material.REDSTONE;
	}
}
