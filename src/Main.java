import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.gson.*;
import com.google.gson.reflect.*;


public class Main {
    public static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );
        return map;
    }

    public static void main(String[] args) {
        String API_KEY = "f88a20aa109b8ad43a1bd9be2df3a89e";
        String Location = "rexburg";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + Location + "&appid=" + API_KEY;


        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            System.out.println(result);

            Map<String, Object> respMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

            System.out.println("Current Temperature:" + mainMap.get("temp"));
            System.out.println("Current Humidity:" + mainMap.get("humidity"));
            System.out.println("Wind Speeds::" + mainMap.get("speed"));
            System.out.println("Wind Angle::" + mainMap.get("deg"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Hello and welcome!");

    }
}
