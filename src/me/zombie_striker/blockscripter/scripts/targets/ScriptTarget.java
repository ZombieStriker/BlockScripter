package me.zombie_striker.blockscripter.scripts.targets;

public class ScriptTarget {

	public String name;
	
	private String displayName;

	public TargetType returnType;

	public ScriptTarget(String name, String displayname, TargetType returnType) {
		this.returnType = returnType;
		this.name = name;
		this.displayName = displayname;
	}

	public String getDisplayName() {
		return displayName;
	}

	public TargetType getTargetType() {
		return this.returnType;
	}

	public String getName() {
		return this.name;
	}
}
