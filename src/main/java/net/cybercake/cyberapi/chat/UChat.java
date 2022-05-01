package net.cybercake.cyberapi.chat;

import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.Log;
import net.cybercake.cyberapi.instances.Bungee;
import net.cybercake.cyberapi.instances.Spigot;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UChat {

    //
    // COLORING AND COMPONENTS
    //

    public static String chat(String msg) { return chat('&', msg); }
    public static String chat(Character alt, String msg) { return ChatColor.translateAlternateColorCodes(alt, msg); }
    public static ArrayList<String> listChat(String... message) {
        ArrayList<String> strings = new ArrayList<>();
        for(String msg : message) {
            strings.add(chat(msg));
        }
        return strings;
    }
    public static ArrayList<String> listChat(Character alt, String... message) {
        ArrayList<String> strings = new ArrayList<>();
        for(String msg : message) {
            strings.add(chat(alt, msg));
        }
        return strings;
    }
    public static ArrayList<Component> listComponent(String... message) {
        ArrayList<Component> strings = new ArrayList<>();
        for(String msg : message) {
            strings.add(component(msg));
        }
        return strings;
    }
    public static ArrayList<Component> listComponent(Character alt, String... message) {
        ArrayList<Component> strings = new ArrayList<>();
        for(String msg : message) {
            strings.add(component(alt, msg));
        }
        return strings;
    }
    public static Content content(String msg) { return new Text(chat(msg)); }
    public static Content content(Character alt, String msg) { return new Text(chat(alt, msg)); }
    public static Content text(String msg) { return new Text(chat(msg)); }
    public static Content text(Character alt, String msg) { return new Text(chat(alt, msg)); }
    public static HoverEvent hover(String msg) { return new HoverEvent(HoverEvent.Action.SHOW_TEXT, text(msg)); }
    public static HoverEvent hover(Character alt, String msg) { return new HoverEvent(HoverEvent.Action.SHOW_TEXT, text(alt, msg)); }
    public static ClickEvent click(String msg) { return new ClickEvent(ClickEvent.Action.RUN_COMMAND, msg); }
    public static String getSeperator(ChatColor color) {
        return getSeperator(color, 80);
    }
    public static String getSeperator(ChatColor color, int characters) {
        if(characters <= 0) {
            return null;
        } else if(characters >= 600) {
            return null;
        } else {
            String seperators = "";
            for(int i=0; i<characters; i++) {
                seperators = UChat.chat(seperators + color + "&m ");
            }
            return seperators;
        }
    }
    public static String getSeperator(org.bukkit.ChatColor color) { return getSeperator(color, 80); }
    public static String getSeperator(org.bukkit.ChatColor color, int characters) {
        if(characters <= 0) {
            return null;
        } else if(characters >= 600) {
            return null;
        } else {
            String seperators = "";
            for(int i=0; i<characters; i++) {
                seperators = UChat.chat(seperators + color + "&m ");
            }
            return seperators;
        }
    }
    public static String getProgressBar(ChatColor used, ChatColor unused, double percentage, String spaceCharacter) {
        return getProgressBar(used, unused, percentage, spaceCharacter, 30);
    }

    public static String getProgressBar(ChatColor used, ChatColor unused, double percentage, String spaceCharacter, int characters) {
        StringBuilder progress = new StringBuilder(UChat.chat(""));
        double percentageSoFar = 0.0;
        for(double i=0; i<characters; i++) {
            if(percentage > percentageSoFar) {
                progress.append(used).append(spaceCharacter);
            }else if(percentage <= percentageSoFar) {
                progress.append(unused).append(spaceCharacter);
            }else{
                progress.append("&8&m").append(spaceCharacter);
            }
            percentageSoFar = i / characters;
        }
        return progress.toString();
    }

    //
    // Spigot vvv
    //

    /**
     * Gives you an adventure 'Component' that can be used better in spigot-fork sendMessage or other related
     * messages.
     * @apiNote This is a PAPERSPIGOT only method!
     * @param msg the message to send
     * @return an adventure Component to be used in spigot-related messages
     */
    public static Component component(String msg) { return component('&', msg); }

    /**
     * Gives you an adventure 'Component' that can be used better in spigot-fork sendMessage or other related
     * messages. The alt code is the same as 'ChatColor.translateAltColorCodes(right here is usually the alt, ...)'
     * @apiNote This is a PAPERSPIGOT only method!
     * @param alt the alt color code
     * @param msg the message to send
     * @return an adventure Component to be used in spigot-related messages
     */
    public static Component component(Character alt, String msg) { return Component.text(chat(alt, msg)); }

    /**
     * Paginate strings into different lines based on length. This means that strings will be put on a separate item in a
     * list if the line length does not match. You can set the line length by doing the same method but adding a number to
     * the end.
     * @param string the string you want to input, will be split up accordingly
     * @return returns a list in which every item is a string
     * @apiNote This is a SPIGOT only method!
     */
    public static List<String> paginate(String string, int lineLength) {
        ArrayList<String> pagination = new ArrayList<>();
        for(String str : ChatPaginator.wordWrap(string, lineLength)) {
            pagination.add(UChat.chat(str));
        }
        return pagination;
    }

    /**
     * Paginate strings into different lines based on length. This means that strings will be put on a separate item in a
     * list if the line length does not match. You can set the line length by doing the same method but adding a number to
     * the end.
     * @param string the string you want to input, will be split up accordingly
     * @return returns a list in which every item is a string
     * @apiNote This is a SPIGOT only method!
     */
    public static List<String> paginate(String string) {
        return paginate(string, 30);
    }

    /**
     * Paginate components rather than strings. This means that components will be put on a separate item in a list if
     * the line length does not match.
     * @param string the string you want to input, will be split up accordingly
     * @param lineLength the amount of length the {@link ChatPaginator bukkit ChatPaginator} will take before wrapping the line
     * @return returns a list in which every item is an adventure component
     * @apiNote This is a PAPERSPIGOT only method!
     */
    public static List<Component> paginateComponents(String string, int lineLength) {
        ArrayList<Component> pagination = new ArrayList<>();
        for(String str : ChatPaginator.wordWrap(UChat.chat(string), lineLength)) {
            pagination.add(UChat.component(str));
        }
        return pagination;
    }

    /**
     * Paginate components rather than strings. This means that components will be put on a separate item in a list if
     * the line length does not match. You can set the line length by doing the same method but adding a number to the end.
     * @param string the string you want to input, will be split up accordingly
     * @return returns a list in which every item is an adventure component
     * @apiNote This is a PAPERSPIGOT only method!
     */
    public static List<Component> paginateComponents(String string) {
        return paginateComponents(string, 30);
    }

    //
    // BUNGEECORD
    //

    /**
     * Gives you a bungeecord 'BaseComponent' that can be used better in bungee-fork sendMessage or other related
     * messages. The 'b' at the beginning of this method's name (bComponent) is to signify Bungeecord
     * @apiNote This is a BUNGEECORD only method!
     * @param msg the message to send
     * @return a BaseComponent to be used in bungee-related messages
     */
    public static BaseComponent bComponent(String msg) { return bComponent('&', msg); }

    /**
     * Gives you a bungeecord 'BaseComponent' that can be used better in bungee-fork sendMessage or other related
     * messages. The 'b' at the beginning of this method's name (bComponent) is to signify Bungeecord. The alt code
     * is the same as 'ChatColor.translateAltColorCodes(right here is usually the alt, ...)'
     * @apiNote This is a BUNGEECORD only method!
     * @param alt the alt color code
     * @param msg the message to send
     * @return a BaseComponent to be used in bungee-related messages
     */
    public static BaseComponent bComponent(Character alt, String msg) { return new TextComponent(chat(alt, msg)); }

    //
    // MISC
    //

    public static String clearChat() { return clearChat(999); }

    public static String clearChat(int loopAmount) {
        return " \n".repeat(Math.max(0, loopAmount));
    }

    public static void clearChatSpigot(Player player) {
        player.sendMessage(clearChat());
    }

    public static void clearChatBungee(ProxiedPlayer player) {
        player.sendMessage(bComponent(clearChat()));
    }

    /**
     * Broadcasts a message based on if the plugin type is a fork of bungeecord or fork of
     * spigot. Includes color and logs to console in color as well.
     * @param msg the message you want to broadcast to the entire server/servers
     * @param permission The permission required to send to the player
     */
    public static void broadcast(String msg, String permission) {
        boolean noPermissionSet = permission.strip().toLowerCase(Locale.ROOT).equalsIgnoreCase("");
        switch (CyberAPI.getAPI().getServerType()) {
            case BUNGEE -> {
                for(ProxiedPlayer player : Bungee.getOnlinePlayers()) {
                    if(noPermissionSet || player.hasPermission(permission)) player.sendMessage(bComponent(msg));
                }
                Log.info(chat(msg));
            }
            case SPIGOT -> {
                for(Player player : Spigot.getOnlinePlayers()) {
                    if(noPermissionSet || player.hasPermission(permission)) player.sendMessage(chat(msg));
                }
                Log.info(chat(msg));
            }
            default -> System.out.println("Couldn't determine server type!");
        }
    }

    /**
     * Broadcasts a message based on if the plugin type is a fork of bungeecord or fork of
     * spigot. Includes color and logs to console in color as well.
     * @param msg the message you want to broadcast to the entire server/servers
     */
    public static void broadcast(String msg) {
        broadcast(msg, "");
    }


}
