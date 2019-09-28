package me.zombie_striker.blockscripter.events;

import me.zombie_striker.blockscripter.ScriptableTargetHolder;
import me.zombie_striker.blockscripter.scripts.ScriptManager;
import me.zombie_striker.blockscripter.scripts.ScriptedBlock;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public class InteractListener implements Listener {

	@EventHandler
	public void onWalk(PlayerMoveEvent e) {
		if (!e.getTo().getBlock().getLocation().equals(e.getPlayer().getLocation().getBlock().getLocation())) {
			Location temp = e.getTo().clone().subtract(0, 0.01, 0).getBlock().getLocation();
			for (ScriptedBlock sc : ScriptManager.blocks) {
				if (sc.getBlock().getWorld() == e.getTo().getWorld()) {
					if (temp.equals(sc.getBlock().getLocation())) {
						ScriptableTargetHolder sth = new ScriptableTargetHolder();
						sth.setSelfBlock(sc);
						sth.setLocation(sc.getBlock().getLocation());
						sth.setEntity(e.getPlayer());
						sth.setEvent(e);
						sc.activate(e, sth);
						break;
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent e) {
		for (ScriptedBlock sc : ScriptManager.blocks) {
			if (sc.getBlock().getWorld() == e.getBlock().getWorld()) {
				if (e.getBlock().getLocation().equals(sc.getBlock().getLocation())) {
					ScriptableTargetHolder sth = new ScriptableTargetHolder();
					sth.setSelfBlock(sc);
					sth.setLocation(sc.getBlock().getLocation());
					sth.setEntity(e.getPlayer());
					sth.setEvent(e);
					sc.activate(e, sth);
					break;
				}
			}
		}
	}

	@EventHandler
	public void onProjHit(ProjectileHitEvent e) {
		try {
			if (e.getHitBlock() != null && e.getEntity().getShooter() instanceof Player) {
				for (ScriptedBlock sc : ScriptManager.blocks) {
					if (sc.getBlock().equals(e.getHitBlock())) {
						ScriptableTargetHolder sth = new ScriptableTargetHolder();
						sth.setSelfBlock(sc);
						sth.setLocation(e.getHitBlock().getLocation());
						sth.setEntity((Entity) e.getEntity().getShooter());
						sth.setEvent(e);
						sth.setProjectile(e.getEntity());
						sc.activate(e, sth);
						break;
					}
				}
			}
		} catch (Error | Exception e4) {

		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onRedstone(BlockRedstoneEvent e) {
		for (ScriptedBlock sc : ScriptManager.blocks) {
			if (sc.getBlock().equals(e.getBlock())) {
				ScriptableTargetHolder sth = new ScriptableTargetHolder();
				sth.setSelfBlock(sc);
				sth.setLocation(e.getBlock().getLocation());
				// sth.setEntity(e.getPlayer());
				// sth.setEvent(e);
				sc.activate(e, sth);
				break;
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent e) {
		for (ScriptedBlock sc : ScriptManager.blocks) {
			if (sc.getBlock().getLocation().equals(e.getBlock().getLocation())) {
				ScriptableTargetHolder sth = new ScriptableTargetHolder();
				sth.setSelfBlock(sc);
				sth.setLocation(e.getBlock().getLocation());
				sth.setEntity(e.getPlayer());
				sth.setEvent(e);
				sc.activate(e, sth);
				break;
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent e) {
		try {
			if (e.getHand() == EquipmentSlot.OFF_HAND)
				return;
		}catch (Error|Exception e4){}
		for (ScriptedBlock sc : ScriptManager.blocks) {
			if (sc.getBlock().equals(e.getClickedBlock())) {
				ScriptableTargetHolder sth = new ScriptableTargetHolder();
				sth.setSelfBlock(sc);
				sth.setLocation(e.getClickedBlock().getLocation());
				sth.setEntity(e.getPlayer());
				sth.setEvent(e);
				sc.activate(e, sth);
				break;
			}
		}
	}

	@EventHandler
	public void onPiston(BlockPistonExtendEvent e) {
		for (Block b : e.getBlocks()) {

			for (ScriptedBlock sc : ScriptManager.blocks) {
				if (sc.getBlock().equals(b)) {
					sc.setBlock(b.getRelative(e.getDirection()));
					break;
				}
			}

			// BlockPlacer ac = null;
			// if ((ac = StockPlus.getBlockPlacer(b.getLocation())) != null)
			// ac.setLocation(b.getRelative(e.getDirection()).getLocation());
		}
	}

	@EventHandler
	public void onPiston(BlockPistonRetractEvent e) {
		for (Block b : e.getBlocks()) {
			for (ScriptedBlock sc : ScriptManager.blocks) {
				if (sc.getBlock().equals(b)) {
					Vector offset = b.getRelative(e.getDirection()).getLocation().subtract(b.getLocation()).toVector();
					sc.setBlock(b.getLocation().add(offset).getBlock());
					break;
				}
			}
			// BlockPlacer ac = null;
			// if ((ac = StockPlus.getBlockPlacer(b.getLocation())) != null) {
			// Vector offset =
			// b.getRelative(e.getDirection()).getLocation().subtract(b.getLocation()).toVector();
			// ac.setLocation(b.getLocation().add(offset));
			// }
		}
	}

}
