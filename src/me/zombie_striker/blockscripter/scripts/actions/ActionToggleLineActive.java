package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionToggleLineActive extends ScriptAction {

	public ActionToggleLineActive(String name) {
		super("toggleActiveLines", name, new TargetType[] { TargetType.BLOCK });
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		ScriptedBlock sb = target.getSelfScriptedBlock();
		for (Object o : objects) {
			if (o != null) {
				int lineID = 0;
				if (o instanceof Integer) {
					lineID = (Integer) o;
				} else if (o instanceof Double) {
					lineID = (int) ((double) ((Double) o));
				} else {
					continue;
				}
				if (lineID >= 0) {
					ScriptLine sl = sb.getScriptLine(lineID);
					if (sl != null)
						sl.setActive(!sl.isActive());
				}
			}
		}
	}

	@Override
	public int getParameterAmount() {
		return 4;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { null, null, null, null };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.NUMBER, ParamTypes.NUMBER, ParamTypes.NUMBER, ParamTypes.NUMBER };
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] { "A line to toggle", "A line to toggle", "A line to toggle", "A line to toggle" };
	}

	@Override
	public Material getOverrideMaterial() {
		return null;
	}
}
