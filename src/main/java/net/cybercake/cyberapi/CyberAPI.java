package net.cybercake.cyberapi;

import net.cybercake.cyberapi.instances.Bungee;
import net.cybercake.cyberapi.instances.Spigot;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CyberAPI  {

    public final String version = "1.4.0";
    public final int protocol = 4;

    public enum ServerType {
        SPIGOT, BUNGEE
    }
    public static Plugin bungeePlugin;
    public static String bungeePrefix = "";

    public static JavaPlugin spigotPlugin;

    public static ServerType serverType;

    public static boolean prefixInLogs = true;

    public CyberAPI() {
    }

    public void sendStartupMessage() {
        switch(serverType) {
            case BUNGEE -> sendStartupMessage(Bungee.get().getDescription().getName());
            case SPIGOT -> sendStartupMessage(Spigot.get().getDescription().getName());
        }
    }

    private static void sendStartupMessage(String pluginName) {
        String typeFormatted = null;
        switch(CyberAPI.serverType) {
            case SPIGOT -> typeFormatted = "&aSPIGOT";
            case BUNGEE -> typeFormatted = "&9BUNGEE";
        }
        String msg = "&d" + getAPI().getPrefix(false) + " The plugin &c" + pluginName + " &dis using CyberAPI version &e" + getAPI().getVersion() + "&d! The plugin's type is " + typeFormatted + "&d, as marked by the plugin developer.";

        Log.info(msg);
    }

    public static void initSpigot(JavaPlugin plugin) {
        initSpigot(plugin, true);
    }

    public static void initSpigot(JavaPlugin plugin, boolean sendPrefixInLogs) {
        CyberAPI.serverType = CyberAPI.ServerType.SPIGOT;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        spigotPlugin = plugin;
        prefixInLogs = sendPrefixInLogs;
        sendStartupMessage(plugin.getDescription().getName());
    }

    public static void initBungee(Plugin plugin) {
        initBungee(plugin, plugin.getDescription().getName(), true);
    }

    public static void initBungee(Plugin plugin, String prefix, boolean sendPrefixInLogs) {
        CyberAPI.serverType = ServerType.BUNGEE;
        bungeePlugin = plugin;
        bungeePrefix = prefix;
        prefixInLogs = sendPrefixInLogs;
        sendStartupMessage(plugin.getDescription().getName());
    }

    public static CyberAPI getAPI() {
        return new CyberAPI();
    }

    public String getBungeePrefix() { return bungeePrefix; }

    /**
     * Prints [CyberAPI Exception] if it's errored and [CyberAPI] if it's not errored (space after bracket not included!)
     * @param error if it is an error the prefix is display on
     * @return the prefix for CyberAPI
     */
    public String getPrefix(boolean error) {
        return (error ? "[CyberAPI Exception]" : getPrefix());
    }

    /**
     * returns [CyberAPI] (space after bracket not included!)
     * @return the prefix for CyberAPI
     */
    public String getPrefix() {
        return "[CyberAPI]";
    }

    public int getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

}
