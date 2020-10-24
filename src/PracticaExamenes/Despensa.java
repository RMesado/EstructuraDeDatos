package PracticaExamenes;

import java.util.*;

public class Despensa {
    public Map<String, Integer> almacen = new HashMap();

    public void reponer(Collection<String> cesta) {
        Iterator<String> iter = cesta.iterator();
        while (iter.hasNext()) {
            String cosa = iter.next();
            Integer anterior = almacen.get(cosa);
            if (anterior == null) {
                almacen.put(cosa, 1);
            } else {
                almacen.put(cosa, anterior + 1);
            }

        }
    }

    public Collection<String> buscar(Collection<String> pedido) {
        List<String> faltan = new LinkedList<>();
        Map<String, Integer> lista = new HashMap();

        for (String elemento : pedido) {
            Integer cant = lista.get(elemento);
            if (cant == null) {
                lista.put(elemento, 1);
            } else {
                lista.put(elemento, cant + 1);
            }
        }

        for (String elemento : pedido) {
            Integer a = almacen.get(elemento);
            int b = lista.get(elemento);
            if (a == null) {
                faltan.add(elemento);
            } else {
                int diferencia = b - a;

                for (int i = 0; i < diferencia; i++)
                    faltan.add(elemento);
            }
        }

        return faltan;
    }

    public boolean servir(Collection<String> pedido){
        Map<String, Integer> lista = new HashMap<>();
        boolean suficiente = true;

        for(String elemento : pedido){
            Integer cant = lista.get(elemento);
            if(cant==null)
                lista.put(elemento, 1);
            else
                lista.put(elemento, cant+1);
        }

        for(String elemento : pedido){
            Integer a = almacen.get(elemento);
            int b = lista.get(elemento);
            if(a==null || b>a){
                suficiente=false;
            }
        }

        if(suficiente==true){
            for(String elemento : pedido){
                Integer a = almacen.get(elemento);
                int b = lista.get(elemento);
                almacen.put(elemento, a-b);
            }
        }
        return suficiente;
    }
}
