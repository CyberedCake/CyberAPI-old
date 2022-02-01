package net.cybercake.cyberapi.generalutils;

import net.cybercake.cyberapi.CyberAPI;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class IPUtils {

    private static String apiKey;

    private final static String apiKeyError = "The plugin developer of ${plugin} has not put in an API key for IPInfoDB. Please nag them to add IPUtils.setAPIKey(String key) to their plugin before they use any IPUtils methods.";

    private HashMap<String, JSONObject> ipStorage = new HashMap<>();

    public static void setAPIKey(String key) {
        apiKey = key;
    }
    public static boolean isAPIKeySet() {
        return apiKey != null;
    }

    private String ip;
    public IPUtils(String ip) {
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return;
        }

        this.ip = ip;
    }
    public static IPUtils getFromIP(String ip) {
        return new IPUtils(ip);
    }

    public String getIP() {
        return ip;
    }

    public int getTimeOffset() {
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return 0;
        }

        int offset;
        if (ipStorage.containsKey(ip)){
            offset = Integer.parseInt((String) ipStorage.get(ip).get("timeZone"));
        } else {
            String url = "[url]http://api.ipinfodb.com/v3/ip-city/?key=" + apiKey + "&ip="+ip+"&format=json";
            JSONObject object = stringToJSON(getUrlSource(url));
            String timezone = (String) object.get("timeZone");
            if (timezone != null && timezone.length() > 3){
                offset = Integer.parseInt(timezone.substring(0,timezone.length()-3));
                ipStorage.put(ip,object);
            } else {
                return 0;
            }
        }
        return offset;
    }

    public String getTime() {
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        Calendar time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        time.add(Calendar.HOUR_OF_DAY,getTimeOffset());
        DateFormat formatter = new SimpleDateFormat("EEEEEE hh:mm");
        formatter.setCalendar(time);
        String date = formatter.format(time.getTime());
        DateFormat formatter2 = new SimpleDateFormat("aa");
        formatter2.setCalendar(time);
        date += formatter2.format(time.getTime()).toLowerCase();
        return date;
    }


    public String getCityName(){
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        JSONObject json;

        if (ipStorage.containsKey(ip)){
            json = ipStorage.get(ip);
        } else {
            String url = "[URL]http://api.ipinfodb.com/v3/ip-city/?key=" + apiKey + "&ip=[/url]"+ip+"&format=json";
            JSONObject object = stringToJSON(getUrlSource(url));
            json = object;

            ipStorage.put(ip,object);
        }

        return (String) json.get("cityName");
    }

    public String getStateName(){
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        JSONObject json;

        if (ipStorage.containsKey(ip)){
            json = ipStorage.get(ip);
        } else {
            String url = "[url]http://api.ipinfodb.com/v3/ip-city/?key=" + apiKey + "&ip=[/url]"+ip+"&format=json";
            JSONObject object = stringToJSON(getUrlSource(url));
            json = object;
            ipStorage.put(ip,object);
        }

        return (String) json.get("regionName");
    }

    public String getCountryName(){
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        JSONObject json;

        if (ipStorage.containsKey(ip)){
            json = ipStorage.get(ip);
        } else {
            String url = "[url]http://api.ipinfodb.com/v3/ip-city/?key=" + apiKey + "&ip=[/url]"+ip+"&format=json";
            JSONObject object = stringToJSON(getUrlSource(url));
            json = object;

            ipStorage.put(ip,object);
        }

        String country = (String) json.get("countryName");

        if (country.contains(",")) country = country.split(",")[0];

        return country;
    }

    public String getCountryCode(){
        if(!isAPIKeySet()) {
            CyberAPI.getAPI().logAPI(CyberAPI.APILevel.ERROR, apiKeyError.replace("${plugin}", CyberAPI.getAPI().getPluginName())); return null;
        }

        JSONObject json;

        if (ipStorage.containsKey(ip)) {
            json = ipStorage.get(ip);
        } else {
            String url = "[url]http://api.ipinfodb.com/v3/ip-city/?key=" + apiKey + "&ip=[/url]"+ip+"&format=json";
            JSONObject object = stringToJSON(getUrlSource(url));
            json = object;

            ipStorage.put(ip,object);
        }
        return (String) json.get("countryCode");
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
        }

        return returned.toString();
    }
}
