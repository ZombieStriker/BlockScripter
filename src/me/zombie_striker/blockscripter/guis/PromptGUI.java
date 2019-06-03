package me.zombie_striker.blockscripter.guis;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;

public class PromptGUI {

	public static ItemStack accept;
	public static ItemStack reject;

	public static List<PromptGUI> guis = new ArrayList<>();

	private Inventory inv;
	private ScriptedBlock sb;
	private boolean response;
	private Player player;

	public PromptGUI(ScriptedBlock sb, Player player, String title) {
		this.sb = sb;
		inv = Bukkit.createInventory(null, 9, title);
		inv.setItem(1, accept);
		inv.setItem(7, reject);
		guis.add(this);
		this.player = player;
		this.player.openInventory(inv);
	}
	public ScriptedBlock getBlock() {
		return sb;
	}
	public boolean getResponse() {
		return response;
	}
	public Inventory getInventory() {
		return inv;
	}
	public void callResponse(boolean accepted){
		response = accepted;
		ScriptableTargetHolder sth = new ScriptableTargetHolder();
		sth.setSelfBlock(sb.getBlock());
		sth.setLocation(sb.getBlock().getLocation());
		sth.setEntity(player);
		sb.activate(this, sth);
	}

}
