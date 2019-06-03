package me.zombie_striker.blockscripter.scripts.actions;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.guis.PromptGUI;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public class ActionOpenPrompt extends ScriptAction {

	public ActionOpenPrompt(String name) {
		super("openPrompt", name, new TargetType[] { TargetType.ENTITY});
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if (objects.length == 0 || objects[0] == null)
			return;
		String title = (String) objects[0];
		/*PromptGUI gui =*/ new PromptGUI(target.getSelfScriptedBlock(), (Player)target.getEntity(), title);
	}

	@Override
	public int getParameterAmount() {
		return 1;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { "Default message: Do you accept" };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.STRING };
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] { "Explain what the prompt does" };
	}
	@Override
	public Material getOverrideMaterial() {
		return null;
	}
}
