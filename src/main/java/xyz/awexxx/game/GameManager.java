package xyz.awexxx.game;

import xyz.awexxx.main.ChatUtils;

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

public class GameManager {
    private static boolean canStart = false;

    public static void start() {
        // New Tasks
        new Tasks("Tie up in Electrical!");
        new Tasks("Hit the asteroids!");
        new Tasks("Clear the garbage!");
        new Tasks("Get scanned in Medbay!");
        new Tasks("Swipe your card!");

        GameState.setState(GameState.IN_GAME);
        Player randomPlayer = Bukkit.getOnlinePlayers().stream().findAny().get();

        Teams.possibleColors.add(Material.RED_CONCRETE);
        Teams.possibleColors.add(Material.LIME_CONCRETE);
        Teams.possibleColors.add(Material.PINK_CONCRETE);
        Teams.possibleColors.add(Material.GRAY_CONCRETE);
        Teams.possibleColors.add(Material.GREEN_CONCRETE);
        Teams.possibleColors.add(Material.PURPLE_CONCRETE);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setWalkSpeed(0.2f);
            player.setHealth(0.5f);
            player.setGameMode(GameMode.ADVENTURE);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.getInventory().clear();
    
            Location loc = new Location(Bukkit.getWorld("amogus"), 118, 34, 384);
            player.teleport(loc);

            if(Teams.checkTeamLength("Impostors").equals(1)) {
                Teams.add(player, "Crewmates");
            } else Teams.add(randomPlayer, "Impostors");

            // Assign Tasks to crewmates
            if(Teams.getTeamName(player) == "Crewmates") {
                Tasks.assignTask(player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Tasks.getAllActiveTasks(player).toString()));
            } else if(Teams.getTeamName(player) == "Impostors") {
                PlayerInventory inventory = player.getInventory();
                inventory.setItem(1, new ItemStack(Material.IRON_SWORD));
            } 
            
            ChatUtils.sendAllTitleMessage("You are on " + Teams.getTeamName(player) + "!", "Who did it..");
        }
    }

    public static void stop() {

        Voting.clearVotes();
        Tasks.clearTasks();

        for (Player player : Bukkit.getOnlinePlayers()) {
            GameState.setState(GameState.IN_LOBBY);

            player.setWalkSpeed(0.2f);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.getInventory().clear();
            player.closeInventory();
            
            player.setGameMode(GameMode.ADVENTURE);
            
            ChatUtils.sendAllTitleMessage("Game over!", Teams.getTeam(player).get(0) + " was the impostor!");


            Teams.remove(player);
        }
    }

    public static boolean canStart() {
        return canStart;
    }

    public static void setCanStart(boolean b) {
        canStart = b;
    }
}
