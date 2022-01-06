package net.cybercake.cyberapi.exceptions;

import net.cybercake.cyberapi.CyberAPI;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class BetterStackTraces {


    public static ArrayList<String> get(Exception e) {
        ArrayList<String> stackTrace = new ArrayList<>();
        stackTrace.add("  " + e.toString());
        for(StackTraceElement element : e.getStackTrace()) {
            stackTrace.add("    " + element.toString());
        }
        return stackTrace;
    }

    public static void print(Exception e) {
        for(String str : get(e)) {
            if(CyberAPI.serverType.equals(CyberAPI.ServerType.SPIGOT)) {
                Bukkit.getLogger().severe(str);
            }else{
                ProxyServer.getInstance().getLogger().severe(str);
            }
        }
    }

}
