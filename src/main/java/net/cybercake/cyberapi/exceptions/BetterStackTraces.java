package net.cybercake.cyberapi.exceptions;

import net.cybercake.cyberapi.CyberAPI;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.logging.Level;

public class BetterStackTraces {

    public static ArrayList<String> get(Exception exception) {
        ArrayList<String> stackTrace = new ArrayList<>();
        stackTrace.add(exception.toString());
        for(StackTraceElement element : exception.getStackTrace()) {
            stackTrace.add("  " + element.toString());
        }
        return stackTrace;
    }

    public static void print(Exception exception) {
        for(String str : get(exception)) {
            switch (CyberAPI.serverType) {
                case SPIGOT -> Bukkit.getLogger().severe(str);
                case BUNGEE -> ProxyServer.getInstance().getLogger().severe(str);
            }
        }
    }

    public static void print(Level logLevel, Exception exception) {
        for(String str : get(exception)) {
            switch (CyberAPI.serverType) {
                case SPIGOT -> Bukkit.getLogger().log(logLevel, str);
                case BUNGEE -> ProxyServer.getInstance().getLogger().log(logLevel, str);
            }
        }
    }

}
