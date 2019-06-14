package me.zombie_striker.blockscripter.guis;

import java.util.ArrayList;
import java.util.List;

import me.zombie_striker.blockscripter.BlockScripter;
import me.zombie_striker.blockscripter.easygui.EasyGUI;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.zombie_striker.blockscripter.MaterialNameHandler;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptManager;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.targets.TargetType;
import net.md_5.bungee.api.ChatColor;

public class BlockScriptWindow {
	public static String blockScripterTitleOvereviewPrefix = "[BlockScripter] ";

	public static void loadWindow(Player player, Block b) {
		int invsize = getInvSize();
		ScriptedBlock sb = ScriptManager.getBlock(b);
		String name = sb.getName();
		Inventory inv = Bukkit.createInventory(null, invsize, blockScripterTitleOvereviewPrefix + name);

		// TRIGGER,AFFECTED,EFFECT,PARAMETER_ONE,PARAMETER_THREE,PARAMETER_TWO,PARAMETER_FOUR,PARAMETER_FIVE;
		inv.setItem(0, createItem(b.getType(), 1, "&6Change name?", "&fCurrently: " + name));
		inv.setItem(1, createItem(MaterialNameHandler.getGlass(DyeColor.WHITE), 1, "&6Trigger"));
		inv.setItem(2, createItem(MaterialNameHandler.getGlass(DyeColor.WHITE), 1, "&6Target"));
		inv.setItem(3, createItem(MaterialNameHandler.getGlass(DyeColor.WHITE), 1, "&6Action"));
		inv.setItem(4, createItem(MaterialNameHandler.getGlass(DyeColor.WHITE), 1, "&6Param One"));
		inv.setItem(5, createItem(MaterialNameHandler.getGlass(DyeColor.WHITE), 1, "&6Param Two"));
		inv.setItem(6, createItem(MaterialNameHandler.getGlass(DyeColor.WHITE), 1, "&6Param Three"));
		inv.setItem(7, createItem(MaterialNameHandler.getGlass(DyeColor.WHITE), 1, "&6Param Four"));
		inv.setItem(8, createItem(MaterialNameHandler.getGlass(DyeColor.WHITE), 1, "&6param Five"));
		for (int i = 1; i < ((int) invsize / 9); i++) {
			ScriptLine sl = sb.getScriptLine(i);

			inv.setItem(((i) * 9), createItem(
					sl.isActive() ? MaterialNameHandler.getGlass(DyeColor.WHITE) : new ItemStack(Material.BARRIER), i,
					"&fLine : " + i, "&fCurrently: " + (sl.isActive() ? "&a Active" : "&8 Deactivated"),
					(sl.isActive() ? "&fClick this to deactive this line." : "&fClick this to activate this line.")));

			if (sl.getTrigger() == null) {
				inv.setItem(((i) * 9) + 1,
						createItem(MaterialNameHandler.getGlass(DyeColor.GRAY), 1, "&6Select a Trigger "));
				fillRestLineBlanks(inv, i, 2);
				continue;
			} else {
				inv.setItem(((i) * 9) + 1,
						createItem(MaterialNameHandler.getGlass(DyeColor.WHITE), 1, sl.getTrigger().getDisplayName()));
			}

			if (sl.getTarget() == null) {
				inv.setItem(((i) * 9) + 2,
						createItem(MaterialNameHandler.getGlass(DyeColor.GRAY), 1, "&6Select a Target "));
				fillRestLineBlanks(inv, i, 3);
				continue;
			} else {
				// Material used = Material.WHITE_STAINED_GLASS_PANE;
				ItemStack used = MaterialNameHandler.getGlass(DyeColor.WHITE);
				ScriptedBlock target = null;
				if (sl.getTarget().getTargetType() == TargetType.BLOCK) {
					if (sl.hasTargetSpecifications()) {
						used = new ItemStack(
								((ScriptedBlock) sl.getTargetSpecifications()).getBlock().getType() != Material.AIR
										? ((ScriptedBlock) sl.getTargetSpecifications()).getBlock().getType()
										: Material.BARRIER);
						target = ((ScriptedBlock) sl.getTargetSpecifications());
					} else {
						used = new ItemStack(b.getType());
						target = sb;
					}
				}

				inv.setItem(((i) * 9) + 2, createItem(used, 1, sl.getTarget().getDisplayName(),
						(target != null ? "&fTargeting: " + target.getName() : "")));
			}

			if (sl.getAction() == null) {
				inv.setItem(((i) * 9) + 3,
						createItem((MaterialNameHandler.getGlass(DyeColor.GRAY)), 1, "&6Select an Action "));
				fillRestLineBlanks(inv, i, 4);
				continue;
			} else {
				inv.setItem(((i) * 9) + 3,
						createItem(
								(sl.getAction().getOverrideMaterial() != null
										? new ItemStack(sl.getAction().getOverrideMaterial())
										: MaterialNameHandler.getGlass(DyeColor.WHITE)),
								1, sl.getAction().getDisplayName()));
			}

			// Params exist
			if (sl.getAction().getParameterAmount() > 0) {
				for (int id = 0; id < sl.getParameters().length; id++) {
					Object obj = sl.getParameters()[id];
					if (obj == null) {
						inv.setItem(((i) * 9) + (4 + id), createItem(Material.BARRIER, 1,
								sl.getAction().getParameterTitles()[id] + ": Unspecified"));
					} else {
						switch (sl.getAction().getParameterTypes()[id]) {
						case MATERIAL:
							inv.setItem(((i) * 9) + (4 + id), createItem(((Material) obj), 1,
									sl.getAction().getParameterTitles()[id] + ": " + ((Material) obj).name()));
							break;
						case SOUND_NAMES:
							inv.setItem(((i) * 9) + (4 + id),
									createItem(MaterialNameHandler.getGlass(DyeColor.LIGHT_BLUE), 1,
											sl.getAction().getParameterTitles()[id] + ": " + ((String) obj)));
							break;
						case SPECIFIC_BLOCK:
							ScriptedBlock sbl = null;
							if (obj instanceof String) {
								sbl = ScriptManager.getBlockByName((String) obj);
							} else if (obj instanceof ScriptedBlock)

								sbl = (ScriptedBlock) obj;
							inv.setItem(((i) * 9) + (4 + id),
									createItem(
											sbl.getBlock().getType() != Material.AIR ? sbl.getBlock().getType()
													: Material.BARRIER,
											1, sl.getAction().getParameterTitles()[id] + ": " + sbl.getName()
													+ ChatColor.RESET + ""));
							break;
						case BOOLEAN:
							boolean active = (boolean) obj;
							inv.setItem(((i) * 9) + (4 + id),
									createItem(active ? Material.EMERALD_BLOCK : Material.BARRIER, 1,
											sl.getAction().getParameterTitles()[id] + ": " + (active)));
							break;
						case STRING:
							try {
								inv.setItem(((i) * 9) + (4 + id),
										createItem(Material.OAK_SIGN, 1, sl.getAction().getParameterTitles()[id]
												+ ": \"" + ((String) obj) + ChatColor.RESET + "\""));
							} catch (Error | Exception e4) {
								inv.setItem(((i) * 9) + (4 + id),
										createItem(Material.valueOf("SIGN"), 1,
												sl.getAction().getParameterTitles()[id] + ": \""

														+ ((String) obj) + ChatColor.RESET + "\""));

							}
							break;
						case NUMBER:
							double d = 0;// (double) ((Integer) obj);
							if (obj instanceof Double) {
								d = ((Double) obj);
							} else if (obj instanceof Integer) {
								d = ((Integer) obj);
							} else if (obj instanceof Float)
								d = ((Float) obj);

							inv.setItem(((i) * 9) + (4 + id),
									createItem(MaterialNameHandler.getGlass(DyeColor.WHITE),
											(d <= 0 ? 1 : d > 64 ? 1 : (int) d),
											sl.getAction().getParameterTitles()[id] + ": " + d));
							break;
						case ENTITYTYPE:
							Material used = MaterialNameHandler.getEntityEgg((EntityType) obj);
							inv.setItem(((i) * 9) + (4 + id), createItem(used, 1, ((EntityType) obj).name()));
							break;
						case BLOCKFACE_OFFSET:
							inv.setItem(((i) * 9) + (4 + id), createItem(MaterialNameHandler.getMagentaClay(), 1,
									sl.getAction().getParameterTitles()[id] + ": " + ((BlockFace) obj).name()));
							break;
						}
					}
				}
			}
			fillRestLineBlanks(inv, i, 4 + sl.getAction().getParameterAmount());
		}

		GUIManager.setScriptLookingAt(player, sb);
		player.openInventory(inv);
	}

