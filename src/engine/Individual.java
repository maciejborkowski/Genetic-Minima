package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Individual {
	private static final Random RAND = new Random();

	public final double from;
	public final double to;
	public final List<Section> sections;
	public final int sectionNumber;
	private int minimums = -1;

	public Individual(double from, double to, int sectionNumber) {
		this.sections = new ArrayList<>();
		this.from = from;
		this.to = to;
		this.sectionNumber = sectionNumber;
		randomizeSections();
	}

	public Individual(List<Section> sections) {
		this.sections = sections;
		this.from = sections.get(0).from();
		this.sectionNumber = sections.size();
		this.to = sections.get(sectionNumber - 1).to();
	}

	public void randomizeSections() {
		double[] borders = new double[sectionNumber + 1];
		for (int i = 0; i < sectionNumber - 1; i++) {
			borders[i] = RAND.nextDouble() * (to - from) + from;
		}
		borders[sectionNumber - 1] = from;
		borders[sectionNumber] = to;
		Arrays.sort(borders);
		for (int i = 0; i < sectionNumber; i++) {
			sections.add(new Section(borders[i], borders[i + 1]));
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
