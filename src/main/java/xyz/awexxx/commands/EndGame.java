package xyz.awexxx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import xyz.awexxx.game.GameManager;
import xyz.awexxx.game.GameState;

public class EndGame {
    public static class CommandStop implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (GameState.isState(GameState.IN_GAME))
                GameManager.stop();
            else
                sender.sendMessage("Game is not running!");

            return true;
        }
    }
}
