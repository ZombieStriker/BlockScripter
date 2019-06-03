package me.zombie_striker.blockscripter;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;

import me.zombie_striker.blockscripter.scripts.ScriptManager;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;

public class ScriptableTargetHolder {

	private Location location;
	private Entity entity;
	private ScriptedBlock block;
	private Event event;
	private Projectile projectile;

	public void setProjectile(Projectile p) {
		this.projectile = p;
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public ScriptedBlock getSelfScriptedBlock() {
		return block;
	}

	public Block getSelfBlock() {
		return block.getBlock();
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event e) {
		event = e;
	}

	public Location getLocation() {
		return location;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity e) {
		this.entity = e;
	}

	public void setSelfBlock(Block b) {
		this.block = ScriptManager.getBlock(b);
	}

	public void setSelfBlock(ScriptedBlock b) {
		this.block = b;
	}

	public void setLocation(Location l) {
		this.location = l;
	}
}
