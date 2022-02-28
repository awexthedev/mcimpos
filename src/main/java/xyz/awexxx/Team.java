package xyz.awexxx;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team {
    private static List<Team> allTeams = new ArrayList<Team>();
    private static HashMap<String, Team> playerTeams = new HashMap<String, Team>();

    private String teamName;

    public Team(String teamName) {
        this.teamName = teamName;
        allTeams.add(this);
    }

    public String getName() {
        return teamName;
    }

    public void add(Player player) {
        playerTeams.put(player.getName(), this);
    }

    public boolean remove(Player player) {
        if(hasTeam(player))
            return false;
        playerTeams.remove(player.getName());
        return true;
    }

    public static boolean hasTeam(Player player) {
        return playerTeams.containsKey(player.getName());
    } 

    public static Team getTeam(Player player) {
        if(!hasTeam(player))
            return null;
        return playerTeams.get(player.getName());
    }

    public static Team getTeam(String name) {
        for (Team t : allTeams)
            if (t.teamName.equalsIgnoreCase(name))
                return t;
            return null;
    }

    public static List<Team> getAllTeams() {
        return allTeams;
    }
}
