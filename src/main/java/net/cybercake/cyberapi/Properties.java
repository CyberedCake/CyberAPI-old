package net.cybercake.cyberapi;

import net.cybercake.cyberapi.exceptions.BetterStackTraces;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public class Properties {

    public static void setServerProperty(ServerProperty property, String value) {
        setServerProperty(property.getPropertyName(), value);
    }

    public static void setServerProperty(String property, String value) {
        FileInputStream in = null;
        try {
            java.util.Properties properties = new java.util.Properties();

            in = getServerProperties();
            properties.load(in);

            properties.setProperty(property, value);
        } catch (FileNotFoundException ex) {
            Log.error("CyberAPI failed to find the server.properties file!");
            BetterStackTraces.print(ex);
        } catch (IOException ex) {
            Log.warn("An IO exception occurred whilst trying to read server.properties file!");
            BetterStackTraces.print(Level.WARNING, ex);
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException ex) {
                Log.warn(CyberAPI.getAPI().getPrefix() + " An IO exception occurred whilst trying to read server.properties file!");
                BetterStackTraces.print(Level.WARNING, ex);
            }
        }
    }

    public static java.util.Properties getPropertyManager() throws IOException {
        FileInputStream in = null;
        java.util.Properties properties = new java.util.Properties();

        in = getServerProperties();
        properties.load(in);
        return properties;
    }

    public static FileInputStream getServerProperties() throws IOException {
        FileInputStream in = null;
        java.util.Properties properties = new java.util.Properties();

        in = new FileInputStream("server.properties");
        properties.load(in);
        return in;
    }

    public static ServerProperty propertyFromString(String property) {
        ServerProperty returned = null;
        for(ServerProperty property1 : ServerProperty.values()) {
            if(property1.getPropertyName().equalsIgnoreCase(property)) {
                returned = property1;
            }
        }
        return returned;
    }

    public enum ServerProperty {

        FLIGHT("allow-flight", "false"),
        NETHER("allow-nether", "true"),
        CONSOLE_TO_OPS("broadcast-console-to-ops", "true"),
        REMOTE_CONSOLE_TO_OPS("broadcast-rcon-to-ops", "true"),
        DIFFICULTY("difficulty", "easy"),
        COMMAND_BLOCKS("enable-command-block", "false"),
        JMX_MONITORING("enable-jmx-monitoring", "false"),
        REMOTE_CONSOLE("enable-rcon", "false"),
        SYNCHRONOUS_CHUNK_WRITES("sync-chunk-writes", "true"),
        SHOW_ONLINE("enable-status", "true"),
        ENABLE_QUERY("enable-query", "false"),
        SEND_ENTITY_INFO_PERCENT("entity-broadcast-range-percentage", "100"),
        SHOULD_FORCE_GAMEMODE("force-gamemode", "false"),
        DATAPACK_PERMISSION_LEVEL("function-permission-level", "2"),
        GAMEMODE("gamemode", "survival"),
        GENERATE_STRUCTURES("generate-structures", "true"),
        WORLD_GENERATION_SETTINGS("generator-settings", ""),
        IS_HARDCORE("hardcore", "false"),
        WORLD_NAME("level-name", "world"),
        WORLD_SEED("level-seed", ""),
        WORLD_TYPE("level-type", "default"),
        MAXIMUM_PLAYERS("max-players", "20"),
        MS_TO_FORCE_CRASH("max-tick-time", "60000"),
        WORLD_SIZE_MAX("max-world-size", "29999984"),
        SERVER_MOTD("motd", "A Minecraft Server"),
        MAX_PACKET_SIZE("network-compression-threshold", "256"),
        IS_ONLINE("online-mode", "true"),
        OP_PERMISSION_LEVEL("op-permission-level", "4"),
        PLAYER_AFK_KICK_TIME("player-idle-timeout", "0"),
        PREVENT_VPNS_AND_PROXIES("prevent-proxy-connections", "false"),
        PVP("pvp", "true"),
        QUERY_PORT("query.port", "25565"),
        MAX_PACKET_SENT("rate-limit", "0"),
        REMOTE_CONSOLE_PASSWORD("rcon.password", ""),
        REMOTE_CONSOLE_PORT("rcon.port", "25575"),
        RESOURCE_PACK_LINK("resource-pack", ""),
        RESOURCE_PACK_PROMPT("resource-pack-prompt", ""),
        RESOURCE_PACK_SHA1("resource-pack-sha1", ""),
        RESOURCE_PACK_IS_REQUIRED("require-resource-pack", "false"),
        IP_TO_BIND("server-ip", ""),
        SERVER_PORT("server-port", "25565"),
        SIMULATION_DISTANCE("simulation-distance", "10"),
        MOJANG_SNOOPING("snooper-enabled", "true"),
        ALLOW_ANIMALS_SPAWNING("spawn-animals", "true"),
        ALLOW_MONSTER_SPAWNING("spawn-monsters", "true"),
        ALLOW_VILLAGER_SPAWNING("spawn-npcs", "true"),
        SPAWN_PROTECTION("spawn-protection", "16"),
        TEXT_FILTERING("text-filtering-config", ""),
        OPTIMIZED_PACK_SR_LINUX("use-native-transport", "true"),
        WORLD_VIEW_DISTANCE("view-distance", "10"),
        IS_WHITELISTED("white-list", "false"),
        KICK_AFTER_WHITELIST_RELOAD("enforce-whitelist", "false");


        private final String propertyName;
        private final String defaultValue;

        ServerProperty(String propertyName, String defaultValue) {
            this.propertyName = propertyName;
            this.defaultValue = defaultValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
        public String getPropertyName() {
            return propertyName;
        }

        public void resetToDefault() {
            setServerProperty(getPropertyName(), getDefaultValue());
        }
        public void setValue(String value) {
            setServerProperty(getPropertyName(), value);
        }

        public String getValue() {
            FileInputStream in = null;
            try {
                java.util.Properties properties = new java.util.Properties();

                in = getServerProperties();
                properties.load(in);

                return properties.getProperty(propertyName);
            } catch (FileNotFoundException ex) {
                Log.error("CyberAPI failed to find the server.properties file!");
                BetterStackTraces.print(ex);
            } catch (IOException ex) {
                Log.warn("An IO exception occurred whilst trying to read server.properties file!");
                BetterStackTraces.print(Level.WARNING, ex);
            } finally {
                try {
                    if (in != null) in.close();
                } catch (IOException ex) {
                    Log.warn(CyberAPI.getAPI().getPrefix(false) + "An IO exception occurred whilst trying to read server.properties file!");
                    BetterStackTraces.print(Level.WARNING, ex);
                }
            }
            return null;
        }

    }
}