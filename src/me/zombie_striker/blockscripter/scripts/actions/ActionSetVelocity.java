package me.zombie_striker.blockscripter.scripts.actions;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;

import org.bukkit.Material;
import org.bukkit.util.Vector;

public class ActionSetVelocity extends ScriptAction {

	public ActionSetVelocity(String name) {
		super("setPlayerVelocity", name, new TargetType[] { TargetType.ENTITY,TargetType.PROJECTILE });
	}

	@Override
	public void activate(ScriptLine line, ScriptableTargetHolder target, Object... objects) {
		if(objects.length < 3)
			return;
		double x = 0;
		if(objects[0] instanceof Integer) {
			x= ((Integer)objects[0]);
		}else if (objects[0] instanceof Double) {
			x=((Double)objects[0]);
		}
		double y = 0;
		if(objects[1] instanceof Integer) {
			y= ((Integer)objects[0]);
		}else if (objects[1] instanceof Double) {
			y=((Double)objects[1]);
		}
		double z = 0;
		if(objects[2] instanceof Integer) {
			z= ((Integer)objects[2]);
		}else if (objects[2] instanceof Double) {
			z=((Double)objects[2]);
		}
		
		
		target.getEntity().setVelocity(new Vector(x,y,z));
	}

	@Override
	public int getParameterAmount() {
		return 3;
	}

	@Override
	public Object[] getDefaultParameters(ScriptedBlock sb) {
		return new Object[] { 0, 0, 0 };
	}

	@Override
	public ParamTypes[] getParameterTypes() {
		return new ParamTypes[] { ParamTypes.NUMBER, ParamTypes.NUMBER, ParamTypes.NUMBER };
	}

	@Override
	public String[] getParameterTitles() {
		return new String[] {"X","Y","Z"};
	}
	@Override
	public Material getOverrideMaterial() {
		return null;
	}
}
