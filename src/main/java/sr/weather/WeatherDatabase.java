package sr.weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WeatherDatabase {

	public void save(WeatherReading w)throws Exception {
		Connection conn=DriverManager.getConnection("jdbc:sqlite:weather.db");
		Statement st=conn.createStatement(); 
		st.execute("CREATE TABLE IF NOT EXISTS readings("
				+ "city TEXT, temperature REAL, feels_like REAL, humidity INTEGER,"
				+ "wind_speed REAL, description TEXT, measured_at INTEGER)");
		st.close();
		
		String sql="INSERT INTO readings VALUES (?,?,?,?,?,?,?)";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, w.city);
		ps.setDouble(2, w.temperature);
		ps.setDouble(3, w.feelsLike);
		ps.setInt(4, w.humidity);
		ps.setDouble(5, w.windSpeed);
		ps.setString(6, w.description);
		ps.setLong(7, w.measuredAt);
		ps.executeUpdate();
		conn.close();
	}
	
	public List<WeatherReading> loadAll()throws Exception{
		Connection conn=DriverManager.getConnection("jdbc:sqlite:weather.db");
		List<WeatherReading>list=new ArrayList<WeatherReading>();
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM readings ORDER BY measured_at");
		while(rs.next()) {
			String city=rs.getString("city");
			double temp=rs.getDouble("temperature");
			double feels=rs.getDouble("feels_like");
			int humid=rs.getInt("humidity");
			double windSp=rs.getDouble("wind_speed");
			String desc=rs.getString("description");
			int measured=rs.getInt("measured_at");
			list.add(new WeatherReading(city,temp,feels,humid,windSp,desc,measured));
		}
		conn.close();
		return list;
	}
}
