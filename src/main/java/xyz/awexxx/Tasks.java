package xyz.awexxx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.json.JSONArray;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Tasks {
    private static List<String> allTasks = new ArrayList<String>();
    private static HashMap<Player, String> activeTasks = new HashMap<Player, String>();
    private static HashMap<Player, JSONArray> completedTasks = new HashMap<Player, JSONArray>();

    public Tasks(String taskName) {
        allTasks.add(taskName);
    }

    public static String randomTask(Player player) {
        Random rand = new Random();
        String newTask = allTasks.get(rand.nextInt(2 - 0 + 1) + 0);

        if(completedTasks.get(player) == null) return newTask;
        if(completedTasks.get(player).length() == 3) return "done";

        if (completedTasks.get(player).length() != 3) {
            if (completedTasks.get(player).toString().contains(newTask)) {
                return randomTask(player);
            }
        }

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(newTask));
        return newTask;
    }

    public static boolean assignTask(Player player) {
        String s = randomTask(player);
        if(s.equals("done")) return false;
        
        activeTasks.put(player, s);
        return true;
    }

    public static boolean isActive(Player player, String task) {
        String s = activeTasks.get(player);
        if(task.equals("Get scanned in Medbay!")) {
            if(!s.equals("Get scanned in Medbay!")) return false;
        } else if (task.equals("Tie up in Electrical!")) {
            if(!s.equals("Tie up in Electrical!")) return false;
        } else if (task.equals("Clear the garbage!")) {
            if(!s.equals("Clear the garbage!")) return false;
        }

        return true;
    }

    public static String completeTask(Player player, String task) {
        if (completedTasks.get(player) == null) {
            JSONArray arr = new JSONArray();
            completedTasks.put(player, arr.put(task));
        } else {
            JSONArray arr = new JSONArray(completedTasks.get(player));
            completedTasks.put(player, arr.put(task.toString()));

            if(arr.length() == 3) {
                ChatUtils.sendAllTitleMessage(player.getName() + " completed all tasks!", "Go faster!");

                player.setGameMode(GameMode.SPECTATOR);
                Team.getTeam(player).remove(player);
            }
        }

        return randomTask(player);
    }

    public static JSONArray getAllCompletedTasks(Player player) {
        return completedTasks.get(player);
    }
    
    public static String getAllActiveTasks(Player player) {
        return activeTasks.get(player).toString();
    }

    public static boolean clearTasks() {
        activeTasks.clear();
        completedTasks.clear();
        return true;
    }
}
