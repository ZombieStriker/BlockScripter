package me.zombie_striker.blockscripter.guis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;

public class GUIManager {

	private static HashMap<UUID, ScriptedBlock> scriptBlocks = new HashMap<>();
	private static HashMap<UUID, ScriptLine> scriptLines = new HashMap<>();
	private static HashMap<UUID, Integer> scriptOption = new HashMap<>();

	private static List<UUID> checkForChat = new ArrayList<>();

	public static void playerCloseGUI(Player player) {
		scriptBlocks.remove(player.getUniqueId());
		scriptLines.remove(player.getUniqueId());
		scriptOption.remove(player.getUniqueId());
	}

	public static ScriptedBlock getScriptLookingAt(Player player) {
		return scriptBlocks.get(player.getUniqueId());
	}

	public static ScriptLine getLineLookingAt(Player player) {
		return scriptLines.get(player.getUniqueId());
	}

	public static Integer getOptionIDLookingAt(Player player) {
		return scriptOption.get(player.getUniqueId());
	}

	public static void setScriptLookingAt(Player player, ScriptedBlock sb) {
		scriptBlocks.put(player.getUniqueId(), sb);
	}

	public static void setLinesLookingAt(Player player, ScriptLine sb) {
		scriptLines.put(player.getUniqueId(), sb);
	}

	public static void setOptionLookingAt(Player player, Integer sb) {
		scriptOption.put(player.getUniqueId(), sb);
	}

	public static void addListenerForChat(Player player) {
		checkForChat.add(player.getUniqueId());
	}

	public static boolean isCheckingChat(Player player) {
		return checkForChat.contains(player.getUniqueId());
	}

	public static void removeListenerForChat(Player player) {
		checkForChat.remove(player.getUniqueId());
	}
}
