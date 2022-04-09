package net.cybercake.cyberapi.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Item {

    public static List<ItemStack> itemStackSimilar(String... strings) {
        List<ItemStack> returned = new ArrayList<>();
        for(Material material : Material.values()) {
            if(!Arrays.asList(strings).contains(material.toString().toLowerCase(Locale.ROOT))) continue;

            returned.add(new ItemStack(material));
        }
        return returned;
    }

    public enum SimilarItem {
        SWORD, SHOVEL, PICKAXE, AXE, HOE, ARMOR;
    }

    public static List<ItemStack> getAll(SimilarItem similarItem) {
        return switch (similarItem) {
            case SWORD, SHOVEL, PICKAXE, HOE -> itemStackSimilar(similarItem.name().toLowerCase(Locale.ROOT));
            case AXE -> itemStackSimilar("_axe");
            case ARMOR -> itemStackSimilar("boots", "leg", "pants", "chestplate", "tunic", "helmet", "cap");
        };
    }

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