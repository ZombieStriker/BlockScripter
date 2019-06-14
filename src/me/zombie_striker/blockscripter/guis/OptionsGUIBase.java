package me.zombie_striker.blockscripter.guis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.zombie_striker.blockscripter.MaterialNameHandler;
import me.zombie_striker.blockscripter.scripts.ScriptManager;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.ScriptAction;

public class OptionsGUIBase {

	public static ItemStack[] colorList = new ItemStack[] { MaterialNameHandler.getGlass(DyeColor.BLACK),
			MaterialNameHandler.getGlass(DyeColor.BLUE), MaterialNameHandler.getGlass(DyeColor.BROWN),
			MaterialNameHandler.getGlass(DyeColor.CYAN), MaterialNameHandler.getGlass(DyeColor.GREEN),
			MaterialNameHandler.getGlass(DyeColor.LIGHT_BLUE), MaterialNameHandler.getGlass(DyeColor.LIME),
			MaterialNameHandler.getGlass(DyeColor.MAGENTA), MaterialNameHandler.getGlass(DyeColor.ORANGE),
			MaterialNameHandler.getGlass(DyeColor.PURPLE), MaterialNameHandler.getGlass(DyeColor.PINK),
			MaterialNameHandler.getGlass(DyeColor.RED), MaterialNameHandler.getGlass(DyeColor.YELLOW) };

	public static String blockScripterTitlePrefix = "[BlockScripter]-";

	public static String previousPage = ChatColor.GOLD + "[Previous Page]";
	public static String nextPage = ChatColor.GOLD + "[Next Page]";

	public static HashMap<UUID, List<Inventory>> allGuis = new HashMap<>();

	public static int maxInvSize = 6 * 9;

	public static Inventory createGUI(UUID viewer, String inventoryName, GUISelectorType type, boolean hasNoneOption,
			Object... options) {
		if (options.length + (hasNoneOption ? 1 : 0) < maxInvSize) {
			return createSingleGUI(inventoryName, type, hasNoneOption, false, false, options);
		} else {
			Inventory first = null;
			List<Inventory> inventories = new ArrayList<>();
			int maxInv = (options.length / maxInvSize + 1);
			for (int i = 0; i < maxInv; i++) {
				Inventory inv = createSingleGUI(inventoryName, type, (hasNoneOption && first == null), first != null,
						i != (maxInv - 1), getPortionOfContents(options, i * maxInvSize, ((i + 1) * maxInvSize)));
				if (first == null)
					first = inv;
				inventories.add(inv);
			}
			allGuis.put(viewer, inventories);
			return first;
		}
	}

	public static void playerClosedGUI(UUID player) {
		if (allGuis.containsKey(player))
			allGuis.remove(player);
	}

	public static Object[] getPortionOfContents(Object[] options, int start, int end) {
		Object[] newOptions = new String[end - start];
		int endD = Math.min(options.length, end);
		for (int i = start; i < endD; i++) {
			newOptions[i - start] = options[i];
		}
		return newOptions;
	}

