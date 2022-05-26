package net.cybercake.cyberapi.chat;

import net.cybercake.cyberapi.CyberAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class CenteredMessage {

    // THIS CODE IS NOT MINE (I JUST MODIFIED IT)
    // THE ORIGINAL CODE CAN BE FOUND HERE:
    // https://www.spigotmc.org/threads/center-motds-and-messages.354209

    // lineLength = 80 (Chat Length)
    // lineLength = 45 (MOTD Length)

    public static String get(String message, int lineLength) {
        if(message == null || message.equals("")) { return ""; }
        char[] chars = message.toCharArray(); // Get a list of all characters in text
        boolean isBold = false;
        double length = 0;
        ChatColor pholder = null;
        for (int i = 0; i < chars.length; i++) { // Loop through all characters
            // Check if the character is a ColorCode..
            if (chars[i] == '&' && chars.length != (i + 1) && (pholder = ChatColor.getByChar(chars[i + 1])) != null) {
                if (pholder != ChatColor.UNDERLINE && pholder != ChatColor.ITALIC
                        && pholder != ChatColor.STRIKETHROUGH && pholder != ChatColor.MAGIC) {
                    isBold = (chars[i + 1] == 'l'); // Setting bold  to true or false, depending on if the ChatColor is Bold.
                    length--; // Removing one from the length, of the string, because we don't wanna count color codes.
                    i += isBold ? 1 : 0;
                }
            } else {
                // If the character is not a color code:
                length++; // Adding a space
                length += (isBold ? (chars[i] != ' ' ? 0.1555555555555556 : 0) : 0); // Adding 0.156 spaces if the character is bold.
            }
        }

        double spaces = (lineLength - length) / 2; // Getting the spaces to add by (max line length - length) / 2

        // Adding the spaces
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            builder.append(' ');
        }
        String copy = builder.toString();
        builder.append(message).append(copy);

        return builder.toString();
    }
    public static Component get(Component message, int lineLength) {
        if(message == null) { return Component.empty(); }
        String componentString = LegacyComponentSerializer.legacy('&').serialize(message);

        char[] chars = componentString.toCharArray(); // Get a list of all characters in text
        boolean isBold = false;
        double length = 0;
        ChatColor pholder = null;
        for (int i = 0; i < chars.length; i++) { // Loop through all characters
            // Check if the character is a ColorCode
            if (chars[i] == '&' && chars.length != (i + 1) && (pholder = ChatColor.getByChar(chars[i + 1])) != null) {
                if (pholder != ChatColor.UNDERLINE && pholder != ChatColor.ITALIC
                        && pholder != ChatColor.STRIKETHROUGH && pholder != ChatColor.MAGIC) {
                    isBold = (chars[i + 1] == 'l'); // Setting bold  to true or false, depending on if the ChatColor is Bold.
                    length--; // Removing one from the length, of the string, because we don't wanna count color codes.
                    i += isBold ? 1 : 0;
                }
            } else {
                // If the character is not a color code:
                length++; // Adding a space
                length += (isBold ? (chars[i] != ' ' ? 0.1555555555555556 : 0) : 0); // Adding 0.156 spaces if the character is bold.
            }
        }

        double spaces = (lineLength - length) / 2; // Getting the spaces to add by (max line length - length) / 2

        // Adding the spaces
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            builder.append(' ');
        }
        String copy = builder.toString();
        builder.append(componentString).append(copy);

        return LegacyComponentSerializer.legacy('&').deserialize(builder.toString());
    }

    public enum TextType {
        CHAT(74),
        MOTD(45);

        private final int length;

        TextType(int length) {
            this.length = length;
        }

        public int getLength() {
            return length;
        }
    }

    public static int fromTextType(TextType textType) {
        switch(textType) {
            case CHAT -> {
                return TextType.CHAT.getLength();
            }
            case MOTD -> {
                return TextType.MOTD.getLength();
            }
            default -> {
                return 0;
            }
        }
    }

    public static String get(String message, TextType textType) {
        return get(message, fromTextType(textType));
    }
    public static Component get(Component message, TextType textType) {
        return get(message, fromTextType(textType));
    }

    public static String get(String message) { return get(message, fromTextType(TextType.CHAT)); }
    public static Component get(Component message) { return get(message, fromTextType(TextType.CHAT)); }

    public static void send(Object player, String message) {
        if(!(player instanceof CommandSender) || !(player instanceof net.md_5.bungee.api.CommandSender)) {
            return;
        }
        switch(CyberAPI.getAPI().getServerType()) {
            case SPIGOT -> spigotSend((CommandSender)player, message);
            case BUNGEE -> bungeeSend((net.md_5.bungee.api.CommandSender)player, message);
        }
    }

    public static void spigotSend(CommandSender player, String message) {
        player.sendMessage(UChat.component(get(message, TextType.CHAT.getLength())));
    }

    public static void bungeeSend(net.md_5.bungee.api.CommandSender player, String message) {
        player.sendMessage(UChat.bComponent(get(message, TextType.CHAT.getLength())));
    }

}
