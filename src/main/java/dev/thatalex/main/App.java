package dev.thatalex.main;

import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;

import dev.thatalex.game.GameState;

public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        GameState.setState(GameState.IN_LOBBY);
        this.getCommand("startg").setExecutor(new dev.thatalex.commands.StartGame.CommandStart());
        this.getCommand("endg").setExecutor(new dev.thatalex.commands.EndGame.CommandStop());
        this.getCommand("submit").setExecutor(new dev.thatalex.commands.Submit.CommandSubmit());
        this.getCommand("sabotage").setExecutor(new dev.thatalex.commands.Sabotage.CommandSabatoge());
        this.getCommand("tasks").setExecutor(new dev.thatalex.commands.Task.CommandTasks());

        try {
            if (UpdateChecker.checkForUpdates() == false) {
                getLogger().warning("I was not able to properly check for updates. The API is likely offline. Please check the GitHub repo manually; https://github.com/awexthedev/mcimpos");
            } else getLogger().info("[MCImpos] Up to date! Moving on!");
        } catch (IOException e) {
            getLogger().warning("Something went wrong when checking for updates. Please do this manually; https://github.com/awexthedev/mcimpos.");
        }

        // Events
        getServer().getPluginManager().registerEvents(new Events.EventListener(), this);
        getLogger().info("MCImpos Plugin has launched!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MCImpos Plugin has stopped!");
    }
} 
