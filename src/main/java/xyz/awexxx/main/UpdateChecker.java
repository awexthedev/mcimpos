package xyz.awexxx.main;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.json.JSONObject;

public class UpdateChecker {
    public static boolean checkForUpdates() throws IOException {

        Bukkit.getLogger().info("[MCImpos] Making sure the plugin is up-to-date.");

        URL url = new URL("https://api.awexxx.xyz/v0/plugin/version/mcimpos");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        if(conn.getResponseCode() == 200 && conn.getURL().equals(url)) {
            Scanner scan = new Scanner(url.openStream());
            while(scan.hasNext()) {
                String temp = scan.nextLine();
                JSONObject obj = new JSONObject(temp);
                if (Bukkit.getPluginManager().getPlugin("mcimpos").getDescription().getVersion().equals(obj.getJSONObject("data").getString("version"))) {
                    scan.close();
                    return true;
                } else {
                    Bukkit.getLogger().warning("[MCImpos] MCImpos is out of date. You should be on version " + obj.getJSONObject("data").getString("version") + 
                    ", while the current version is " + Bukkit.getPluginManager().getPlugin("mcimpos").getDescription().getVersion() + ".");
                }
            }
        } else return false;

        return true;
    }
}
