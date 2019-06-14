package me.zombie_striker.blockscripter.easygui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class EasyGUI {

	private static HashMap<String, EasyGUI> lists = new HashMap<>();

	private ItemStack[] items;
	private String title;
	private String displayname;

	private EasyGUICallable[] buttons;

	private Inventory[] loadedInventories;

	private static ItemStack BOTTOM;
	private static ItemStack BACK;
	private static ItemStack FORWARD;

	private static final String BACK_NAME = ChatColor.RED + "[Previous Page]";
	private static final String FORWARD_NAME = ChatColor.GREEN + "[Next Page]";
	private static final String BOTTOM_NAME = "";

	static {
		try {
			BOTTOM = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
			BACK = new ItemStack(Material.RED_STAINED_GLASS_PANE);
			FORWARD = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
		} catch (Error | Exception e4) {
			BOTTOM = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 0);
			BACK = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 12);
			FORWARD = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 6);
		}
		ItemMeta temp;

		temp = BOTTOM.getItemMeta();
		temp.setDisplayName(BOTTOM_NAME);
		BOTTOM.setItemMeta(temp);

		temp = BACK.getItemMeta();
		temp.setDisplayName(BACK_NAME);
		BACK.setItemMeta(temp);

		temp = FORWARD.getItemMeta();
		temp.setDisplayName(FORWARD_NAME);
		FORWARD.setItemMeta(temp);
	}

	private static boolean beenEnabled = false;

	public static void INIT(JavaPlugin plugin) {
		if (!beenEnabled)
			Bukkit.getPluginManager().registerEvents(new InventoryListener(), plugin);
		beenEnabled = true;
	}

	public static EasyGUI generateGUIIfNoneExist(ItemStack[] items, String title, boolean removeEmptySlots) {
		return generateGUIIfNoneExist(items, title,title,removeEmptySlots);
	}
	public static EasyGUI generateGUIIfNoneExist(ItemStack[] items, String title, String displayname, boolean removeEmptySlots) {
		if(lists.containsKey(title))
			return lists.get(title);
		return new EasyGUI(items,title,displayname, removeEmptySlots);
	}
	public static EasyGUI generateGUIIfNoneExist(int maxAmountAllowedItems, String title, boolean removeEmptySlots) {
		return generateGUIIfNoneExist(maxAmountAllowedItems,title,title,removeEmptySlots);
	}
	public static EasyGUI generateGUIIfNoneExist(int maxAmountAllowedItems, String title,String displayname, boolean removeEmptySlots) {
		if(lists.containsKey(title))
			return lists.get(title);
		return new EasyGUI(new ItemStack[maxAmountAllowedItems],title, displayname, removeEmptySlots);
	}

	private EasyGUI(ItemStack[] items, String title, boolean maximizeInventory) {
		this(items,title,title,maximizeInventory);
	}
	private EasyGUI(ItemStack[] items, String title, String displayname, boolean maximizeInventory) {

		this.items = items;
		buttons = new EasyGUICallable[items.length];

		this.title = title;
		this.displayname = displayname;
		lists.put(title, this);

		loadedInventories = new Inventory[((items.length / (9 * 5)+1))];
		for (int page = 0; page < (items.length / (45))+1; page++) {
			Inventory pageI = Bukkit.createInventory(null, !maximizeInventory?54: Math.min(45, items.length - (page*45))+(((items.length / (45))==0)?0:9), displayname);
			int id = page * 45;
			for (int i = 0; i < Math.min(45, items.length - id); i++) {
				pageI.setItem(i, items[id + i]);
			}
			if((items.length / (45)) > 0) {
				for (int i = 0; i < 7; i++) {
					pageI.setItem(45 + i, BOTTOM);
				}
				if (page == 0) {
					pageI.setItem(52, BOTTOM);
				} else {
					pageI.setItem(52, BACK);
				}
				if (page == (items.length / (45)) - 1) {
					pageI.setItem(53, BOTTOM);
				} else {
					pageI.setItem(53, FORWARD);
				}
			}
			loadedInventories[page] = pageI;
		}
	}

	public void updateButton(int slot, ItemStack newItem) {
		updateButton(slot, newItem, buttons[slot]);
	}

	public void updateButton(int slot, ItemStack newItem, EasyGUICallable newCallable) {
		int page = slot / 45;
		int i = slot - (page * 45);

		loadedInventories[page].setItem(i, newItem);
		items[slot]=newItem;
		buttons[slot] = newCallable;
	}
	public void updateAllCallables(EasyGUICallable... callables){
		int i = 0;
		for(EasyGUICallable c : callables){
			buttons[i] = c;
			i++;
		}
	}

	public static EasyGUI getEasyGUIByName(String title) {
		return lists.get(title);
	}

	public static EasyGUI getEasyGUIByInventory(Inventory inventory) {
		for (EasyGUI gui : lists.values()) {
			for (Inventory i : gui.loadedInventories)
				if (i.equals(inventory))
					return gui;
		}
		return null;
	}

	public int getPageIDFromInventory(Inventory inventory) {
		for (int i = 0; i < loadedInventories.length; i++)
			if (loadedInventories[i].equals(inventory))
				return i;
		return -1;
	}

	public void registerButton(EasyGUICallable code, int itemSlot) {
		buttons[itemSlot] = code;
	}

	public Inventory getPageByID(int id) {
		return loadedInventories[id];
	}

	static class InventoryListener implements Listener {

		@EventHandler
		public void onClick(InventoryClickEvent e) {
			EasyGUI gui = null;
			if ((gui = EasyGUI.getEasyGUIByInventory(e.getClickedInventory())) != null) {
				e.setCancelled(true);
				if (e.getClick().isShiftClick()) {
					e.setCancelled(true);
				}
				if (e.getCurrentItem() != null) {
					if (e.getSlot() >= 45) {
						e.setCancelled(true);
						if (e.getCurrentItem().isSimilar(BOTTOM))
							return;
						if (e.getSlot() == 52) {
							//back
							int page = gui.getPageIDFromInventory(e.getClickedInventory());
							if (page > 0) {
								e.getWhoClicked().closeInventory();
								e.getWhoClicked().openInventory(gui.getPageByID(page - 1));
							}
						} else if (e.getSlot() == 53) {
							//back
							int page = gui.getPageIDFromInventory(e.getClickedInventory());
							if (page < gui.loadedInventories.length - 1) {
								e.getWhoClicked().closeInventory();
								e.getWhoClicked().openInventory(gui.getPageByID(page + 1));
							}
						}
						return;
					}
					ClickData data = new ClickData(e, gui);
					if (gui.buttons[e.getSlot()] != null)
						gui.buttons[e.getSlot()].call(data);
				}
			}
		}
	}
}
