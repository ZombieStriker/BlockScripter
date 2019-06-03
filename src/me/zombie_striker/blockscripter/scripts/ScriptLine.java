package me.zombie_striker.blockscripter.scripts;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.actions.ScriptAction;
import me.zombie_striker.blockscripter.scripts.targets.ScriptTarget;
import me.zombie_striker.blockscripter.scripts.targets.TargetFurthestPlayer;
import me.zombie_striker.blockscripter.scripts.targets.TargetNearbyPlayer;
import me.zombie_striker.blockscripter.scripts.triggers.ScriptTrigger;

public class ScriptLine {

	private int id;

	protected boolean isactive = true;

	protected ScriptTrigger trigger;
	protected ScriptTarget target;

	protected Object targetSpecifications = null;

	protected ScriptAction effect;

	protected Object[] params;

	public ScriptLine clone() {
		ScriptLine newSl = new ScriptLine(id, trigger, target, effect);
		newSl.targetSpecifications = targetSpecifications;
		newSl.params = params.clone();
		return newSl;
	}

	public ScriptLine(int id, ScriptTrigger trigger, ScriptTarget target, ScriptAction effect) {
		this.id = id;
		this.trigger = trigger;
		this.target = target;
		this.effect = effect;
	}

	public boolean isTriggered(Object e) {
		if (trigger == null)
			return false;
		if (!isActive())
			return false;
		return trigger.isTriggeredBy(e);
	}

	public void updateParameter(int id, Object o) {
		if (params == null) {
			if (effect == null)
				return;
			params = new Object[effect.getParameterAmount()];
		}
		params[id] = o;
	}

	public void activate(ScriptableTargetHolder rt) {
		if (hasTargetSpecifications()) {
			if (getTargetSpecifications() instanceof ScriptedBlock) {
				rt.setLocation(((ScriptedBlock) targetSpecifications).getBlock().getLocation());
			} else if (getTargetSpecifications() instanceof Location) {
				rt.setLocation(((Location) targetSpecifications));
			} else if (getTargetSpecifications() instanceof TargetNearbyPlayer) {
				Player who = null;
				double distance = Double.MAX_VALUE;
				for (Player player : rt.getSelfBlock().getWorld().getPlayers()) {
					double tempD = player.getLocation().distanceSquared(rt.getSelfBlock().getLocation());
					if (tempD < distance) {
						distance = tempD;
						who = player;
					}
				}
				if (who == null)
					return;
				rt.setEntity(who);
			} else if (getTargetSpecifications() instanceof TargetFurthestPlayer) {
				Player who = null;
				double distance = -1;
				for (Player player : rt.getSelfBlock().getWorld().getPlayers()) {
					double tempD = player.getLocation().distanceSquared(rt.getSelfBlock().getLocation());
					if (tempD > distance) {
						distance = tempD;
						who = player;
					}
				}
				if (who == null)
					return;
				rt.setEntity(who);
			}
			/*
			 * else { rt.setLocation(((Location) targetSpecifications)); }
			 */
		}
		if (effect != null)
			effect.activate(this, rt, params);
	}

	public int getLineID() {
		return id;
	}

	public void setActive(boolean b) {
		this.isactive = b;
	}

	public boolean isActive() {
		return isactive;
	}

	public boolean hasTargetSpecifications() {
		return targetSpecifications != null;
	}

	public void setTargetSpecifications(Object o) {
		this.targetSpecifications = o;
	}

	public Object getTargetSpecifications() {
		return targetSpecifications;
	}

	public ScriptTarget getTarget() {
		return target;
	}

	public ScriptTrigger getTrigger() {
		return trigger;
	}

	public ScriptAction getAction() {
		return effect;
	}

	public Object[] getParameters() {
		return params;
	}

	public Object getSpecificTarget() {
		return targetSpecifications;
	}
}
