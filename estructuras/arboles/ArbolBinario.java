package estructuras.arboles;

import java.util.Iterator;
import java.util.NoSuchElementException;

import estructuras.lineales.ArrayList;

public abstract class ArbolBinario<E> implements Iterable<E> {

    Node<E> root;
    int size;

    public ArbolBinario() {
        root = null;
        size = 0;
    }


    public int height() {
        if (root == null) {
            return 0;
        }

        return root.height();
    }


    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }


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
                    elem = node.elem;
                    found = true;
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
                    visited.set(0, true); 

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


    public abstract void add(E c);


    public abstract boolean remove(E c);


    public abstract boolean contains(E c);

    public E min() {
        if (root == null) {
            return null;
        }

        return root.min().elem;
    }


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
        public int height() {
            int leftHeight = (left == null) ? -1 : left.height();
            int rightHeight = (right == null) ? -1 : right.height();
            return 1 + Math.max(leftHeight, rightHeight);
        }


        public boolean isLeaf() {
            return (left == null && right == null);
        }


        public int getGrade() {
            int grade = 0;
            if (left != null) grade++;
            if (right != null) grade++;
            return grade;
        }


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


        public Node<E> min() {
            Node<E> current = this;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }


        public Node<E> max() {
            Node<E> current = this;
            while (current.right != null) {
                current = current.right;
            }
            return current;
        }
    }
}
