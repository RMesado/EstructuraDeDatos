package Practica2.Polinomio;

/**
 * @author morales La clase implementa diversos métodos estáticos para acotar el
 *         valor cero de un valor <code>double</code>.
 */
public class Cero {

	/**
	 * Umbral por debajo del cual cualquier se considera cero el valor absoluto
	 * de cualquier <code>double</code>
	 */
	public static double UMBRAL_CERO = 0.000001;

	/**
	 * Dtermina si dos <code>double</code> son iguales teniendo en cuenta el umbral
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean doubleEquals(double a, double b) {
		return (Math.abs(b - a) < Cero.UMBRAL_CERO);
	}

	/** Determina si un <code>double</code> es cero teniendo en cuenta el umbral
	 * @param a
	 * @return
	 */
	public static boolean esCero(double a) {
		return (Math.abs(a) < UMBRAL_CERO);
	}

	/**  Compara dos <code>double</code> teniendo en cuenta el umbral.
	 * @param a
	 * @param b
	 * @return
	 */
	public static int compareDouble(double a, double b) {
		double diff = b - a;
		if (Cero.esCero(diff))
			return 0;

		return (int) Math.signum(diff);
	}
}
