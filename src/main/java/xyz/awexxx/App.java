package xyz.awexxx;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        GameState.setState(GameState.IN_LOBBY);
        this.getCommand("startg").setExecutor(new CommandStart());
        this.getCommand("endg").setExecutor(new CommandStop());
        this.getCommand("submit").setExecutor(new CommandSubmit());

        // Events
        getServer().getPluginManager().registerEvents(new Events.EventListener(), this);
        getLogger().info("AMOGUS Plugin has launched!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AMOGUS Plugin has stopped!");
    }

    public class CommandStart implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (GameState.isState(GameState.IN_LOBBY))
                Game.start();
            else
                sender.sendMessage("Game is already in progress!");

            return true;
        }
    }

    public class CommandStop implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (GameState.isState(GameState.IN_GAME))
                Game.stop();
            else
                sender.sendMessage("Game is not running!");

            return true;
        }
    }

    public class CommandSubmit implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            Player p = (Player) sender;
            Menus.displayVotingMenu(p);
            return true;
        }
    }
}
