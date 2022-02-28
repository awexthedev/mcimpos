package xyz.awexxx;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Events {
    public static class EventListener implements Listener {
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent e) throws InterruptedException {
            Player p = e.getPlayer();
            Block clicked = e.getClickedBlock();

            if (clicked.getType().toString() == "ACACIA_BUTTON")
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 500.0f, 1.0f);
                    player.sendTitle(p.getName() + " hit the panic button!", "Who did it..", 10, 70, 20);
                    player.setWalkSpeed(0);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1));
                }
            else if (clicked.getType().toString() == "POLISHED_BLACKSTONE_BUTTON") {
                Menus.displayElectricMenu(p);
            }
        }
        
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent e) throws InterruptedException {
            Location loc = new Location(Bukkit.getWorld("amogus"), 118, 34, 384);
            e.getEntity().teleport(loc);
        }
        @EventHandler
        public void onHungerDepletion(FoodLevelChangeEvent e) throws InterruptedException {
            e.setCancelled(true);
            e.setFoodLevel(20);
        }
        @EventHandler
        public void onEntityRegen(EntityRegainHealthEvent e) throws InterruptedException {
            e.setCancelled(true);
            e.setAmount(20);
        }
    }
}
