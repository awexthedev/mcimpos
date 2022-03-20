package dev.thatalex.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.thatalex.game.GameState;
import dev.thatalex.game.Tasks;
import dev.thatalex.game.Teams;

public class Task {
    public static class CommandTasks implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            Player p = (Player) sender;
            if (GameState.isState(GameState.IN_GAME) && Teams.getTeamName(p) != "Impostors") {
                p.sendMessage("Your current task is " + ChatColor.GREEN + Tasks.getAllActiveTasks(p));
            } else if (Teams.getTeamName(p) == "Impostors") {
                p.sendMessage(ChatColor.RED + "You are the impostor!" + ChatColor.RESET + " Your job is to prevent the crewmates from doing their tasks.");
            }

            return true;
        }
    }
}