package me.zombie_striker.blockscripter;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.zombie_striker.blockscripter.guis.BlockScriptWindow;

public class WrenchListener implements Listener {

	private ItemStack wrench = null;
	public WrenchListener(ItemStack w) {
		this.wrench = w;
	}
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if(e.getPlayer().hasPermission("blockscripter.usewrench")){
			if(e.getItem()!=null && e.getClickedBlock()!=null && e.getClickedBlock().getType()!=Material.AIR && e.getItem().hasItemMeta()&&e.getItem().getItemMeta().hasDisplayName()&&e.getItem().getItemMeta().getDisplayName().equals(wrench.getItemMeta().getDisplayName())) {
				e.setCancelled(true);
				BlockScriptWindow.loadWindow(e.getPlayer(), e.getClickedBlock());
			}
		}
		
	}
}
