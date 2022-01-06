package net.cybercake.cyberapi;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CyberAPI  {

    public final String version = "1.2.0";
    public final int protocol = 4;

    public enum ServerType {
        SPIGOT, BUNGEE
    }
    public static Plugin bungeePlugin;
    public static String bungeePrefix = "";

    public static JavaPlugin spigotPlugin;

    public static ServerType serverType = ServerType.SPIGOT;

    public CyberAPI() {
    }

    public static void initSpigot(JavaPlugin plugin) {
        CyberAPI.serverType = CyberAPI.ServerType.SPIGOT;
        spigotPlugin = plugin;
        Log.info(ChatColor.GOLD + getAPI().getPrefix(false) + " The plugin " + ChatColor.GREEN + plugin.getDescription().getName() + ChatColor.GOLD + " is using CyberAPI version " + ChatColor.YELLOW + getAPI().getVersion() + ChatColor.GOLD + "! Plugin type is " + ChatColor.GREEN + "Spigot" + ChatColor.GOLD + ", as marked by the plugin developer.");
    }

    public static void initBungee(Plugin plugin, String prefix) {
        CyberAPI.serverType = ServerType.BUNGEE;
        bungeePlugin = plugin;
        bungeePrefix = prefix;
        Log.info(ChatColor.GOLD + getAPI().getPrefix(false) + " The plugin " + ChatColor.GREEN + plugin.getDescription().getName() + ChatColor.GOLD + " is using CyberAPI version " + ChatColor.YELLOW + getAPI().getVersion() + ChatColor.GOLD + "! Plugin type is " + ChatColor.BLUE + "BungeeCord" + ChatColor.GOLD + ", as marked by the plugin developer.");
    }

    public static CyberAPI getAPI() {
        return new CyberAPI();
    }

    public String getBungeePrefix() { return bungeePrefix; }

    public Plugin getBungeePlugin() {
        return bungeePlugin;
    }

    public String getPrefix(boolean error) {
        return (error ? "[CyberAPI Exception]" : "[CyberAPI]");
    }

    public int getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

}
