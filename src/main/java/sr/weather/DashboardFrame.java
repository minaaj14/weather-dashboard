package sr.weather;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;


public class DashboardFrame extends JFrame{
	
	private List<WeatherReading>readings;
	private JPanel topPanel;
	private XChartPanel<XYChart> chartPanel;
	

	public DashboardFrame(List<WeatherReading>readings){
		this.readings=readings;
		setTitle("Weather Dashboard");
		setSize(900,650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		createTopPanel();
		addChart();
		createBottomPanel();
		Timer timer=new Timer(600000,e->refreshData()); //osvezava na svakih 10min
		timer.start();
		setVisible(true);
	}
	
	private void createBottomPanel() {
		JButton refreshButton=new JButton("REFRESH");
		refreshButton.addActionListener(e->refreshData());
		add(refreshButton,BorderLayout.SOUTH);
	}

	private void addChart() {
		XYChart chart=new XYChartBuilder().width(600).height(400).title("Temperatura kroz vreme").xAxisTitle("merenja").yAxisTitle("°C").build();
		List<Integer>xPodaci=new ArrayList<>();
		List<Double>yPodaci=new ArrayList<>();
		int i=0;
		for(WeatherReading w:readings) {
			i++;
			xPodaci.add(i);
			yPodaci.add(w.temperature);
		}
		chart.addSeries("Temperatura", xPodaci, yPodaci);
		chartPanel = new XChartPanel<>(chart);
		add(chartPanel,BorderLayout.CENTER);
	}

	private void createTopPanel() {
		topPanel=new JPanel(new GridLayout(1,4));
		WeatherReading r=readings.getLast();
		JLabel l1=new JLabel("temperature: "+r.temperature+"°C");
		l1.setFont(new Font("SansSerif", Font.BOLD, 18));
		JLabel l2=new JLabel("feels like: "+ r.feelsLike+"°C");
		l2.setFont(new Font("SansSerif", Font.BOLD, 18));
		JLabel l3=new JLabel("humidity: "+r.humidity);
		l3.setFont(new Font("SansSerif", Font.BOLD, 18));
		JLabel l4=new JLabel("description: "+r.description);
		l4.setFont(new Font("SansSerif", Font.BOLD, 18));
		topPanel.add(l1);
		topPanel.add(l2);
		topPanel.add(l3);
		topPanel.add(l4);
		add(topPanel,BorderLayout.NORTH);
		
	}
	private void refreshData() {
		WeatherService s=new WeatherService();
		try {
			readings.add(s.fetchCurrentWeather());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		remove(topPanel);
		createTopPanel();
		remove(chartPanel);
		addChart();
		revalidate();
		repaint();
	}

	public static void main(String[] args) {
		WeatherDatabase db=new WeatherDatabase();
		List<WeatherReading> list;
		try {
			list = db.loadAll();
			SwingUtilities.invokeLater(()->new DashboardFrame(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
