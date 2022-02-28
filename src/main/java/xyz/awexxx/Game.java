package xyz.awexxx;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

public class Game {
    private static boolean canStart = false;

    public static void start() {
        new Team("Crewmates");
        new Team("Impostors");

        GameState.setState(GameState.IN_GAME);

        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setWalkSpeed(0.2f);
            player.setHealth(0.5f);
            Location loc = new Location(Bukkit.getWorld("amogus"), 118, 34, 384);
            player.teleport(loc);

            if(i >= Team.getAllTeams().size())
                i = 0;
            Team.getTeam(Team.getAllTeams().get(i++).getName()).add(player);

            if(Team.getTeam(player).getName() == "Impostors") {
                PlayerInventory inventory = player.getInventory();
                inventory.addItem(new ItemStack(Material.IRON_SWORD));
            } 
            
            player.sendTitle("You are on " + Team.getTeam(player).getName() + "!", "Who did it..", 10, 70, 20);
        }
    }

    public static void stop() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            GameState.setState(GameState.IN_LOBBY);

            player.setWalkSpeed(0.2f);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            Voting.clearVotes();

            player.setGameMode(GameMode.SURVIVAL);
            Team.getTeam(player).remove(player);
            player.sendMessage("Successfully removed from team " + Team.getTeam(player).getName());
        }
    }

    public static boolean canStart() {
        return canStart;
    }

    public static void setCanStart(boolean b) {
        canStart = b;
    }
}
