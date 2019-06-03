package me.zombie_striker.blockscripter.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import me.zombie_striker.blockscripter.BlockScripter;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptManager;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.ScriptAction;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.ScriptTarget;
import me.zombie_striker.blockscripter.scripts.triggers.ScriptTrigger;

public class BlockScriptConfigManager {

	private static File saveFile;
	private static FileConfiguration c;

	public static void init(BlockScripter bs) {
		saveFile = new File(bs.getDataFolder(), "blockscripts.yml");
		if (!saveFile.exists())
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		c = YamlConfiguration.loadConfiguration(saveFile);
	}

	public static void saveBlockScripts() {
		c.set("blocks", null);
		for (ScriptedBlock bs : ScriptManager.blocks) {
			if (bs.isDud())
				continue;
			try {
				c.set("blocks." + bs.getName() + ".location", bs.getBlock().getLocation());
				for (ScriptLine sl : bs.getScriptLines()) {
					if (sl == null)
						return;
					if (!sl.isActive())
						c.set("blocks." + bs.getName() + ".line." + sl.getLineID() + ".active", sl.isActive());
					if (sl.getTrigger() != null)
						c.set("blocks." + bs.getName() + ".line." + sl.getLineID() + ".trigger",
								sl.getTrigger().getName());
					if (sl.getTarget() != null)
						c.set("blocks." + bs.getName() + ".line." + sl.getLineID() + ".target",
								sl.getTarget().getName());
					if (sl.getAction() != null)
						c.set("blocks." + bs.getName() + ".line." + sl.getLineID() + ".action",
								sl.getAction().getName());
					if (sl.getParameters() != null) {
						for (int i = 0; i < sl.getParameters().length; i++) {
							Object object = null;
							if (sl.getParameters()[i] instanceof String) {
								object = sl.getParameters()[i];
							} else if (sl.getParameters()[i] instanceof EntityType) {
								object = ((EntityType) sl.getParameters()[i]).name();
							} else if (sl.getParameters()[i] instanceof BlockFace) {
								object = ((BlockFace) sl.getParameters()[i]).name();
							} else if (sl.getParameters()[i] instanceof Integer) {
								object = sl.getParameters()[i];
							} else if (sl.getParameters()[i] instanceof Material) {
								object = ((Material) sl.getParameters()[i]).name();
							} else if (sl.getParameters()[i] instanceof ScriptedBlock) {
								object = ((ScriptedBlock) sl.getParameters()[i]).getName();
							} else if (sl.getParameters()[i] instanceof Block) {
								object = ((Block) sl.getParameters()[i]).getLocation();
							} else {
								object = sl.getParameters()[i];
							}
							c.set("blocks." + bs.getName() + ".line." + sl.getLineID() + ".parameter." + i, object);
						}
					}
					if (sl.hasTargetSpecifications()) {
						if (sl.getSpecificTarget() instanceof ScriptedBlock)
							c.set("blocks." + bs.getName() + ".line." + sl.getLineID() + ".specificTarget",
									((ScriptedBlock) sl.getSpecificTarget()).getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			c.save(saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadBlockScripts() {

		List<TargetPair> linkingBlocks = new ArrayList<>();
		if (!c.contains("blocks"))
			return;
		for (String key : c.getConfigurationSection("blocks").getKeys(false)) {
			String name = key;
			Location location = (Location) c.get("blocks." + name + ".location");
			if(location == null)
				continue;
			ScriptedBlock sb = new ScriptedBlock(location.getBlock(), name);
			if (c.contains("blocks." + name + ".line"))
				for (String lines : c.getConfigurationSection("blocks." + name + ".line").getKeys(false)) {
					int line = Integer.parseInt(lines);
					ScriptTrigger trigger = null;
					ScriptTarget target = null;
					ScriptAction action = null;

					String triggerS = c.getString("blocks." + name + ".line." + line + ".trigger");
					if (triggerS != null)
						trigger = ScriptManager.getTriggerByName(triggerS);
					String targetS = c.getString("blocks." + name + ".line." + line + ".target");
					if (targetS != null)
						target = ScriptManager.getTargetByName(targetS);
					String actionS = c.getString("blocks." + name + ".line." + line + ".action");
					if (actionS != null)
						action = ScriptManager.getActionByName(actionS);

					if (actionS != null && action == null)
						continue;
					if (targetS != null && target == null)
						continue;
					if (triggerS != null && trigger == null)
						continue;

					ScriptLine sl = sb.getScriptLine(line);
					Object[] objs = null;
					if (action != null) {
						try {
							objs = new Object[action.getParameterAmount()];
							if (c.contains("blocks." + name + ".line." + line + ".parameter"))
								for (String paramIDS : c
										.getConfigurationSection("blocks." + name + ".line." + line + ".parameter")
										.getKeys(false)) {
									int paramID = Integer.parseInt(paramIDS);
									Object obj = c.get("blocks." + name + ".line." + line + ".parameter." + paramID);
									if (action.getParameterTypes()[paramID] == ParamTypes.BLOCKFACE_OFFSET) {
										obj = BlockFace.valueOf((String) obj);
									}
									if (action.getParameterTypes()[paramID] == ParamTypes.ENTITYTYPE) {
										obj = EntityType.valueOf((String) obj);
									}
									if (action.getParameterTypes()[paramID] == ParamTypes.MATERIAL) {
										obj = Material.matchMaterial((String) obj);
									}
									if (action.getParameterTypes()[paramID] == ParamTypes.SPECIFIC_BLOCK) {
										linkingBlocks.add(new TargetPair(sl, paramID, ((String) obj)));
									}
									objs[paramID] = obj;
								}
						} catch (Error | Exception e4) {
							e4.printStackTrace();
						}
					}

					sb.loadScripts(line, trigger, target, action, objs);

					boolean active = c.contains("blocks." + name + ".line." + line + ".active")
							? c.getBoolean("blocks." + name + ".line." + line + ".active")
							: true;
					sl.setActive(active);

					if (c.contains("blocks." + name + ".line." + line + ".specificTarget")) {
						linkingBlocks.add(new TargetPair(sl, -1,
								c.getString("blocks." + name + ".line." + line + ".specificTarget")));
					}
				}

			ScriptManager.blocks.add(sb);
		}
		if (!linkingBlocks.isEmpty()) {
			for (TargetPair e : linkingBlocks) {
				if (e.getParam() == -1) {
					e.getLine().setTargetSpecifications(ScriptManager.getBlockByName(e.getTarget()));
				} else {
					e.getLine().updateParameter(e.getParam(), ScriptManager.getBlockByName(e.getTarget()));
				}
			}
		}
	}

	static class TargetPair {
		private String target;
		private int id;
		private ScriptLine line;

		public TargetPair(ScriptLine l, int i, String t) {
			this.target = t;
			this.id = i;
			this.line = l;
		}

		public ScriptLine getLine() {
			return line;
		}

		public int getParam() {
			return id;
		}

		public String getTarget() {
			return target;
		}
	}
}
