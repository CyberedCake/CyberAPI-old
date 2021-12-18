package net.cybercake.cyberapi.instances;

import net.cybercake.cyberapi.BetterStackTraces;
import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.Log;
import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.player.CyberProxyPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Bungee extends Plugin {

    public static Plugin get() {
        return CyberAPI.getAPI().getBungeePlugin();
    }

    //
    // BUNGEE SERVERS AND BUNGEE LISTING
    //
    public static boolean isBungeeOnline(int port) {
        try {
            Socket s = new Socket("localhost", port);
            s.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //
    // PLAYERS AND CHAT MESSAGING
    //
    public static ArrayList<ProxiedPlayer> getOnlinePlayers() { return new ArrayList<>(ProxyServer.getInstance().getPlayers()); }
    public static List<CyberProxyPlayer> getOnlineCyberPlayers() {
        List<CyberProxyPlayer> ret = new ArrayList<>();
        ProxyServer.getInstance().getPlayers().forEach(p -> { ret.add((CyberProxyPlayer) p); });
        return ret;
    }
    public static List<String> getOnlinePlayersUsernames()  {
        ArrayList<String> onlinePlayers = new ArrayList<>();
        for(ProxiedPlayer player : getOnlinePlayers()) { onlinePlayers.add(player.getName()); }
        return onlinePlayers;
    }
    public static void broadcast(String msg) {
        getOnlinePlayers().forEach(p -> {
            p.sendMessage(new TextComponent(UChat.chat(msg)));
        });
        Log.info(msg);
    }

    //
    // CONSOLE
    //

    public static net.md_5.bungee.api.ProxyServer getInstance()
    {
        return net.md_5.bungee.api.ProxyServer.getInstance();
    }

    //
    // ERROR HANDLING
    //
    public static void error(CommandSender commandSender, String anErrorOccurred, Exception exception) {
        commandSender.sendMessage(UChat.bComponent("&cAn error occurred " + anErrorOccurred.replace("{name}", "you") + "&c: &8" + exception));
        Log.error("An error occurred " + anErrorOccurred.replace("{name}", commandSender.getName()) + ". Stack trace below:");
        Log.error(" ");
        BetterStackTraces.print(exception);
    }

}
