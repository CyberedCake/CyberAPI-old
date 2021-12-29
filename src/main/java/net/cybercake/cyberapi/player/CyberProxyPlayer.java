package net.cybercake.cyberapi.player;

import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.chat.DefaultFontInfo;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class CyberProxyPlayer {

    private final ProxiedPlayer player;

    public CyberProxyPlayer(ProxiedPlayer player) {
        this.player = player;
    }

    public ProxiedPlayer getPlayer() { return this.player; }

    public static CyberProxyPlayer getCyberProxyPlayer(ProxiedPlayer player) {
        return new CyberProxyPlayer(player);
    }

    //
    // COLOR, COMPONENTS, AND MISC CHAT
    //
    public void sendColored(String msg) { player.sendMessage(new TextComponent(UChat.chat(msg))); }
    public void sendCenteredMessage(String message) {
        if (message == null || message.equals("")) this.getPlayer().sendMessage(UChat.bComponent(""));
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
        this.getPlayer().sendMessage(UChat.bComponent(sb.toString() + message));
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
    public void kick(String msg) {
        player.disconnect(new TextComponent(UChat.chat(msg)));
    }
    public void kick() {
        kick("Disconnected");
    }

    //
    // BUNGEECORD
    //
    public void sendPlayerToServer(ServerInfo server) {
        player.connect(server);
    }
    public void sendPlayerToServer(String server) {
        sendPlayerToServer(ProxyServer.getInstance().getServerInfo(server));
    }




}
