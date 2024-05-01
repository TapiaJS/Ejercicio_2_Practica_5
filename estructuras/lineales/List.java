package estructuras.lineales;


/**
 * TDA para List.
 *
 * @author mindahrelfen
 */
public interface List<E> extends Iterable<E> {

    /**
     * Devuelve el numero de elementos contenidos en esta lista.
     *
     * @return El tamaño de esta lista como entero mayor a cero.
     */
    public int size();

    /**
     * Pregunta si esta lista esta vacia.
     *
     * @return Devuelve true si el numero de elementos dentro de esta lista es cero.
     */
    public boolean isEmpty();

    /**
     * Devuelve el elemento que se encuentra en el indice index de esta lista.
     * Si el indice esta fuera del rango valido de indices de esta lista lanza
     * una excepcion IndexOutOfBoundsException.
     *
     * @param index Indice a revisar
     *
     * @return Una referencia al elemento en la posicion index.
     */
    public E get(int index) throws IndexOutOfBoundsException;

    /**
     * Modifica el elemento que se encuentra en el indice index de esta lista.
     * Si el indice esta fuera del rango valido de indices de esta lista lanza
     * una excepcion IndexOutOfBoundsException.
     *
     * @param index Indice a revisar
     * @param e Nuevo elemento.
     *
     * @return Una referencia al elemento anterior en la posicion index.
     */
    public E set(int index, E e) throws IndexOutOfBoundsException;

    /**
     * Agrega el elemento dado en el indice index de esta lista.
     * Si el indice esta fuera del rango valido de indices de esta lista lanza
     * una excepcion IndexOutOfBoundsException.
     *
     * @param index Indice a revisar
     * @param e Nuevo elemento.
     */
    public void add(int index, E e) throws IndexOutOfBoundsException;

    /**
     * Elimina el elemento que se encuentra en el indice index de esta lista.
     * Si el indice esta fuera del rango valido de indices de esta lista lanza
     * una excepcion IndexOutOfBoundsException.
     *
     * @param index Indice a revisar
     *
     * @return Una referencia al elemento anterior en la posicion index.
     */
    public E remove(int index) throws IndexOutOfBoundsException;
}
