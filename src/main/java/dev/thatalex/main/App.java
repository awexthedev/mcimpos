package dev.thatalex.main;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import dev.thatalex.game.GameManager;
import dev.thatalex.game.GameState;

public class App extends JavaPlugin {
    final FileConfiguration config = getConfig();
    
    @Override
    public void onEnable() {
        GameState.setState(GameState.IN_LOBBY);
        this.getCommand("startg").setExecutor(new dev.thatalex.commands.StartGame.CommandStart());
        this.getCommand("endg").setExecutor(new dev.thatalex.commands.EndGame.CommandStop());
        this.getCommand("submit").setExecutor(new dev.thatalex.commands.Submit.CommandSubmit());
        this.getCommand("sabotage").setExecutor(new dev.thatalex.commands.Sabotage.CommandSabatoge());
        this.getCommand("tasks").setExecutor(new dev.thatalex.commands.Task.CommandTasks());
        this.getCommand("stats").setExecutor(new dev.thatalex.commands.Stats.CommandStats());
        
        saveDefaultConfig();
        dev.thatalex.main.Sql.beginConnection();

        try {
            UpdateChecker.checkForUpdates();
        } catch (IOException e) {
            getLogger().warning("Something went wrong when checking for updates. Please do this manually; https://github.com/awexthedev/mcimpos.");
        }

        // Events
        getServer().getPluginManager().registerEvents(new Events.EventListener(), this);
        getLogger().info("MCImpos Plugin has launched!");
    }

    @Override
    public void onDisable() {
        dev.thatalex.main.Sql.shutDownConnection();
        if (GameState.isState(GameState.IN_GAME)) {
            LoggingUtils.infoLog("Shutting down open games..");
            GameManager.stop(null);
        } 

        getLogger().info("MCImpos Plugin has stopped!");
    }

    // Config Key
    public static FileConfiguration fetchConfig() {
        return Bukkit.getPluginManager().getPlugin("mcimpos").getConfig();
    } 
} 
