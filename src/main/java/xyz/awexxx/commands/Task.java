package xyz.awexxx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.awexxx.game.GameState;
import xyz.awexxx.game.Tasks;

public class Task {
    public static class CommandTasks implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            Player p = (Player) sender;
            if (GameState.isState(GameState.IN_GAME)) {
                p.sendMessage(Tasks.getAllActiveTasks(p));
            }

            return true;
        }
    }
}