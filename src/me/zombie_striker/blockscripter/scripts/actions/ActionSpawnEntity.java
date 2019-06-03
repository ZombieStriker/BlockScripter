package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;

import me.zombie_striker.blockscripter.MaterialNameHandler;
import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionSpawnEntity extends ScriptAction {

	public ActionSpawnEntity(String name) {
		super("spawnEntity", name, new TargetType[] { TargetType.BLOCK});
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length < 2 || objects[0]==null)
			return;
		
		target.getLocation().getWorld().spawnEntity(target.getLocation().getBlock().getRelative((BlockFace) objects[1]).getLocation().add(0.5,0,0.5), (EntityType) objects[0]);
	}

	@Override
	public int getParameterAmount() {
		return 2;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { EntityType.PIG, BlockFace.UP};
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.ENTITYTYPE,ParamTypes.BLOCKFACE_OFFSET};
	}
	@Override
	public String[] getParameterTitles() {
		return new String[] {"Entity","Offset"};
	}
	@Override
	public Material getOverrideMaterial() {
		return MaterialNameHandler.getEntityEgg(EntityType.PIG);
	}
}
