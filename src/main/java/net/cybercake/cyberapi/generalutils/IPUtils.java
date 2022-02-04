package net.cybercake.cyberapi.generalutils;

import net.cybercake.cyberapi.CyberAPI;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class IPUtils {

    private static String apiKey = "unset";

    private final static String apiKeyError = "The plugin developer of ${plugin} has not put in an API key for IPInfoDB. Please nag them to add IPUtils.setAPIKey(String key) to their plugin before they use any IPUtils methods.";

    private static HashMap<String, JSONObject> ipStorage = new HashMap<>();

    public static void setAPIKey(String key) {
        apiKey = key;
    }
    public static boolean isAPIKeySet() {
        return !apiKey.equalsIgnoreCase("unset");
    }

    private String ip;
    public IPUtils(String ip) {
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return;
        }

        this.ip = ip;
    }
    public IPUtils(InetSocketAddress ip) {
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return;
        }

        this.ip = ip.getHostString();
    }

    public static IPUtils fromIP(String ip) {
        return new IPUtils(ip);
    }
    public static IPUtils fromIP(InetSocketAddress ip) {
        return new IPUtils(ip.getHostString());
    }

    public String getIP() {
        return ip;
    }


    public void setKey(String key) {
        apiKey = key;
    }
    public boolean isKeySet() {
        return !apiKey.equalsIgnoreCase("unset");
    }

    public JSONObject cache() {
        String url = "http://api.ipinfodb.com/v3/ip-city/?key=" + apiKey + "&ip="+ip+"&format=json";
        JSONObject object = stringToJSON(getUrlSource(url));
        ipStorage.put(ip,object);
        return object;
    }

    public String getTimeOffsetRaw() {
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        String offset = "unknown";
        try {
            if (ipStorage.containsKey(ip)){
                offset = ipStorage.get(ip).get("timeZone").toString();
            } else {
                JSONObject object = cache();
                String zone = object.get("timeZone").toString();
                if (zone != null && zone.length() > 3){
                    offset = zone;
                }
            }
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst using IPUtils.");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }
        return offset;
    }

    public int getTimeOffset() {
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return 0;
        }


        int offset = 0;
        try {
            if (ipStorage.containsKey(ip)){
                String zone = ipStorage.get(ip).get("timeZone").toString();
                offset = Integer.parseInt(zone.substring(0, zone.length()-3));
            } else {
                JSONObject object = cache();
                String zone = object.get("timeZone").toString();
                if (zone != null && zone.length() > 3){
                    offset = Integer.parseInt(zone.substring(0, zone.length()-3));
                }
            }
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst using IPUtils.");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
            return 0;
        }
        return offset;
    }

    public String getTime() {
        return getTime("EEEEEE hh:mm aa");
    }

    public String getTime(String dateFormat) {
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        String date = "unknown";
        try {
            Calendar time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            time.add(Calendar.HOUR_OF_DAY,getTimeOffset());
            DateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setCalendar(time);
            date = formatter.format(time.getTime());
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst using IPUtils.");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }
        return date;
    }


    public String getCityName(){
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        JSONObject json = null;
        String cityName = "unknown";
        try {
            if (ipStorage.containsKey(ip)){
                json = ipStorage.get(ip);
            } else {
                json = cache();
            }

            cityName = (String) json.get("cityName");
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst using IPUtils.");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }

        return cityName;
    }

    public String getStateName(){
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        JSONObject json = null;
        String regionName = "unknown";
        try {
            if (ipStorage.containsKey(ip)) {
                json = ipStorage.get(ip);
            } else {
                json = cache();
            }
            regionName = (String) json.get("regionName");
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst using IPUtils.");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }

        return regionName;
    }

    public String getCountryName(){
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        String country = "unknown";
        try {
            JSONObject json = null;

            if (ipStorage.containsKey(ip)) {
                json = ipStorage.get(ip);
            } else {
                json = cache();
            }

            country = (String) json.get("countryName");

            if (country.contains(",")) country = country.split(",")[0];
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst using IPUtils.");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }

        return country;
    }

    public String getCountryCode(){
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        JSONObject json = null;
        String countryCode = "unknown";
        try {
            if (ipStorage.containsKey(ip)) {
                json = ipStorage.get(ip);
            } else {
                json = cache();
            }

            countryCode = (String) json.get("countryCode");
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst using IPUtils.");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }

        return countryCode;
    }

    public JSONObject stringToJSON(String json){
        return (JSONObject) JSONValue.parse(json);
    }

    private String getUrlSource(String strUrl) {
        URL url;
        StringBuilder returned = new StringBuilder();

        try {
            url = new URL(strUrl);

            URLConnection urlConnection;
            urlConnection = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), StandardCharsets.UTF_8));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                returned.append(inputLine);

            in.close();
        } catch (IOException ignored) {
        } catch (Exception exception) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, "An error occurred whilst using IPUtils.");
            CyberAPI.getAPI().logAPIError(CyberAPI.APILevel.ERROR, exception);
        }

        return returned.toString();
    }
}
