package xyz.awexxx;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

            if (clicked.getType().toString() == "ACACIA_BUTTON" && GameState.isState(GameState.IN_GAME))
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 500.0f, 1.0f);
                    player.sendTitle(p.getName() + " hit the panic button!", "Who did it..", 10, 70, 20);
                    player.setWalkSpeed(0);
                    player.setFlySpeed(0);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1));
                }
            else if (clicked.getType().toString() == "POLISHED_BLACKSTONE_BUTTON" && GameState.isState(GameState.IN_GAME)) {
                Menus.displayElectricMenu(p);
            } else if (clicked.getType().toString() == "CRIMSON_SIGN" && GameState.isState(GameState.IN_GAME)) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    clicked.breakNaturally();

                    player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 500.0f, 1.0f);
                    player.sendTitle(p.getName() + " found a dead body!", "Who did it..", 10, 70, 20);
                    player.setWalkSpeed(0);
                    player.setFlySpeed(0);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1));
                }
            } else if (clicked.getType().toString() == "IRON_TRAPDOOR" && Team.getTeam(e.getPlayer()).getName() == "Impostors" && GameState.isState(GameState.IN_GAME)) {
                Bukkit.getLogger().info(clicked.getLocation().getBlockX() + " " + clicked.getLocation().getBlockY() + " " + clicked.getLocation().getBlockZ());
                if (clicked.getLocation().getBlockX() == 135) {
                    Location vent1 = new Location(clicked.getWorld(), 137, 34, 376);
                    e.getPlayer().teleport(vent1);
                }
            }
        }
        
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent e) throws InterruptedException {
            Team.getTeam(e.getEntity()).remove(e.getEntity());

            if(Team.getTeam("Crewmates").getName().length() == 0) {
                ChatUtils.sendAllTitleMessage("The impostors win!", "Crewmates, do better!");
                Game.stop();
            }

            Block block = Bukkit.getWorld("amogus").getBlockAt(e.getEntity().getLocation());
            block.setType(Material.CRIMSON_SIGN);

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
