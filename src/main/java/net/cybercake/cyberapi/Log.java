package net.cybercake.cyberapi;

import net.cybercake.cyberapi.instances.Spigot;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Log {

    public static void info(String msg) {
        switch(CyberAPI.getAPI().getServerType()) {
            case SPIGOT -> Bukkit.getLogger().info((CyberAPI.getAPI().showPrefixInLogs() ? Spigot.getPlugin().getDescription().getPrefix() != null ? "[" + Spigot.getPlugin().getDescription().getPrefix() + "] ": "" : "") + ChatColor.translateAlternateColorCodes('&', msg));
            case BUNGEE -> ProxyServer.getInstance().getLogger().info((CyberAPI.getAPI().showPrefixInLogs() ? "[" + CyberAPI.getAPI().getBungeePrefix() + "] ": "") + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void warn(String msg) {
        switch(CyberAPI.getAPI().getServerType()) {
            case SPIGOT -> Bukkit.getLogger().warning((CyberAPI.getAPI().showPrefixInLogs() ? Spigot.getPlugin().getDescription().getPrefix() != null ? "[" + Spigot.getPlugin().getDescription().getPrefix() + "] ": "" : "") + ChatColor.translateAlternateColorCodes('&', msg));
            case BUNGEE -> ProxyServer.getInstance().getLogger().warning((CyberAPI.getAPI().showPrefixInLogs() ? "[" + CyberAPI.getAPI().getBungeePrefix() + "] ": "") + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void error(String msg) {
        switch(CyberAPI.getAPI().getServerType()) {
            case SPIGOT -> Bukkit.getLogger().severe((CyberAPI.getAPI().showPrefixInLogs() ? Spigot.getPlugin().getDescription().getPrefix() != null ? "[" + Spigot.getPlugin().getDescription().getPrefix() + "] ": "" : "") + ChatColor.translateAlternateColorCodes('&', msg));
            case BUNGEE -> ProxyServer.getInstance().getLogger().severe((CyberAPI.getAPI().showPrefixInLogs() ? "[" + CyberAPI.getAPI().getBungeePrefix() + "] ": "") + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
    public static void log(Level level, String msg) {
        switch(CyberAPI.getAPI().getServerType()) {
            case SPIGOT -> Bukkit.getLogger().log(level, msg);
            case BUNGEE -> ProxyServer.getInstance().getLogger().log(level, msg);
        }
    }

}
