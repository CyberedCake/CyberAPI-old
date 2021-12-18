package net.cybercake.cyberapi;

import net.cybercake.cyberapi.instances.Spigot;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

public class Log {

    public static void info(String msg) {
        if(CyberAPI.getAPI().getServerType().equals(CyberAPI.ServerType.SPIGOT)) {
            if(Spigot.get() == null) return;
            Bukkit.getLogger().info("[" + Spigot.getPrefix() + "] " + msg);
        }else if(CyberAPI.getAPI().getServerType().equals(CyberAPI.ServerType.BUNGEE)) {
            if(CyberAPI.getAPI().getBungeePlugin() == null) return;
            ProxyServer.getInstance().getLogger().info(msg);
        }
    }
    public static void warn(String msg) {
        if(CyberAPI.getAPI().getServerType().equals(CyberAPI.ServerType.SPIGOT)) {
            if(CyberAPI.getAPI().getSpigotPlugin() == null) return;
            Bukkit.getLogger().warning("[" + Spigot.getPrefix() + "] " + msg);
        }else if(CyberAPI.getAPI().getServerType().equals(CyberAPI.ServerType.BUNGEE)) {
            if(CyberAPI.getAPI().getBungeePlugin() == null) return;
            ProxyServer.getInstance().getLogger().warning(msg);
        }
    }
    public static void error(String msg) {
        if(CyberAPI.getAPI().getServerType().equals(CyberAPI.ServerType.SPIGOT)) {
            if(CyberAPI.getAPI().getSpigotPlugin() == null) return;
            Bukkit.getLogger().severe("[" + Spigot.getPrefix() + "] " + msg);
        }else if(CyberAPI.getAPI().getServerType().equals(CyberAPI.ServerType.BUNGEE)) {
            if(CyberAPI.getAPI().getBungeePlugin() == null) return;
            ProxyServer.getInstance().getLogger().severe(msg);
        }
    }

}
