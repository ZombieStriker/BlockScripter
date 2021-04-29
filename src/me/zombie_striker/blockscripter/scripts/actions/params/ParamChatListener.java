package me.zombie_striker.blockscripter.scripts.actions.params;

import me.zombie_striker.blockscripter.BlockScripter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.zombie_striker.blockscripter.guis.BlockScriptWindow;
import me.zombie_striker.blockscripter.guis.GUIManager;
import me.zombie_striker.blockscripter.scripts.ScriptLine;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ParamChatListener implements Listener {

	private JavaPlugin main;

	public ParamChatListener(JavaPlugin main){
		this.main = main;
	}

	@EventHandler
	public void onChat(final AsyncPlayerChatEvent e) {
		if (GUIManager.isCheckingChat(e.getPlayer())) {
			final ScriptedBlock sb = GUIManager.getScriptLookingAt(e.getPlayer());
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

			new BukkitRunnable() {
				public void run(){
			BlockScriptWindow.loadWindow((Player)e.getPlayer(),sb.getBlock());
			}}.runTaskLater(main,0);
			e.setCancelled(true);
		}
	}
}
