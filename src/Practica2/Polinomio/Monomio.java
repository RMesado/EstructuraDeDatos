package Practica2.Polinomio;

public class Monomio {
	public double coeficiente;
	public int exponente;
	
	/** Cosntructor, crea un monomio a partir de su coefieciente y exponente.
	 * No admite exponentes negativos. 
	 * Admite coeficientes cero.
	 * @param coef El coeficiente del monomio
	 * @param exp  El exponente del monomio
	 * @throws <code>IllegalArgumentExcetion<\code> si el exponente es negativo.
	 */
	public Monomio (double coef, int exp) {
		if (exp < 0)
			throw new IllegalArgumentException("Exponente negativo");
		
		coeficiente = coef;
		exponente = exp;
	}
	
	/** Constructor copía. 
	 * @param otro El monomio a copiar
	 * @throws <code>NUllPointerException</code> si el parámetro es <code>null</code>.
	 */
	public Monomio (Monomio otro) {
		if (otro == null)
			throw new NullPointerException();
		
		coeficiente = otro.coeficiente;
		exponente = otro.exponente;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(coeficiente);
		if (exponente > 0)
			str.append('X');
		if (exponente > 1)
			str.append("^"+ exponente);
				
		return str.toString(); 
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Monomio other = (Monomio) obj;
		if (exponente != other.exponente)
			return false;
		
		if (!Cero.doubleEquals(coeficiente, other.coeficiente))
			return false;
		
		return true;
	}
}
