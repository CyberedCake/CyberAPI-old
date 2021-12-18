package net.cybercake.cyberapi;

import java.text.NumberFormat;
import java.util.Random;

public class NumberUtils {

    public static boolean isInteger(String string) {
        try {
            int integer = Integer.parseInt(string);
            integer = integer + 5;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String string) {
        try {
            double doub = Double.parseDouble(string);
            doub = doub + 1;
            return true;
        } catch (Exception e) { }
        return false;
    }

    public static boolean isBetweenEquals(int yourInteger, int integer1, int integer2) {
        if(yourInteger >= integer1 && yourInteger <= integer2) {
            return true;
        }
        return false;
    }

    public static boolean isBetween(int yourInteger, int integer1, int integer2) {
        if(yourInteger > integer1 && yourInteger < integer2) {
            return true;
        }
        return false;
    }

    public static String formatLong(long longNumber) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        return numberFormat.format(longNumber);
    }

    public static int randomInt(int min, int max) {
        int rand = new Random().nextInt((max - min) + 1) + min;
        return rand;
    }

    public static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}
