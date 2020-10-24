import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

public class prueba {
    static public int intercambio(List<Integer> l) {
        ListIterator<Integer> iter = l.listIterator();
        int cont = 0;
        while (iter.hasNext()) {
            int dato = iter.next();
            cont++;
            if (iter.hasNext()) {
                iter.remove();
                iter.next();
                iter.add(dato);
                cont++;
            }
        }
        return cont++;
    }

    public static void main(String[] args){
        ArrayList<Integer> l = new ArrayList<Integer>();
        l.add(9);
        l.add(3);
        l.add(4);
        l.add(0);
        l.add(1);
        l.add(2);
        l.add(2);
        l.add(8);
        System.out.println(l.toString());
        intercambio(l);
        System.out.println(l.toString());

    }
}