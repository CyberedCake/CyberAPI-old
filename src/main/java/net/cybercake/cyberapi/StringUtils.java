package net.cybercake.cyberapi;

import net.cybercake.cyberapi.chat.UChat;
import net.md_5.bungee.api.ChatColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<String> seqToList(String... strings) {
        ArrayList<String> chat = new ArrayList<>();
        for(String str : strings) {
            chat.add(UChat.chat(str));
        }
        return chat;
    }

    public static java.lang.String getCharacters(int beginCharacter, int endCharacter, java.lang.String string) {
        if(beginCharacter < 0) {
            return null;
        }

        if(endCharacter > string.length()) {
            return null;
        } else {
            return string.length() < endCharacter ? string : string.substring(beginCharacter, endCharacter);
        }
    }

    public enum CheckType {
        equals, equalsIgnoreCase, contains, startsWith
    }

    public static boolean checkStrings(CheckType checkType, String checkAgainst, String... strings) {
        for(String str : strings) {
            switch (checkType) {
                case equals:
                    if(str.equals(checkAgainst)) {
                        return true;
                    }
                    break;
                case contains:
                    if(str.contains(checkAgainst)) {
                        return true;
                    }
                    break;
                case startsWith:
                    if(str.startsWith(checkAgainst)) {
                        return true;
                    }
                    break;
                case equalsIgnoreCase:
                    if(str.equalsIgnoreCase(checkAgainst)) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    public static String getStringFromArguments(int startFrom, String[] args) {
        StringBuilder sm = new StringBuilder();
        for(int i = startFrom; i < args.length; i++) {
            String arg = (args[i] + " ");
            sm.append(arg);
        }
        return sm.toString();
    }

    public static ArrayList<String> removeDuplicates(ArrayList<String> list) {
        ArrayList<String> alreadyOver = new ArrayList<>();
        for(String str : list) {
            if(!alreadyOver.contains(str)) {
                alreadyOver.add(str);
            }
        }
        return alreadyOver;
    }

    public static ArrayList<String> getDuplicates(ArrayList<String> list) {
        ArrayList<String> alreadyOver = new ArrayList<>();
        for(String str : list) {
            alreadyOver.add(str);
        }
        return alreadyOver;
    }

    public static ArrayList<String> removeFromList(ArrayList<String> list, String object) {
        if(list == null) return new ArrayList<>();
        list.remove(object);
        return list;
    }

    public static ArrayList<String> addToList(ArrayList<String> list, String object) {
        if(list == null) list = new ArrayList<>();
        list.add(object);
        return list;
    }

    public static boolean isAlphanumeric(String string, List<Character> characters) {
        for(Character character : string.toCharArray()) {
            if(!characters.contains(character) && !Character.isLetterOrDigit(character)) return false;
        }
        return true;
    }

    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
