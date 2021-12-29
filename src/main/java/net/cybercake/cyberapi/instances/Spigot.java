package net.cybercake.cyberapi.instances;

import net.cybercake.cyberapi.BetterStackTraces;
import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.Log;
import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.player.CyberPlayer;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Spigot extends JavaPlugin {

    public static JavaPlugin get() {
        return CyberAPI.getAPI().getSpigotPlugin();

    }

    //
    // PLAYERS AND CHAT MESSAGING
    //
    public static ArrayList<Player> getOnlinePlayers() {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return new ArrayList<>();

        return new ArrayList<>(get().getServer().getOnlinePlayers()); }
    public static List<CyberPlayer> getOnlineCyberPlayers() {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return new ArrayList<>();

        List<CyberPlayer> ret = new ArrayList<>();
        Spigot.getPlugin(Spigot.class).getServer().getOnlinePlayers().forEach(p -> { ret.add((CyberPlayer) p); });
        return ret;
    }

    public static List<String> getOnlinePlayersUsernames()  {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return new ArrayList<>();

        ArrayList<String> onlinePlayers = new ArrayList<>();
        for(Player player : getOnlinePlayers()) { onlinePlayers.add(player.getName()); }
        return onlinePlayers;
    }

    public static void broadcast(String msg) {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return;

        if(Bukkit.getOnlinePlayers().size() == 0) return;
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(UChat.component(msg)));
        Log.info(UChat.chat(msg));
    }

    public static List<String> chatToList(String... strings) {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return new ArrayList<>();

        ArrayList<String> chat = new ArrayList<>();
        for(String str : strings) { chat.add(UChat.chat(str)); }
        return chat;
    }

    public static void performCommand(CommandSender sender, String command) {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return;

        if(sender instanceof Player) {
            Player player = (Player) sender;

            player.performCommand(command.substring(1));
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(1));
        }
    }

    public static String getPrefix() {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return "";

        return get().getDescription().getPrefix();
    }

    //
    // ERROR HANDLING
    //
    public static void error(CommandSender commandSender, String anErrorOccurred, Exception exception) {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return;

        if(commandSender instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1F, 1F);
        }
        commandSender.sendMessage(UChat.component("&cAn error occurred " + anErrorOccurred.replace("{name}", "you") + "&c: &8" + exception));
        Log.error("An error occurred " + anErrorOccurred.replace("{name}", commandSender.getName()) + ". Stack trace below:");
        Log.error(" ");
        BetterStackTraces.print(exception);
    }

    //
    // BUNGEECORD
    //
    public static boolean isBungeeOnline(int port) {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return false;

        try {
            Socket s = new Socket("localhost", port);
            s.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //
    // MISC
    //
    public static @Nullable World getMainWorld() {
        Properties pr = new Properties();
        try {
            FileInputStream in = new FileInputStream("server.properties");
            pr.load(in);
            String string = pr.getProperty("level-name");
            return Bukkit.getWorld(string);
        }
        catch (IOException ignored) { }
        return null;
    }
    public static void playSound(CommandSender sender, Sound sound, float volume, float pitch) {
        if(CyberAPI.getAPI().getSpigotPlugin() == null) return;

        if(sender instanceof Player player) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    public static double getOnlyXZDistance(Location loc1, Location loc2) {
        return Math.sqrt(NumberConversions.square(loc1.getX() - loc2.getX()) + NumberConversions.square(loc1.getZ() - loc2.getZ()));
    }

    //
    // CONSOLE LOGGING
    //
    public static void registerCommand(String name, CommandExecutor commandExecutor) { Spigot.get().getCommand(name).setExecutor(commandExecutor); }
    public static void registerTabCompleter(String name, TabCompleter tabCompleter) { Spigot.get().getCommand(name).setTabCompleter(tabCompleter); }
    public static void registerListener(Listener listener) { Spigot.get().getServer().getPluginManager().registerEvents(listener, Spigot.get()); }
    public static int registerRunnableInt(Runnable runnable, long period) { return Bukkit.getScheduler().scheduleSyncRepeatingTask(Spigot.get(), runnable, 10L, period); }
    public static void registerRunnable(Runnable runnable, long period) { Bukkit.getScheduler().scheduleSyncRepeatingTask(Spigot.get(), runnable, 10L, period); }

}
