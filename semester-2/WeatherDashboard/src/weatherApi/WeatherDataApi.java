package weatherApi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherDataApi {
    private static final String API_KEY = "0f7db9746381abd50ff80e56d84b9bf1";

    // Reusable method that accepts a city and returns weather JSON as a string
    public static String getWeatherData(String city) throws IOException, InterruptedException {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city +
                "&appid=" + API_KEY + "&units=metric";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Failed to fetch data: HTTP " + response.statusCode() + "\n" + response.body());
        }
    }

    // Example usage
     }
