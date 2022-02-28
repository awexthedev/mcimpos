package xyz.awexxx;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.luckperms.api.LuckPerms;

public class Perms {
    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    LuckPerms api = provider.getProvider();

    public static boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }
}
