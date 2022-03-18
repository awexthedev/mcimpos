package dev.thatalex.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Sql {
    final static FileConfiguration config = dev.thatalex.main.App.fetchConfig();
    public static final String username = config.getString("username"); // Enter in your db username
    public static final String password = config.getString("password"); // Enter your password for the db

    // Constructs a JDBC URI using available config
    public static final String url = "jdbc:mysql://" + config.getString("hostname") + ":" + config.getString("port") + "/" + config.getString("database");

    // Create the connection variable
    static Connection connection;

    // Begin & shut down methods, creating tables if not exists.
    public static boolean beginConnection() {
        Bukkit.getLogger().info("[MCImpos] Establishing connection with SQL server..");
        try {
            connection = DriverManager.getConnection(url, username, password);

            // Create table if it doesn't exist
            String sql = "CREATE TABLE IF NOT EXISTS users(uuid varchar(64), impKills varchar(5), wins varchar(5));";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();

            Bukkit.getLogger().info("[MCImpos] Successfully loaded data backend.");
            return true;
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Uh oh.. I wasn't able to successfully connect to the SQL server. Double check your credentials and try again.\nError trace: " + e.toString());
            return false;
        }
    }

    public static boolean shutDownConnection() {
        Bukkit.getLogger().info("[MCImpos] Shutting down SQL connection..");

        try {
            if (connection!=null && !connection.isClosed()) {
                connection.close();
                return true;
            }
        } catch(Exception e) {
            return false;
        }

        return false;
    }

    // The juicy stuff. Updating, deleting, you name it.
    public static ResultSet fetchUserData(UUID uuid) {
        try {
            String sql = "SELECT * FROM users WHERE uuid=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, uuid.toString());
            ResultSet results = stmt.executeQuery();
            if(!results.next()) return null;
            else return results;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean addWin(UUID uuid) {
        try {
            ResultSet data = fetchUserData(uuid);
            if(data == null) return false;

            // Parse the wins count as an integer, add one
            String wins = data.getString("wins");
            int winsInt = Integer.parseInt(wins);
            winsInt++; 

            // Insert all the data, parse the int back to a string
            String sql = "UPDATE users SET wins='" + winsInt + "' WHERE uuid='" + uuid.toString() + "'";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Failed to create user entry!\n" + e.toString());
            return false;
        }
    }

    public static boolean addKill(UUID uuid) {
        try {
            ResultSet data = fetchUserData(uuid);
            if(data == null) return false;

            String kills = data.getString("impKills");
            int killsInt = Integer.parseInt(kills);
            killsInt++;

            String sql = "UPDATE users SET wins='" + killsInt + "' WHERE uuid='" + uuid.toString() + "'";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Failed to create user entry!\n" + e.toString());
            return false;
        }
    }

    public static boolean addUser(UUID uuid) {
        if(fetchUserData(uuid) != null) return true;
        try {
            String sql = "INSERT INTO users(uuid, impKills, wins) VALUES('" + uuid.toString() + "', '0', '0')";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Failed to create user entry!\n" + e.toString());
            return false;
        }
    }
}
