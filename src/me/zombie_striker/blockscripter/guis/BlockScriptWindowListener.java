package me.zombie_striker.blockscripter.guis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.zombie_striker.blockscripter.MaterialNameHandler;
import me.zombie_striker.blockscripter.guis.OptionsGUIBase.GUISelectorType;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptManager;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import me.zombie_striker.blockscripter.scripts.actions.ScriptAction;
import me.zombie_striker.blockscripter.scripts.actions.params.ParamTypes;
import me.zombie_striker.blockscripter.scripts.targets.ScriptTarget;
import me.zombie_striker.blockscripter.scripts.triggers.ScriptTrigger;

public class BlockScriptWindowListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getView().getTitle().startsWith(BlockScriptWindow.blockScripterTitleOvereviewPrefix)) {
			e.setCancelled(true);
			if (e.getClickedInventory() == e.getInventory()) {
				int slot = e.getSlot();
				int line = (slot) / 9;
				int row = slot % 9;
				ScriptedBlock sb = GUIManager.getScriptLookingAt((Player) e.getWhoClicked());

				if (e.getSlot() == 0) {
					GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), -1);
					e.getWhoClicked().closeInventory();
					GUIManager.addListenerForChat((Player) e.getWhoClicked());
					e.getWhoClicked().sendMessage("Type in the new name for this block.");
					return;
				}

				ScriptLine sl = sb.getScriptLine(line);
				if (row == 0) {
					sl.setActive(!sl.isActive());
					e.getInventory().setItem(((sl.getLineID()) * 9), BlockScriptWindow.createItem(
							sl.isActive() ? MaterialNameHandler.getGlass(DyeColor.WHITE)
									: new ItemStack(Material.BARRIER),
							sl.getLineID(), "&fLine : " + sl.getLineID(),
							"&fCurrently: " + (sl.isActive() ? "&a Active" : "&8 Deactivated"),
							(sl.isActive() ? "&fClick this to deactive this line."
									: "&fClick this to activate this line.")));
				} else if (row == 1) {
					List<String> onActive = new ArrayList<String>();
					for (ScriptTrigger s : ScriptManager.triggers) {
						onActive.add(s.getDisplayName());
					}
					Collections.sort(onActive);
					Inventory activations = OptionsGUIBase.createGUI(e.getWhoClicked().getUniqueId(),
							OptionsGUIBase.blockScripterTitlePrefix + "setTrigger", GUISelectorType.DEFAULT_SELECTION,
							true, onActive.toArray());
					e.getWhoClicked().closeInventory();
					e.getWhoClicked().openInventory(activations);

					GUIManager.setLinesLookingAt((Player) e.getWhoClicked(), sb.getScriptLine(line));
					GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), row);
				} else if (row == 2) {
					if (sl.getTrigger() != null) {
						List<String> onActive = new ArrayList<String>();
						for (ScriptTarget s : ScriptManager.targets) {
							if (sl.getTrigger().hasTargetType(s.getTargetType())) {
								onActive.add(s.getDisplayName());
							}
						}
						Collections.sort(onActive);
						Inventory activations = OptionsGUIBase.createGUI(e.getWhoClicked().getUniqueId(),
								OptionsGUIBase.blockScripterTitlePrefix + "setTarget",
								GUISelectorType.DEFAULT_SELECTION, true, onActive.toArray());
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().openInventory(activations);

						GUIManager.setLinesLookingAt((Player) e.getWhoClicked(), sb.getScriptLine(line));
						GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), row);
					}
				} else if (row == 3) {
					if (sl.getTarget() != null) {
						List<ScriptAction> onActive = new ArrayList<ScriptAction>();
						for (ScriptAction s : ScriptManager.actions) {
							if (s.getAcceptedRequirementObjects().contains(sl.getTarget().getTargetType())) {
								onActive.add(s);
							}
						}
						Collections.sort(onActive);
						Inventory activations = OptionsGUIBase.createGUI(e.getWhoClicked().getUniqueId(),
								OptionsGUIBase.blockScripterTitlePrefix + "setAction",
								GUISelectorType.DEFAULT_SELECTION, true, onActive.toArray());
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().openInventory(activations);

						GUIManager.setLinesLookingAt((Player) e.getWhoClicked(), sb.getScriptLine(line));
						GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), row);
					}
				} else {
					if (sl.getAction() != null && row < 4 + sl.getAction().getParameterAmount()) {
						ParamTypes type = sl.getAction().getParameterTypes()[row - 4];
						GUIManager.setLinesLookingAt((Player) e.getWhoClicked(), sb.getScriptLine(line));
						GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), row);
						switch (type) {
						case STRING:
							e.getWhoClicked().closeInventory();
							GUIManager.addListenerForChat((Player) e.getWhoClicked());
							e.getWhoClicked().sendMessage("Input the message into chat.");
							break;
						case NUMBER:
							e.getWhoClicked().closeInventory();
							GUIManager.addListenerForChat((Player) e.getWhoClicked());
							e.getWhoClicked().sendMessage("Input the number into chat.");
							break;
						case BOOLEAN:
							List<String> onActiveBool = new ArrayList<String>();
							onActiveBool.add("true");
							onActiveBool.add("false");

							Inventory activationsBool = OptionsGUIBase.createGUI(e.getWhoClicked().getUniqueId(),
									OptionsGUIBase.blockScripterTitlePrefix + "getBoolean", GUISelectorType.BOOLEAN,
									true, onActiveBool.toArray());
							e.getWhoClicked().closeInventory();
							e.getWhoClicked().openInventory(activationsBool);

							GUIManager.setLinesLookingAt((Player) e.getWhoClicked(), sl);
							GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), row);
							break;
						case SPECIFIC_BLOCK:
							List<String> onActiveS = new ArrayList<String>();
							for (ScriptedBlock s : ScriptManager.blocks) {
								onActiveS.add(s.getName());
							}
							Collections.sort(onActiveS);
							Inventory activationsS = OptionsGUIBase.createGUI(e.getWhoClicked().getUniqueId(),
									OptionsGUIBase.blockScripterTitlePrefix + "getTargetedBlock",
									GUISelectorType.TARGET_BLOCK, true, onActiveS.toArray());
							e.getWhoClicked().closeInventory();
							e.getWhoClicked().openInventory(activationsS);

							GUIManager.setLinesLookingAt((Player) e.getWhoClicked(), sl);
							GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), row);
							break;
						case MATERIAL:
							List<String> onActive = new ArrayList<String>();
							for (Material m : Material.values()) {
								boolean isItem = true;
								try {
									isItem = m.isItem();
								} catch (Error | Exception e2) {
								}
								if (isItem && m.isBlock() && m != Material.AIR) {
									if (m.name().endsWith("_BED") || m.name().endsWith("_DOOR")
											|| m.name().endsWith("_FENCE") || m.name().endsWith("_FENCE_GATE")
											|| m.name().endsWith("_BANNER"))
										continue;
									onActive.add(m.name());
								}
							}
							Collections.sort(onActive);
							Inventory activations = OptionsGUIBase.createGUI(e.getWhoClicked().getUniqueId(),
									OptionsGUIBase.blockScripterTitlePrefix + "getMaterial",
									GUISelectorType.MATERIAL_CHECK, false, onActive.toArray());
							e.getWhoClicked().closeInventory();
							e.getWhoClicked().openInventory(activations);

							break;
						case ENTITYTYPE:
							List<EntityType> onActiveEntityType = new ArrayList<EntityType>();
							for (EntityType bf : EntityType.values()) {
								if (MaterialNameHandler.getEntityEgg(bf) != null)
									onActiveEntityType.add(bf);
							}
							Collections.sort(onActiveEntityType);
							Inventory activationsEntityType = OptionsGUIBase.createGUI(e.getWhoClicked().getUniqueId(),
									OptionsGUIBase.blockScripterTitlePrefix + "getLocationOffset",
									GUISelectorType.ENTITY_TYPE, true, onActiveEntityType.toArray());
							e.getWhoClicked().closeInventory();
							e.getWhoClicked().openInventory(activationsEntityType);

							GUIManager.setLinesLookingAt((Player) e.getWhoClicked(), sl);
							GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), row);
							break;
						case BLOCKFACE_OFFSET:
							List<String> onActiveLOCOFF = new ArrayList<String>();
							for (BlockFace bf : new BlockFace[] { BlockFace.UP, BlockFace.DOWN, BlockFace.EAST,
									BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH }) {
								onActiveLOCOFF.add(bf.name());
							}
							Collections.sort(onActiveLOCOFF);
							Inventory activationsLOOCOFF = OptionsGUIBase.createGUI(e.getWhoClicked().getUniqueId(),
									OptionsGUIBase.blockScripterTitlePrefix + "getLocationOffset",
									GUISelectorType.LOCATION_OFFSET, true, onActiveLOCOFF.toArray());
							e.getWhoClicked().closeInventory();
							e.getWhoClicked().openInventory(activationsLOOCOFF);

							GUIManager.setLinesLookingAt((Player) e.getWhoClicked(), sl);
							GUIManager.setOptionLookingAt((Player) e.getWhoClicked(), row);
							break;

						case SOUND_NAMES:
							break;
						}
					}
				}
			}
		}
	}

}
