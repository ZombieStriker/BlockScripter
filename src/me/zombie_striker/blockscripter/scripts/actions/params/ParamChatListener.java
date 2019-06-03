package me.zombie_striker.blockscripter.scripts.actions.params;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.zombie_striker.blockscripter.guis.BlockScriptWindow;
import me.zombie_striker.blockscripter.guis.GUIManager;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;

public class ParamChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (GUIManager.isCheckingChat(e.getPlayer())) {
			ScriptedBlock sb = GUIManager.getScriptLookingAt(e.getPlayer());
			ScriptLine sl = GUIManager.getLineLookingAt(e.getPlayer());
			int param = GUIManager.getOptionIDLookingAt(e.getPlayer());

			if (param == -1) {
				if(!sb.setName("\""+e.getMessage().trim()+"\"")) {
					e.getPlayer().sendMessage("Another block already has this name. Choose a different name");					
				}
			} else {
				if (sl.getAction().getParameterTypes()[param - 4] == ParamTypes.NUMBER) {
					try {
						sb.updateParam(sl.getLineID(), param - 4, Double.parseDouble(e.getMessage().trim()));
					} catch (Error | Exception e4) {
						e.getPlayer().sendMessage("Invalid number. Please provide a valid number.");
					}
				} else {
					sb.updateParam(sl.getLineID(), param - 4, e.getMessage());
				}
			}
			GUIManager.removeListenerForChat(e.getPlayer());

			BlockScriptWindow.loadWindow((Player) e.getPlayer(), sb.getBlock());
			e.setCancelled(true);
		}
	}
}
