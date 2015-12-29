package engine;

public class Section {
	public static int SIZE = 25;
	private final double from;
	private final double to;

	private final double[] xCoordinates;
	private double[] yCoordinates;

	public Section(double from, double to) {
		this.from = from;
		this.to= to;
		xCoordinates = new double[SIZE + 1];
		calculateXCoordinates();
	}

	public boolean containsMinimum() {
		double[] xs = getXCoordinates();

		double leftBorder = Function.getValue(xs[0]);
		double rightBorder = Function.getValue(xs[SIZE]);

		for (int i = 1; i < SIZE - 1; i++) {
			double value = Function.getValue(xs[i]);
			if (value < leftBorder & value < rightBorder) {
				return true;
			}
		}
		return false;
	}

	public double[] getXCoordinates() {
		return xCoordinates;
	}

	public double[] getYCoordinates() {
		if (yCoordinates == null) {
			calculateYCoordinates();
		}
		return yCoordinates;
	}

	public double width() {
		return to - from;
	}

	public double from() {
		return from;
	}

	public double to() {
		return to;
	}

	private void calculateXCoordinates() {
		double step = width() / SIZE;
		for (int i = 0; i < SIZE + 1; i++) {
			xCoordinates[i] = from + step * i;
		}
	}

	private void calculateYCoordinates() {
		yCoordinates = new double[SIZE + 1];
		for (int i = 0; i < SIZE + 1; i++) {
			yCoordinates[i] = Function.getValue(xCoordinates[i]);
		}
	}

	@Override
	public String toString() {
		return from + " - "  + to;
	}

}
