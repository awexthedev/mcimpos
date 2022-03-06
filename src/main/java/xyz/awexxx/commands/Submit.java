package xyz.awexxx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.awexxx.game.GameState;
import xyz.awexxx.main.Menus;

public class Submit {
    public static class CommandSubmit implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            Player p = (Player) sender;
            if (GameState.isState(GameState.IN_GAME)) {
                Menus.displayVotingMenu(p);
            }

            return true;
        }
    }
}
