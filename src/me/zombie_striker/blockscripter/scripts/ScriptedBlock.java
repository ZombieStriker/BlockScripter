package me.zombie_striker.blockscripter.scripts;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.actions.ActionSetBlock;
import me.zombie_striker.blockscripter.scripts.actions.ScriptAction;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.ScriptTarget;
import me.zombie_striker.blockscripter.scripts.triggers.ScriptTrigger;
import me.zombie_striker.blockscripter.scripts.triggers.TriggerOnBlockPlaceHere;

public class ScriptedBlock {

	private Block b;

	private String name;

	public HashMap<Integer, ScriptLine> lines = new HashMap<Integer, ScriptLine>();

	public ScriptedBlock(Block b, String name) {
		this.b = b;
		this.name = name;
	}

	public void setBlock(Block newLocation) {
		this.b = newLocation;
	}

	public String getName() {
		return name;
	}

	public boolean setName(String name) {
		for (ScriptedBlock sb : ScriptManager.blocks) {
			if (sb.getName().equals(name) && sb != this) {
				return false;
			}
		}
		this.name = name;
		return true;
	}

	public Collection<ScriptLine> getScriptLines() {
		return lines.values();
	}

	public ScriptLine getScriptLine(int line) {
		if (lines.containsKey(line))
			return lines.get(line);
		ScriptLine sl = new ScriptLine(line, null, null, null);
		lines.put(line, sl);
		return sl;
	}

	public void activate(Object activationCode, ScriptableTargetHolder trigger) {
		for (ScriptLine line : lines.values()) {
			if (line.isTriggered(activationCode)) {
				line.activate(trigger);
			}
		}
	}

	public boolean isDud() {
		if (b == null)
			return true;
		if(b.getType()==Material.AIR) {
			for (ScriptLine e : lines.values()) {
				if (e.getAction() instanceof ActionSetBlock)
					return false;
				if (e.getTrigger() instanceof TriggerOnBlockPlaceHere)
					return false;
			}
			return true;
		}
		if (!lines.isEmpty()) {
			for (ScriptLine e : lines.values()) {
				if (e.trigger != null)
					return false;
			}
		}
		for (ScriptedBlock sb : ScriptManager.blocks) {
			for (ScriptLine sl : sb.getScriptLines()) {
				if (sl.getTarget() != null && sl.getTargetSpecifications() == this)
					return false;
				if (sl.getAction() != null && sl.getAction().getParameterAmount() > 0) {
					for (Object j : sl.getParameters())
						if (j instanceof ScriptedBlock && j == this)
							return false;
				}
			}
		}
		return true;
	}

	public boolean hasLine(int line) {
		if (!lines.containsKey(line))
			return false;
		return lines.get(line).trigger != null;
	}

	public void updateTargetSpecific(int line, Object target) {
		ScriptLine sl;
		if (lines.containsKey(line)) {
			sl = lines.get(line);
			sl.targetSpecifications = target;
		} else {
		}

	}

	public void loadScripts(int line, ScriptTrigger trig, ScriptTarget tar, ScriptAction effect, Object[] params) {
		ScriptLine sl;
		if (lines.containsKey(line)) {
			sl = lines.get(line);
			sl.params = params;
			sl.effect = effect;
			sl.target = tar;
			sl.trigger = trig;
		} else {
			sl = new ScriptLine(line, trig, tar, effect);
			sl.params = params;
			lines.put(line, sl);
		}
	}

	public void updateTriggers(int line, ScriptTrigger t) {
		ScriptLine sl;
		if (lines.containsKey(line)) {
			sl = lines.get(line);
			sl.params = null;
			sl.effect = null;
			sl.target = null;
			sl.targetSpecifications = null;
			sl.trigger = t;
		} else {
			sl = new ScriptLine(line, t, null, null);
			lines.put(line, sl);
		}
	}

	public void updateTarget(int line, ScriptTarget t) {
		ScriptLine sl;
		if (lines.containsKey(line)) {
			sl = lines.get(line);
			sl.params = null;
			sl.effect = null;
			sl.targetSpecifications = null;
			sl.target = t;
		} else {
		}
	}

	public void updateEffect(int line, ScriptAction t) {
		ScriptLine sl;
		if (lines.containsKey(line)) {
			sl = lines.get(line);
			if (t != null)
				sl.params = t.getDefaultParameters(this);
			sl.effect = t;
		} else {
		}
	}

	public void updateParam(int line, int param, Object input) {
		ScriptLine sl;
		if (lines.containsKey(line)) {
			sl = lines.get(line);
			if (sl.params == null) {
				sl.params = new Object[sl.effect.getParameterAmount()];
			}
			if (input == null && sl.effect.getParameterTypes()[param] == ParamTypes.NUMBER) {
				sl.params[param] = 0;
			} else {
				sl.params[param] = input;
			}
		} else {
		}
	}

	public Block getBlock() {
		return b;
	}
}
