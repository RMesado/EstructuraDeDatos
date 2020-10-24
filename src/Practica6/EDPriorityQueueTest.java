package Practica6;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class EDPriorityQueueTest {
    private <T> boolean checkHeap(EDPriorityQueue<T> heap, boolean isMin) {
        for (int i = 0; i < heap.size / 2; i++) {
            int hi = i * 2 + 1;
            int hd = i * 2 + 2;

            if (isMin && hi < heap.size && heap.compare(heap.data[i], heap.data[hi]) > 0)
                return false;

            if (isMin && hd < heap.size && heap.compare(heap.data[i], heap.data[hd]) > 0)
                return false;

            if (!isMin && hi < heap.size && heap.compare(heap.data[i], heap.data[hi]) < 0)
                return false;

            if (!isMin && hd < heap.size && heap.compare(heap.data[i], heap.data[hd]) < 0)
                return false;
        }

        return true;
    }

    private <T> boolean checkHeapContents(EDPriorityQueue<T> heap, Collection<T> col) {
        if (heap.size() != col.size())
            return false;

        List<T> l = new LinkedList<>();
        l.addAll(col);

        for(int i = 0; i < col.size(); i++) {
            if (!l.contains(heap.data[i]))
                return false;
            l.remove(heap.data[i]);
        }

        return true;
    }

    static <T> List<List<T>> permutaciones(List<T> list, int limite) {
        List<List<T>> resultado = new ArrayList<>();

        int[] factoriales = new int[list.size() + 1];
        factoriales[0] = 1;
        for (int i = 1; i <= list.size(); i++) {
            factoriales[i] = factoriales[i - 1] * i;
        }

        if (limite < 0 || limite > factoriales[list.size()])
            limite = factoriales[list.size()];

        for (int i = 0; i < limite; i++) {
            List<T> caso = new ArrayList<>();
            List<T> aux = new LinkedList<>(list);

            int positionCode = i;
            for (int position = aux.size(); position > 0; position--) {
                int selected = positionCode / factoriales[position - 1];
                caso.add(aux.get(selected));
                positionCode = positionCode % factoriales[position - 1];
                aux.remove(selected);
            }

            if (i < limite)
                resultado.add(caso);
            else break;

        }

        return resultado;
    }


    static private boolean[] crearMascara(int talla) {
        return new boolean[talla];
    }

    static private boolean incrementaMascara(boolean mascara[]) {
        boolean propagar = false;
        int pos = 0;
        do {
            if (mascara[pos] == true) {
                mascara[pos] = false;
                propagar = true;
            } else {
                mascara[pos] = true;
                propagar = false;
            }
            pos++;
        } while (propagar && (pos < mascara.length));

        return (!propagar || pos != mascara.length);
    }

    static private <T> List<List<T>> todasSublistas(List<T> semilla) {
        List<List<T>> resultado = new LinkedList<>();

        boolean mascara[] = crearMascara(semilla.size());

        do {
            List<T> aux = new LinkedList<>();
            for (int i = 0; i < mascara.length; i++) {
                if (mascara[i])
                    aux.add(semilla.get(i));
            }
            resultado.add(aux);

        } while (incrementaMascara(mascara));

        return resultado;

    }

    static private <T> List<List<T>> permutacionesCompletas(List<T> semilla) {
        List<List<T>> resultado = new LinkedList<>();

        List<List<T>> sublistas = todasSublistas(semilla);
        for (List<T> sub : sublistas)
            resultado.addAll(permutaciones(sub, -1));


        return resultado;
    }

    static private <T> List<T> aplanar(List<List<T>> listas) {
        List<T> resultado = new LinkedList<>();

        for (List<T> l : listas)
            resultado.addAll(l);

        return resultado;
    }

    static private <T> List<List<T>> convertirMatriz(T[][] vec) {
        List<List<T>> resultado = new LinkedList<>();
        for (T[] elem : vec)
            resultado.add(Arrays.asList(elem));

        return resultado;
    }

    static private Integer minimo(List<Integer> l) {
        if (l.isEmpty())
            return null;
        int min = l.get(0);
        for (int e: l)
            if (min > e)
                min = e;

        return min;
    }

    static private Integer[][] vSemillas = {{3, 5, 10, 12}, {1, 4, 7}, {11, 15}, {1, 2,}};

    private static <T> List<List<T>> generarCasos(T[][] semillas) {
        List<List<T>> resultado = new ArrayList<>();
        List<List<T>> aux = convertirMatriz(semillas);

        List<List<List<T>>> permutaciones = permutacionesCompletas(aux);

        for (List<List<T>> perm : permutaciones)
            resultado.add(aplanar(perm));

        return resultado;
    }

    @Test
    public void constructorTest() {
        List<List<Integer>> casos = generarCasos(vSemillas);

        int cuenta = 1;
        for (List<Integer> caso : casos) {
            System.out.println("\nPRUEBA " + cuenta);
            System.out.println("EDPriorityQueue(list: " + caso + ")");
            EDPriorityQueue<Integer> heap = new EDPriorityQueue<Integer>(caso);
            System.out.println("RESULTADO OBTENIDO");
            System.out.println(heap);

            if (!checkHeapContents(heap,caso)) {
                System.out.println("La cola no contiene los mismos elementos que la colección");
                Assert.fail();;
            }
            if (!checkHeap(heap, true)) {
                System.out.println("La cola no es un montículo a mínimos");
                Assert.fail();
            }


            cuenta++;
        }
    }

    @Test
    public void addTest() {
        List<List<Integer>> casos = generarCasos(vSemillas);

        int cuenta = 1;
        for (List<Integer> caso : casos) {
            EDPriorityQueue<Integer> heap = new EDPriorityQueue<Integer>();

            List<Integer> l = new LinkedList<>();
            for (Integer i : caso) {
                int s = heap.size();
                System.out.println("\nPRUEBA " + cuenta);
                System.out.println(heap);
                System.out.println("add(" + i + ")");
                boolean retorno = heap.add(i);
                System.out.println("RESULTADO OBTENIDO");
                System.out.println(retorno);
                System.out.println(heap);
                l.add(i);

                if (!checkHeapContents(heap,l)) {
                    System.out.println("El último elemento no se ha añadido correctametno");
                    Assert.fail();;
                }

                if (!checkHeap(heap, true)) {
                    System.out.println("La cola no es un montículo a mínimos");
                    Assert.fail();
                }
                cuenta++;


            }
        }
    }

    @Test
    public void removeTest() {
        List<List<Integer>> casos = generarCasos(vSemillas);

        int cuenta = 1;
        for (List<Integer> caso : casos) {
            EDPriorityQueue<Integer> heap = new EDPriorityQueue<Integer>(caso);

            while (!caso.isEmpty()) {
                int s = heap.size();
                System.out.println("\nPRUEBA " + cuenta);
                System.out.println(heap);
                System.out.println("remove()");
                int esperado = minimo(caso);
                System.out.println("RESULTADO ESPERADO");
                System.out.println(esperado);
                int retorno = heap.remove();
                System.out.println("RESULTADO OBTENIDO");
                System.out.println(retorno);
                System.out.println(heap);
                if (esperado != retorno)
                    Assert.fail();

                caso.remove((Integer)esperado);

                if (!checkHeapContents(heap,caso)) {
                    System.out.println("El último elemento no se ha añadido correctametno");
                    Assert.fail();;
                }

                if (!checkHeap(heap, true)) {
                    System.out.println("La cola no es un montículo a mínimos");
                    Assert.fail();
                }
                cuenta++;
            }


        }
    }

    @Test
    public void removeItemTest() {
        List<List<Integer>> casos = generarCasos(vSemillas);

        int cuenta = 1;
        for (List<Integer> caso : casos) {
            EDPriorityQueue<Integer> heap = new EDPriorityQueue<Integer>(caso);

            while (!caso.isEmpty()) {
                int i = caso.get(0);
                caso.remove(0);

                System.out.println("\nPRUEBA " + cuenta);
                System.out.println(heap);
                System.out.println("remove(" + i + ")");
                boolean retorno = heap.remove(i);
                System.out.println("RESULTADO OBTENIDO");
                System.out.println(retorno);
                System.out.println(heap);

                if (!retorno) {
                    System.out.println("Resultado incorrecto");
                    Assert.fail();
                }

                if (!checkHeapContents(heap,caso)) {
                    System.out.println("El último elemento no se ha añadido correctametno");
                    Assert.fail();;
                }

                if (!checkHeap(heap, true)) {
                    System.out.println("La cola no es un montículo a mínimos");
                    Assert.fail();
                }
                cuenta++;

                i = -i;
                System.out.println("\nPRUEBA " + cuenta);
                System.out.println(heap);
                System.out.println("remove(" + i + ")");
                retorno = heap.remove(i);
                System.out.println("RESULTADO OBTENIDO");
                System.out.println(retorno);
                System.out.println(heap);

                if (retorno) {
                    System.out.println("Resultado incorrecto");
                    Assert.fail();
                }
                if (!checkHeapContents(heap,caso)) {
                    System.out.println("El último elemento no se ha añadido correctametno");
                    Assert.fail();;
                }

                if (!checkHeap(heap, true)) {
                    System.out.println("La cola no es un montículo a mínimos");
                    Assert.fail();
                }
                cuenta++;
            }
        }
    }

    @Test
    public void elementTest() {
        List<List<Integer>> casos = generarCasos(vSemillas);

        int cuenta = 1;
        for (List<Integer> caso : casos) {
            EDPriorityQueue<Integer> heap = new EDPriorityQueue<Integer>();

            List<Integer> l = new LinkedList<>();
            for (Integer i : caso) {
                System.out.println("\nPRUEBA " + cuenta);
                System.out.println(heap);
                System.out.println("add(" + i + ")\nelement()");
                heap.add(i);
                int retorno = heap.element();
                l.add(i);
                int esperado = minimo(l);
                System.out.println("RESULTADO ESPERADO");
                System.out.println(retorno);
                System.out.println("RESULTADO OBTENIDO");
                System.out.println(retorno);
                System.out.println(heap);

                if (retorno != esperado) {
                    System.out.println("Resultado incorrecto");
                    Assert.fail();
                }

                if (!checkHeapContents(heap,l)) {
                    System.out.println("El último elemento no se ha añadido correctametno");
                    Assert.fail();;
                }

                if (!checkHeap(heap, true)) {
                    System.out.println("La cola no es un montículo a mínimos");
                    Assert.fail();
                }
                cuenta++;


            }
        }
    }

    @Test
    public void containsTest() {
        List<List<Integer>> casos = generarCasos(vSemillas);

        int cuenta = 1;
        for (List<Integer> caso : casos) {
            EDPriorityQueue<Integer> heap = new EDPriorityQueue<Integer>(caso);

            for (int i: caso) {

                System.out.println("\nPRUEBA " + cuenta);
                System.out.println(heap);
                System.out.println("contains(" + i + ")");
                boolean retorno = heap.contains(i);
                System.out.println("RESULTADO OBTENIDO");
                System.out.println(retorno);
                System.out.println(heap);

                if (!retorno) {
                    System.out.println("Resultado incorrecto");
                    Assert.fail();
                }

                if (!checkHeapContents(heap,caso)) {
                    System.out.println("El último elemento no se ha añadido correctametno");
                    Assert.fail();;
                }

                if (!checkHeap(heap, true)) {
                    System.out.println("La cola no es un montículo a mínimos");
                    Assert.fail();
                }
                cuenta++;

                i = -i;
                System.out.println("\nPRUEBA " + cuenta);
                System.out.println(heap);
                System.out.println("contains(" + i + ")");
                retorno = heap.contains(i);
                System.out.println("RESULTADO OBTENIDO");
                System.out.println(retorno);
                System.out.println(heap);

                if (retorno) {
                    System.out.println("Resultado incorrecto");
                    Assert.fail();
                    Assert.fail();
                }
                if (!checkHeapContents(heap,caso)) {
                    System.out.println("El último elemento no se ha añadido correctametno");
                    Assert.fail();;
                }

                if (!checkHeap(heap, true)) {
                    System.out.println("La cola no es un montículo a mínimos");
                    Assert.fail();
                }
                cuenta++;
            }
        }
    }

    @Test
    public void excpetionsTest() {
        EDPriorityQueue<Integer> heap = new EDPriorityQueue<Integer>();
        System.out.println(heap);

        try {
            System.out.println("element()");
            heap.element();
            System.out.println("...exception not thrown");
            Assert.fail("NoSuchElementException expected");
        } catch(NoSuchElementException e) {
            System.out.println("...exception: "+ e.getLocalizedMessage());
        }

        try {
            System.out.println("remove()");
            heap.remove();
            System.out.println("...exception not thrown");
            Assert.fail("NoSuchElementException expected");
        } catch(NoSuchElementException e) {
            System.out.println("...exception: " + e.getLocalizedMessage());
        }
    }
}
