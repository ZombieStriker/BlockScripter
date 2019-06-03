package me.zombie_striker.blockscripter.scripts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

import me.zombie_striker.blockscripter.scripts.actions.ScriptAction;
import me.zombie_striker.blockscripter.scripts.targets.ScriptTarget;
import me.zombie_striker.blockscripter.scripts.triggers.ScriptTrigger;

public class ScriptManager {

	public static final List<ScriptedBlock> blocks = new ArrayList<ScriptedBlock>();

	public static final List<ScriptTrigger> triggers = new ArrayList<ScriptTrigger>();
	public static final List<ScriptTarget> targets = new ArrayList<ScriptTarget>();
	public static final List<ScriptAction> actions = new ArrayList<ScriptAction>();

	// TRIGGER,AFFECTED,EFFECT,PARAMETER_ONE,PARAMETER_THREE,PARAMETER_TWO,PARAMETER_FOUR,PARAMETER_FIVE;

	public static void registerTrigger(ScriptTrigger trigger) {
		triggers.add(trigger);
	}

	public static void unregisterTrigger(ScriptTrigger trigger) {
		triggers.remove(trigger);
	}

	public static void registerAction(ScriptAction trigger) {
		actions.add(trigger);
	}

	public static void unregisterAction(ScriptAction trigger) {
		actions.remove(trigger);
	}

	public static void registerTarget(ScriptTarget trigger) {
		targets.add(trigger);
	}

	public static void unregisterTarget(ScriptTarget trigger) {
		targets.remove(trigger);
	}

	public static ScriptTrigger getTriggerByDisplayName(String name) {
		name = ChatColor.stripColor(name);
		for (ScriptTrigger st : triggers) {
			if (st.getDisplayName().equals(name))
				return st;
		}
		return null;
	}

	public static ScriptTarget getTargetByDisplayName(String name) {
		name = ChatColor.stripColor(name);
		for (ScriptTarget st : targets) {
			if (st.getDisplayName().equals(name))
				return st;
		}
		return null;
	}

	public static ScriptAction getActionByDisplayName(String name) {
		name = ChatColor.stripColor(name);
		for (ScriptAction st : actions) {
			if (st.getDisplayName().equals(name))
				return st;
		}
		return null;
	}

	public static ScriptTrigger getTriggerByName(String name) {
		name = ChatColor.stripColor(name);
		for (ScriptTrigger st : triggers) {
			if (st.getName().equals(name))
				return st;
		}
		return null;
	}

	public static ScriptTarget getTargetByName(String name) {
		name = ChatColor.stripColor(name);
		for (ScriptTarget st : targets) {
			if (st.getName().equals(name))
				return st;
		}
		return null;
	}

	public static ScriptAction getActionByName(String name) {
		name = ChatColor.stripColor(name);
		for (ScriptAction st : actions) {
			if (st.getName().equals(name))
				return st;
		}
		return null;
	}

	public static boolean isScriptedBlock(Block b) {
		return false; // TODO: stuff
	}

	public static ScriptedBlock getBlock(Block b) {
		for(ScriptedBlock sb : blocks) {
			if(sb.getBlock().getLocation().equals(b.getLocation()))
				return sb;
		}
		ScriptedBlock sb = new ScriptedBlock(b,b.getWorld().getName()+"_X="+b.getLocation().getBlockX()+"Y="+b.getLocation().getBlockY()+"Z="+b.getLocation().getBlockX());
		blocks.add(sb);
		return sb;
	}

	public static ScriptedBlock getBlockByName(String name) {
		for (ScriptedBlock sb : blocks) {
			if (sb.getName().equals(name))
				return sb;
		}
		return null;
	}

}
