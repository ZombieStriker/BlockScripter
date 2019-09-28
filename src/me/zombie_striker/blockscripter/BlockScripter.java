package me.zombie_striker.blockscripter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.zombie_striker.blockscripter.config.BlockScriptConfigManager;
import me.zombie_striker.blockscripter.events.InteractListener;
import me.zombie_striker.blockscripter.guis.BlockScriptWindowListener;
import me.zombie_striker.blockscripter.guis.OptionsGUIListener;
import me.zombie_striker.blockscripter.guis.PromptGUI;
import me.zombie_striker.blockscripter.guis.PromptGUIListener;
import me.zombie_striker.blockscripter.scripts.ScriptManager;
import me.zombie_striker.blockscripter.scripts.actions.*;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamChatListener;
import me.zombie_striker.blockscripter.scripts.targets.*;
import me.zombie_striker.blockscripter.scripts.triggers.*;

public class BlockScripter extends JavaPlugin {

	public ItemStack WRENCH;

	private static final String SERVER_VERSION;
	static {
		String name = Bukkit.getServer().getClass().getName();
		name = name.substring(name.indexOf("craftbukkit.") + "craftbukkit.".length());
		name = name.substring(0, name.indexOf("."));
		SERVER_VERSION = name;
	}

	private static BlockScripter main = null;

	public static void bypassPermissionCommand(Player player, String command, String permission){
		PermissionAttachment att = null;
		if(!player.hasPermission(permission)){
			att = player.addAttachment(main,0);
			att.setPermission(permission,true);
		}
		Bukkit.dispatchCommand(player,command);
		if(att!=null){
			player.removeAttachment(att);
		}
	}
	public static boolean isVersionHigherThan(int mainVersion, int secondVersion) {
		if (secondVersion >= 9 && Bukkit.getPluginManager().isPluginEnabled("ViaRewind"))
			return false;
		String firstChar = SERVER_VERSION.substring(1, 2);
		int fInt = Integer.parseInt(firstChar);
		if (fInt < mainVersion)
			return false;
		StringBuilder secondChar = new StringBuilder();
		for (int i = 3; i < 10; i++) {
			if (SERVER_VERSION.charAt(i) == '_' || SERVER_VERSION.charAt(i) == '.')
				break;
			secondChar.append(SERVER_VERSION.charAt(i));
		}

		int sInt = Integer.parseInt(secondChar.toString());
		if (sInt < secondVersion)
			return false;
		return true;
	}

	public void onEnable() {
main = this;
		// Creates the config.yml
		saveConfig();
		if (!getConfig().contains("Wrench.Material")) {
			getConfig().set("Wrench.Material", MaterialNameHandler.getGoldAxe().getType().name());
			getConfig().set("Wrench.DisplayName", "&6BlockScripter Wrench");
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GOLD + "How to use the Wrench");
			lore.add(ChatColor.GRAY + "---------------------");
			lore.add(ChatColor.GRAY + "Right-Click any object to bring up the it's menu");
			lore.add(ChatColor.GRAY + "---------------------");
			getConfig().set("Wrench.Lore", lore);
			saveConfig();
		}
		if (!getConfig().contains("Prompt.Accept.Material")) {
			getConfig().set("Prompt.Accept.Material", Material.EMERALD_BLOCK.name());
			getConfig().set("Prompt.Accept.DisplayName", "&2Accept");
			saveConfig();
		}
		if (!getConfig().contains("Prompt.Reject.Material")) {
			getConfig().set("Prompt.Reject.Material", Material.BARRIER.name());
			getConfig().set("Prompt.Reject.DisplayName", "&4Reject");
			saveConfig();
		}

		// Creates the Wrench Instance
		WRENCH = new ItemStack(Material.valueOf(getConfig().getString("Wrench.Material")));
		ItemMeta im = WRENCH.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Wrench.DisplayName")));
		im.setLore(getConfig().getStringList("Wrench.Lore"));
		WRENCH.setItemMeta(im);

