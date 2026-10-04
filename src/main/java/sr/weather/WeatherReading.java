package sr.weather;

public class WeatherReading {
	 String city;
	 double temperature;
	 double feelsLike; 
	 int humidity;
	 double windSpeed;
	 String description;
	 long measuredAt;   // vreme merenja (dt iz JSON-a)
	 
    @Override  public String toString() {
    	return city + ": " + temperature + "°C (oseća se " + feelsLike + "°C), vlaga " + humidity + "%, vetar " + windSpeed
    			+ " m/s, " + description; 
    	}
    public WeatherReading(){};
    public WeatherReading(String city, double temperature, double feelsLike, int humidity, double windSpeed, 
    		String description,long measured){
    	this.city=city;
    	this.temperature=temperature;
    	this.feelsLike=feelsLike;
    	this.humidity=humidity;
    	this.windSpeed=windSpeed;
    	this.description=description;
    	this.measuredAt=measured;
    };
}
