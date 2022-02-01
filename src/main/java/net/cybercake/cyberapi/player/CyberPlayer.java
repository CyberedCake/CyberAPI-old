package net.cybercake.cyberapi.player;

import com.sun.tools.javac.Main;
import net.cybercake.cyberapi.Log;
import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.chat.DefaultFontInfo;
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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CyberPlayer {

    private final Player player;

    public CyberPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() { return this.player; }

    public static CyberPlayer getCyberPlayer(Player player) {
        return new CyberPlayer(player);
    }

    //
    // COLOR, COMPONENTS, AND MISC CHAT
    //
    public void sendColored(String msg) { player.getPlayer().sendMessage(UChat.chat(msg)); }
    public void sendActionBar(@NotNull String msg) { player.sendActionBar(UChat.component(msg)); }
    public void sendCenteredMessage(String message) {
        if (message == null || message.equals("")) this.getPlayer().sendMessage(UChat.component(""));
        int CENTER_PX = 154;

        message = UChat.chat(message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
                continue;
            } else if (previousCode) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                } else isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        this.getPlayer().sendMessage(UChat.component(sb.toString() + message));
    }

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
        }else if(ping >= 1 || ping <= 99) {
            return ChatColor.GREEN + String.valueOf(ping) + "ms";
        }else if(ping >= 100 || ping <= 199) {
            return ChatColor.YELLOW + String.valueOf(ping) + "ms";
        }else if(ping >= 200 || ping <= 359) {
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


}
