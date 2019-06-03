package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionSpawnItem extends ScriptAction {

	public ActionSpawnItem(String name) {
		super("spawnItem", name, new TargetType[] { TargetType.BLOCK});
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length < 3 || objects[0]==null)
			return;
		int amount = (int)(Double.parseDouble(objects[2]+""));
		if(amount <= 0)
			return;
		ItemStack dropped = new ItemStack((Material) objects[0],amount);
		target.getLocation().getWorld().dropItem(target.getLocation().getBlock().getRelative((BlockFace) objects[1]).getLocation().add(0.5,0,0.5), dropped);
	}

	@Override
	public int getParameterAmount() {
		return 3;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { Material.DIRT, BlockFace.UP,1};
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.MATERIAL,ParamTypes.BLOCKFACE_OFFSET,ParamTypes.NUMBER};
	}
	@Override
	public String[] getParameterTitles() {
		return new String[] {"Item","Offset","Amount"};
	}
	@Override
	public Material getOverrideMaterial() {
		return null;
	}
}
