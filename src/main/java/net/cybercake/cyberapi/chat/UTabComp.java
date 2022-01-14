package net.cybercake.cyberapi.chat;

import net.cybercake.cyberapi.generalutils.NumberUtils;
import net.cybercake.cyberapi.items.Item;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UTabComp {


    public enum TabCompleteType {
        CONTAINS, SEARCH
    }

    public static List<String> tabCompletions(TabCompleteType type, String currentArg, List<String> completions) {
        if (currentArg.length() <= 0) { return completions; }
        currentArg = currentArg.toLowerCase(Locale.ROOT);
        List<String> returnedCompletions = new ArrayList<>();
        if (type == TabCompleteType.CONTAINS) {
            for (String str : completions) {
                if (str.toLowerCase(Locale.ROOT).contains(currentArg)) {
                    returnedCompletions.add(str);
                }
            }
        }else if (type == TabCompleteType.SEARCH) {
            for (String str : completions) {
                if (str.toLowerCase(Locale.ROOT).startsWith(currentArg)) {
                    returnedCompletions.add(str);
                }
            }
        }
        return returnedCompletions;
    }

    public static List<String> tabCompletionsContains(String currentArg, List<String> completions) { return tabCompletions(TabCompleteType.CONTAINS, currentArg, completions); }
    public static List<String> tabCompletionsSearch(String currentArg, List<String> completions) { return tabCompletions(TabCompleteType.SEARCH, currentArg, completions); }

    public static List<String> tabCompleteItem(String arguments) {


        if(Item.mcItems().contains(Item.toKey(arguments))) {
            return UChat.emptyList();
        }
        ArrayList<String> withoutMinecraftColon = new ArrayList<>();
        withoutMinecraftColon.add("minecraft:");
        for(String material : Item.mcItems()) {
            if(arguments.startsWith("minecraft:")) {
                withoutMinecraftColon.add(material);
            } else{
                withoutMinecraftColon.add(material.replace("minecraft:", ""));
            }
        }
        return UTabComp.tabCompletions(UTabComp.TabCompleteType.CONTAINS, arguments, withoutMinecraftColon);
    }

    public static ArrayList<String> getIntegers(String integerArgument, int lowest, int highest) {
        ArrayList<String> integers = new ArrayList<>();
        if(!NumberUtils.isInteger(integerArgument)) { return integers; }
        if(!NumberUtils.isBetweenEquals(Integer.parseInt(integerArgument), lowest, highest)) { return integers; }

        for(int i=1; i<10; i++) {
            if(!NumberUtils.isBetweenEquals(Integer.parseInt(integerArgument + i), lowest, highest)) continue;

            integers.add(integerArgument + i + "");
        }
        return integers;
    }

    public static ArrayList<Component> emptyComponentList = new ArrayList<>();
    public static ArrayList<String> emptyList = new ArrayList<>();
}