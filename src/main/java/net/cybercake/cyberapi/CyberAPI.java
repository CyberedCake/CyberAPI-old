package net.cybercake.cyberapi;

import net.cybercake.cyberapi.instances.Bungee;
import net.cybercake.cyberapi.instances.Spigot;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CyberAPI  {

    // version variables are now in 'version.txt'

    public enum ServerType {
        SPIGOT, BUNGEE
    }
    private static Plugin bungeePlugin;
    private static String bungeePrefix = "";

    private static JavaPlugin spigotPlugin;

    private static ServerType serverType;
    private static File dataFolder;

    private static boolean isCyberNet = false;
    private static boolean prefixInLogs = true;

    private static Boolean silence = null;

    private static String latestVersion = "unknown";
    private static int latestProtocol = 0;

    public void sendStartupMessage() {
        switch(serverType) {
            case BUNGEE -> sendStartupMessage(Bungee.getPlugin().getDescription().getName());
            case SPIGOT -> sendStartupMessage(Spigot.getPlugin().getDescription().getName());
        }
        versionCheck();
    }

    private static void sendStartupMessage(String pluginName) {
        String typeFormatted = null;
        switch(CyberAPI.getAPI().getServerType()) {
            case SPIGOT -> typeFormatted = "&aSPIGOT";
            case BUNGEE -> typeFormatted = "&9BUNGEE";
        }
        String msg = "&rThe plugin &c" + pluginName + " &ris using CyberAPI version &e" + getAPI().getVersion() + " &6(" + getAPI().getProtocol() + ")&r! The plugin's type is " + typeFormatted + "&r, as marked by the plugin developer.";

        CyberAPI.getAPI().logAPI(APILevel.INFO, msg);
        getAPI().versionCheck();
    }

    public static void initSpigot(@NotNull JavaPlugin plugin) {
        initSpigot(plugin, true);
    }

    public static void initSpigot(@NotNull JavaPlugin plugin, boolean sendPrefixInLogs) {
        CyberAPI.getAPI().setServerType(CyberAPI.ServerType.SPIGOT);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        spigotPlugin = plugin;
        prefixInLogs = sendPrefixInLogs;
        if(silence == null) silence = false;
        sendStartupMessage(plugin.getDescription().getName());
    }

    public static void initBungee(@NotNull Plugin plugin) {
        initBungee(plugin, plugin.getDescription().getName(), true, false);
    }

    public static void initBungee(@NotNull Plugin plugin, String prefix) { initBungee(plugin, prefix, true, false);}

    public static void initBungee(@NotNull Plugin plugin, String prefix, boolean sendPrefixInLogs) { initBungee(plugin, plugin.getDescription().getName(), true, false);}

    public static void initBungee(@NotNull Plugin plugin, String prefix, boolean sendPrefixInLogs, boolean silenceLogs) {
        CyberAPI.getAPI().setServerType(CyberAPI.ServerType.BUNGEE);
        bungeePlugin = plugin;
        bungeePrefix = prefix;
        prefixInLogs = sendPrefixInLogs;
        if(silence == null) silence = false;
        sendStartupMessage(plugin.getDescription().getName());
    }

    /**
     * Show version checking and how far behind the CyberAPI is. You must put this before the init though!
     */
    public static void silenceLogs(boolean silenceLogs) {
        CyberAPI.silence = silenceLogs;
    }



    private CyberAPI() {}



    /**
     * Get the API instance
     * @return The API instance
     */
    public static CyberAPI getAPI() {
        return new CyberAPI();
    }

    /**
     * Get the bungeecord prefix as defined in {@link CyberAPI#initBungee(Plugin, String, boolean) CyberAPI.initBungee(Plugin, String, boolean)}
     * @return the string version of the bungee prefix
     */
    public String getBungeePrefix() { return bungeePrefix; }

    /**
     * Get the server type in the form of an Enumeration
     * @return the server type (ServerType.SPIGOT or ServerType.BUNGEE)
     */
    public ServerType getServerType() {
        return serverType;
    }

    private void setServerType(ServerType serverType) { CyberAPI.serverType = serverType; }

    /**
     * Prints [CyberAPI Error] if it's errored and [CyberAPI] if it's not errored (space after bracket not included!)
     * @param error if it is an error the prefix is display on
     * @return the prefix for CyberAPI
     */
    public String getPrefix(boolean error) {
        return (error ? "[CyberAPI Error]" : getPrefix());
    }

    /**
     * returns [CyberAPI] (space after bracket not included!)
     * @return the prefix for CyberAPI
     */
    public String getPrefix() {
        return "[CyberAPI]";
    }

    /**
     * Get the version TXT inside CyberAPI.
     * @return BufferedReader of the /resources/version.txt inside CyberAPI
     */
    public BufferedReader getVersionTxt() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("version.txt");
        if(stream == null) return null;

        return new BufferedReader(new InputStreamReader(stream));
    }

    /**
     * Get the version TXT inside CyberAPI in the form of a List of strings
     * @return List of Strings of the /resources/version.txt inside CyberAPI
     */
    public List<String> getVersionTxtLines()  {
        BufferedReader reader = getVersionTxt();
        if(reader == null) {
            return Arrays.asList("unknown, value is null", "unknown, value is null");
        }
        List<String> list = new ArrayList<>();
        String line;

        try {
            while((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException ioException) {
            logAPI(APILevel.ERROR, "An IOException has occurred whilst trying to get version TXT lines: &8" + ioException);
        }

        return list;
    }

    public String getVersion() {
        return getVersionTxtLines().get(0).replace("version=", "");
    }

    public int getProtocol() {
        return getVersionTxtLines().get(1).equalsIgnoreCase("unknown, value is null") ? -1 : Integer.parseInt(getVersionTxtLines().get(1).replace("protocol=", ""));
    }

    public enum APILevel {
        INFO, WARN, ERROR
    }

    public void logAPI(APILevel apiLevel, String msg) {
        if(silence) return;

        switch(getServerType()) {
            case BUNGEE -> msg = msg.replace("{plugin}", Bungee.getPlugin().getDescription().getName());
            case SPIGOT -> msg = msg.replace("{plugin}", Spigot.getPlugin().getDescription().getName());
        }
        switch(apiLevel) {
            case INFO -> Log.info("&d" + getAPI().getPrefix() + " &r" + msg);
            case WARN -> Log.warn("&d" + getAPI().getPrefix() + " &e" + msg);
            case ERROR -> Log.error("&d" + getAPI().getPrefix(true) + " &c" + msg);
            default -> System.out.println(getAPI().getPrefix() + " " + msg);
        }
    }

    public void logAPIError(APILevel apiLevel, Exception exception) {
        logAPI(apiLevel, "  " + exception.toString());
        for(StackTraceElement stackTraceElement : exception.getStackTrace()) {
            logAPI(apiLevel, "    " + stackTraceElement.toString());
        }
    }

    public String getPluginName() {
        switch(getServerType()) {
            case SPIGOT -> {
                return Spigot.getPlugin().getDescription().getName();
            }
            case BUNGEE -> {
                return Bungee.getPlugin().getDescription().getName();
            }
        }
        return null;
    }

    public boolean showPrefixInLogs() {
        return prefixInLogs;
    }
    public void versionCheck() {
        if(silence) return;

        try {
            URL url = new URL("https://raw.githubusercontent.com/CyberedCake/CyberAPI/main/src/main/resources/version.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;
            while((inputLine = reader.readLine()) != null) {
                if(inputLine.startsWith("version=")) {
                    latestVersion = inputLine.replace("version=", "");
                }else if(inputLine.startsWith("protocol=")) {
                    latestProtocol = Integer.parseInt(inputLine.replace("protocol=", ""));
                }
            }
        } catch (Exception exception) {
            logAPI(APILevel.ERROR, "Failed version checking for CyberAPI version " + getVersion() + "! " + ChatColor.DARK_GRAY + exception); return;
        }

        if(getProtocol() != latestProtocol) {
            logAPI(APILevel.WARN, "CyberAPI for " + getAPI().getPluginName() + " is outdated! The latest version is " + ChatColor.GREEN + latestVersion + ChatColor.YELLOW + ", your version is " + getVersion() + "!");
            if (latestProtocol - getProtocol() > 0) {
                logAPI(APILevel.WARN, "CyberAPI for " + getAPI().getPluginName() + " is " + ChatColor.RED + (latestProtocol - getProtocol()) + " version(s) " + ChatColor.YELLOW + "behind!");
                logAPI(APILevel.WARN, "Tell the author of " + getAPI().getPluginName() + " that they can download the latest version of CyberAPI at " + ChatColor.LIGHT_PURPLE + getDownloadLink() + ChatColor.YELLOW + "!");
            }
        }
    }
    public Plugin getBungeePlugin() {
        return bungeePlugin;
    }

    public JavaPlugin getSpigotPlugin() {
        return spigotPlugin;
    }



    public String getDownloadLink() {
        return "https://github.com/CyberedCake/CyberAPI/releases/latest";
    }

    public File getDataFolder() { return dataFolder; }

    /**
     * This basically has no effect on normal users. This is a CyberNet-only feature and basically provides you with nothing other than a few methods that aren't even useful
     * if your plugin does not run on CyberNet!
     */
    public void setCyberNet(JavaPlugin plugin, File file) {
        if(plugin.getDescription().getName().contains("CyberNet")) {
            CyberAPI.isCyberNet = true;
            CyberAPI.dataFolder = file;

            logAPI(APILevel.INFO, "&aCyberAPI is running on a CyberNet server!");
        }
    }

    /**
     * This basically has no effect on normal users. This is a CyberNet-only feature and basically provides you with nothing other than a few methods that aren't even useful
     * if your plugin does not run on CyberNet!
     */
    public void setCyberNet(Plugin plugin, File file) {
        if(plugin.getDescription().getName().contains("CyberNet")) {
            CyberAPI.isCyberNet = true;
            CyberAPI.dataFolder = file;

            logAPI(APILevel.INFO, "&aCyberAPI is running on a CyberNet server!");
        }
    }

    /**
     * This basically has no effect on normal users. This is a CyberNet-only feature and basically provides you with nothing other than a few methods that aren't even useful
     * if your plugin does not run on CyberNet!
     */
    public boolean isCyberNet() {
        return isCyberNet;
    }

}
