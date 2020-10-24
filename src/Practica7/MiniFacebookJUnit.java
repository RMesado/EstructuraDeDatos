package Practica7;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;


public class MiniFacebookJUnit {
	
	static public String ficheroRef = "miniFacebook.ref";
	
	private static int nNodos;
	private static String [] nodos; // nodos
	private static List<Integer>[] adyacentes; //nodos adyacentes
	private static String popular;
	private static class InfoDist {
		String name;
		int [] dist;
	}
	
	private static ArrayList<InfoDist> distancias = new ArrayList<InfoDist>();
	
	private static class Data {
		String name;
		ArrayList<String> elements;
		public Data() {
			elements=new ArrayList<String>();
		}
	}
	
	private static class Tinfo {
		String name;
		ArrayList<Data> lista;
		
		public Tinfo() {
			lista = new ArrayList<Data>();
		}
	}
	
	private static ArrayList<Tinfo> co = new ArrayList<Tinfo>();
	private static ArrayList<Tinfo> su = new ArrayList<Tinfo>();
	
	private static void leerDatos() {
		RandomAccessFile ref=null;
		try {
			ref = new RandomAccessFile(ficheroRef, "r");
			
		} catch(FileNotFoundException e) {
			System.err.println("  No se pudo abrir el fichero");
			return;
		}
		
		try {
			//leer Nodos y Arcos
			nNodos=ref.readInt();
			System.out.println("nNodos: "+nNodos);
			nodos = new String[nNodos];
			adyacentes =   new ArrayList[nNodos];
			for (int i=0; i<nNodos; i++) {
				nodos[i]=ref.readUTF();
				int nadyacentes = ref.readInt();
				adyacentes[i] = new ArrayList();
				for (int j=0; j<nadyacentes; j++) 
					adyacentes[i].add(ref.readInt());
			}
			
			popular = ref.readUTF(); //most popular
			
			for (int i=0; i<nNodos; i++) {
				InfoDist info=new InfoDist();
				info.name = ref.readUTF();
				info.dist = new int[nNodos];
				for (int j=0; j<nNodos; j++) 
					info.dist[j] = ref.readInt();
				distancias.add(info);
			}
			

			for (int i=0; i<nNodos; i++) {
				String n1 = ref.readUTF();
				Tinfo inf_co = new Tinfo();
				Tinfo inf_su = new Tinfo();
				inf_co.name =n1;
				inf_su.name =n1;
				for (int j=0; j<nNodos-1; j++) {
					Data d = new Data();
					String n2 = ref.readUTF();
					d.name = n2;
					int nElem = ref.readInt();
					for (int k=0; k<nElem; k++)
						d.elements.add(ref.readUTF());
					inf_co.lista.add(d);
					
					Data d2 = new Data();
					d2.name = n2;
					nElem = ref.readInt();
					for (int k=0; k<nElem; k++)
						d2.elements.add(ref.readUTF());
					inf_su.lista.add(d2);
				}
				co.add(inf_co);
				su.add(inf_su);								
					
			}
			ref.close();
		} catch (IOException e) {
			System.err.println("Error en la lectura del fichero de referencia");
			return;
		}
	}
	
	
	@Test
	public final void  testComprobarGrafo() {
		
		String filename = "miniFacebook.txt";
		EDGraph grafo =MiniFacebook.leerGrafo(filename);

		if (grafo == null)
			fail("No se pudo leer el grafo");

		leerDatos();
		
		System.out.println(" Comprobando el grafo ");
		
		System.out.println("  Numero de nodos ");
		assertEquals(nNodos, grafo.getSize());
		
		for (int i=0; i<nNodos; i++) {
			int index = grafo.getNodeIndex(nodos[i]);
			System.out.println("Comprobar que están todos los nodos. Nodo "+nodos[i]);
			assertNotEquals(index,-1);
			
			Set<Integer> ady = grafo.getAdyacentNodes(index);
			System.out.println("Comprobar el número de nodos adyacentes: "+adyacentes[i].size());
			assertEquals(adyacentes[i].size(),ady.size());
			System.out.println("Comprobar los adyacentes ");
			assertTrue(ady.containsAll(adyacentes[i]));
		}
		
	}	
		
	@Test
	public final void testComprobarPopular() {
		String filename = "miniFacebook.txt";
		EDGraph grafo =MiniFacebook.leerGrafo(filename);

		if (grafo == null)
			fail("No se pudo leer el grafo");

		leerDatos();
		
		System.out.println("Más popular: "+popular);
		assertEquals(popular,grafo.mostPopular());
		
	}

	
	@Test
	public final void testComprobarDistancias() {
		String filename = "miniFacebook.txt";
		EDGraph grafo =MiniFacebook.leerGrafo(filename);

		if (grafo == null)
			fail("No se pudo leer el grafo");

		leerDatos();
		
		System.out.println("Comprobando las distancias entre cada par de nodos ");
		for (int i=0; i<nNodos; i++) {
			InfoDist info = distancias.get(i);
			int index = grafo.getNodeIndex(info.name);
			assertNotEquals(index, -1);
			int [] res = grafo.distanceToAll(info.name);
			assertEquals(info.dist.length, res.length);
			System.out.print("   "+info.name+": ");
			for (int j=0; j<nNodos; j++) {
				System.out.print(info.dist[j]+" ");
				assertEquals(info.dist[j], res[j]);
			}
			System.out.println();
		}
		
	}


    @Test
    public final void testComprobarComunes() {
        String filename = "miniFacebook.txt";
        EDGraph grafo =MiniFacebook.leerGrafo(filename);

        if (grafo == null)
            fail("No se pudo leer el grafo");

        leerDatos();

        System.out.println("Comprobando amigos comunes entre cada par de nodos");
        for (int i=0; i<nNodos; i++) {
            Tinfo info = co.get(i);
            String name1= info.name;
            for (int j=0; j<info.lista.size(); j++) {
                Data data = info.lista.get(j);
                String name2 = data.name;
                Set<String> amigos = grafo.common(name1, name2);

                System.out.println("   ("+name1+", "+name2+", "+data.elements.size()+" amigos comunes)");
                assertEquals(data.elements.size(), amigos.size());
                assertTrue(amigos.containsAll(data.elements));
            }
        }

    }
		
	@Test
	public final void testComprobarSugerencias() {
		String filename = "miniFacebook.txt";
        EDGraph grafo =MiniFacebook.leerGrafo(filename);

        if (grafo == null)
            fail("No se pudo leer el grafo");

		leerDatos();
		
		System.out.println("Comprobando amigos sugeridos entre cada par de nodos");
		for (int i=0; i<nNodos; i++) {
			Tinfo info = su.get(i);
			String name1= info.name;
			for (int j=0; j<info.lista.size(); j++) {
				Data data = info.lista.get(j);
				String name2 = data.name;
				Set<String> amigos = grafo.suggest(name1, name2);
				System.out.println("   ("+name1+", "+name2+", "+data.elements.size()+" sugerencias)");
			
				assertEquals(data.elements.size(), amigos.size());
				assertTrue(amigos.containsAll(data.elements));
			}
		}
					
	}
		
	
}
