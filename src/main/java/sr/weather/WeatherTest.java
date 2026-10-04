package sr.weather;

public class WeatherTest {
	public static void main(String[] args) {
		try {
			System.out.println(new WeatherService().fetchCurrentWeather().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
