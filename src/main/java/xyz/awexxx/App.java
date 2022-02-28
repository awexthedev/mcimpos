package xyz.awexxx;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        GameState.setState(GameState.IN_LOBBY);
        this.getCommand("startg").setExecutor(new CommandStart());
        this.getCommand("endg").setExecutor(new CommandStop());
        this.getCommand("submit").setExecutor(new CommandSubmit());
        this.getCommand("check").setExecutor(new CommandCheck());

        // Events
        getServer().getPluginManager().registerEvents(new Events.EventListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
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

    public class CommandCheck implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                int voteCheck = Voting.checkVotes(player);

                if (voteCheck > Bukkit.getOnlinePlayers().size() / 2) {
                    if (Team.getTeam(player).getName().toString() == "Impostors") {
                        Bukkit.broadcastMessage(player.getName() + " was the impostor!");
                        Game.stop();
                    } else {
                        player.setGameMode(GameMode.SPECTATOR);
                        Bukkit.broadcastMessage(player.getName() + " was NOT the impostor!");
                    }
                } else Bukkit.getPlayer("thatoneawex").sendMessage("Not enough votes.");
            }

            return true;
        }
    }
}
