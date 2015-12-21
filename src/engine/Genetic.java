package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Genetic {
	public static Individual BEST = null;
	private static final Random RAND = new Random();

	public List<Individual> population = new ArrayList<>();
	public GeneticOptions options;

	public Genetic(GeneticOptions options) {
		this.options = options;
		for (int i = 0; i < options.populationSize; i++) {
			population.add(new Individual(options.from, options.to));
		}
	}

	public void live(int time) {
		for (int i = 0; i < time; i++) {
			for (int j = 0; j < 10; j++) {
				cross();
				if (RAND.nextDouble() < options.mutationRate) {
					mutate();
				}
			}
			die();
		}
	}

	private void cross() {

	}

	private void mutate() {

	}

	private void die() {
		Collections.sort(population, new Comparator<Individual>() {

			@Override
			public int compare(Individual i1, Individual i2) {
				return i1.fitness() - i2.fitness();
			}

		});

		if (BEST == null || BEST.fitness() < population.get(0).fitness()) {
			BEST = population.get(0);
		}
		population = population.subList(0, options.populationSize);
	}

}
