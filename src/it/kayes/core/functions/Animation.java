package it.kayes.core.functions;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import it.kayes.core.main.Main;

public class Animation {

	public static void createTeleportAnimation(Location loc) {
		
		for (int i = 0; i<30; i++) {
			float x = (float) (0.5 - 0.03*i);
			float y = -2 * Math.abs(x);
			
			new BukkitRunnable() {
				byte s = 0;
				short a = -90;
				boolean t = false;
				float n = 0;
				@Override
				public void run() {
					if (s==20)
						cancel();
					
					if (!t) {
						if (s==12) t = true; 
						
						if (s%3==0) {
							a+=90;
							for (int i = 0; i<a+90; i+=10)
								loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.getX()+Math.cos(Math.toRadians(i)) * 0.5, loc.getY(), loc.getZ()+Math.sin(Math.toRadians(i)) * 0.5, 0);
						}
						
						loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.getX()+x, loc.getY()+y+1, loc.getZ()+x, 0);
						loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.getX()+x, loc.getY()+y+1, loc.getZ()-x, 0);
					}else {
						for (int i = 0; i<=360; i+=10) 
							loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc.getX()+Math.cos(Math.toRadians(i)) * 0.5, loc.getY()+2-n, loc.getZ()+Math.sin(Math.toRadians(i)) * 0.5, 0);
						n+=0.25;
					}
					s++;
				}
				
			}.runTaskTimerAsynchronously(Main.getInstance(), 5L, 5L);
			
		}
		
	}
	
	
}
