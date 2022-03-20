package dev.thatalex.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

public class LoggingUtils {
    public static Logger log = Bukkit.getLogger();

    public static void infoLog(String message) {
        log.info("[MCImpos] " + message);
    }

    public static void warningLog(String message) {
        log.warning("[MCImpos] " + message);
    }

    public static void errorLog(String error) {
        log.info("[MCImpos] " + ChatColor.RED + "Something went wrong!" + ChatColor.RESET + " Please see the error trace below.\n" + ChatColor.RED + error);
    }
}
