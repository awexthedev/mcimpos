package dev.thatalex.game;

import dev.thatalex.main.ChatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.json.JSONArray;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Tasks {
    private static List<String> allTasks = new ArrayList<String>();
    private static HashMap<Player, String> activeTasks = new HashMap<Player, String>();
    private static HashMap<Player, JSONArray> completedTasks = new HashMap<Player, JSONArray>();
    private static Integer count = 0;

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
        activeTasks.remove(player);
        activeTasks.put(player, newTask);
        return newTask;
    }

    public static boolean assignTask(Player player) {
        String s = randomTask(player);
        if(s.equals("done")) return false;
        
        activeTasks.put(player, s);
        return true;
    }

    public static boolean isActive(Player player, String task) {
        String s = activeTasks.get(player).toString();
        if(s.contains(task)) return true;
        else return false;
    }

    public static String completeTask(Player player, String task) {
        if (completedTasks.get(player) == null) {
            JSONArray arr = new JSONArray();
            completedTasks.put(player, arr.put(task));
            checkIfComplete();
        } else {
            JSONArray arr = new JSONArray(completedTasks.get(player));
            completedTasks.put(player, arr.put(task.toString()));

            if(arr.length() == 3 && checkIfComplete() == false) {
                ChatUtils.sendAllTitleMessage(player.getName() + " completed all tasks!", "Go faster!");

                player.setGameMode(GameMode.SPECTATOR);
                Teams.remove(player);
            }
        }

        return randomTask(player);
    }

    public static boolean checkIfComplete() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            JSONArray arr = completedTasks.get(player);

            if (arr.length() == 3) {
                count++;
            }
        }

        if(count == Bukkit.getOnlinePlayers().size()) {
            count = 0;
            ChatUtils.sendAllTitleMessage("All crewmates have completed every task!", "Better luck next time, Imposter!");
            GameManager.stop();
            return true;
        }

        return false;
    }

    public static JSONArray getAllCompletedTasks(Player player) {
        return completedTasks.get(player);
    }
    
    public static String getAllActiveTasks(Player player) {
        return activeTasks.get(player);
    }

    public static boolean clearTasks() {
        activeTasks.clear();
        completedTasks.clear();
        return true;
    }
}
