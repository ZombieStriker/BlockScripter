package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import me.zombie_striker.blockscripter.BlockScripter;
import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptManager;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionFireRelayOneSecond extends ScriptAction {

	private BlockScripter bs;
	
	public ActionFireRelayOneSecond(String name, BlockScripter bs) {
		super("fireRelaySecond", name, new TargetType[] { TargetType.BLOCK});
		this.bs = bs;
	}

	@Override
	public void activate(ScriptLine line, final ScriptableTargetHolder target, Object... objects) {
		final ScriptableTargetHolder sth= new ScriptableTargetHolder();
		sth.setLocation(target.getLocation());
		new BukkitRunnable() {
			
			@Override
			public void run() {
				ScriptManager.getBlock(target.getLocation().getBlock()).activate("relay", sth);				
			}
		}.runTaskLater(bs, 20);
	}

	@Override
	public int getParameterAmount() {
		return 0;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] {};
	}
	@Override
	public String[] getParameterTitles() {
		return null;
	}
	@Override
	public Material getOverrideMaterial() {
		return Material.REPEATER;
	}
}
