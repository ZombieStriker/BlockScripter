package me.zombie_striker.blockscripter.scripts.actions;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

import org.bukkit.Material;
import org.bukkit.event.Cancellable;

public class ActionCancelEvent extends ScriptAction {

	public ActionCancelEvent(String name) {
		super("cancelEvent", name, new TargetType[] { TargetType.CANCELABLE_EVENT });
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(target.getEvent() instanceof Cancellable) {
			((Cancellable)target.getEvent()).setCancelled(true);
		}
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
		return new String[] {};
	}
	@Override
	public Material getOverrideMaterial() {

		try {
			return Material.BARRIER;
		}catch (Error|Exception e3){return Material.STONE;}
	}
}
