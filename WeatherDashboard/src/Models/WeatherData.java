package Models;

public record WeatherData(float temp, float feelsLike, float tempMin, float tempMax, int pressure, int humidity,
                          String cityName, long sunrise, long sunset, long currentTime) {
}
