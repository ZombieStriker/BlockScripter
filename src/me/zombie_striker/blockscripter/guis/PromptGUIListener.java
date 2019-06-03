package me.zombie_striker.blockscripter.guis;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PromptGUIListener implements Listener{
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		for(PromptGUI gui : PromptGUI.guis) {
			if(gui.getInventory().equals(e.getClickedInventory())) {
				if(e.getCurrentItem()!=null) {
					if(e.getCurrentItem().isSimilar(PromptGUI.accept)) {
						gui.callResponse(true);
					}else if(e.getCurrentItem().isSimilar(PromptGUI.reject))
						gui.callResponse(false);
					e.getWhoClicked().closeInventory();
					break;
				}
			}
		}
	}

}
