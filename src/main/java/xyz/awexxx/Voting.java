package xyz.awexxx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

public class Voting {
    private static List<Player> votes = new ArrayList<Player>();

    public static void vote (Player voted) {
        votes.add(voted);
    }

    public static int checkVotes (Player player) {
        return Collections.frequency(votes, player);
    }

    public static boolean clearVotes() {
        votes.clear();
        return true;
    }
}
