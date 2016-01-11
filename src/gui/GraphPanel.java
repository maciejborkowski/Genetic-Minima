package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import engine.Function;
import engine.Individual;
import engine.Section;

@SuppressWarnings("serial")
public class GraphPanel extends ChartPanel {

	public GraphPanel() {
		super(null);
	}

	public void drawGraph(Individual individual, boolean showAllSections) {
		XYDataset dataset = createDataset(
				Optional.ofNullable(individual).map(Individual::getSections).orElse(new ArrayList<>()),
				showAllSections);
		System.out.println("Minimas: " + individual.minimums() + " Sum: " + individual.precision());
		JFreeChart lineChart = ChartFactory.createXYLineChart("", "x", "f(x)", dataset);
		lineChart.removeLegend();
		setChart(lineChart);

		revalidate();
		repaint();
	}

	private XYDataset createDataset(List<Section> sections, boolean showAllSections) {
		XYSeriesCollection dataset = new XYSeriesCollection();

		for (Section section : sections) {
			if (showAllSections || section.containsMinimum()) {
				// System.out.println(section.toString());
				XYSeries data = new XYSeries(section.hashCode());
				double step = section.width() / Section.SIZE;
				for (int i = 0; i < Section.SIZE; i++) {
					double x = section.from() + step * i;
					double y = Function.getValue(x);
					data.add(x, y);
				}
				dataset.addSeries(data);
			}
		}

		return dataset;
	}
}
