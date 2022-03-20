package dev.thatalex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.thatalex.game.GameState;
import dev.thatalex.main.Menus;

public class Submit {
    public static class CommandSubmit implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            Player p = (Player) sender;
            if (GameState.isState(GameState.PANIC_MODE)) {
                Menus.displayVotingMenu(p);
            }

            return true;
        }
    }
}
