package estructuras.lineales;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación de ArrayList.
 *
 * @author mindahrelfen
 */
public class ArrayList<E> implements List<E> {

    /**
     * Arreglo donde se guardan los valores que esta lista contiene.
     */
    protected E[] array;

    /**
     * Capacidad inicial para la lista.
     */
    public static final int CAPACITY = 16;

    /**
     * Cantidad de elementos dentro de esta Lista.
     */
    protected int size;

    /**
     * Construye una lista vacía.
     */
    public ArrayList() {
        this(CAPACITY);
    }

    /**
     * Construye una lista vacía con capacidad inicial.
     *
     * @param capacity Capacidad inicial de la lista.
     */
    public ArrayList(int capacity) {
        size = 0;
        array = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Revisa si el indice dado esta fuera del rango valido de indices de esta lista.
     * Si el indice es invalido lanza una excepcion IndexOutOfBoundsException.
     *
     * @param index Indice a revisar
     */
    protected void checkIndex(int index, int max) throws IndexOutOfBoundsException {
        if (index < 0 || index >= max) {
            throw new IndexOutOfBoundsException("Indice ilegal: " + index);
        }
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        checkIndex(index, size);

        return array[index];
    }

    @Override
    public E set(int index, E e) throws IndexOutOfBoundsException {
        E previous;
        checkIndex(index, size);

        previous = array[index];
        array[index] = e;

        return previous;
    }

    @Override
    public void add(int index, E e) throws IndexOutOfBoundsException {
        E[] aux;

        checkIndex(index, size + 1);

        if (size == array.length) {
            aux = (E[]) new Object[size * 2];

            for (int j = 0; j < size; j++) {
                aux[j] = array[j];
            }

            array = aux;
        }

        for (int j = size - 1; j >= index; j--) {
            array[j + 1] = array[j];
        }

        array[index] = e;
        size++;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        E e;

        checkIndex(index, size);

        e = array[index];

        for (int j = index; j < size - 1; j++) {
            array[j] = array[j + 1];
        }

        array[size - 1] = null;
        size--;

        return e;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * Clase que implementa el Iterador de la clase ColaArreglo.
     */
    protected class ArrayListIterator implements Iterator<E> {

        /**
         * Posición del valor next a devolver.
         */
        protected int next;

        /**
         * Bandera que dice si se puede borrar un elemento o no.
         */
        protected boolean canRemove;

        /**
         * Inicializa el iterador al inicio de la lista.
         */
        public ArrayListIterator() {
            next = 0;
            canRemove = false;
        }

        @Override
        public boolean hasNext() {
            return next < size;
        }

        @Override
        public E next() {
            E e;

            if (!hasNext()) throw new NoSuchElementException();

            e = array[next++];
            canRemove = true;

            return e;
        }

        @Override
        public void remove() {
            int index;

            if (!canRemove) throw new IllegalStateException();

            next--;
            index = next;
            while (index < size - 1) {
                array[index] = array[index + 1];
                index++;
            }

            size--;

            canRemove = false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb;

        if (isEmpty()) return "[]";

        sb = new StringBuilder();

        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) sb.append(" ");
        }
        sb.append("]");

        return sb.toString();
    }
}
