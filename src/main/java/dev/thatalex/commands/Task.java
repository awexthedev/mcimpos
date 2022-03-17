package dev.thatalex.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.thatalex.game.GameState;
import dev.thatalex.game.Tasks;

public class Task {
    public static class CommandTasks implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            Player p = (Player) sender;
            if (GameState.isState(GameState.IN_GAME)) {
                p.sendMessage("Your current task is " + ChatColor.GREEN + Tasks.getAllActiveTasks(p));
            }

            return true;
        }
    }
}