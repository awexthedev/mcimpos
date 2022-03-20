package dev.thatalex.main;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.json.JSONObject;

public class UpdateChecker {
    public static boolean checkForUpdates() throws IOException {

        LoggingUtils.infoLog("Making sure the plugin is up-to-date.");

        URL url = new URL("https://api.thatalex.dev/v0/plugin/version/mcimpos");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if(conn.getResponseCode() == 200 && conn.getURL().equals(url)) {
            Scanner scan = new Scanner(url.openStream());
            while (scan.hasNext()) {
                String temp = scan.nextLine();
                JSONObject obj = new JSONObject(temp);
                if(Bukkit.getPluginManager().getPlugin("mcimpos").getDescription().getVersion().equals(obj.getJSONObject("data").getString("version"))) {
                    scan.close();
                    LoggingUtils.infoLog("Plugin is up-to-date! Moving on..");
                } else LoggingUtils.warningLog("MCImpos is out-of-date. The latest version is " + obj.getJSONObject("data").getString("version") + ", while the version you are on is " + Bukkit.getPluginManager().getPlugin("mcimpos").getDescription().getVersion() + ".");
            }
        } else LoggingUtils.warningLog("I was unable to check for updates. The API may be offline. Please check via the GitHub releases page. (https://github.com/awexthedev/mcimpos/releases)");

        return true;
    }
}
