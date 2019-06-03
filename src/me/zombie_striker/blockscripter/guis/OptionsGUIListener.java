package me.zombie_striker.blockscripter.guis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.zombie_striker.blockscripter.guis.OptionsGUIBase.GUISelectorType;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptManager;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.ScriptAction;
import me.zombie_striker.blockscripter.scripts.targets.*;
import me.zombie_striker.blockscripter.scripts.triggers.ScriptTrigger;

public class OptionsGUIListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getView().getTitle().startsWith(OptionsGUIBase.blockScripterTitlePrefix)) {
			e.setCancelled(true);
			if (e.getClickedInventory() == e.getInventory()) {
				if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
					ScriptedBlock bs = GUIManager.getScriptLookingAt((Player) e.getWhoClicked());
					ScriptLine sl = GUIManager.getLineLookingAt((Player) e.getWhoClicked());
					int id = GUIManager.getOptionIDLookingAt((Player) e.getWhoClicked());
					String option = e.getCurrentItem().getItemMeta().getDisplayName();
					if (option.equals(OptionsGUIBase.previousPage)) {
						List<Inventory> pages = OptionsGUIBase.allGuis.get(e.getWhoClicked().getUniqueId());
						int currentpage = pages.indexOf(e.getInventory());
						Inventory previous = pages.get(currentpage - 1);
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().openInventory(previous);
						return;
					}
					if (option.equals(OptionsGUIBase.nextPage)) {
						List<Inventory> pages = OptionsGUIBase.allGuis.get(e.getWhoClicked().getUniqueId());
						int currentpage = pages.indexOf(e.getInventory());
						Inventory next = pages.get(currentpage + 1);
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().openInventory(next);
						return;
					}
					if (option.equals(ChatColor.RED + "[NONE]")) {
						if (id == 1) {
							bs.updateTriggers(sl.getLineID(), null);
						} else if (id == 2) {
							bs.updateTarget(sl.getLineID(), null);
						} else if (id == 3) {
							bs.updateEffect(sl.getLineID(), null);
						} else if (id > 3) {
							bs.updateParam(sl.getLineID(), id - 4, null);
						}
						e.getWhoClicked().closeInventory();
						BlockScriptWindow.loadWindow((Player) e.getWhoClicked(), bs.getBlock());
						OptionsGUIBase.playerClosedGUI(e.getWhoClicked().getUniqueId());
						return;
					}
					if (id == 1) {
						ScriptTrigger st = ScriptManager.getTriggerByDisplayName(option);
						bs.updateTriggers(sl.getLineID(), st);
						e.getWhoClicked().closeInventory();
					} else if (id == 2) {
						ScriptTarget st = ScriptManager.getTargetByDisplayName(option);
						bs.updateTarget(sl.getLineID(), st);
						if (st instanceof TargetSpecificBlock) {

							List<String> onActive = new ArrayList<String>();
							for (ScriptedBlock s : ScriptManager.blocks) {
								onActive.add(s.getName());
							}
							Collections.sort(onActive);
							Inventory activations = OptionsGUIBase.createGUI(e.getWhoClicked().getUniqueId(),
									OptionsGUIBase.blockScripterTitlePrefix + "getTargetedBlock",
									GUISelectorType.TARGET_BLOCK, true, onActive.toArray());
							e.getWhoClicked().closeInventory();
							e.getWhoClicked().openInventory(activations);

							GUIManager.setLinesLookingAt((Player) e.getWhoClicked(), sl);
							GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), -1);
							OptionsGUIBase.playerClosedGUI(e.getWhoClicked().getUniqueId());
							return;							
						} else {
							e.getWhoClicked().closeInventory();
						}
					} else if (id == 3) {
						ScriptAction st = ScriptManager.getActionByDisplayName(option);
						bs.updateEffect(sl.getLineID(), st);
						e.getWhoClicked().closeInventory();
					} else if (id >= 4) {
						switch (sl.getAction().getParameterTypes()[id - 4]) {
						case SPECIFIC_BLOCK:
							bs.updateParam(sl.getLineID(), id - 4, ScriptManager.getBlockByName(option));
							e.getWhoClicked().closeInventory();
							break;
						case BOOLEAN:
							bs.updateParam(sl.getLineID(), id - 4, Boolean.parseBoolean(option));
							e.getWhoClicked().closeInventory();
							break;
						case MATERIAL:
							bs.updateParam(sl.getLineID(), id - 4, e.getCurrentItem().getType());
							e.getWhoClicked().closeInventory();
							break;
						case SOUND_NAMES:
							bs.updateParam(sl.getLineID(), id - 4, e.getCurrentItem().getItemMeta().getDisplayName());
							e.getWhoClicked().closeInventory();
							break;
						case ENTITYTYPE:
							bs.updateParam(sl.getLineID(), id - 4, EntityType.valueOf(e.getCurrentItem().getItemMeta().getDisplayName()));
							e.getWhoClicked().closeInventory();
							break;
						case BLOCKFACE_OFFSET:
							bs.updateParam(sl.getLineID(), id - 4, BlockFace.valueOf(e.getCurrentItem().getItemMeta().getDisplayName()));
							e.getWhoClicked().closeInventory();
							break;
							//Invalid types, that may need to be checked still
						case NUMBER:
						case STRING:
							break;
						}
					} else if (id < 0) {
						if (id == -1) {
							bs.updateTargetSpecific(sl.getLineID(), ScriptManager.getBlockByName(option));
							e.getWhoClicked().closeInventory();
						}
					}

					BlockScriptWindow.loadWindow((Player) e.getWhoClicked(), bs.getBlock());
					OptionsGUIBase.playerClosedGUI(e.getWhoClicked().getUniqueId());
				}
			}
		}
	}
}
