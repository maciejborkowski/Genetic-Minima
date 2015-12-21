package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Individual {
	private static final Random RAND = new Random();

	public double from;
	public double to;
	public final List<Section> sections = new ArrayList<>();
	private int minimums = -1;

	public Individual(double from, double to) {
		this.from = from;
		this.to = to;
		for (int i = 0; i < Section.SIZE; i++) {
			sections.add(new Section());
		}
		randomizeSections();
	}

	public void randomizeSections() {
		double[] borders = new double[Section.SIZE + 1];
		for (int i = 0; i < Section.SIZE - 2; i++) {
			borders[i] = RAND.nextDouble() * (to - from) + from;
		}
		borders[Section.SIZE - 1] = from;
		borders[Section.SIZE] = to;
		Arrays.sort(borders);
		for (int i = 0; i < Section.SIZE; i++) {
			sections.get(i).setFrom(borders[i]);
			sections.get(i).setTo(borders[i + 1]);
		}
	}

	public int fitness() {
		if (minimums == -1) {
			minimums = 0;
			for (Section section : sections) {
				if (section.containsMinimum()) {
					minimums++;
				}
			}
		}

		return minimums;
	}

	public List<Section> getSections() {
		return sections;
	}
}
