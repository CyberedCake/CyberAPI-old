package net.cybercake.cyberapi;

import net.cybercake.cyberapi.exceptions.BetterStackTraces;
import net.cybercake.cyberapi.instances.Spigot;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.List;
import java.util.Map;

@Deprecated
public class YamlUtils {
    
    // THIS CLASS IS VERY OLD AND I WOULD NOT RECOMMEND USING THIS!
    // IT IS PURELY A COMPATIBILITY THING
    // THIS IS SO DISORGANIZED I DON'T EVEN KNOW WHERE TO START

    // ALSO NOTE: THIS ONLY WORKS WITH SPIGOT PLUGINS!

    public static void saveCustomYml(String fileName) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + fileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);
            try {
                customConfig.save(customYml);
                //System.out.println(ChatColor.GREEN + "Successfully saved the yml file: '" + fileName + ".yml'");
            } catch (Exception e) {
                Bukkit.getLogger().severe(" ");
                Bukkit.getLogger().severe("Failed to save the configuration file (" + fileName + ".yml). Error: " + e.toString());
                Bukkit.getLogger().severe(" ");
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe("Failed to create or find the configuration file (" + fileName + ".yml). Error: " + e.toString());
            Bukkit.getLogger().severe(" ");
        }


    }

    public static void saveCustomYml(String fileName, String directory) {
        try {
            File customYml = new File(directory + fileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);
            try {
                customConfig.save(customYml);
                //System.out.println(ChatColor.GREEN + "Successfully saved the yml file: '" + directory + fileName + ".yml'");
            } catch (Exception e) {
                Bukkit.getLogger().severe(" ");
                Bukkit.getLogger().severe("Failed to save the configuration file (" + directory + fileName + ".yml). Error: " + e.toString());
                Bukkit.getLogger().severe(" ");
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe("Failed to create or find the configuration file (" + directory + fileName + ".yml). Error: " + e.toString());
            Bukkit.getLogger().severe(" ");
        }


    }

    public static Object getCustomYmlObject(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.get(path);
        } catch (Exception e) {
            BetterStackTraces.print(e);
            return null;
        }
    }

    public static Object getCustomYmlObject(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.get(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCustomYmlString(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getString(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCustomYmlString(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getString(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<?> getCustomYmlList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<?> getCustomYmlList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean getCustomYmlBoolean(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getBoolean(path);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getCustomYmlBoolean(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getBoolean(path);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double getCustomYmlDouble(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getDouble(path);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0D;
        }
    }

    public static double getCustomYmlDouble(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getDouble(path);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0D;
        }
    }

    public static int getCustomYmlInt(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getInt(path);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getCustomYmlInt(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getInt(path);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static List<Character> getCustomYmlCharacterList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getCharacterList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Character> getCustomYmlCharacterList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getCharacterList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Color getCustomYmlColor(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getColor(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Color getCustomYmlColor(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getColor(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Boolean> getCustomYmlBooleanList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getBooleanList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Boolean> getCustomYmlBooleanList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getBooleanList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Double> getCustomYmlDoubleList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getDoubleList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Double> getCustomYmlDoubleList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getDoubleList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Float> getCustomYmlFloatList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getFloatList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Float> getCustomYmlFloatList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getFloatList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Integer> getCustomYmlIntegerList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getIntegerList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Integer> getCustomYmlIntegerList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getIntegerList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack getCustomYmlItemStack(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getItemStack(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack getCustomYmlItemStack(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getItemStack(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Long getCustomYmlLong(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getLong(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Long getCustomYmlLong(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getLong(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Long> getCustomYmlLongList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getLongList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Long> getCustomYmlLongList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getLongList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Short> getCustomYmlShortList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getShortList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Short> getCustomYmlShortList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getShortList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Map<?, ?>> getCustomYmlMapList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getMapList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Map<?, ?>> getCustomYmlMapList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getMapList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OfflinePlayer getCustomYmlOfflinePlayer(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getOfflinePlayer(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OfflinePlayer getCustomYmlOfflinePlayer(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getOfflinePlayer(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getCustomYmlStringList(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getStringList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getCustomYmlStringList(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getStringList(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Vector getCustomYmlVector(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getVector(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Vector getCustomYmlVector(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getVector(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Location getCustomYmlLocation(String ymlFileName, String path) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getLocation(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Location getCustomYmlLocation(String ymlDirectory, String ymlFileName, String path) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getLocation(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> getCustomYmlAllValues(String ymlFileName, boolean deep) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getValues(true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> getCustomYmlAllValues(String ymlDirectory, String ymlFileName, boolean deep) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig.getValues(true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void setCustomYml(String ymlFileName, String path, Object toWhat) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            customConfig.set(path, toWhat);

            try {
                customConfig.save(customYml);
                //System.out.println(ChatColor.GREEN + "Successfully saved the yml file: '" + ymlFileName + ".yml'");
            } catch (Exception e) {
                Bukkit.getLogger().severe(" ");
                Bukkit.getLogger().severe("Failed to save the configuration file (" + ymlFileName + ".yml). Error: " + e.toString());
                Bukkit.getLogger().severe(" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCustomYml(String ymlDirectory, String ymlFileName, String path, Object toWhat) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            customConfig.set(path, toWhat);

            try {
                customConfig.save(customYml);
                //System.out.println(ChatColor.GREEN + "Successfully saved the yml file: '" + ymlFileName + ".yml'");
            } catch (Exception e) {
                Bukkit.getLogger().severe(" ");
                Bukkit.getLogger().severe("Failed to save the configuration file (" + ymlDirectory + ymlFileName + ".yml). Error: " + e.toString());
                Bukkit.getLogger().severe(" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean customYmlExist(String ymlFileName) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            if(customYml.exists() == true) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean customYmlExist(String ymlDirectory, String ymlFileName) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            if(customYml.exists() == true) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addListValue(String ymlFileName, String path, String whatValue) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            customConfig.getStringList(path).add(whatValue);

            customConfig.save(customYml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addListValue(String ymlDirectory, String ymlFileName, String path, String whatValue) {
        try {
            File customYml = new File(ymlDirectory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            customConfig.getStringList(path).add(whatValue);

            customConfig.save(customYml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeListValue(String ymlFileName, String path, Object whatValue) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            customConfig.getList(path).remove(whatValue);

            customConfig.save(customYml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteListValue(String ymlFileName, String path, Object whatValue) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            customConfig.getStringList(path).remove(whatValue);

            customConfig.save(customYml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeListValue(String directory, String ymlFileName, String path, Object whatValue) {
        try {
            File customYml = new File(directory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            customConfig.getStringList(path).remove(whatValue);

            customConfig.save(customYml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteListValue(String directory, String ymlFileName, String path, Object whatValue) {
        try {
            File customYml = new File(directory + ymlFileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            customConfig.getStringList(path).remove(whatValue);

            customConfig.save(customYml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getCustomYmlFile(String fileName) {
        try {
            File file = new File(Spigot.get().getDataFolder() + "/" + fileName + ".yml");

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getCustomYmlFile(String directory, String fileName) {
        try {
            File file = new File(directory + fileName + ".yml");

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FileConfiguration getCustomYmlFileConfig(File file) {
        try {
            File customYml = file;
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FileConfiguration getCustomYmlFileConfig(String fileName) {
        try {
            File customYml = new File(Spigot.get().getDataFolder() + "/" + fileName + ".yml");
            FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

            return customConfig;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FileConfiguration getCustomYmlFileConfig(String fileName, String directory) {
        File customYml = new File(directory + fileName + ".yml");
        FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

        return customConfig;
    }

    public static void createDirectory(String directory, boolean usePluginDirectory) {
        try {
            File theDir = null;
            if(usePluginDirectory) {
                theDir = new File(Spigot.get().getDataFolder() + "/" + directory);
            } else if (!usePluginDirectory){
                theDir = new File(directory);
            }
            if(!theDir.exists()) {
                theDir.mkdirs();
            } else {
                Bukkit.getLogger().severe("Failed to create directory: This directory already exist at " + theDir);
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe(" ");
            if(usePluginDirectory) {
                Bukkit.getLogger().severe("Failed to create directory (" + Spigot.get().getDataFolder() + directory + "). Error: " + e.toString());
            } else if (!usePluginDirectory){
                Bukkit.getLogger().severe("Failed to create directory (" + directory + "). Error: " + e.toString());
            }
            Bukkit.getLogger().severe(" ");

        }
    }

    public static void createDirectory(String directory, boolean usePluginDirectory, boolean sendMsgOnFail) {
        try {
            File theDir = null;
            if(usePluginDirectory) {
                theDir = new File(Spigot.get().getDataFolder() + "/" + directory);
            } else if (!usePluginDirectory){
                theDir = new File(directory);
            }
            if(!theDir.exists()) {
                theDir.mkdirs();
            } else {
                if(sendMsgOnFail) {
                    Bukkit.getLogger().severe("Failed to create directory: This directory already exist at " + theDir);
                }
            }
        } catch (Exception e) {
            if(sendMsgOnFail) {
                Bukkit.getLogger().severe(" ");
                if(usePluginDirectory) {
                    Bukkit.getLogger().severe("Failed to create directory (" + Spigot.get().getDataFolder() + directory + "). Error: " + e.toString());
                } else if (!usePluginDirectory){
                    Bukkit.getLogger().severe("Failed to create directory (" + directory + "). Error: " + e.toString());
                }
                Bukkit.getLogger().severe(" ");
            }

        }
    }

    public static String[] getFilesInDirectory(String directory) {
        try {
            String[] pathnames;
            File f = new File(directory);
            pathnames = f.list();
            return pathnames;
        } catch (Exception e) {
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe("Failed to get files in directory (" + directory + "). Error: " + e.toString());
            Bukkit.getLogger().severe(" ");
        }
        return null;
    }

    public static String[] getFilesInPluginDirectory(String directory) {
        try {
            String[] pathnames;
            File f = new File(Spigot.get().getDataFolder() + "/" + directory);
            pathnames = f.list();
            return pathnames;
        } catch (Exception e) {
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe("Failed to get files in directory (" + directory + "). Error: " + e.toString());
            Bukkit.getLogger().severe(" ");
        }
        return null;
    }

    public static void createNewFile(String fileName) {
        try {
            File f = new File(Spigot.get().getDataFolder() + "/" + fileName);
            f.createNewFile();
        } catch (Exception e) {
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe("Failed to create file (" + fileName + "). Error: " + e.toString());
            Bukkit.getLogger().severe(" ");
        }
    }

    public static void createNewFile(String directory, String fileName) {
        try {
            File f = new File(directory + fileName);
            f.createNewFile();
        } catch (Exception e) {
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe("Failed to create file (" + fileName + "). Error: " + e.toString());
            Bukkit.getLogger().severe(" ");
        }
    }

    public static void deleteFile(String fileName) {
        try {
            File f = new File(Spigot.get().getDataFolder() + "/" + fileName);
            if(f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe("Failed to delete file (" + fileName + "). Error: " + e.toString());
            Bukkit.getLogger().severe(" ");
        }
    }

    public static void deleteFile(String directory, String fileName) {
        try {
            File f = new File(directory + fileName);
            if(f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe(" ");
            Bukkit.getLogger().severe("Failed to delete file (" + fileName + "). Error: " + e.toString());
            Bukkit.getLogger().severe(" ");
        }
    }

}
