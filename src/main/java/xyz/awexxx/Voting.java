package xyz.awexxx;

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
                    if (Team.getTeam(player).getName().toString() == "Impostors") {
                        Bukkit.broadcastMessage(player.getName() + " was the impostor!");
                        Game.stop();
                    } else {
                        player.setGameMode(GameMode.SPECTATOR);
                        Bukkit.broadcastMessage(player.getName() + " was NOT the impostor!");
                    }
                } else Bukkit.getPlayer("thatoneawex").sendMessage("Not enough votes.");
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
