package Practica5;

import jdk.nashorn.api.tree.BinaryTree;
import org.junit.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.Iterator;


public class EDTreeSet<E extends Comparable<E>> implements Set<E> {

    protected class BinaryNode {
        protected E data;
        protected BinaryNode left;
        protected BinaryNode right;

        BinaryNode(E data) {
            this.data = data;
        }

        BinaryNode(E data, BinaryNode lnode, BinaryNode rnode) {
            this.data = data;
            this.left = lnode;
            this.right = rnode;
        }
    }

    private BinaryNode root;
    private Comparator<? super E> comparator;
    private int size; //number of elements
    protected boolean insertReturn; //Return value for the public insert method
    protected E removeReturn; //Return value for the public remove method


    public EDTreeSet() {
        root = null;
        comparator = null;
        size = 0;
    }

    public EDTreeSet(Comparator<? super E> comp) {
        root = null;
        comparator = comp;
        size = 0;
    }

    public EDTreeSet(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    public EDTreeSet(SortedSet<E> s) {
        this(s.comparator());
        addAll(s);
    }

    private int compare(E left, E right) {
        if (comparator != null) { //A comparator is defined
            return comparator.compare(left, right);
        } else {
            return (((Comparable<E>) left).compareTo(right));
        }
    }

    private BinaryNode first(BinaryNode r) {
        if (r == null)
            return null;
        else
            while (r.left != null)
                r = r.left;
        return r;
    }

    public E first() {
        BinaryNode r = root;

        return first(r).data;
    }

    private BinaryNode last(BinaryNode r) {
        if (r == null)
            return null;
        else
            while (r.right != null)
                r = r.right;
        return r;
    }

    public E last() {
        BinaryNode r = root;
        return last(r).data;
    }


    private BinaryNode add(BinaryNode r, E item) {
        if (r == null) {
            size++;
            insertReturn = true;
            return new BinaryNode(item);
        }
        if (compare(item, r.data) < 0)
            r.left = add(r.left, item);
        else if (compare(item, r.data) > 0)
            r.right = add(r.right, item);
        else {
            insertReturn = false;
            return r;
        }

        return r;
    }

    @Override
    public boolean add(E item) {
        root = add(root, item);
        return insertReturn;
    }


    @Override
    public boolean addAll(Collection<? extends E> arg0) {
        if (arg0 == null) throw new NullPointerException();
        boolean changed = false;
        for (E e : arg0) {
            boolean res = add(e);
            if (!changed && res) changed = true;
        }
        return (changed);
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private boolean contains(BinaryNode r, E item) {
        if (r == null)
            return false;
        else if (compare(item, r.data) == 0)
            return true;
        else if (compare(item, r.data) < 0)
            return contains(r.left, item);
        else if (compare(item, r.data) > 0)
            return contains(r.right, item);
        return false;
    }

    @Override
    public boolean contains(Object arg0) {
        E item = (E) arg0;
        return contains(root, item);
    }


    @Override
    public boolean containsAll(Collection<?> arg0) {
        if (arg0 == null) throw new NullPointerException();
        boolean cont = true;
        Iterator<?> it = arg0.iterator();
        while (it.hasNext() && cont) {
            cont = contains(it.next());
        }
        return cont;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private BinaryNode remove(BinaryNode r, E item) {
        if (r == null) {
            removeReturn=null;
        } else if (compare(item, r.data) < 0)
            r.left = remove(r.left, item);
        else if (compare(item, r.data) > 0)
            r.right = remove(r.right, item);
        else {
            removeReturn = r.data;
            if (r.left == null && r.right == null) {
                r = null;
                size--;
            } else {
                if (r.left != null && r.right != null) {
                    E min = r.data;
                    BinaryNode aux = r.right;
                    while (aux.left != null) {
                        aux = aux.left;
                    }
                    r.data = aux.data;
                    r.right = remove(r.right, aux.data);
                } else if (r.left != null) {
                    r = r.left;
                    size--;
                } else {
                    r = r.right;
                    size--;
                }
            }
        }
        return r;
    }

    @Override
    public boolean remove(Object arg0) {
        E item = (E) arg0;
        root = remove(root, item);
        return removeReturn != null;
    }

    private BinaryNode ceiling(BinaryNode r, E e) {
        if (r == null)
            return null;

        if (compare(e, r.data) == 0)
            return r;
        else if (compare(e, r.data) > 0)
            return ceiling(r.right, e);

        BinaryNode aux = ceiling(r.left, e);

        if (aux != null)
            return aux;
        else return r;

    }


    public E ceiling(E e) {
        BinaryNode r = ceiling(root, e);
        if (r == null)
            return null;
        return r.data;
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        if (arg0 == null) throw new NullPointerException();
        int originalSize = size();
        int newSize = originalSize;
        Object[] v = this.toArray();
        for (int i = 0; i < v.length; i++) {
            if (arg0.contains(v[i])) {
                remove(v[i]);
                newSize--;
            }
        }
        return (originalSize != newSize);
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        if (arg0 == null) throw new NullPointerException();
        int originalS = size();
        int newS = originalS;
        Object[] v = this.toArray();
        for (int i = 0; i < v.length; i++) {
            if (!arg0.contains(v[i])) {
                remove(v[i]);
                newS--;
            }
        }
        return (originalS != newS);

    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Object[] toArray() {

        Object[] v = new Object[size()];
        toArray(0, root, v);

        return v;
    }

    private int toArray(int pos, BinaryNode r, Object[] v) {
        if (r != null) {
            if (r.left != null) pos = toArray(pos, r.left, v);
            //System.out.println("toArray pos-> "+pos +" data--> "+r.data);
            v[pos] = r.data;
            pos++;
            if (r.right != null) pos = toArray(pos, r.right, v);
        }
        return pos;
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        if (arg0 == null) throw new NullPointerException();
        int n = size();
        if (n > arg0.length)
            arg0 = (T[]) new Object[n];
        toArray(0, root, arg0);

        return arg0;
    }


    /**
     * Returns an String with the data in the nodes
     * in inorder
     */
    public String toString() {
        return toString(root) + " - size:" + size;
    }

    private String toString(BinaryNode r) {
        String s = "";
        if (r != null) {
            String sl = toString(r.left);
            String sd = toString(r.right);
            if (sl.length() > 0)
                s = sl + ", ";
            s = s + r.data;
            if (sd.length() > 0)
                s = s + ", " + sd;
        }
        return s;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

}
