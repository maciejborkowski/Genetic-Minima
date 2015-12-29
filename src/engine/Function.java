package engine;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

public class Function {
	private static double a = 4.0;
	private static double b = 6.0;
	private static double c = 2.3;
	private static double d = 2.0;

	public static double getValue(double x) {
		return exp(sin(a * x)) - sin(exp(log(x))) + sin(b * x) - sin(sin(pow(x, c))) + d * sin(x);
	}
}
