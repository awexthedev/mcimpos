package xyz.awexxx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import xyz.awexxx.game.GameManager;
import xyz.awexxx.game.GameState;

public class StartGame {
    public static class CommandStart implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (GameState.isState(GameState.IN_LOBBY))
                GameManager.start();
            else
                sender.sendMessage("Game is already in progress!");

            return true;
        }
    }
}
