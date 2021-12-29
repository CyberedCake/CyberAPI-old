package net.cybercake.cyberapi;

import net.cybercake.cyberapi.instances.Spigot;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

public class Log {

    public static void info(String msg) {
        switch(CyberAPI.getAPI().getServerType()) {
            case SPIGOT -> Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "[" + Spigot.getPrefix() + "] " + msg));
            case BUNGEE -> ProxyServer.getInstance().getLogger().info(ChatColor.translateAlternateColorCodes('&', "[" + msg));
        }
    }
    public static void warn(String msg) {
        switch(CyberAPI.getAPI().getServerType()) {
            case SPIGOT -> Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', "[" + Spigot.getPrefix() + "] " + msg));
            case BUNGEE -> ProxyServer.getInstance().getLogger().info(msg);
        }
    }
    public static void error(String msg) {
        switch(CyberAPI.getAPI().getServerType()) {
            case SPIGOT -> Bukkit.getLogger().severe(ChatColor.translateAlternateColorCodes('&', "[" + Spigot.getPrefix() + "] " + msg));
            case BUNGEE -> ProxyServer.getInstance().getLogger().info(msg);
        }
    }

}
