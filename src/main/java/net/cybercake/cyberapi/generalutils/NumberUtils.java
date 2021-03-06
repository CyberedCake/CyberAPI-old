package net.cybercake.cyberapi.generalutils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class NumberUtils {

    public static boolean isByte(String string) {
        try {
            byte integer = Byte.parseByte(string);
            integer = (byte) (integer + 5);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isShort(String string) {
        try {
            short integer = Short.parseShort(string);
            integer = (short) (integer + 5);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String string) {
        try {
            int integer = Integer.parseInt(string);
            integer = integer + 5;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLong(String string) {
        try {
            long integer = Long.parseLong(string);
            integer = integer + 5L;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String string) {
        try {
            float integer = Float.parseFloat(string);
            integer = integer + 5F;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String string) {
        try {
            double doub = Double.parseDouble(string);
            doub = doub + 1D;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBetweenEquals(int yourInteger, int integer1, int integer2) {
        return yourInteger >= integer1 && yourInteger <= integer2;
    }

    public static boolean isBetweenEquals(long yourLong, long long1, long long2) {
        return yourLong >= long1 && yourLong <= long2;
    }

    public static boolean isBetweenEquals(short yourShort, short short1, short short2) {
        return yourShort >= short1 && yourShort <= short2;
    }

    public static boolean isBetweenEquals(float yourFloat, float float1, float float2) {
        return yourFloat >= float1 && yourFloat <= float2;
    }

    public static boolean isBetweenEquals(double yourDouble, double double1, double double2) {
        return yourDouble >= double1 && yourDouble <= double2;
    }

    public static boolean isBetweenEquals(byte yourByte, byte byte1, byte byte2) {
        return yourByte >= byte1 && yourByte <= byte2;
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

    /**
     * Please do not use this method!
     * @deprecated please use {@link NumberUtils#formatDecimal(double, int) NumberUtils::formatDecimal} instead!
     */
    @Deprecated(forRemoval = true)
    public static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public static String formatDecimal(double value, int places) {
        return formatDecimal(value, places, RoundingMode.HALF_EVEN);
    }

    public static String formatDecimal(double value, int places, RoundingMode roundingMode) {
        DecimalFormat decimalFormat = new DecimalFormat("0." + "#".repeat(Math.max(0, places)));
        decimalFormat.setRoundingMode(roundingMode);
        return decimalFormat.format(value);
    }

}
