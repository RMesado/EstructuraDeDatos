package Practica2.Polinomio;

import java.util.*;

import static Practica2.Polinomio.Cero.esCero;

public class Polinomio {

    // La lista de monomios
    private List<Monomio> datos = new LinkedList<Monomio>();

    /**
     * Constructor por defecto. La lista de monomios está vacía
     */
    public Polinomio() {
    }

    ;

    /**
     * Constructor a partir de un vector. Toma los coeficientes de los monomios
     * de los valores almacenados en el vector, y los exponentes son las
     * posiciones dentro del vector. Si <code>v[i]</code> contiene
     * <code>a</code> el monomio contruido será aX^i. <br>
     * <p>
     * Por ejemplo: <br>
     * <p>
     * v = [-1, 0, 2] -> 2X^2 -1X^0
     *
     * @param v
     */
    public Polinomio(double v[]) {
        for (int i = 0; i < v.length; i++) {
            if (!esCero(v[i])) {
                Monomio monomio = new Monomio(v[i], i);
                this.datos.add(monomio);
            }
        }

    }

    /**
     * Constructor copia
     *
     * @param otro
     * @throws <code>NullPointerException</code> si el parámetro es nulo
     */
    public Polinomio(Polinomio otro) {
        if (otro == null)
            throw new NullPointerException();

        for (Monomio item : otro.datos)
            datos.add(new Monomio(item));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();


        boolean primero = true;

        for (int i = 0; i < datos.size(); i++) {
            Monomio item = datos.get(i);

            if (item.coeficiente < 0) {
                str.append('-');
                if (!primero)
                    str.append(' ');
            } else if (!primero)
                str.append("+ ");

            str.append(Math.abs(item.coeficiente));
            if (item.exponente > 0)
                str.append('X');
            if (item.exponente > 1)
                str.append("^" + item.exponente);

            if (i < datos.size() - 1)
                str.append(' ');

            primero = false;
        }
        if (primero)
            str.append("0.0");

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

        Polinomio other = (Polinomio) obj;

        if (this.datos.size() != other.datos.size())
            return false;

        Iterator<Monomio> iter1 = this.datos.iterator();
        Iterator<Monomio> iter2 = other.datos.iterator();

        while (iter1.hasNext())
            if (!(iter1.next().equals(iter2.next())))
                return false;

        return true;
    }

    /**
     * Devuelve la lista de monomios
     */
    public List<Monomio> monomios() {
        return datos;
    }

    /**
     * Suma un polinomio sobre <code>this</code>, es decir, modificando el
     * polinomio local. Debe permitir la auto autosuma, es decir,
     * <code>polinomio.sumar(polinomio)</code> debe dar un resultado correcto.
     *
     * @param otro
     * @return <code>this<\code>
     * @throws <code>NullPointeExcepction</code> en caso de que el parámetro sea <code>null</code>.
     */
    public void sumar(Polinomio otro) {
        if (otro == null) {
            throw new NullPointerException();
        }

        Iterator<Monomio> iter1 = otro.datos.iterator();
        while (iter1.hasNext()) {
            Monomio dato = iter1.next();
            Iterator<Monomio> iter2 = this.datos.iterator();
            boolean existe = false;
            while (iter2.hasNext()) {
                Monomio dato2 = iter2.next();
                if (dato.exponente == dato2.exponente) {
                    existe = true;
                    dato2.coeficiente += dato.coeficiente;
                }
                if (esCero(dato2.coeficiente)) {
                    iter2.remove();
                }
            }
            if (existe == false) {
                if (dato.exponente <= this.datos.size()) {
                    this.datos.add(dato.exponente, dato);
                } else {
                    this.datos.add(dato);
                }
            }
        }
    }


    /**
     * Multiplica el polinomio <code>this</code> por un monomio.
     *
     * @param mono
     */
    public void multiplicarMonomio(Monomio mono) {
        if (mono != null) {
            Iterator<Monomio> iter = this.datos.iterator();
            while (iter.hasNext()) {
                Monomio dato = iter.next();
                dato.exponente += mono.exponente;
                dato.coeficiente *= mono.coeficiente;
                if (esCero(dato.coeficiente)) {
                    iter.remove();

                }
            }
        }
    }
}
