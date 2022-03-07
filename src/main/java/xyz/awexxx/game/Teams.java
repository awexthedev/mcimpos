package xyz.awexxx.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Teams {
    private static List<String> crewmates = new ArrayList<String>();
    private static List<String> impostors = new ArrayList<String>();
    private static HashMap<String, Material> colors = new HashMap<String, Material>();
    public static List<Material> possibleColors = new ArrayList<Material>();

    public static void add(Player player, String team) {
        if (team.equals("Crewmates")) {
            crewmates.add(player.getName());
            colors.put(player.getName(), possibleColors.stream().findAny().get());
        }
        if (team.equals("Impostors")) {
            impostors.add(player.getName());
            colors.put(player.getName(), possibleColors.stream().findAny().get());
        }
    }

    public static Material getColor(Player player) {
        if(hasTeam(player)) {
            Bukkit.getLogger().info(colors.get(player.getName()).toString());
            return colors.get(player.getName());
        } else return null;
    }

    public static void remove(Player player) {
        getTeam(player).remove(player.getName());
    }

    public static boolean hasTeam(Player player) {
        if (crewmates.contains(player.getName())) return true;
        if (impostors.contains(player.getName())) return true;

        return false;
    } 

    public static List<String> getTeam(Player player) {
        if (crewmates.contains(player.getName())) return crewmates;
        if (impostors.contains(player.getName())) return impostors;

        return null;
    }

    public static String getTeamName(Player player) {
        if(crewmates.contains(player.getName())) return "Crewmates";
        if(impostors.contains(player.getName())) return "Impostors";

        return null;
    }

    public static Integer checkTeamLength(String team) {
        if (team.equals("Crewmates")) return crewmates.size();
        if (team.equals("Impostors")) return impostors.size();

        return null;
    }
}
