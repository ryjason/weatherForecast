import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class Main {

    // Method to convert JSON string to a Map
    public static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        return map;
    }

    public static void main(String[] args) {

        // Infinite loop to keep asking for input until the user enters 'exit'
        while (true) {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter a city name,");
            System.out.println("Or to quit, enter 'exit':");
            String Location = myObj.nextLine();
            

            // Check if the user wants to exit the program
            if (Objects.equals(Location, "exit")) {
                break;
            }

            System.out.println("Enter the unit you want to see(metric/imperial):");
            String unit = myObj.nextLine();

            // OpenWeatherMap API Key
            String API_KEY = "f88a20aa109b8ad43a1bd9be2df3a89e";
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + Location + "&appid=" + API_KEY + "&units=" + unit;

            try {
                // Make API request and read response
                StringBuilder result = new StringBuilder();
                URL url = new URL(urlString);
                URLConnection conn = url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();

                // Print weather information from API response
                System.out.println(result);

                // Parse JSON response into a map
                Map<String, Object> respMap = jsonToMap(result.toString());
                Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
                Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

                // Display weather information
                System.out.println("Current Temperature:" + mainMap.get("temp"));
                System.out.println("Current Humidity:" + mainMap.get("humidity"));
                System.out.println("Wind Speeds:" + windMap.get("speed"));
                System.out.println("Wind Angle:" + windMap.get("deg"));

                // Write weather information to a file
                try (FileWriter writer = new FileWriter("weather.txt", true)) {
                    writer.write("City: " + Location + "\n");
                    writer.write("Current Temperature: " + mainMap.get("temp") + "\n");
                    writer.write("Current Humidity: " + mainMap.get("humidity") + "\n");
                    writer.write("Wind Speeds: " + windMap.get("speed") + "\n");
                    writer.write("Wind Angle: " + windMap.get("deg") + "\n");
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to the file.");
                    e.printStackTrace();
                }

            } catch (IOException e) {
                System.out.println("An error occurred while fetching weather information.");
                e.printStackTrace();

            }

        }
    }
}

