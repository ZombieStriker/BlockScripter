package me.zombie_striker.blockscripter.scripts.actions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

public abstract class ScriptAction implements Comparable<ScriptAction>{

	private String name;
	private String displayname;

	private Set<TargetType> acceptedObjectTypes = new HashSet<>();

	public ScriptAction(String name, String displayName, TargetType[] acceptedObjectTypes) {
		for (TargetType c : acceptedObjectTypes) {
			this.acceptedObjectTypes.add(c);
		}
		this.displayname = displayName;
		this.name = name;
	}

	public String getDisplayName() {
		return displayname;
	}

	public Set<TargetType> getAcceptedRequirementObjects() {
		return this.acceptedObjectTypes;
	}

	public String getName() {
		return this.name;
	}

	public abstract void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects);

	public abstract int getParameterAmount();

	public abstract Object[] getDefaultParameters(ScriptedBlock sb);

	public abstract ParamTypes[] getParameterTypes();

	public abstract String[] getParameterTitles();
	
	public abstract Material getOverrideMaterial();
	
	@Override
	public int compareTo(ScriptAction arg0) {
		return this.displayname.compareTo(arg0.displayname);
	}

}
