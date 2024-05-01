package estructuras.arboles;

import java.util.Iterator;
import java.util.NoSuchElementException;

import estructuras.lineales.ArrayList;

public abstract class ArbolBinario<E> implements Iterable<E> {

    /**
     * Es la raiz del arbol.
     */
    Node<E> root;

    /**
     * Representa el tamaño del arbol.
     */
    int size;

    public ArbolBinario() {
        root = null;
        size = 0;
    }

    /**
     * Devuelve la altura de este arbol.
     *
     * @return int - Altura actual del arbol.
     */
    public int height() {
        if (root == null) {
            return 0;
        }

        return root.height();
    }

    /**
     * Devuelve el numero de elementos dentro de este arbol.
     *
     * @return int - Tamaño actual del arbol.
     */
    public int size() {
        return size;
    }

    /**
     * Devuelve si el arbol esta vacio.
     *
     * @return boolean - true si el arbol esta vacio.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Devuelve el iterador que contiene el recorrido pre-orden.
     *
     * @return Iterator - Devuelve los elementos en pre-orden.
     */
    public Iterator<E> getIteratorPreOrder() {
        return new IteratorPreOrder();
    }

    protected class IteratorPreOrder implements Iterator<E> {
        private ArrayList list;

        public IteratorPreOrder() {
            list = new ArrayList<>();
            if (root != null) {
                list.add(0, root);
            }
        }
    
        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }
    
        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
    
            Object obj;
            Node<E> node;
            E elem = null;
            boolean found = false;
    
            while (!found && !list.isEmpty()) {
                obj = list.remove(0);
                if (obj instanceof Node) {
                    node = (Node<E>) obj;
    
                    // En PreOrder, procesamos la raíz primero, luego el izquierdo, y después el derecho
                    elem = node.elem; // Procesamos el nodo actual
                    found = true;
    
                    // Agregar primero el derecho y luego el izquierdo al principio de la lista para continuar el recorrido PreOrder
                    if (node.right != null) {
                        list.add(0, node.right);
                    }
                    if (node.left != null) {
                        list.add(0, node.left);
                    }
                }
            }
    
            if (!found) {
                throw new NoSuchElementException();
            }
    
            return elem;
        }
    
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Devuelve el iterador que contiene el recorrido post-orden.
     *
     * @return Iterator - Devuelve los elementos en post-orden.
     */
    public Iterator<E> getIteratorPostOrder() {
        return new IteratorPostOrder();
    }

    public class IteratorPostOrder implements Iterator<E> {
        private ArrayList list;
        private ArrayList visited;
    
        public IteratorPostOrder() {
            list = new ArrayList<>();
            visited = new ArrayList<>();
            if (root != null) {
                list.add(0, root);
                visited.add(0, false);
            }
        }
    
        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }
    
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
    
            while (!list.isEmpty()) {
                Node<E> node = (Node)list.get(0);
                boolean hasVisited = (boolean)visited.get(0);
    
                if (hasVisited) {
                    list.remove(0);
                    visited.remove(0);
                    return node.elem;
                } else {
                    visited.set(0, true); // Marcar este nodo como visitado
    
                    // Empujar primero el hijo derecho y luego el izquierdo a la lista
                    if (node.right != null) {
                        list.add(0, node.right);
                        visited.add(0, false);
                    }
                    if (node.left != null) {
                        list.add(0, node.left);
                        visited.add(0, false);
                    }
                }
            }
            
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    /**
     * Devuelve el iterador que contiene el recorrido in-orden.
     *
     * @return Iterator - Devuelve los elementos en in-orden.
     */
    public Iterator<E> getIteratorInOrder() {
        return new IteratorInOrder();
    }

    @Override
    public Iterator<E> iterator() {
        return getIteratorInOrder();
    }

    protected class IteratorInOrder implements Iterator<E> {

        ArrayList list;

        public IteratorInOrder() {
            list = new ArrayList();
            if (root != null) list.add(0, root);
        }

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public E next() {
            E c;
            Object obj = null;

            Node<E> node;

            if (!hasNext()) throw new NoSuchElementException();

            do {
                obj = list.remove(0);

                if (obj instanceof Node) {
                    node = (Node<E>)obj;
                    if (node.right != null) list.add(0, node.right);
                    list.add(0, node.elem);
                    if (node.left != null) list.add(0, node.left);
                }
            } while (obj instanceof Node);

            c = (E)obj;

            return c;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<E> it = iterator();

        sb.append("[");
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) sb.append(" ");
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * Inserta un elemento en el arbol.
     *
     * @param E - Elemento a insertar.
     */
    public abstract void add(E c);

    /**
     * Elimina un elemento en el arbol.
     *
     * @param E - Elemento a eliminar.
     * @return boolean - true si fue capaz de eliminar el elemento dado.
     */
    public abstract boolean remove(E c);

    /**
     * Busca un elemento en el arbol.
     *
     * @param E - Elemento a buscar.
     * @return boolean - true si fue capaz de encontrar el elemento dado.
     */
    public abstract boolean contains(E c);

    /**
     * Busca el elemento minimo en el arbol.
     *
     * @return E - Elemento minimo.
     */
    public E min() {
        if (root == null) {
            return null;
        }

        return root.min().elem;
    }

    /**
     * Busca el elemento maximo en el arbol.
     *
     * @return E - Elemento maximo.
     */
    public E max() {
        if (root == null) {
            return null;
        }

        return root.max().elem;
    }

    class Node<E> {

        public Node<E> padre;
        public Node<E> left;
        public Node<E> right;
        public E elem;

        /**
         * Devuelve la altura de este nodo.
         *
         * @return int - Altura actual del nodo.
         */
        public int height() {
            int leftHeight = (left == null) ? -1 : left.height();
            int rightHeight = (right == null) ? -1 : right.height();
            //Se regresa al menos uno, si el nodo tiene al menos un hijo
            return 1 + Math.max(leftHeight, rightHeight);
        }

        /**
         * Pregunta si este nodo es una hoja.
         *
         * @return boolean - true si este nodo es una hoja.
         */
        public boolean isLeaf() {
            return (left == null && right == null);
        }

        /**
         * Devuelve el grado de este nodo.
         *
         * @return int - Grado actual del nodo.
         */
        public int getGrade() {
            int grade = 0;
            if (left != null) grade++;
            if (right != null) grade++;
            return grade;
        }

        /**
         * Devuelve un entero que representa que hijo es el parametro..
         *
         * @param Node - Nodo a revisar.
         * @return int - 0 si es el izquierdo, 1 si es el derecho y -1 si no es ninguno.
         */
        public int getChild(Node<E> hijo) {
            if (hijo == null) {
                return -1;
            }
            if (hijo == left) {
                return 0;
            } else if (hijo == right) {
                return 1;
            } else {
                return -1;
            }
        }

        /**
         * Busca el elemento minimo en el nodo.
         *
         * @return Node<E> - Elemento minimo.
         */
        public Node<E> min() {
            Node<E> current = this;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        /**
         * Busca el elemento maximo en el nodo.
         *
         * @return Node<E> - Elemento maximo.
         */
        public Node<E> max() {
            Node<E> current = this;
            while (current.right != null) {
                current = current.right;
            }
            return current;
        }
    }
}
