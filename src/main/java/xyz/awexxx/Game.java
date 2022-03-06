package xyz.awexxx;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Game {
    private static boolean canStart = false;

    public static void start() {
        new Team("Crewmates");
        new Team("Impostors");

        // New Tasks
        new Tasks("Tie up in Electrical!");
        new Tasks("Hit the asteroids!");
        new Tasks("Clear the garbage!");
        new Tasks("Get scanned in Medbay!");

        GameState.setState(GameState.IN_GAME);

        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setWalkSpeed(0.2f);
            player.setHealth(0.5f);
            player.setGameMode(GameMode.SURVIVAL);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.getInventory().clear();
    
            Location loc = new Location(Bukkit.getWorld("amogus"), 118, 34, 384);
            player.teleport(loc);

            if(i >= Team.getAllTeams().size())
                i = 0;
            Team.getTeam(Team.getAllTeams().get(i++).getName()).add(player);

            // Assign Tasks to crewmates
            if(Team.getTeam(player).getName() == "Crewmates") {
                Tasks.assignTask(player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Tasks.getAllActiveTasks(player).toString()));
            } else if(Team.getTeam(player).getName() == "Impostors") {
                PlayerInventory inventory = player.getInventory();
                inventory.addItem(new ItemStack(Material.IRON_SWORD));
            } 
            
            ChatUtils.sendAllTitleMessage("You are on " + Team.getTeam(player).getName() + "!", "Who did it..");
        }
    }

    public static void stop() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            GameState.setState(GameState.IN_LOBBY);

            player.setWalkSpeed(0.2f);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.getInventory().clear();

            Voting.clearVotes();
            Tasks.clearTasks();
            
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