	private static void fillRestLineBlanks(Inventory inv, int line, int slot) {
		for (int i = slot; i < 9; i++) {
			inv.setItem(((line) * 9) + i, createItem(MaterialNameHandler.getSilverGlass(), 1, "..."));
		}
	}

	protected static ItemStack createItem(ItemStack m, int amount, String name, String... lore) {
		ItemStack is = m.clone();
		m.setAmount(amount);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(
				(name.contains("&") ? "" : ChatColor.RESET) + ChatColor.translateAlternateColorCodes('&', name));
		List<String> lores = new ArrayList<>();
		for (String l : lore)
			lores.add(ChatColor.translateAlternateColorCodes('&', l));
		im.setLore(lores);
		is.setItemMeta(im);
		return is;
	}

	protected static ItemStack createItem(Material m, int amount, String name, String... lore) {
		ItemStack is = new ItemStack(m, amount);
		ItemMeta im = is.getItemMeta();
		if (name != null)
			im.setDisplayName(
					(name.contains("&") ? "" : ChatColor.RESET) + ChatColor.translateAlternateColorCodes('&', name));
		List<String> lores = new ArrayList<>();
		for (String l : lore)
			lores.add(ChatColor.translateAlternateColorCodes('&', l));
		im.setLore(lores);
		is.setItemMeta(im);
		return is;
	}

	private static int getInvSize() {
		if(BlockScripter.isVersionHigherThan(1,14))
			return 9*6;
		return 72;
	}
}
