package sr.weather;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ApiClient {
	private static final String API_KEY = System.getenv("OPENWEATHER_KEY");
	private static final String URL="https://api.openweathermap.org/data/2.5/weather?q=Belgrade&appid="+API_KEY+"&units=metric";
	
	String getWeatherInfo()throws Exception{
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).header("User-Agent", "weather-dashboard").build();
		HttpResponse<String> response =client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() != 200) {        
			throw new RuntimeException("API nije odgovorio, kod: " + response.statusCode());  
			}
		return response.body();
	}
    WeatherReading parseWeather(String json) {
		JsonObject root = JsonParser.parseString(json).getAsJsonObject();
		WeatherReading w=new WeatherReading();
		w.city=root.get("name").getAsString();
		JsonObject main = root.getAsJsonObject("main");
		w.temperature = main.get("temp").getAsDouble();
		w.feelsLike=main.get("feels_like").getAsDouble();
		w.humidity= main.get("humidity").getAsInt();
		JsonObject wind = root.getAsJsonObject("wind");      
	    w.windSpeed = wind.get("speed").getAsDouble();
	    JsonObject weather0 = root.getAsJsonArray("weather").get(0).getAsJsonObject();    //prvi element   
	    w.description = weather0.get("description").getAsString();
	    w.measuredAt = root.get("dt").getAsLong();
		return w;
	}
}
