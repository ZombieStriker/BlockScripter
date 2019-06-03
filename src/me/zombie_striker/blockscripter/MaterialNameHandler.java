package me.zombie_striker.blockscripter;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MaterialNameHandler {
	private static ItemStack goldAxe;
	private static ItemStack purplecaly;

	@SuppressWarnings("deprecation")
	public static ItemStack getGlass(DyeColor d) {
		ItemStack returns;
		try {
			returns = new ItemStack(Material.matchMaterial(d.name() + "_STAINED_GLASS_PANE"));
		} catch (Error | Exception e5) {
			returns = new ItemStack(Material.matchMaterial("STAINED_GLASS_PANE"), 1, (short) d.getDyeData());
		}
		return returns;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getSilverGlass() {
		ItemStack returns;
		try {
			returns = new ItemStack(Material.matchMaterial("LIGHT_GRAY_STAINED_GLASS_PANE"));
		} catch (Error | Exception e5) {
			returns = new ItemStack(Material.matchMaterial("STAINED_GLASS_PANE"), 1, (short) 8);
		}
		return returns;
	}

	public static ItemStack getGoldAxe() {
		ItemStack returns = goldAxe;
		if (returns != null)
			return returns;
		try {
			returns = new ItemStack(Material.matchMaterial("GOLDEN_AXE"));
		} catch (Error | Exception e5) {
			returns = new ItemStack(Material.matchMaterial("GOLD_AXE"));
		}
		goldAxe = returns;
		return returns;
	}

	public static ItemStack getMagentaClay() {
		ItemStack returns = purplecaly;
		if (returns != null)
			return returns;
		try {
			returns = new ItemStack(Material.matchMaterial("MAGENTA_GLAZED_TERRACOTTA"));
		} catch (Error | Exception e5) {
			returns = new ItemStack(Material.matchMaterial("STAINED_GLASS_PANE"));
		}
		purplecaly = returns;
		return returns;
	}

	public static Material getEntityEgg(EntityType t) {
		if (t != null) {
			switch (t.name()) {
			case "COMPLEX_PART":
			case "TIPPED_ARROW":
			case "WEATHER":
			case "LINGERING_POTION":
				return null;
			default:
				break;
			}
		}
		switch (t) {
		case AREA_EFFECT_CLOUD:
		case ARROW:
		case BOAT:
		case DRAGON_FIREBALL:
		case DROPPED_ITEM:
		case EGG:
		case FIREBALL:
		case FIREWORK:
		case SMALL_FIREBALL:
		case EXPERIENCE_ORB:
		case WITHER_SKULL:
		case UNKNOWN:
		case TRIDENT:
		case SPECTRAL_ARROW:
		case SPLASH_POTION:
		case SNOWBALL:
		case LEASH_HITCH:
		case FALLING_BLOCK:
		case EVOKER_FANGS:
		case FISHING_HOOK:
		case ITEM_FRAME:
		case LIGHTNING:
		case LLAMA_SPIT:
		case PAINTING:
		case PLAYER:
		case SHULKER_BULLET:
		case PRIMED_TNT:
		case THROWN_EXP_BOTTLE:
			return null;
		case ARMOR_STAND:
			return Material.ARMOR_STAND;
		case MINECART:
		case MINECART_CHEST:
		case MINECART_COMMAND:
		case MINECART_FURNACE:
		case MINECART_HOPPER:
		case MINECART_MOB_SPAWNER:
		case MINECART_TNT:
			return Material.MINECART;
		case SHULKER:
			return Material.SHULKER_BOX;
		case ENDER_DRAGON:
			return Material.DRAGON_EGG;
		default:
			try {
				return Material.matchMaterial(t.name() + "_SPAWN_EGG");
			} catch (Error | Exception e4) {
				return Material.matchMaterial("MONSTER_EGG");
			}
		}
	}
}
