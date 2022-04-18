package net.cybercake.cyberapi.player;

import net.cybercake.cyberapi.CyberAPI;
import net.cybercake.cyberapi.chat.CenteredMessage;
import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.exceptions.BetterStackTraces;
import net.cybercake.cyberapi.instances.Spigot;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class CyberPlayer {

    private final Player player;

    public CyberPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() { return this.player; }
    public UUID getUniqueId() { return this.player.getUniqueId(); }

    public static CyberPlayer getCyberPlayer(Player player) {
        return new CyberPlayer(player);
    }

    //
    // COLOR, COMPONENTS, AND MISC CHAT
    //
    public void sendColored(String msg) { player.getPlayer().sendMessage(UChat.chat(msg)); }
    public void sendActionBar(@NotNull String msg) { player.sendActionBar(UChat.component(msg)); }
    public void sendCenteredMessage(String message) { CenteredMessage.spigotSend(player, message); }

    //
    // LUCK PERMS UTILITIES
    //
    public User getLuckpermsUser() { return LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()); }
    public CachedMetaData getMetaData() { return this.getLuckpermsUser().getCachedData().getMetaData(); }
    public String getPrefix() { return this.getMetaData().getPrefix(); }
    public String getSuffix() { return this.getMetaData().getSuffix(); }
    public String getDisplayName() { return (this.getMetaData().getPrefix() == null ? "" : this.getMetaData().getPrefix()) + player.getName() + (this.getMetaData().getSuffix() == null ? "" : this.getMetaData().getSuffix()); }
    public boolean isInLPGroup(String group) { return this.hasPermission("group." + group); }
    public boolean hasPermission(@NotNull String permissionNode) { return this.getLuckpermsUser().getCachedData().getPermissionData().checkPermission(permissionNode).asBoolean(); }

    //
    // CONNECTION AND PING
    //
    public String getColoredPing() {
        int ping = player.getPing();
        if(ping == 0) {
            return ChatColor.DARK_GRAY + "Loading...";
        }else if(ping >= 1 && ping <= 99) {
            return ChatColor.GREEN + String.valueOf(ping) + "ms";
        }else if(ping >= 100 && ping <= 199) {
            return ChatColor.YELLOW + String.valueOf(ping) + "ms";
        }else if(ping >= 200 && ping <= 359) {
            return ChatColor.RED + String.valueOf(ping) + "ms";
        }else if(ping >= 360) {
            return ChatColor.DARK_RED + String.valueOf(ping) + "ms";
        }
        return ChatColor.DARK_GRAY + "Failed to get your ping!";
    }
    public void kick(String reason) {
        player.kick(UChat.component(reason));
    }
    public void kick() {
        kick("Disconnected");
    }
    public void connectThrow(String server) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(Spigot.getPlugin(), "BungeeCord", b.toByteArray());
        b.close();
        out.close();
    }
    public void connect(String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(Spigot.getPlugin(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        } catch (Exception exception) {
            BetterStackTraces.print(exception);
        }
    }


    //
    // INVENTORY
    //

    public void give(ItemStack item) {
        if(this.hasInventoryRoom(item)) {
            player.getInventory().addItem(item);
        } else if(!this.hasInventoryRoom(item)) {
            Location playerLocation = player.getLocation();
            Vector playerVector = playerLocation.getDirection().multiply(0.5);
            playerLocation.setDirection(playerVector);
            Item newItem = player.getWorld().dropItem(playerLocation, item);
            newItem.setVelocity(playerVector);
        }
    }

    public boolean hasInventoryRoom(ItemStack itemStack) {
        int amountToHold = 0;
        int currentSize = itemStack.getAmount();
        for(int counter=0; counter<36; counter++) {
            if(player.getInventory().getItem(counter) == null) return true;
            if(player.getInventory().getItem(counter).equals(itemStack)) {
                amountToHold = itemStack.getMaxStackSize()-player.getInventory().getItem(counter).getAmount();
                if(amountToHold > 0) return true;

            }
        }
        return false;
    }

    //
    // MISC
    //
    public void playSound(Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    private final int DEFAULT_IMAGE_SCALE = 8;
    private final boolean DEFAULT_HELMET = false;
    private final Character DEFAULT_CHARACTER = '⬛';
    private final String[] DEFAULT_LINES = new String[]{};

    public String getUserHead() throws IOException { return getUserHead(DEFAULT_IMAGE_SCALE, DEFAULT_HELMET, DEFAULT_CHARACTER, DEFAULT_LINES); }
    public String getUserHead(int imageScale, boolean helmet) throws IOException { return getUserHead(imageScale, helmet, DEFAULT_CHARACTER, DEFAULT_LINES); }
    public String getUserHead(int imageScale, boolean helmet, String[] lines) throws IOException { return getUserHead(imageScale, helmet, DEFAULT_CHARACTER, lines); }
    public String getUserHead(int imageScale) throws IOException { return getUserHead(imageScale, DEFAULT_HELMET, DEFAULT_CHARACTER, DEFAULT_LINES); }
    public String getUserHead(int imageScale, Character character) throws IOException { return getUserHead(imageScale, DEFAULT_HELMET, character, DEFAULT_LINES); }
    public String getUserHead(int imageScale, Character character, String[] lines) throws IOException { return getUserHead(imageScale, DEFAULT_HELMET, character, lines); }
    public String getUserHead(int imageScale, String[] lines) throws IOException { return getUserHead(imageScale, DEFAULT_HELMET, DEFAULT_CHARACTER, lines); }
    public String getUserHead(boolean helmet, Character character) throws IOException { return getUserHead(DEFAULT_IMAGE_SCALE, helmet, character, DEFAULT_LINES); }
    public String getUserHead(boolean helmet, Character character, String[] lines) throws IOException { return getUserHead(DEFAULT_IMAGE_SCALE, helmet, character, lines); }
    public String getUserHead(boolean helmet) throws IOException { return getUserHead(DEFAULT_IMAGE_SCALE, helmet, DEFAULT_CHARACTER, DEFAULT_LINES); }
    public String getUserHEad(boolean helmet, String[] lines) throws IOException { return getUserHead(DEFAULT_IMAGE_SCALE, helmet, DEFAULT_CHARACTER, lines); }
    public String getUserHead(Character character) throws IOException { return getUserHead(DEFAULT_IMAGE_SCALE, DEFAULT_HELMET, character, DEFAULT_LINES); }
    public String getUserHead(Character character, String[] lines) throws IOException { return getUserHead(DEFAULT_IMAGE_SCALE, DEFAULT_HELMET, character, lines); }
    public String getUserHead(int imageScale, boolean helmet, Character character, String[] lines) throws IOException {

        UUID uuid = player.getPlayer().getUniqueId();
        String trimmedUUID = uuid.toString().replace("-", "");

        URL url = new URL("https://minotar.net/avatar/" + trimmedUUID + "/" + imageScale + ".png");
        if(helmet) {
            url = new URL("https://minotar.net/helm/" + trimmedUUID + "/" + imageScale + ".png");
        }

        BufferedImage img = ImageIO.read(url);

        StringBuilder columnBuilder = new StringBuilder();
        for(int row=0; row<img.getHeight(); row++) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int column=0; column<img.getWidth(); column++) {
                String hex = "#" + Integer.toHexString(img.getRGB(column, row)).substring(2);
                rowBuilder.append(net.md_5.bungee.api.ChatColor.of(hex)).append(character);
            }
            columnBuilder.append(rowBuilder).append("\n");
        }
        return columnBuilder.toString();
    }

    public void printUserHead() { printUserHead(player); }
    public void printUserHead(Player printTo) {
        try {
            printTo.sendMessage(getUserHead());
        } catch (IOException ioException) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst printing the user head of " + player.getName() + " to " + printTo.getName());
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, ioException);
        }
    }
    public void printUserHead(String[] lines) { printUserHead(player, lines); }
    public void printUserHead(Player printTo, String[] lines) {
        try {
            printTo.sendMessage(getUserHead());
        } catch (IOException ioException) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst printing the user head of " + player.getName() + " to " + printTo.getName());
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, ioException);
        }
    }


}
