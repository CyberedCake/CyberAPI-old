package net.cybercake.cyberapi.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Item {

    public static String toKey(String notKey) {
        if(!notKey.startsWith("minecraft:")) {
            return "minecraft:" + notKey;
        }
        return notKey;
    }

    public static ArrayList<String> mcItems() {
        ArrayList<String> mcItems = new ArrayList<>();
        for(Material material : Material.values()) {
            if(material.isItem()) {
                mcItems.add(material.getKey().toString());
            }
        }
        return mcItems;
    }

    public static boolean compare(ItemStack item1, ItemStack item2) {
        item1 = item1.clone();
        item2 = item2.clone();

        item1.setAmount(1);
        item2.setAmount(1);

        return item1.equals(item2);
    }

}