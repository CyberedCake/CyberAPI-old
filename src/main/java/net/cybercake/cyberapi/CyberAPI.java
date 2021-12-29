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

    public ServerType serverType;
    public JavaPlugin spigotPlugin;
    public Plugin bungeePlugin;
    public String bungeePrefix;

    private static boolean softAPIExceptions;

    public void initSpigot(JavaPlugin plugin, boolean disableSoftAPIExceptions) {
        spigotPlugin = plugin;
        softAPIExceptions = disableSoftAPIExceptions;
        serverType = ServerType.SPIGOT;
    }

    public void initBungee(Plugin plugin, boolean disableSoftAPIExceptions, String prefix) {
        bungeePlugin = plugin;
        softAPIExceptions = disableSoftAPIExceptions;
        serverType = ServerType.BUNGEE;
        bungeePrefix = prefix;
    }

    public CyberAPI() {
    }

    public static CyberAPI getAPI() {
        return new CyberAPI();
    }

    public String getBungeePrefix() { return bungeePrefix; }

    public ServerType getServerType() {
        return serverType;
    }

    public JavaPlugin getSpigotPlugin() {
        return spigotPlugin;
    }

    public Plugin getBungeePlugin() {
        return bungeePlugin;
    }


    public String getPrefix(boolean error) {
        return (error ? "[CyberAPI Exception]" : "[CyberAPI]");
    }

    public void logAPIError(String string, boolean soft) {
        switch(getServerType()) {
            case BUNGEE:
                if(soft) {
                    if (!softAPIExceptions) Bungee.get().getLogger().log(Level.WARNING, getPrefix(true) + " A soft API exception occurred: " + string);
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
