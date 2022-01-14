package net.cybercake.cyberapi;

import net.cybercake.cyberapi.instances.Spigot;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

public class Log {

    public static void info(String msg) {
        switch(CyberAPI.serverType) {
            case SPIGOT -> Bukkit.getLogger().info((CyberAPI.prefixInLogs ? "[" + Spigot.get().getDescription().getPrefix() + "] ": "") + ChatColor.translateAlternateColorCodes('&', msg));
            case BUNGEE -> ProxyServer.getInstance().getLogger().info((CyberAPI.prefixInLogs ? "[" + CyberAPI.bungeePrefix + "] ": "") + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void warn(String msg) {
        switch(CyberAPI.serverType) {
            case SPIGOT -> Bukkit.getLogger().warning((CyberAPI.prefixInLogs ? "[" + Spigot.get().getDescription().getPrefix() + "] ": "") + ChatColor.translateAlternateColorCodes('&', msg));
            case BUNGEE -> ProxyServer.getInstance().getLogger().info((CyberAPI.prefixInLogs ? "[" + CyberAPI.bungeePrefix + "] ": "") + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void error(String msg) {
        switch(CyberAPI.serverType) {
            case SPIGOT -> Bukkit.getLogger().severe((CyberAPI.prefixInLogs ? "[" + Spigot.get().getDescription().getPrefix() + "] ": "") + ChatColor.translateAlternateColorCodes('&', msg));
            case BUNGEE -> ProxyServer.getInstance().getLogger().info((CyberAPI.prefixInLogs ? "[" + CyberAPI.bungeePrefix + "] ": "") + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

}
