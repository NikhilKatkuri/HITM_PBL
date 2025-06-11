package weatherApi;

import Models.WeatherData;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherParser {

    public static WeatherData parseCurrentWeather(String jsonResponse) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonResponse);

        JSONObject city = (JSONObject) json.get("city");
        String cityName = city.get("name").toString() + ", " + city.get("country").toString();

        JSONArray list = (JSONArray) json.get("list");
        JSONObject current = (JSONObject) list.get(0);
        JSONObject main = (JSONObject) current.get("main");

        // Casted to Number and get float/double values
        float temp = ((Number) main.get("temp")).floatValue();
        float feelsLike = ((Number) main.get("feels_like")).floatValue();
        float tempMin = ((Number) main.get("temp_min")).floatValue();
        float tempMax = ((Number) main.get("temp_max")).floatValue();
        int pressure = ((Number) main.get("pressure")).intValue();
        int humidity = ((Number) main.get("humidity")).intValue();

        long currentTime = ((Number) current.get("dt")).longValue();
        long sunrise = ((Number) city.get("sunrise")).longValue();
        long sunset = ((Number) city.get("sunset")).longValue();

        return new WeatherData(temp, feelsLike, tempMin, tempMax, pressure, humidity, cityName, sunrise, sunset, currentTime);
    }
}
