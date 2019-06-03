package me.zombie_striker.blockscripter;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleHandlers {

	public static boolean is13 = true;

	public static void initValues() {
		is13 = BlockScripter.isVersionHigherThan(1, 13);
	}

	public static void spawnColoredParticle(double r, double g, double b, Location loc) {
		try {
			if (is13) {
				Particle.DustOptions dust = new Particle.DustOptions(
						Color.fromRGB((int) (r * 255), (int) (g * 255), (int) (b * 255)), 1);
				loc.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 0, 0, 0, 0, dust);
			} else {
				loc.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 0, r, g, b, 1);
			}
		} catch (Error | Exception e45) {
			e45.printStackTrace();
		}
	}
}
