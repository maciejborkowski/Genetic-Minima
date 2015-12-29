package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import engine.Function;
import engine.Genetic;
import engine.GeneticOptions;
import engine.Individual;
import engine.Section;

public class GeneticMinimaApplication {
	private final static String WINDOW_LABEL = "Genetic Minima";

	private JFrame frame;
	private ChartPanel chartPanel;
	private Genetic genetic;

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
		initializeGenetic();
		initializeApplication();
	}

	private void initializeApplication() {
		initFrame();
	}

	private void initializeGenetic() {
		GeneticOptions options = new GeneticOptions();
		options.from = 0;
		options.to = 10;
		options.populationSize = 100;
		options.mutationRate = 0.005;
		options.sectionNumber = 100;
		genetic = new Genetic(options);
		try {
			genetic.live(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initFrame() {
		frame = new JFrame(WINDOW_LABEL);
		frame.setBounds(0, 0, 800, 680);
		frame.getContentPane().setLayout(new BorderLayout());
		initGraph();
	}

	private void initGraph() {
		XYDataset dataset = createDataset(
				Optional.ofNullable(Genetic.BEST).map(Individual::getSections).orElse(new ArrayList<>()));
		System.out.println("Minimas: " + Genetic.BEST.fitness());
		JFreeChart lineChart = ChartFactory.createXYLineChart("", "x", "f(x)", dataset);
		lineChart.removeLegend();
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));

		chartPanel = new ChartPanel(lineChart);
		frame.add(chartPanel);
	}

	private XYDataset createDataset(List<Section> sections) {
		XYSeriesCollection dataset = new XYSeriesCollection();

		for (Section section : sections) {
			//if (section.containsMinimum()) {
				System.out.println(section.toString());
				XYSeries data = new XYSeries(section.hashCode());
				double step = section.width() / Section.SIZE;
				for (int i = 0; i < Section.SIZE; i++) {
					double x = section.from() + step * i;
					double y = Function.getValue(x);
					data.add(x, y);
				}
				dataset.addSeries(data);
			//}
		}

		return dataset;
	}

}