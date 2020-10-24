package Practica7;

import java.io.*;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;

//import ejer1.EDListGraph.Node;

public class MiniFacebook {

	 
	public static EDGraph<String, Object> leerGrafo (String nomfich) {
	    EDListGraph grafo = new EDListGraph();
        String usuario1, usuario2;
	    try {
            Scanner entrada = new Scanner(new File(nomfich));
            while(entrada.hasNextLine()){
                usuario1 = entrada.next();
                usuario2 = entrada.next();
                if(!grafo.getNodes().contains(usuario1))
                	grafo.insertNode(usuario1);
				if(!grafo.getNodes().contains(usuario2))
                	grafo.insertNode(usuario2);
                grafo.insertEdge(new EDEdge(grafo.getNodeIndex(usuario1), grafo.getNodeIndex(usuario2)));
            }
            entrada.close();
        }catch(Exception e){
	        return null;
        }
	    return grafo;
	}
	
		
	public static void main(String[] args) throws IOException {

		String filename = "miniFacebook.txt";

		EDGraph<String, Object> grafo = leerGrafo(filename);

		if (grafo == null)
			System.out.println("No se pudo leer el fichero " + filename);
							
		grafo.printGraphStructure();
	}
}
