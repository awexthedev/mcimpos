package dev.thatalex.game;

import dev.thatalex.main.ChatUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Voting {
    private static List<Player> votes = new ArrayList<Player>();

    public static void vote (Player voted) {
        votes.add(voted);

        if (checkTotalVotes() == true) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                int voteCheck = Voting.checkVotes(player);

                if (voteCheck > Bukkit.getOnlinePlayers().size() / 2) {
                    if (Teams.getTeamName(player) == "Impostors") {
                        GameManager.stop("crewmates");
                    } else {
                        player.setGameMode(GameMode.SPECTATOR);
                        Teams.remove(player);
                        ChatUtils.sendAllTitleMessage(player.getName() + " was NOT the impostor!", "Look harder!");
                    }
                } else ChatUtils.sendAllTitleMessage("Not enough votes!", "Keep going!");
            }
        }
    }

    public static int checkVotes (Player player) {
        return Collections.frequency(votes, player);
    }

    public static boolean clearVotes() {
        votes.clear();
        return true;
    }

    public static boolean checkTotalVotes() {
        if (votes.size() == Bukkit.getOnlinePlayers().size()) {
            return true;
        } else return false;
    }
}
