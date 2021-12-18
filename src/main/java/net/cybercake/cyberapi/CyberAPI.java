package net.cybercake.cyberapi;

import net.cybercake.cyberapi.exceptions.UnknownServerType;
import net.cybercake.cyberapi.instances.Bungee;
import net.cybercake.cyberapi.instances.Spigot;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class CyberAPI  {

    public enum ServerType {
        SPIGOT, BUNGEE
    }

    private static ServerType serverType;
    private static JavaPlugin spigotPlugin;
    private static Plugin bungeePlugin;

    private static boolean softAPIExceptions;

    public static void initSpigot(JavaPlugin plugin, boolean disableSoftAPIExceptions) {
        spigotPlugin = plugin;
        softAPIExceptions = disableSoftAPIExceptions;
        serverType = ServerType.SPIGOT;
    }

    public static void initBungee(Plugin plugin, boolean disableSoftAPIExceptions) {
        bungeePlugin = plugin;
        softAPIExceptions = disableSoftAPIExceptions;
        serverType = ServerType.BUNGEE;
    }

    public CyberAPI() {
    }

    public static CyberAPI getAPI() {
        return new CyberAPI();
    }

    public ServerType getServerType() {
        return serverType;
    }

    public static JavaPlugin getSpigotPlugin() {
        return spigotPlugin;
    }

    public static Plugin getBungeePlugin() {
        return bungeePlugin;
    }


    public static String getPrefix(boolean error) {
        return (error ? "[CyberAPI Exception]" : "[CyberAPI]");
    }

    public void wrongServerType(ServerType accessed) throws UnknownServerType {
        if(!getServerType().equals(accessed)) {
            logAPIError("Note: You tried accessing a resource that isn't made for your server type! Try accessing only resources used for " + getServerType() + ". Use at your own risk.", true);
            return;
        }
        throw new UnknownServerType("You must specify a valid server type in your onEnable() in order to use the CyberAPI!");
    }

    public void logAPIError(String string, boolean soft) {
        switch(getServerType()) {
            case BUNGEE:
                if(soft) {
                    if (!this.softAPIExceptions) Bungee.get().getLogger().log(Level.WARNING, getPrefix(true) + " A soft API exception occurred: " + string);
                }else{
                    Bungee.get().getLogger().log(Level.SEVERE, getPrefix(true) + " An API exception occurred: " + string);
                }
                break;
            case SPIGOT:
                if(soft) {
                    if (!this.softAPIExceptions) Spigot.get().getLogger().log(Level.WARNING, getPrefix(true) + " A soft API exception occurred: " + string);
                }else{
                    Spigot.get().getLogger().log(Level.SEVERE, getPrefix(true) + " An API exception occurred: " + string);
                }
                break;
            default:
                throw new UnknownServerType("logAPIError failed because the server type could not be accurately detected!");
        }
    }

}
