package net.cybercake.cyberapi;

import net.cybercake.cyberapi.generalutils.NumberUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Time {

    public static long getUnix() {
        return Instant.now().getEpochSecond();
    }

    public final static long ONE_SECOND = 1000;
    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long ONE_HOUR = ONE_SECOND * 3600;
    public final static long ONE_DAY = ONE_SECOND * 86400;
    public final static long ONE_WEEK = ONE_SECOND * 604800;
    public final static long ONE_MONTH = ONE_SECOND * 2628000;
    public final static long ONE_YEAR = ONE_SECOND * 31560000;

    public static String toBetterDurationDisplay(long duration) {
        return toBetterDurationDisplay(duration, true);
    }

    public static String toBetterDurationDisplay(long biggerNumber, long smallerNumber) {
        return toBetterDurationDisplay(biggerNumber, smallerNumber, true);
    }

    public static String toBetterDurationDisplay(long biggerNumber, long smallerNumber, boolean showAll) {
        return toBetterDurationDisplay(biggerNumber-smallerNumber, showAll);
    }

    public static String toBetterDurationDisplay(long duration, boolean showAll) {
        StringBuilder durationBuilder = new StringBuilder();
        duration = duration*1000L;
        long temp;
        if (duration >= ONE_SECOND) {
            temp = duration / ONE_YEAR;
            if (temp > 0) {
                duration -= temp * ONE_YEAR;
                if(!showAll) return temp + " year" + (temp != 1 ? "s" : "");
                durationBuilder.append(temp).append(" year").append(temp != 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_MONTH;
            if (temp > 0) {
                duration -= temp * ONE_MONTH;
                if(!showAll) return temp + " month" + (temp != 1 ? "s" : "");
                durationBuilder.append(temp).append(" month").append(temp != 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_WEEK;
            if (temp > 0) {
                duration -= temp * ONE_WEEK;
                if(!showAll) return temp + " week" + (temp != 1 ? "s" : "");
                durationBuilder.append(temp).append(" week").append(temp != 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                if(!showAll) return temp + " day" + (temp != 1 ? "s" : "");
                durationBuilder.append(temp).append(" day").append(temp != 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                if(!showAll) return temp + " hour" + (temp != 1 ? "s" : "");
                durationBuilder.append(temp).append(" hour").append(temp != 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                if(!showAll) return temp + " minute" + (temp != 1 ? "s" : "");
                durationBuilder.append(temp).append(" minute").append(temp != 1 ? "s" : "");
            }

            if (!durationBuilder.toString().equals("") && duration >= ONE_SECOND) {
                durationBuilder.append(" and ");
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                durationBuilder.append(temp).append(" second").append(temp != 1 ? "s" : "");
                if(!showAll) return temp + " second" + (temp != 1 ? "s" : "");
            }
            return durationBuilder.toString();
        } else {
            return "0 seconds";
        }
    }

    public static Map<TimeUnit,Long> getDateDifference(Date date1, Date date2) {

        long diffInSeconds = date2.getTime() - date1.getTime();

        //create the list
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        //create the result map of TimeUnit and difference
        Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
        long secondsRest = diffInSeconds;

        for ( TimeUnit unit : units ) {

            //calculate difference in millisecond
            long diff = unit.convert(secondsRest,TimeUnit.SECONDS);
            long diffInSecondsForUnit = unit.toSeconds(diff);
            secondsRest = secondsRest - diffInSecondsForUnit;

            //put the result in the map
            result.put(unit,diff);
        }

        return result;
    }

    public static String getFormattedDate(String pattern) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat(pattern);
        return ft.format(dNow);
    }

    public static String getFormattedDate(String pattern, String timeOffset) {
        ZoneOffset zoneOffset = ZoneOffset.of(timeOffset);
        OffsetDateTime offset = OffsetDateTime.now(zoneOffset);
        Date date = new Date(offset.toInstant().toEpochMilli());
        SimpleDateFormat ft = new SimpleDateFormat(pattern);
        return ft.format(date);
    }

    public static String getFormattedDate(String pattern, int timeOffset) {
        String date;
        Calendar time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        time.add(Calendar.HOUR_OF_DAY,timeOffset);
        DateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setCalendar(time);
        date = formatter.format(time.getTime());
        return date;
    }

    public static String getFormattedDateUnix(long unix, String pattern) {
        Date time = new Date(unix*1000L);
        SimpleDateFormat ft = new SimpleDateFormat(pattern);
        return ft.format(time);
    }

    public static String getFormattedDateUnix(long unix, String pattern, int timeOffset) {
        String date;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.HOUR_OF_DAY, timeOffset);
        DateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setCalendar(calendar);
        calendar.getTime().setTime(unix*1000L);
        date = formatter.format(calendar.getTime());
        return date;
    }

    public static String formatTime(long number) {

        int SECOND = 1000;        // no. of ms in a second
        int MINUTE = SECOND * 60; // no. of ms in a minute
        int HOUR = MINUTE * 60;   // no. of ms in an hour

        long hours   = (number / HOUR);
        long minutes = ((number % HOUR) / MINUTE);
        long seconds = ((number % MINUTE) / SECOND);

        return hours + "h, " + minutes + "m, " + seconds + "s";
    }

    public static String formatTimeColons(long number) {

        int SECOND = 1000;        // no. of ms in a second
        int MINUTE = SECOND * 60; // no. of ms in a minute
        int HOUR = MINUTE * 60;   // no. of ms in an hour

        long hours   = (number / HOUR);
        long minutes = ((number % HOUR) / MINUTE);
        long seconds = ((number % MINUTE) / SECOND);

        return (hours != 0 ? hours + ":" : "") + (NumberUtils.isBetweenEquals(minutes, 0, 9) ? "0" : "") + minutes + ":" + (NumberUtils.isBetweenEquals(seconds, 0, 9) ? "0" : "") + seconds + "";
    }

    public static String formatTimeMs(long number) {
        return formatTimeMs(number, true);
    }

    public static String formatTimeMs(long number, boolean minuteLeadingZero) {

        int SECOND = 1000;        // no. of ms in a second
        int MINUTE = SECOND * 60; // no. of ms in a minuts

        long minutes = (number / MINUTE);
        long seconds = ((number % MINUTE) / SECOND);

        return (minuteLeadingZero ? (NumberUtils.isBetweenEquals((int)minutes, 0, 9) ? "0" : "") : "") + minutes + ":" + (seconds <= 9 ? "0" + seconds : seconds) + "." + ((number % 1000) <= 9 ? "00" + (number % 1000) : ((number % 1000) <= 99 ? "0" + (number % 1000) : (number % 1000)));
    }

}
