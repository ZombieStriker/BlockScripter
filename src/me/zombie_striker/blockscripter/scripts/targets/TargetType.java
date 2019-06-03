package me.zombie_striker.blockscripter.scripts.targets;

public enum TargetType {
	ENTITY("entity"), BLOCK("block"), CANCELABLE_EVENT("event"),PROJECTILE("projectile");
	private String name;

	private TargetType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
