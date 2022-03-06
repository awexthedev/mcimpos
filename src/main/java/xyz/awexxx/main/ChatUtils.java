package xyz.awexxx.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static void sendAllTitleMessage(String title, String subtitle) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle, 10, 70, 20);
        }
    }

    public static void sendToOne(String title, String subtitle, Player sendto) {
        sendto.sendTitle(title, subtitle, 10, 70, 20);
    }
}