		PromptGUI.accept = new ItemStack(Material.valueOf(getConfig().getString("Prompt.Accept.Material")));
		ItemMeta im2 = PromptGUI.accept.getItemMeta();
		im2.setDisplayName(
				ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prompt.Accept.DisplayName")));
		PromptGUI.accept.setItemMeta(im2);

		PromptGUI.reject = new ItemStack(Material.valueOf(getConfig().getString("Prompt.Reject.Material")));
		ItemMeta im3 = PromptGUI.reject.getItemMeta();
		im3.setDisplayName(
				ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prompt.Reject.DisplayName")));
		PromptGUI.reject.setItemMeta(im3);

		// Registers the Wrench Listener
		Bukkit.getPluginManager().registerEvents(new WrenchListener(WRENCH), this);

		Bukkit.getPluginManager().registerEvents(new BlockScriptWindowListener(), this);
		Bukkit.getPluginManager().registerEvents(new OptionsGUIListener(), this);

		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);

		Bukkit.getPluginManager().registerEvents(new ParamChatListener(), this);

		Bukkit.getPluginManager().registerEvents(new PromptGUIListener(), this);

		ScriptManager.registerTrigger(new TriggerOnPlayerInteract("onPlayerInteract"));
		ScriptManager.registerTrigger(new TriggerOnRightClick("onRightClick"));
		ScriptManager.registerTrigger(new TriggerOnLeftClick("onLeftClick"));
		ScriptManager.registerTrigger(new TriggerOnRelay("onRelay"));
		ScriptManager.registerTrigger(new TriggerOnPlayerWalkOn("onPlayerWalkOn"));
		ScriptManager.registerTrigger(new TriggerOnBlockBreak("onBlockBreak"));
		ScriptManager.registerTrigger(new TriggerOnRedstonePowered("onRedstonePowered"));
		ScriptManager.registerTrigger(new TriggerOnRedstonePowered("onRedstoneDepowered"));
		ScriptManager.registerTrigger(new TriggerOnProjectileHit("onProjectileHit"));
		ScriptManager.registerTrigger(new TriggerOnPromptAccept("onPromptAccept"));
		ScriptManager.registerTrigger(new TriggerOnPromptReject("onPromptReject"));
		ScriptManager.registerTrigger(new TriggerOnBlockPlaceHere("onPlaceBlockHere"));

		ScriptManager.registerTarget(new TargetBlock("SELF", TargetType.BLOCK));
		ScriptManager.registerTarget(new TargetPlayer("Player", TargetType.ENTITY));
		ScriptManager.registerTarget(new TargetNearbyPlayer("ClosestPlayer", TargetType.ENTITY));
		ScriptManager.registerTarget(new TargetNearbyPlayer("FurthestPlayer", TargetType.ENTITY));
		ScriptManager.registerTarget(new TargetEvent("Event", TargetType.CANCELABLE_EVENT));
		ScriptManager.registerTarget(new TargetProjectile("Projectile", TargetType.PROJECTILE));
		ScriptManager.registerTarget(new TargetSpecificBlock("<Specific_Block>", TargetType.BLOCK));

		ScriptManager.registerAction(new ActionPlaySound("playSound"));
		ScriptManager.registerAction(new ActionBreakBlock("breakBlock"));
		ScriptManager.registerAction(new ActionSetBlock("setMaterial"));
		// ScriptManager.registerAction(new
		// ActionBroadcastHelloWorld("broadcastHelloWorld"));
		ScriptManager.registerAction(new ActionBroadcastMessage("broadcastMessage"));
		ScriptManager.registerAction(new ActionSetVelocity("setVelocity"));
		ScriptManager.registerAction(new ActionFireRelayOneTick("fireRelay_instant", this));
		ScriptManager.registerAction(new ActionFireRelayOneSecond("fireRelay_1_Second_Delay", this));
		ScriptManager.registerAction(new ActionSendMessage("sendMessage"));
		ScriptManager.registerAction(new ActionSendMessageNearby("sendMessageNearby"));
		ScriptManager.registerAction(new ActionCancelEvent("cancelEvent"));
		ScriptManager.registerAction(new ActionTeleportTo("teleportTo"));
		ScriptManager.registerAction(new ActionToggleLineActive("toggleActiveLines"));
		ScriptManager.registerAction(new ActionAddHealth("addHealth"));
		ScriptManager.registerAction(new ActionSetHealth("setHealth"));
		ScriptManager.registerAction(new ActionSetHunger("setHunger"));
		ScriptManager.registerAction(new ActionSendTitleMessage("sendTitle"));
		ScriptManager.registerAction(new ActionSetFireTicks("setFireTicks"));

		ScriptManager.registerAction(new ActionShootArrow("shootArrow"));

		ScriptManager.registerAction(new ActionSpawnItem("spawnItem"));
		ScriptManager.registerAction(new ActionSpawnColoredParticle("spawnColoredParticle"));
		ScriptManager.registerAction(new ActionSpawnEntity("spawnEntity"));

		// ScriptManager.registerAction(new ActionProjectileBounce("bounce"));

		ScriptManager.registerAction(new ActionOpenPrompt("openPrompt"));

		ScriptManager.registerAction(new ActionSendCommandAsConsole("sendCommandAsConsole"));
		ScriptManager.registerAction(new ActionSendCommandAsPlayer("sendCommandAsPlayer"));
		ScriptManager.registerAction(new ActionSendCommandAsPlayerBypassPerm("sendCommandAsPlayerBypassPermission"));
		BlockScriptConfigManager.init(this);
		new BukkitRunnable() {

			@Override
			public void run() {
				BlockScriptConfigManager.loadBlockScripts();
			}
		}.runTaskLater(this, 0);
	}

	@Override
	public void onDisable() {
		BlockScriptConfigManager.saveBlockScripts();
	}

	private void a(List<String> tabs, String checkFor, String arg) {
		if (checkFor.toLowerCase().startsWith(arg.toLowerCase())) {
			tabs.add(checkFor);
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> tabs = new ArrayList<>();
		if (args.length == 1) {
			a(tabs, "help", args[0]);
			a(tabs, "giveWrench", args[0]);
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("giveWrench"))
				return null;
		}
		return tabs;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0 || args[0].equals("help")) {
			sender.sendMessage(ChatColor.GRAY + " TODO: MAKE official");
			sender.sendMessage("/BlockScripter help");
			sender.sendMessage("/BlockScripter giveWrench <player>");
			return true;
		}
		if (args[0].equalsIgnoreCase("givewrench")) {
			if (!sender.hasPermission("blockscripter.givewrench")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			Player who = null;
			if (args.length == 1) {
				who = (Player) sender;
			} else {
				who = Bukkit.getPlayer(args[1]);
			}
			if (who == null) {
				sender.sendMessage("The player " + args[1] + " is not online");
				return true;
			}
			who.getInventory().addItem(WRENCH);
			sender.sendMessage("Sending " + who.getName() + " a wrench");
		}
		return true;
	}
}
