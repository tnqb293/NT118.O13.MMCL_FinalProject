package com.uit.weatherapp.model;

public class WeatherData {
    private String humidity = "";
    private String temperature = "";
    private String rainfall = "";
    private String windSpeed = "";

    public WeatherData(String humidity, String temperature, String rainfall, String windSpeed) {
        this.humidity = humidity;
        this.temperature = temperature;
        this.rainfall = rainfall;
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getRainfall() {
        return rainfall;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
}
