package sr.weather;

public class WeatherService {
	
	private final ApiClient api = new ApiClient();
	private final WeatherDatabase db = new WeatherDatabase();

	public WeatherReading fetchCurrentWeather() throws Exception {
		WeatherReading r=api.parseWeather(api.getWeatherInfo());
		db.save(r);
		return r;
		
	}
}
