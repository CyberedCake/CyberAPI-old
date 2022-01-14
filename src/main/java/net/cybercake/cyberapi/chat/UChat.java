package net.cybercake.cyberapi.chat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.Log;
import net.cybercake.cyberapi.instances.Bungee;
import net.cybercake.cyberapi.instances.Spigot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.util.ChatPaginator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public static String getProgressBar(ChatColor used, ChatColor unused, double percentage, String spaceCharacter) {
        return getProgressBar(used, unused, percentage, spaceCharacter, 30);
    }

    public static String getProgressBar(ChatColor used, ChatColor unused, double percentage, String spaceCharacter, int characters) {
        String progress = UChat.chat("");
        double percentageSoFar = 0.0;
        for(double i=0; i<characters; i++) {
            if(percentage > percentageSoFar) {
                progress = progress + used + spaceCharacter;
            }else if(percentage <= percentageSoFar) {
                progress = progress + unused + spaceCharacter;
            }else{
                progress = progress + "&8&m" + spaceCharacter;
            }
            percentageSoFar = i / characters;
        }
        return progress;
    }
    // Spigot vvv
    public static Component component(String msg) { return component('&', msg); }
    public static Component component(Character alt, String msg) { return LegacyComponentSerializer.legacy(alt).deserialize(msg); }
    @Deprecated public static String spigotGetCenteredMessage(String message) {
        return CenteredMessage.get(message, 154);
    }
    @Deprecated public static void spigotSendCenteredMessage(org.bukkit.command.CommandSender player, String message) {
        player.sendMessage(UChat.component(CenteredMessage.get(message, 154)));
    }
    public static List<String> paginate(String string, int lineLength) {
        ArrayList<String> pagination = new ArrayList<>();
        for(String str : ChatPaginator.wordWrap(string, lineLength)) {
            pagination.add(UChat.chat(str));
        }
        return pagination;
    }

    public static List<String> paginate(String string) {
        return paginate(string, 30);
    }

    public static List<Component> paginateComponents(String string, int lineLength) {
        ArrayList<Component> pagination = new ArrayList<>();
        for(String str : ChatPaginator.wordWrap(UChat.chat(string), lineLength)) {
            pagination.add(UChat.component(str));
        }
        return pagination;
    }

    public static List<Component> paginateComponents(String string) {
        return paginateComponents(string, 30);
    }
    // Bungee vvv
    public static BaseComponent bComponent(String msg) { return bComponent('&', msg); }
    public static BaseComponent bComponent(Character alt, String msg) { return new TextComponent(chat(alt, msg)); }
    @Deprecated public static String bungeeGetCenteredMessage(String message) {
        return CenteredMessage.get(message, 154);
    }
    @Deprecated public static void bungeeSendCenteredMessage(CommandSender player, String message) {
        player.sendMessage(UChat.bComponent(CenteredMessage.get(message, 154)));
    }

    //
    // MISC
    //
    public static ArrayList<String> emptyList(){
        return new ArrayList<>(); }
    public static String pluralize(String message, int value) {
        String ret = message.replaceAll("!#", String.valueOf(value));
        ret = ret.replaceAll("!s", ((value == 1)?"":"s"));        // sword | swords
        ret = ret.replaceAll("!es", ((value == 1)?"":"es"));      // bus | buses
        ret = ret.replaceAll("!ies", ((value == 1)?"y":"ies"));   // penny | pennies
        ret = ret.replaceAll("!oo", ((value == 1)?"oo":"ee"));    // tooth | teeth
        ret = ret.replaceAll("!an", ((value == 1)?"an":"en"));    // woman | women
        ret = ret.replaceAll("!us", ((value == 1)?"us":"i"));     // cactus | cacti
        ret = ret.replaceAll("!is", ((value == 1)?"is":"es"));    // analysis | analyses
        ret = ret.replaceAll("!o", ((value == 1)?"o":"oes"));     // potato | potatoes
        ret = ret.replaceAll("!on", ((value == 1)?"a":"on"));     // criteria | criterion
        ret = ret.replaceAll("!lf", ((value == 1)?"lf":"lves"));  // elf | elves
        ret = ret.replaceAll("!ia", ((value == 1)?"is":"are"));
        ret = ret.replaceAll("!ww", ((value == 1)?"was":"were"));
        return ret;
    }
    public static void broadcast(String msg) {
        switch(CyberAPI.serverType) {
            case BUNGEE:
                Bungee.getOnlinePlayers().forEach(player -> player.sendMessage(bComponent(msg)));
                Log.info(chat(msg));
                break;
            case SPIGOT:
                Spigot.getOnlinePlayers().forEach(player -> player.sendMessage(component(msg)));
                Log.info(chat(msg));
                break;
            default:
                System.out.println("Couldn't determine server type!");
        }
    }

    @Deprecated
    public static String getUUID(String name) {
        String uuid = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream()));
            uuid = (((JsonObject)new JsonParser().parse(in)).get("id")).toString().replaceAll("\"", "");
            uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            in.close();
        } catch (Exception e) {
            System.out.println("Unable to get UUID of: " + name + "!");
            uuid = "er";
        }
        return uuid;
    }

    @Deprecated
    public static String getName(UUID uuid) {
        String name = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid).openStream()));
            name = (((JsonObject)new JsonParser().parse(in)).get("name")).toString().replaceAll("\"", "");
            in.close();
        } catch (Exception e) {
            System.out.println("Unable to get Name of: " + name + "!");
            name = "er";
        }
        return name;
    }

}
