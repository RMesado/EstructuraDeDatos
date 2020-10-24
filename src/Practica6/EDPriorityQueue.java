package Practica6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

//IMPLEMENTS A PRIORITY QUEUE USING A MINHEAP

public class EDPriorityQueue<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    E[] data;
    int size;

    private Comparator<E> comparator;


    public EDPriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    public EDPriorityQueue(Comparator<E> comp) {
        this(DEFAULT_INITIAL_CAPACITY, comp);
    }

    public EDPriorityQueue(int capacity, Comparator<E> comp) {
        if (capacity < 1)
            throw new IllegalArgumentException();
        this.data = (E[]) new Object[capacity];
        this.comparator = comp;
    }

    public EDPriorityQueue(Collection<E> c) {
        this(c.size() + 1, null);
        size = c.size();
        int i = 0;
        for (E elem : c) {
            data[i] = elem;
            i++;
        }
        heapify();
    }

    public EDPriorityQueue(Collection<E> c, Comparator<E> comp) {
        this(c.size(), comp);
        size = c.size();
        int i = 0;
        for (E elem : c) {
            data[i] = elem;
            i++;
        }
        heapify();
    }

    //private methods

    /**
     * compares two objects and returns a negative number if object
     * left is less than object right, zero if are equal, and a positive number if
     * object left is greater than object right
     */
    int compare(E left, E right) {
        if (comparator != null) { //A comparator is defined
            return comparator.compare(left, right);
        } else {
            return (((Comparable<E>) left).compareTo(right));
        }
    }

    /**
     * Exchanges the object references in the data field at indexes i and j
     *
     * @param i index of firt object in data
     * @param j index of second objet in data
     */
    private void swap(int i, int j) {
        E elem_i = this.data[i];
        this.data[i] = data[j];
        this.data[j] = elem_i;
    }

    /**
     * Auxiliares para hijos y padres
     */

    private int parent(int child) {
        return (child - 1) / 2;
    }

    private int rightChild(int parent) {
        return parent * 2 + 2;
    }

    private int leftChild(int parent) {
        return parent * 2 + 1;
    }

    private void sink(int parent) {
        int min, izda, der;
        boolean lugar = false;
        while (parent < size / 2 && !lugar) {
            min = parent;
            izda = leftChild(parent);
            if (compare(data[izda], data[parent]) < 0)
                min = izda;
            der = rightChild(parent);
            if (der < size && compare(data[der], data[min]) < 0)
                min = der;
            if (parent != min) {
                swap(parent, min);
                parent = min;
            } else {
                lugar = true;
            }

        }

    }

    private void floating(int child) {
        while (child > 0 && compare(data[child], data[parent(child)]) < 0) {
            swap(child, parent(child));
            child = parent(child);
        }
    }

    /**
     * Duplicates the size of the array storing the elements
     */
    private void grow() {
        int initial_capacity = data.length;
        E[] old = this.data;
        data = (E[]) new Object[initial_capacity * 2];
        for (int i = 0; i < size; i++) {
            this.data[i] = old[i];
        }
    }

    private void heapify() {
        for(int i = data.length-1; i>=0; i--)
            sink(i);

    }

    private int indexOf(E item, int current) {
        int pos = current;
        while (pos < size && compare(data[pos], item) != 0)
            pos++;
        if (pos == size)
            return -1;
        return pos;

    }

    public boolean contains(E item) {
        int pos = indexOf(item, 0);
        return (pos != -1);
    }

    public boolean isEmpty() {

        return (this.size == 0);
    }

    public int size() {
        return size;
    }

    /**
     * Inserts an item into the priority_queue. Returns true if successful;
     * returns false if the item is not inserted
     *
     * @param item Item to be inserted in the priority queue
     * @return True if the element have been added or false if not (in practice always true)
     */
    public boolean add(E item) {
        if (item != null) {
            data[size] = item;
            size++;
            floating(size - 1);
            if (size == data.length) {
                grow();
            }
            return true;
        }
        return false;

    }


    /**
     * Removes the front of the queue if  the priority queue is not empty.
     * If the queue is empty, returns NoSuchElementException
     *
     * @return E smallest element in the queue
     */
    public E remove() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();
        E result = data[0]; //the root of the heap

        if (size > 1) {
            //remove the last item in the array and put it in the root position
            data[0] = data[size - 1];  //puts last item in the root position
            sink(0);
        }
        size = size - 1;  //deletes last element
        return result;

    }

    /**
     * Returns the smallest entry, WITHOUT REMOVING IT.
     * If the queue is empty, returns NoSuchElementException
     *
     * @return E smallest entry
     */
    public E element() throws NoSuchElementException {
        if (!isEmpty()) {
            return data[0];
        }
        throw new NoSuchElementException();
    }

    /**
     * Removes the first occurrence of the element E in the queue.
     *
     * @param item The element to be removed
     * @return True is the element was found and removed, false if not.
     */
    public boolean remove(E item) {
        if (item != null) {
            int pos = indexOf(item, 0);
            if(pos<0 || pos>=size)
                return false;
            swap(0, pos);
            remove();
            heapify();
            return true;
        }
        return false;
    }

    String toStringHeap() {
        StringBuilder s = new StringBuilder();
        int enNivel = 1;
        int finNivel = 1;
        for (int i = 0; i < size; i++) {
            s.append(data[i]);

            if (i != size - 1)
                if (i == finNivel - 1) {
                    s.append("] [");
                    enNivel *= 2;
                    finNivel += enNivel;
                } else
                    s.append(", ");
        }
        s.append("]");
        return s.toString();
    }

    public String toString() {
        return "EDPriorityQueue: [" + this.toStringHeap() + " - size: " + size;
    }
}
