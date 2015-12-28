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
			population.add(new Individual(options.from, options.to, options.sectionNumber));
		}
	}

	public void live(int time) {
		for (int i = 0; i < time; i++) {
			die();
			int sum = 0;
			for (Individual individual : population) {
				sum += individual.fitness() + 1;
			}
			for (int j = 0; j < 40; j++) {
				Individual[] parents = choose(sum);
				Individual[] offspring = cross(parents);
				for (Individual child : offspring) {
					if (RAND.nextDouble() < options.mutationRate) {
						mutate(child);
					}
					population.add(child);
				}
			}
		}
	}

	private Individual[] choose(int sum) {
		Individual[] parents = new Individual[2];

		int rand1 = RAND.nextInt(sum);
		int rand2 = RAND.nextInt(sum);

		int lower = Math.min(rand1, rand2);
		int higher = Math.max(rand1, rand2);
		int count = 0;

		for (Individual individual : population) {
			count += individual.fitness() + 1;
			if (lower <= count && parents[0] == null) {
				parents[0] = individual;
			}
			if (higher <= count) {
				parents[1] = individual;
				break;
			}
		}

		return parents;
	}

	private Individual[] cross(Individual[] parents) {
		Individual[] offspring = new Individual[2];
		int cut = RAND.nextInt(options.sectionNumber - 1) + 1;

		List<Section> first = new ArrayList<>(parents[0].sections.subList(0, cut));
		first.addAll(parents[1].sections.subList(cut, options.sectionNumber));
		offspring[0] = new Individual(first);

		List<Section> second = new ArrayList<>(parents[1].sections.subList(0, cut));
		second.addAll(parents[0].sections.subList(cut, options.sectionNumber));
		offspring[1] = new Individual(second);

		return offspring;
	}

	private void mutate(Individual child) {

	}

	private void die() {
		for (Individual individual : population) {
			individual.fitness();
		}
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
