package dev.thatalex.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.thatalex.main.Sql;

public class Stats {
    public static class CommandStats implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            Player p = (Player) sender;
            try {
                ResultSet d = Sql.fetchUserData(p.getUniqueId());
                p.sendMessage("You have " + d.getString("wins") + " total wins (between Crewmate and Impostor) and " + d.getString("impKills") + " kills as Impostor.");
            } catch (SQLException e) {
                return false;
            }

            return true;
        }
    }
}
