package dev.thatalex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.thatalex.game.GameState;
import dev.thatalex.game.Teams;
import dev.thatalex.main.Menus;

public class Sabotage {
    public static class CommandSabatoge implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            Player p = (Player) sender;
            if(Teams.getTeamName(p) == "Impostors" && GameState.isState(GameState.IN_GAME)) {
                Menus.displaySaboMenu(p);
            } else p.sendMessage("You are not an impostor!");

            return true;
        }
    }
}
