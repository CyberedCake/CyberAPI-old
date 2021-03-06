package net.cybercake.cyberapi.items;

import net.cybercake.cyberapi.exceptions.BetterStackTraces;
import net.cybercake.cyberapi.Log;
import net.cybercake.cyberapi.chat.UChat;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.List;

public class GUI {

    public static void fillGUI(Inventory inventory, Material material, int amount, boolean shiny, String name, String... lore) {
        ItemStack items = item(material, amount, shiny, name, lore);
        for(int i=0; i<inventory.getSize(); i++) {
            inventory.setItem(i, items);
        }
    }

    public static ItemStack item(Material material, int amount, boolean hideNbt, boolean shiny, String name, String... lore) {
        try {
            ItemStack item = new ItemStack(material, amount);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(UChat.component(name));
            List<String> blankLore = new ArrayList<>();
            for(String loreActual : lore) {
                if(!loreActual.startsWith("paginate:")) {
                    blankLore.add(UChat.chat(loreActual));
                }else{
                    String color = loreActual.substring(loreActual.length()-2);
                    loreActual = loreActual.substring(9, loreActual.length()-3);
                    for(String str : ChatPaginator.wordWrap(UChat.chat(color + loreActual), 40)) {
                        blankLore.add(UChat.chat(str));
                    }
                }
            }
            if(shiny) {
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if(hideNbt) {
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);
            }
            meta.setLore(blankLore);
            item.setItemMeta(meta);
            return item;
        } catch (Exception exception) {
            Log.error("An exception occurred whilst creating a basic ItemStack");
            BetterStackTraces.print(exception);
        }
        return null;
    }

    public static ItemStack item(Material material, int amount, boolean shiny, String name, ArrayList<String> lore) {
        return item(material, amount, false, shiny, name, lore.toArray(new String[0]));
    }

    public static ItemStack item(Material material, int amount, String name, ArrayList<String> lore) {
        return item(material, amount, false, false, name, lore.toArray(new String[0]));
    }

    public static ItemStack item(Material material, int amount, String name, String... lore) {
        return item(material, amount, false, false, name, lore);
    }

    public static ItemStack item(Material material, int amount, boolean hideNbt, String name, String... lore) {
        return item(material, amount, hideNbt, false, name, lore);
    }

    public static ItemStack item(Material material, int amount, boolean hideNbt, String name) {
        return item(material, amount, hideNbt, false, name);
    }

    public static ItemStack item(Material material, int amount, String name) {
        try {
            ItemStack item = new ItemStack(material, amount);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(UChat.component(name));
            item.setItemMeta(meta);
            return item;
        } catch (Exception exception) {
            Log.error("An exception occurred whilst creating a basic ItemStack");
            BetterStackTraces.print(exception);
        }
        return null;
    }

}
