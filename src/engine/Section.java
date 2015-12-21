package engine;

public class Section {
	public static int SIZE = 50;
	private double from;
	private double to;

	private double[] xCoordinates;
	private double[] yCoordinates;

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
		if (xCoordinates == null) {
			calculateXCoordinates();
		}
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

	public void setFrom(double from) {
		xCoordinates = null;
		yCoordinates = null;
		this.from = from;
	}

	public double to() {
		return to;
	}

	public void setTo(double to) {
		xCoordinates = null;
		yCoordinates = null;
		this.to = to;
	}

	private void calculateXCoordinates() {
		double step = width() / SIZE;
		xCoordinates = new double[SIZE + 1];
		for (int i = 0; i < SIZE + 1; i++) {
			xCoordinates[i] = step * i;
		}
	}

	private void calculateYCoordinates() {
		yCoordinates = new double[SIZE + 1];
		for (int i = 0; i < SIZE + 1; i++) {
			yCoordinates[i] = Function.getValue(xCoordinates[i]);
		}
	}

}
