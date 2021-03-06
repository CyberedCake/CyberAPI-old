package net.cybercake.cyberapi.instances;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.Log;
import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.exceptions.BetterStackTraces;
import net.cybercake.cyberapi.player.CyberPlayer;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class Spigot extends JavaPlugin {

    public static JavaPlugin getPlugin() {
        return CyberAPI.getAPI().getSpigotPlugin();
    }

    //
    // PLAYERS AND CHAT MESSAGING
    //
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        try {
            player.showTitle(Title.title(UChat.component(title), UChat.component(subtitle), Title.Times.times(Duration.ofMillis(fadeIn* 50L), Duration.ofMillis(stay* 50L), Duration.ofMillis(fadeOut* 50L))));
        } catch (Exception exception) {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }
    public static void sendTitle(Player player, String title, String subtitle) { sendTitle(player, title, subtitle, 20, 100, 20); }

    public static ArrayList<Player> getOnlinePlayers() {
        if(getPlugin() == null) return new ArrayList<>();

        return new ArrayList<>(getPlugin().getServer().getOnlinePlayers()); }
    public static List<CyberPlayer> getOnlineCyberPlayers() {
        if(getPlugin() == null) return new ArrayList<>();

        List<CyberPlayer> ret = new ArrayList<>();
        Spigot.getPlugin(Spigot.class).getServer().getOnlinePlayers().forEach(p -> { ret.add((CyberPlayer) p); });
        return ret;
    }

    public static List<String> getOnlinePlayersUsernames()  {
        if(getPlugin() == null) return new ArrayList<>();

        ArrayList<String> onlinePlayers = new ArrayList<>();
        for(Player player : getOnlinePlayers()) { onlinePlayers.add(player.getName()); }
        return onlinePlayers;
    }

    public static void broadcast(String msg) {
        if(getPlugin() == null) return;

        if(Bukkit.getOnlinePlayers().size() == 0) return;
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(UChat.component(msg)));
        Log.info(UChat.chat(msg));
    }

    public static List<String> chatToList(String... strings) {
        if(getPlugin() == null) return new ArrayList<>();

        ArrayList<String> chat = new ArrayList<>();
        for(String str : strings) { chat.add(UChat.chat(str)); }
        return chat;
    }

    public static void performCommand(CommandSender sender, String command) {
        if(getPlugin() == null) return;

        if(sender instanceof Player) {
            Player player = (Player) sender;

            player.performCommand(command.substring(1));
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(1));
        }
    }

    public static String getPrefix() {
        if(getPlugin() == null) return "";

        return getPlugin().getDescription().getPrefix();
    }

    //
    // ERROR HANDLING
    //
    public static void error(CommandSender commandSender, String anErrorOccurred, Exception exception) {
        if(getPlugin() == null) return;

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
        if(getPlugin() == null) return false;

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
        if(getPlugin() == null) return;

        if(sender instanceof Player player) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    public static double getOnlyXZDistance(Location loc1, Location loc2) {
        return Math.sqrt(NumberConversions.square(loc1.getX() - loc2.getX()) + NumberConversions.square(loc1.getZ() - loc2.getZ()));
    }
    public static FileConfiguration getMainConfig() {
        return Spigot.getPlugin().getConfig();
    }
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

    //
    // LOCATION
    //
    public static Location getTopBlock(Location checkEmpty) {
        return getTopBlock(checkEmpty, 319);
    }

    public static Location getTopBlock(Location checkEmpty, long yStartChecking) {
        checkEmpty.setY(yStartChecking);
        for(int i=0; i<yStartChecking; i++) {
            if(checkEmpty.getWorld().getBlockAt(checkEmpty).isEmpty()) {
                checkEmpty.setY(checkEmpty.getY() - 1);
            }else if(!checkEmpty.getWorld().getBlockAt(new Location(checkEmpty.getWorld(), checkEmpty.getX(), checkEmpty.getY()+1, checkEmpty.getZ(), checkEmpty.getYaw(), checkEmpty.getPitch())).isEmpty()) {
                checkEmpty.setY(checkEmpty.getY() - 1);
            }else if(!checkEmpty.getWorld().getBlockAt(checkEmpty).isEmpty()) {
                checkEmpty.setY(checkEmpty.getY()+1);
                return checkEmpty;
            }
        }
        return null;
    }

    //
    // RECIPES
    //

    /**
     * If anyone has any better idea on how to do this, lemme know
     *
     * This code has been taken from: https://www.spigotmc.org/threads/learn-all-recipes.450378/
     * @return list of namespace keys that contain all recipes
     */
    public static List<NamespacedKey> getAllRegisteredRecipes() {
        List<NamespacedKey> recipeKeys = new ArrayList<>();
        Spigot.getPlugin().getServer().recipeIterator().forEachRemaining(recipe -> {
            if(recipe instanceof ShapedRecipe shaped) {
                recipeKeys.add(shaped.getKey());
            }else if(recipe instanceof ShapelessRecipe shapeless) {
                recipeKeys.add(shapeless.getKey());
            }
        });
        return recipeKeys;
    }

    //
    // CONSOLE LOGGING
    //
    public static void registerCommand(String name, CommandExecutor commandExecutor) { Spigot.getPlugin().getCommand(name).setExecutor(commandExecutor); }
    public static void registerTabCompleter(String name, TabCompleter tabCompleter) { Spigot.getPlugin().getCommand(name).setTabCompleter(tabCompleter); }
    public static void registerListener(Listener listener) { Spigot.getPlugin().getServer().getPluginManager().registerEvents(listener, Spigot.getPlugin()); }
    public static int registerRunnableInt(Runnable runnable, long period) { return Bukkit.getScheduler().scheduleSyncRepeatingTask(Spigot.getPlugin(), runnable, 10L, period); }
    public static void registerRunnable(Runnable runnable, long period) { Bukkit.getScheduler().scheduleSyncRepeatingTask(Spigot.getPlugin(), runnable, 10L, period); }

}
