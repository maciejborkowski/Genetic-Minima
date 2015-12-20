package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import engine.Function;

public class GeneticMinimaApplication {
	private final static String WINDOW_LABEL = "Genetic Minima";

	private JFrame frame;
	private ChartPanel chartPanel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneticMinimaApplication window = new GeneticMinimaApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GeneticMinimaApplication() {
		initializeApplication();
	}

	private void initializeApplication() {
		initFrame();
	}

	private void initFrame() {
		frame = new JFrame(WINDOW_LABEL);
		frame.setBounds(0, 0, 800, 680);
		frame.getContentPane().setLayout(new BorderLayout());
		initGraph();
	}

	private void initGraph() {
		JFreeChart lineChart = ChartFactory.createXYLineChart("", "x", "f(x)", createDataset(0.0, 10.0, 1000));

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));

		chartPanel = new ChartPanel(lineChart);
		frame.add(chartPanel);
	}

	private XYDataset createDataset(double from, double to, int steps) {
		XYSeriesCollection dataset = new XYSeriesCollection();

		XYSeries data = new XYSeries("f(x)");
		double width = to - from;
		for (int i = 0; i < steps; i++) {
			double x = from + width * i / steps;
			double y = Function.getValue(x);
			data.add(x, y);
		}
		dataset.addSeries(data);

		return dataset;
	}
}