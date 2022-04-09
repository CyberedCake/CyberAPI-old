package net.cybercake.cyberapi.player;

import net.cybercake.cyberapi.chat.CenteredMessage;
import net.cybercake.cyberapi.chat.UChat;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class CyberBungeePlayer {

    private final ProxiedPlayer player;

    public CyberBungeePlayer(ProxiedPlayer player) {
        this.player = player;
    }

    public ProxiedPlayer getPlayer() { return this.player; }

    public static CyberBungeePlayer getCyberProxyPlayer(ProxiedPlayer player) {
        return new CyberBungeePlayer(player);
    }

    //
    // COLOR, COMPONENTS, AND MISC CHAT
    //
    public void sendColored(String msg) { player.sendMessage(new TextComponent(UChat.chat(msg))); }
    public void sendCenteredMessage(String message) { CenteredMessage.bungeeSend(player, message); }

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
