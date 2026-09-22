import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque61B<T> implements Deque61B{


    private static class Node {
        Object item; // Object: To enable abstraction
        Node next;
        Node prev;

        public Node(Object x, Node n, Node p) {
            item = x;
            next = n;
            prev = p;
        }
    }

    Node sentinel;
    int size;

    public LinkedListDeque61B() {
        // Create sentinel node
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        // set size to 0
        size = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(Object x) {

        Node first = new Node(x, sentinel.next, sentinel);
        sentinel.next.prev = first;
        sentinel.next = first;

        size += 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(Object x) {

        Node last = new Node(x, sentinel, sentinel.prev);
        sentinel.prev.next = last;
        sentinel.prev = last;

        size += 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List toList() {
        List<Object> returnList = new ArrayList<>();

        Node p = sentinel.next;
        while (p != sentinel) {
            returnList.add(p.item);
            p = p.next;
        }

        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public Object getFirst() {
        return isEmpty() ? null : sentinel.next.item;
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public Object getLast() {
        return isEmpty() ? null : sentinel.prev.item;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public Object removeFirst() {

        if (isEmpty()) { return null; }

        Node p = sentinel.next;
        sentinel.next = p.next;
        p.next.prev = sentinel;

        size -= 1;
        return p.item;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public Object removeLast() {

        if (isEmpty()) { return null; }

        Node p = sentinel.prev;
        sentinel.prev = p.prev;
        p.prev.next = p.next;

        size -= 1;
        return p.item;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public Object get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node p = sentinel.next;
        while (index > 0) {
            index -= 1;
            p = p.next;
        }

        return p.item;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public Object getRecursive(int index) {
        if (index < 0 || index >= size) { return null; }
        return getRecursive(index, sentinel.next);
    }

    private Object getRecursive(int index, Node p) {
        if (index == 0) { return p.item; }
        return getRecursive(index - 1, p.next);
    }

    public static void main(String[] args) {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        lld.addLast(0); // [1]
        lld.addLast(1); // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
    }
}