	public static Inventory createSingleGUI(String inventoryName, GUISelectorType type, boolean hasNoneOption,
			boolean previous, boolean next, Object... options) {
		Inventory inv = Bukkit.createInventory(null,
				getInvSize(options.length + (hasNoneOption ? 1 : 0)) + (previous || next ? 9 : 0), inventoryName);
		if (hasNoneOption) {
			ItemStack is = new ItemStack(Material.BARRIER);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(ChatColor.RED + "[NONE]");
			is.setItemMeta(im);
			inv.addItem(is);
		}
		int id = 0;
		for (Object o : options) {
			ItemStack is = null;
			
			String s = null;
			Material mat = null;
			if(o instanceof String)
				s = (String)o;
			if(o instanceof EntityType)
				s = ((EntityType)o).name();
			if(o instanceof ScriptAction) {
				s=((ScriptAction)o).getDisplayName();
				mat=((ScriptAction)o).getOverrideMaterial();
			}
			
			
			switch (type) {
			case TARGET_BLOCK:
				ScriptedBlock sb = ScriptManager.getBlockByName(s);
				is = new ItemStack(
						sb.getBlock().getType() == Material.AIR ? Material.BARRIER : sb.getBlock().getType());
				break;
			case DEFAULT_SELECTION:
				is = new ItemStack(mat!=null?new ItemStack(mat):MaterialNameHandler.getGlass(DyeColor.WHITE));
				break;
			case COLORED_GLASS:
				is = new ItemStack(colorList[id]);
				id++;
				id %= colorList.length;
				break;
			case MATERIAL_CHECK:
				is = new ItemStack(Material.matchMaterial(s));
				break;
			case BOOLEAN:
				boolean b = Boolean.parseBoolean(s);
				is = new ItemStack(b ? Material.EMERALD_BLOCK : Material.BARRIER);
				break;
			case LOCATION_OFFSET:
				is = new ItemStack(MaterialNameHandler.getMagentaClay());
				break;
			case ENTITY_TYPE:
					Material used = MaterialNameHandler.getEntityEgg((EntityType) o);
					/*switch ((EntityType)o) {
					case "AREA_EFFECT_CLOUD:
					case "ARROW":
					case "BOAT":
					case "COMPLEX_PART":
					case "DRAGON_FIREBALL":
					case "DROPPED_ITEM":
					case "EGG":
					case "FIREBALL":
					case "FIREWORK":
					case "SMALL_FIREBALL":
					case "EXPERIENCE_ORB":
					case "WITHER_SKULL":
					case "UNKNOWN":
					case "TRIDENT":
					case "SPECTRAL_ARROW":
					case "TIPPED_ARROW":
					case "SPLASH_POTION":
					case "SNOWBALL":
					case "LEASH_HITCH":
					case "FALLING_BLOCK":
					case "EVOKER_FANGS":
					case "FISHING_HOOK":
					case "ITEM_FRAME":
					case "WEATHER":
					case "LIGHTNING":
					case "LINGERING_POTION":
					case "LLAMA_SPIT":
					case "PAINTING":
					case "PLAYER":
					case "SHULKER_BULLET":
					case "PRIMED_TNT":
					case "THROWN_EXP_BOTTLE":
						break;
					case "ARMOR_STAND":
						used = Material.ARMOR_STAND;
						break;
					case "MINECART":
					case "MINECART_CHEST":
					case "MINECART_COMMAND":
					case "MINECART_FURNACE":
					case "MINECART_HOPPER":
					case "MINECART_MOB_SPAWNER":
					case "MINECART_TNT":
						used = Material.MINECART;
						break;
					case "SHULKER":
						used = Material.SHULKER_BOX;
						break;
					case "ENDER_DRAGON":
						used = Material.DRAGON_EGG;
						break;
					default:
						break;
					}*/
					if (used != null) {
						is = new ItemStack(used);
					}				
				break;
			}
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(ChatColor.RESET+s);
			is.setItemMeta(im);
			inv.addItem(is);
		}
		if (previous) {
			ItemStack prev = new ItemStack(MaterialNameHandler.getGlass(DyeColor.RED));
			ItemMeta prevm = prev.getItemMeta();
			prevm.setDisplayName(previousPage);
			prev.setItemMeta(prevm);
			inv.setItem(inv.getSize() - 1 - 1, prev);
		}
		if (next) {
			ItemStack prev = new ItemStack(MaterialNameHandler.getGlass(DyeColor.GREEN));
			ItemMeta prevm = prev.getItemMeta();
			prevm.setDisplayName(nextPage);
			prev.setItemMeta(prevm);
			inv.setItem(inv.getSize() - 1, prev);
		}
		return inv;
	}

	enum GUISelectorType {
		DEFAULT_SELECTION, COLORED_GLASS, MATERIAL_CHECK, TARGET_BLOCK, BOOLEAN, LOCATION_OFFSET, ENTITY_TYPE;
	}

	public static int getInvSize(int options) {
		return Math.min(maxInvSize, (((options - 1) / 9) + 1) * 9);
	}

}
