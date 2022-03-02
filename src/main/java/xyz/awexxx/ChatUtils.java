package xyz.awexxx;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static void sendAllTitleMessage(String title, String subtitle) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle, 10, 70, 20);
        }
    }
}
