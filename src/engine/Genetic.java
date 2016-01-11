package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Genetic {
	public Individual best = null;
	private static final Random RAND = new Random();

	public List<Individual> population = new ArrayList<>();
	public GeneticOptions options;

	public Genetic(GeneticOptions options) {
		this.options = options;
		for (int i = 0; i < options.populationSize; i++) {
			population.add(new Individual(options.from, options.to, options.sectionNumber));
		}
	}

	public void live(int time) throws Exception {
		for (int i = 0; i < time; i++) {
			die();
			int sum = 0;
			for (Individual individual : population) {
				sum += individual.minimums() + 1;
			}
			for (int j = 0; j < (int) (0.15 * options.populationSize); j++) {
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
			count += individual.minimums() + 1;
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

	private Individual[] cross(Individual[] parents) throws Exception {
		Individual[] offspring = new Individual[4];
		int cut = RAND.nextInt(options.sectionNumber - 1) + 1;

		List<Section> a1 = parents[0].sections.subList(0, cut);
		List<Section> a2 = parents[0].sections.subList(cut, options.sectionNumber);
		List<Section> b1 = parents[1].sections.subList(0, cut);
		List<Section> b2 = parents[1].sections.subList(cut, options.sectionNumber);

		offspring[0] = strongWeak(a1, b2);
		offspring[1] = weakStrong(a1, b2);
		offspring[2] = strongWeak(b1, a2);
		offspring[3] = weakStrong(b1, a2);
		for (Individual off : offspring) {
			for (int i = 0; i < off.sections.size() - 1; i++) {
				if (off.sections.get(i).to() != off.sections.get(i + 1).from()) {
					System.out.println("INVALID DATA!");
				}
				if (off.sections.get(i).from() > off.sections.get(i).to()) {
					System.out.println("INVALID DATA!");
				}
			}
		}

		return offspring;
	}

	private Individual strongWeak(List<Section> strong, List<Section> weak) throws Exception {
		List<Section> partA = new LinkedList<>(strong);
		List<Section> partB = new LinkedList<>(weak);

		double partAto = partA.get(partA.size() - 1).to();
		double newFrom = partAto;
		double newTo;
		boolean broken = true;

		for (int i = 0; i < partB.size(); i++) {
			if (broken) {
				if (partB.get(i).to() < newFrom) {
					newTo = strongAverage(newFrom, partB.subList(i + 1, partB.size()));
				} else {
					newTo = partB.get(i).to();
					broken = false;
				}
				Section newSection = new Section(newFrom, newTo);
				newFrom = newTo;
				partA.add(newSection);
			} else {
				partA.add(partB.get(i));
			}
		}

		return new Individual(partA);
	}

	private Individual weakStrong(List<Section> weak, List<Section> strong) throws Exception {
		List<Section> partA = new LinkedList<>(weak);
		List<Section> partB = new LinkedList<>(strong);

		double partBfrom = partB.get(0).from();
		double newTo = partBfrom;
		double newFrom;
		boolean broken = true;

		for (int i = partA.size() - 1; i >= 0; i--) {
			if (broken) {
				if (partA.get(i).from() > newTo) {
					newFrom = weakAverage(newTo, partA);
				} else {
					newFrom = partA.get(i).from();
					broken = false;
				}
				Section newSection = new Section(newFrom, newTo);
				newTo = newFrom;
				partB.add(0, newSection);
			} else {
				partB.add(0, partA.get(i));
			}
		}

		return new Individual(partB);
	}

	private double strongAverage(double value, List<Section> sections) throws Exception {
		for (Section section : sections) {
			double num = (value + section.to()) / 2.0;
			if (num > value) {
				return num;
			}
		}
		throw new Exception("Unacceptable");
	}

	private double weakAverage(double value, List<Section> sections) throws Exception {
		for (int i = sections.size() - 1; i >= 0; i--) {
			double num = (value + sections.get(i).from()) / 2.0;
			if (num < value) {
				return num;
			}
		}
		throw new Exception("Unacceptable");
	}

	private void mutate(Individual child) {

	}

	private void die() {
		for (Individual individual : population) {
			individual.minimums();
		}
		Collections.sort(population, new Comparator<Individual>() {

			@Override
			public int compare(Individual i1, Individual i2) {
				int cmp = Integer.compare(i2.minimums(), i1.minimums());
				if (cmp != 0) {
					return cmp;
				} else {
					return Double.compare(i1.precision(), i2.precision());
				}
			}

		});

		if (best == null || best.minimums() < population.get(0).minimums()
				|| (best.minimums() == population.get(0).minimums())
						&& best.precision() > population.get(0).precision()) {
			best = population.get(0);
		}
		population = new LinkedList<>(population.subList(0, (int) (0.4 * options.populationSize)));
	}

}
