package Practica2.Polinomio;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class PolinomioJUnit {

	/** Método auxiliar.
	 * Comprueba que una lista de monomios sea correcta: Monomios ordenados de exponente 
	 * menor a mayor, coeficientes distintos de cero.<br>  
	 */
	static void testOrdenyCeros(List<Monomio> polinomio) {
		int exp = -1;
		assertNotNull(polinomio);
		
		for (Monomio mono: polinomio) {
			assertFalse("Practica2.Polinomio.Polinomio.Monomio con coeficietne cero", Cero.esCero(mono.coeficiente));
			assertTrue("Monomios desordenados", exp < mono.exponente);
			exp = mono.exponente;
		}
	}
	
	/**
	 * Método auxiliar. Compara un vector y una lista de monomios. Por cada elemento del vector i distinto de 
	 * cero debe existir un monomio con exponente i y coeficiente v[i].
	 */
	static void testComparacion(double v[], List<Monomio> poli) {
		
		assertNotNull(poli);
		assertTrue("Demasiados monomios", v.length >= poli.size());
		
		Iterator<Monomio> iter = poli.iterator();
		for (int exp=0; exp < v.length; exp++) {
			if (!Cero.esCero(v[exp])) {
				assertTrue("Faltan monomios", iter.hasNext());
				Monomio mono = iter.next();
				
				assertEquals("Exponentes distintos", exp, mono.exponente);
				assertTrue("Coeficientes distintos", Cero.doubleEquals(v[exp], mono.coeficiente));
			}
		}
		
		assertFalse(iter.hasNext());
			
	}
	
	/**
	 * Método auxuliar. Comprueba que dos polinomios distintos tengan el mismo contenido.
	 */
	static void testdeepCopy(List<Monomio> poli1, List<Monomio> poli2) {
		assertTrue( poli1 != null && poli2 != null);
		assertEquals("Polinomios de distonto tamaño", poli1.size(), poli2.size());
		Iterator<Monomio> iter1 = poli1.iterator();
		Iterator<Monomio> iter2 = poli2.iterator();
		
		while (iter1.hasNext())
			assertFalse("Monomios son el mismo objecto", iter1.next() == iter2.next());
	}
	
	static double [] sumaVectores(double v1[], double v2[]) {
		double result [] = new double[Math.max(v1.length, v2.length)];
		
		for (int i=0 ; i < v1.length; i++)
			result[i] = v1[i];
		
		for (int i=0 ; i < v2.length; i++)
			result[i] += v2[i];
		
		return result;
	}
	
	static double[] multiplicaMonomio(double v[], Monomio mono) {
		double result[] = new double[v.length + mono.exponente];
		
		for (int i = 0; i < v.length; i++)
			result[i + mono.exponente] = v[i]*mono.coeficiente;
		
		return result;
	}
	
	/**
	 * Lista de vectores de prueba. Se construiran polinomios a partir de ellos.
	 */
	static double poliData1[][] =  {
		{},
		{1,2,3,4},
		{1,0,-2},
		{0,0,0,0},
		{0,0,0,0, -5},
		{0, 3, 0, 0},
		{6, 0, 0, 0},
		{1, -1, 1, -1},
		{0.00000000001, 0.000000001, -0.0000000001}
	};
	
	
	/**
	 * Test que verifica que un polinomio esté bien construido por defecto.<br>
	 * 
	 * El tamaño de la lista de monomios debe ser 0.
	 */
	@Test
	public void testConstructorDefault()
	{
		Polinomio poli = null;
		System.out.println("\nValidando constructor por defecto...");
		poli = new Polinomio();
		
		System.out.println("Resultado :" + poli);
		assertEquals(0, poli.monomios().size());		
	}
	
	
	/**
	 * Test que verifica que un polinomio esté bien construido a partir de un vector.<br>
	 * 
	 * Contruye polinomios a partir de todos los vectores de prueba.
	 * Comprueba que no contengan ceros, y los monomios contengan los valores correctos.
	 */
	@Test
	public void testConstructorVector() {
		System.out.println("\nValidando constructor a partir de vector...");
		System.out.println(" [Vector de entrada] -> Practica2.Polinomio.Polinomio.Practica2.Polinomio.Polinomio resultante\n");

		for (double[] v: poliData1) {
			System.out.print(Arrays.toString(v));
			Polinomio poli = new Polinomio(v);
			System.out.println(" -> " + poli);	
			
			testOrdenyCeros(poli.monomios());
			testComparacion(v, poli.monomios());
		}
	}

	/**
	 * Test que verifica que un polinomio esté bien copiado a partir de otro.<br>
	 * 
	 * Contruye polinomios a partir de los vectores de prueba, realiza una copia de ellos 
	 * y compureba que los polinimios resultantes no contengan ceros, y los monomios contengan los valores correctos.
	 * Además comprueba que el polinomio orifinal y final no compartan monomios. 
	 */
	@Test
	public void testConstructorCopia() {
		System.out.println("\nValidando constructor a partir de una polinomio...");
		System.out.println(" Practica2.Polinomio.Polinomio.Practica2.Polinomio.Polinomio original -> polinomio resultante\n");

		for (double[] v: poliData1) {
			Polinomio poli = new Polinomio(v);
			testOrdenyCeros(poli.monomios());
			testComparacion(v, poli.monomios());
			
			System.out.print(poli);
			Polinomio copia = new Polinomio(poli);
			System.out.println(" -> " + copia);	
			
			testOrdenyCeros(copia.monomios());
			testComparacion(v, copia.monomios());
			testdeepCopy(poli.monomios(), copia.monomios());
		}
	}
	

	/**
	 * Segunda lista de vectores de prueba. 
	 */
	double poliData2[][] =  {
			{3, 4, 5},
			{9, 8, 6},
			{5, 10, 7, 6},
			{0, 2, -2},
			{2, 2, 2},
			{-2, -2,},
			{2, 2},
			{},
			{1, 2, 3},
			{-1, 0, -3},
			{0, 0, -5, 0, 0},
			{2, 0, 3},
			{2, 0, 3, 0, 0, 8},
			{0.00000000001, 0.000000001, -0.0000000001}
	};
			
		
	/**
	 * Prueba el funcionamiento del método de suma <br>
	 * 
	 * Toma vectores de datos, dos a dos, construye sendos polinomios a partir de ellos, y los suma.
	 * Comprueba que el resultado sea un polinomio bien construido y que el resultado de la suma sea correcto.
	 */
	@Test
	public void TestSuma() {
		System.out.println("\nValidando suma de polinomios...");
		System.out.println(" Primer polinomo \n + Segundo polinomio \n -> polinomio resultado\n");
		for (int i = 0; i < poliData2.length-1; i++) {
			Polinomio poli1 = new Polinomio(poliData2[i]);
			Polinomio poli2 = new Polinomio(poliData2[i+1]);
			
			System.out.println("(this) " + poli1 + "\n   +   " + poli2);
			
			poli1.sumar(poli2);
			
			double expected[] = sumaVectores(poliData2[i], poliData2[i+1]);
			
			System.out.println("  ->   " + poli1 + "\n");
			testOrdenyCeros(poli1.monomios());
			testComparacion(expected, poli1.monomios());
		}
	}
			
	/**
	 * Prueba la suma de un polinomio consigo mismo<br>
	 * 
	 * Contruye un poilnomio a partir de un vector de prueba. Suma el polinomio resultante consigo mismo.
	 * Verifica que el polinomio resultante esté bien construido y contenga los valores correctos
	 */
	@Test
	public void TestSumaAuto() {
		System.out.println("\nValidando suma de polinomios consigo mismos...");
		System.out.println(" Polinomo \n + Practica2.Polinomio.Polinomio.Practica2.Polinomio.Polinomio \n -> polinomio resultado\n");
		for (int i = 0; i < poliData1.length; i++) {
			Polinomio poli1 = new Polinomio(poliData1[i]);
			
			System.out.println("(this) " + poli1 + "\n   +   " + poli1);
			
			poli1.sumar(poli1);
			
			double expected[] = sumaVectores(poliData1[i], poliData1[i]);
			
			System.out.println("  ->   " + poli1 + "\n");
			testOrdenyCeros(poli1.monomios());
			testComparacion(expected, poli1.monomios());
		}
	}
	
	/** 
	 * Prueba que un polinomio no pueda sumarse con null.
	 */
	@Test(expected = NullPointerException.class)
	public void TestSumaExpception() {
		System.out.println("\nValidando excepciones en la suma...");
		
		double v[] = {1, 2, 3};
		
		Polinomio poli = new Polinomio (v);	
		poli.sumar(null);
	}
	
	/**
	 * Comprueba el método equals de polinomios. Primero de un polinomio consigo mismo, 
	 * y segundo entre dos polinomios distintos pero de igual contenido
	 */
	@Test
	public void testEquals() {
		System.out.println("\nValidando igualdad de polinomios a si mismos...");
		for (int i = 0; i < poliData1.length; i++) {
			Polinomio poli1 = new Polinomio(poliData1[i]);
			
			System.out.println(poli1);
			assertEquals(poli1, poli1);
		}
		
		System.out.println("\nValidando igualdad de polinomios a copias de si mismos...");
		for (int i = 0; i < poliData1.length; i++) {
			Polinomio poli1 = new Polinomio(poliData1[i]);
			Polinomio poli2 = new Polinomio(poli1);
			
			System.out.print(poli1 + " == " + poli2);
			assertEquals(poli1, new Polinomio(poli1));
			System.out.println(" ... OK");
		}
		
		System.out.println("\nValidando igualdad entre polinomios distintos...");
		for (int i = 0; i < poliData1.length-1; i++) {
			Polinomio poli1 = new Polinomio(poliData1[i]);
			Polinomio poli2 = new Polinomio(poliData1[i+1]);
			
			System.out.print(poli1 + " != " + poli2);
			assertNotEquals(poli1, poli2);
			System.out.println(" ... OK");
		}
		
	}
	
	/** 
	 * Vector de monomios de prueba
	 */
	static double[][] poliData3 = {{2, 0}, {0,0}, {2, 2}, {0,2}, {-1, 1}, {-1, 0}, {0.0000000001, 2}};

	/**
	 * Prueba la multiplicación de un monomio por un polinomio. <br>
	 * 
	 * Contruye un polinomio a partir de un vector, y un monomio a partir de un par de números. 
	 * Realiza la multiplicación de ambos. Comprueba que el resultado sea polinomio bien construido, 
	 * y que el resultado sea correcto. 
	 */
	@Test
	public void TestMultiplcarMonomio() {
		System.out.println("\nValidando multiplicacion por monomios..");
		System.out.println("(polinomio) * monomio \n -> polinomio resultante");
		for (int i = 0; i < poliData3.length; i++) {
			Monomio mono = new Monomio(poliData3[i][0], (int)poliData3[i][1]);
			
			for (int j = 0; j < poliData1.length; j++) {
				Polinomio poli = new Polinomio(poliData1[j]);
				
				System.out.println("(" + poli + ") " + " * " + mono);
				
				poli.multiplicarMonomio(mono);
				
				System.out.println(" -> " + poli + "\n");

				double expected[] = multiplicaMonomio(poliData1[j], mono);
							
				testComparacion(expected, poli.monomios());
				
			}
		}	
	}
}
