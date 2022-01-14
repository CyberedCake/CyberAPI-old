package net.cybercake.cyberapi.instances;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.Log;
import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.exceptions.BetterStackTraces;
import net.cybercake.cyberapi.player.CyberBungeePlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Bungee extends Plugin {

    public static Plugin get() {
        return CyberAPI.bungeePlugin;
    }

    //
    // BUNGEE SERVERS AND BUNGEE LISTING
    //
    public static boolean isServerOnline(int port) {
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
    public static List<CyberBungeePlayer> getOnlineCyberPlayers() {
        List<CyberBungeePlayer> ret = new ArrayList<>();
        ProxyServer.getInstance().getPlayers().forEach(p -> { ret.add((CyberBungeePlayer) p); });
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

    public static net.md_5.bungee.api.ProxyServer getInstance() {
        return net.md_5.bungee.api.ProxyServer.getInstance();
    }
    public static String getPrefix() {
        return CyberAPI.getAPI().getBungeePrefix();
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

    //
    // CONSOLE LOGGING
    //
    public static void registerCommand(Command commandExecutor) { ProxyServer.getInstance().getPluginManager().registerCommand(Bungee.get(), commandExecutor); }
    public static void registerListener(Listener listener) { ProxyServer.getInstance().getPluginManager().registerListener(Bungee.get(), listener); }
    public static void registerRunnable(Runnable runnable, long period) { ProxyServer.getInstance().getScheduler().schedule(Bungee.get(), runnable, 500, period, TimeUnit.MILLISECONDS); }

    //
    // MISC
    //
    public static String getUUID(String name) {
        String uuid = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream()));
            uuid = (((JsonObject)new JsonParser().parse(in)).get("id")).toString().replaceAll("\"", "");
            uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            in.close();
        } catch (Exception e) {
            System.out.println("Unable to get UUID of: " + name + "!");
            uuid = null;
        }
        return uuid;
    }

    public static String getName(UUID uuid) {
        String name = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid).openStream()));
            name = (((JsonObject)new JsonParser().parse(in)).get("name")).toString().replaceAll("\"", "");
            in.close();
        } catch (Exception e) {
            System.out.println("Unable to get Name of: " + name + "!");
            name = null;
        }
        return name;
    }

}
