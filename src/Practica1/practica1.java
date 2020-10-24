package Practica1;

import java.util.*;

public class practica1 {

    /**
     * Método que toma dos conjuntos de enteros y separa los elementos entre aquellos que sólo aparecen una vez
     * y aquellos que aparecen repetidos. El método modifica los conjuntos que toma como parámetros.
     *
     * @param unicos    A la entrada un conjunto de enteros. A la sálida los elementos que solo aparecen en uno de
     *                  los conjuntos.
     * @param repetidos A la entrada un conjunto de enteros. A la salida los elementos que aparecen en ambos conjuntos.
     */
    static public void separa(Set<String> unicos, Set<String> repetidos) {
        Iterator<String> u;
        Iterator<String> r;
        String rep = null, un = null;
        r = repetidos.iterator();

        if (unicos.size() > 0) {//----------Si el tamaño de unicos es >0, entra
            while (r.hasNext()) { //--------Para recorrer repetidos
                boolean borrado = false;//--Booleano para saber si en esa iteración se ha borrado
                rep = r.next();
                u = unicos.iterator();//--Inicializo el segundo iterador, para que no se quede "enganchado" en ninguna posicion
                while (u.hasNext()) {
                    un = u.next();
                    if (rep == un) {//---Si son iguales, borro el de unicos y borrado=true
                        u.remove();
                        borrado = true;
                    }
                }
                if (borrado == false) {//--- Si borrado==false, es que no se ha borrado, por lo tanto es único.
                    unicos.add(rep);//---Añado en unicos y borro en repetidos.
                    r.remove();
                }
            }
        } else {//----Si no, se añade todo a unicos y se borra todo de repetidos
            unicos.addAll(repetidos);
            repetidos.removeAll(unicos);
        }
    }

    /**
     * Toma un iterador a una colección de enteros positivos y devuelve como resultado un conjunto con aquellos elementos
     * de la colección que no son múltiplos de algún otro de la colección. Los ceros son descartados
     *
     * @param iter Iterador a una colección de enteros
     * @return Conjunto de de enteros.
     */
    static public Set<Integer> filtra(Iterator<Integer> iter) {
        Set<Integer> enteros = new HashSet<>();
        int entrada;
        while (iter.hasNext()) {
            entrada = iter.next();
            enteros.add(entrada);
        }

        Iterator<Integer> iter2;
        int num1, num2;
        iter = enteros.iterator();


        while (iter.hasNext()) {
            boolean esMúltiplo = false;
            num1 = iter.next();
            if (num1 != 0) {
                if (num1 != 1) {
                    iter2 = enteros.iterator();
                    while (iter2.hasNext()) {
                        num2 = iter2.next();
                        if (num1 != num2 && num2 != 0 && num1 % num2 == 0) {
                            esMúltiplo = true;
                        }
                    }
                    if (esMúltiplo == true) {
                        iter.remove();
                    }
                }
            } else {
                iter.remove();
            }
        }
        return enteros;
    }

    /**
     * Toma una colección de conjuntos de <i>String</i> y devuelve como resultado un conjunto con aquellos <i>String </i>
     * Que aparecen en al menos dos conjuntos de la colección.
     *
     * @param col Coleccion de conjuntos de <i>String</i>
     * @return Conjunto de <i>String</i> repetidos.
     */
    static public Set<String> repetidos(Collection<Set<String>> col) {
        Set<String> repes = new HashSet<>();
        Set<String> conjunto;
        String recorrido;
        Iterator<Set<String>> coleccion = col.iterator();
        while (coleccion.hasNext()) {//----Recorro la coleccion de conjuntos
            conjunto = coleccion.next();
            Iterator<String> recorre = conjunto.iterator();
            while (recorre.hasNext()) {//---Recorro cada elemento del conjunto
                int contador = 0;// Este contador me comprobará si está repetido
                recorrido = recorre.next();
                Iterator<Set<String>> coleccion2 = col.iterator();
                Set<String> conjunto2;
                while (coleccion2.hasNext()) {// Vuelvo a recorrer los conjuntos para ver si contienen el elemeento
                    conjunto2 = coleccion2.next();
                    if (conjunto2.contains(recorrido)) {
                        contador++;
                    }

                }
                if (contador > 1) {
                    repes.add(recorrido);
                }
            }

        }

        return repes;
    }
}
