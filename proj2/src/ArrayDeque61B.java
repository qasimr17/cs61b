import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private static final int INITIAL_CAPACITY = 8;

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /*
     Invariants:
        -- nextFirst points to the index where the next first item will be placed
        -- nextLast points to the index where the next last item will be placed
        -- size is the number of items currently in the deque
        -- items between nextFirst and nextLast contain the deque's elements
    */

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }

        items[nextFirst] = x;
        size++;

        nextFirst = decrement(nextFirst);
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }

        items[nextLast] = x;
        size++;

        nextLast = increment(nextLast);
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        int p = increment(nextFirst);

        for (int i = 0; i < size; i++) {
            returnList.add(items[p]);
            p = increment(p);
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T getFirst() {
        if (isEmpty()) {
            return null;
        }

        return items[increment(nextFirst)];
    }

    @Override
    public T getLast() {
        if (isEmpty()) {
            return null;
        }

        return items[decrement(nextLast)];
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        if (size <= items.length / 4 && items.length > 8) {
            resize(items.length / 2);
        }

        int first = increment(nextFirst);
        T firstItem = items[first];

        items[first] = null;
        nextFirst = first;
        size--;

        return firstItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        if (size <= items.length / 4 && items.length > 8) {
            resize(items.length / 2);
        }

        int last = decrement(nextLast);
        T lastItem = items[last];

        items[last] = null;
        nextLast = last;
        size--;

        return lastItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        int first = increment(nextFirst);
        int physicalIndex = (first + index) % items.length;

        return items[physicalIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException(
                "No need to implement getRecursive for ArrayDeque61B."
        );
    }

    private void resize(int capacity) {
        T[] resized = (T[]) new Object[capacity];

        int firstIndex = size / 2;

        for (int i = 0; i < size; i++) {
            resized[firstIndex] = get(i);
            firstIndex++;
        }

        items = resized;
        nextFirst = (size / 2) - 1;
        nextLast = firstIndex;
    }

    private int increment(int index) {
        return (index + 1) % items.length;
    }

    private int decrement(int index) {
        return (index - 1 + items.length) % items.length;
    }

    // Iterator methods
    public class ArrayDequeIterator implements Iterator<T> {

        int pos = 0;

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T itemToReturn = get(pos);
                pos += 1;
                return itemToReturn;
            }
            return null;
        }

    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }


    // Overriding the string representation
    @Override
    public String toString() {
        StringBuilder stringToReturn = new StringBuilder("[");
        for (T x : this) {
            if (stringToReturn.length() > 1) {
                stringToReturn.append(", ");
            }
            stringToReturn.append(x);
        }
        stringToReturn.append("]");
        return stringToReturn.toString();
    }

    // Overriding the equality check
    @Override
    public boolean equals(Object other) {

        if (this == other) { return true; }

        if (other instanceof ArrayDeque61B otherArrayDeque) {
            if (size != otherArrayDeque.size) { return false; }
            for (int i = 0; i < size; i += 1) {
                if (get(i) != otherArrayDeque.get(i)) { return false; }
            }
            return true;
        }
        return false;
    }
}