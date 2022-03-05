package xyz.awexxx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONArray;

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
        player.sendMessage("Great job! You have now been assigned " + newTask);
        return newTask;
    }

    public static boolean assignTask(Player player) {
        activeTasks.put(player, randomTask(player));
        return true;
    }

    public static boolean isActive(Player player, String task) {
        String s = activeTasks.get(player);
        if (task.equals("medbay")) {
            if (!s.equals("Get scanned in Medbay!")) {
                return false;
            }
        } else if (task.equals("electrical")) {
            if (!s.equals("Tie up in Electrical!")) {
                return false;
            }
        } else if (task.equals("garbage")) {
            if (!s.equals("Clear the garbage!")) {
                return false;
            }
        }
        
        return true;
    }

    public static String completeTask(Player player, String task) {
        if (completedTasks.get(player) == null) {
            JSONArray arr = new JSONArray();
            completedTasks.put(player, arr.put(task));
            Bukkit.getLogger().info(arr.toString());
        } else {
            JSONArray arr = new JSONArray(completedTasks.get(player));
            completedTasks.put(player, arr.put(task.toString()));
            Bukkit.getLogger().info(arr.toString());
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
