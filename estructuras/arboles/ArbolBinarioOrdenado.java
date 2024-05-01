package estructuras.arboles;

import java.util.Iterator;
import java.util.NoSuchElementException;

import estructuras.lineales.ArrayList;

public class ArbolBinarioOrdenado<C extends Comparable<C>> extends ArbolBinario<C> {

    public ArbolBinarioOrdenado() {
        super();
    }

    @Override
    public void add(C elem) {
        root = add(elem, root, null);
    }

    private Node<C> add(C elem, Node<C> node, Node<C> parent) {
        if (node == null) {
            node = new Node<C>();
            node.elem = elem;
            node.padre = parent;
            size++;
        } else if (elem.compareTo(node.elem) < 0) {
            node.left = add(elem, node.left, node);
        } else if (elem.compareTo(node.elem) > 0) {
            node.right = add(elem, node.right, node);
        }
        return node;
    }

    @Override
    public boolean remove(C elem) {
        Node<C> node = find(elem, root);
        if (node == null) {
            return false;
        }
        remove(node);
        size--;
        return true;
    }

    private void remove(Node<C> node) {
        if (node.left != null && node.right != null) {
            Node<C> successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.elem = successor.elem;
            remove(successor);
        } else if (node.left != null || node.right != null) {
            Node<C> child = (node.left != null) ? node.left : node.right;
            replaceNodeInParent(node, child);
        } else {
            replaceNodeInParent(node, null);
        }
    }

    private void replaceNodeInParent(Node<C> node, Node<C> child) {
        if (node.padre != null) {
            if (node == node.padre.left) {
                node.padre.left = child;
            } else {
                node.padre.right = child;
            }
        } else {
            root = child;
        }
        if (child != null) {
            child.padre = node.padre;
        }
    }

    @Override
    public boolean contains(C elem) {
        return find(elem, root) != null;
    }

    private Node<C> find(C elem, Node<C> node) {
        while (node != null) {
            int cmp = elem.compareTo(node.elem);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }
}
