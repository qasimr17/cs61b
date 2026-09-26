import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    public void constructorStartsEmpty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.toList()).isEmpty();
    }

    @Test
    public void addFirst() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);

        assertThat(deque.toList()).containsExactly(1, 2, 3).inOrder();
    }

    @Test
    public void addLast() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.toList()).containsExactly(1, 2, 3).inOrder();
    }

    @Test
    public void addFirstAndLastTogether() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addLast(4);

        assertThat(deque.toList()).containsExactly(1, 2, 3, 4).inOrder();
    }

    @Test
    public void addAfterRemoveToEmpty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addFirst(1);
        deque.removeFirst();

        deque.addFirst(2);
        deque.addLast(3);

        assertThat(deque.toList()).containsExactly(2, 3).inOrder();
    }

    @Test
    public void getFirstAndLastEmpty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertThat(deque.getFirst()).isNull();
        assertThat(deque.getLast()).isNull();
    }

    @Test
    public void getFirstAndLast() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.getFirst()).isEqualTo(1);
        assertThat(deque.getLast()).isEqualTo(3);
    }

    @Test
    public void getValidIndices() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);

        assertThat(deque.get(0)).isEqualTo(10);
        assertThat(deque.get(1)).isEqualTo(20);
        assertThat(deque.get(2)).isEqualTo(30);
    }

    @Test
    public void getInvalidIndices() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(10);
        deque.addLast(20);

        assertThat(deque.get(-1)).isNull();
        assertThat(deque.get(2)).isNull();
        assertThat(deque.get(100)).isNull();
    }

    @Test
    public void isEmptyAndSize() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.size()).isEqualTo(0);

        deque.addLast(1);
        deque.addLast(2);

        assertThat(deque.isEmpty()).isFalse();
        assertThat(deque.size()).isEqualTo(2);

        deque.removeFirst();

        assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    public void toListEmpty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertThat(deque.toList()).isEmpty();
    }

    @Test
    public void toList() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(0);

        assertThat(deque.toList())
                .containsExactly(0, 1, 2, 3)
                .inOrder();
    }

    @Test
    public void wrapAround() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 6; i++) {
            deque.addLast(i);
        }

        deque.removeFirst();
        deque.removeFirst();
        deque.addLast(6);
        deque.addLast(7);

        assertThat(deque.toList())
                .containsExactly(2, 3, 4, 5, 6, 7)
                .inOrder();
    }

    @Test
    public void getAfterWrapAround() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 6; i++) {
            deque.addLast(i);
        }

        deque.removeFirst();
        deque.removeFirst();

        deque.addLast(6);
        deque.addLast(7);

        assertThat(deque.get(0)).isEqualTo(2);
        assertThat(deque.get(3)).isEqualTo(5);
        assertThat(deque.get(5)).isEqualTo(7);
    }

    @Test
    public void removeFirst() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(2, 3).inOrder();

        assertThat(deque.removeFirst()).isEqualTo(2);
        assertThat(deque.removeFirst()).isEqualTo(3);
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void removeLast() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.removeLast()).isEqualTo(3);
        assertThat(deque.toList()).containsExactly(1, 2).inOrder();

        assertThat(deque.removeLast()).isEqualTo(2);
        assertThat(deque.removeLast()).isEqualTo(1);
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void removeFromEmpty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.removeLast()).isNull();
    }

    @Test
    public void removeFirstAndLastTogether() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);

        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque.removeLast()).isEqualTo(4);

        assertThat(deque.toList()).containsExactly(2, 3).inOrder();
    }

    @Test
    public void addFirstTriggersResize() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 8; i++) {
            deque.addFirst(i);
        }

        assertThat(deque.size()).isEqualTo(8);
        assertThat(deque.toList())
                .containsExactly(7, 6, 5, 4, 3, 2, 1, 0)
                .inOrder();

        deque.addFirst(8);

        assertThat(deque.size()).isEqualTo(9);
        assertThat(deque.getFirst()).isEqualTo(8);
    }

    @Test
    public void addLastTriggersResize() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 8; i++) {
            deque.addLast(i);
        }

        deque.addLast(8);

        assertThat(deque.size()).isEqualTo(9);
        assertThat(deque.getLast()).isEqualTo(8);
        assertThat(deque.toList())
                .containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8)
                .inOrder();
    }

    @Test
    public void resizeWithWrappedElements() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 8; i++) {
            deque.addLast(i);
        }

        deque.removeFirst();
        deque.removeFirst();

        deque.addLast(8);
        deque.addLast(9);

        deque.addLast(10);

        assertThat(deque.toList())
                .containsExactly(2, 3, 4, 5, 6, 7, 8, 9, 10)
                .inOrder();
    }

    @Test
    public void removeFirstTriggersResizeDown() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 32; i++) {
            deque.addLast(i);
        }

        for (int i = 0; i < 25; i++) {
            deque.removeFirst();
        }

        assertThat(deque.size()).isEqualTo(7);
        assertThat(deque.toList())
                .containsExactly(25, 26, 27, 28, 29, 30, 31)
                .inOrder();
    }

    @Test
    public void removeLastTriggersResizeDown() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 32; i++) {
            deque.addLast(i);
        }

        for (int i = 0; i < 25; i++) {
            deque.removeLast();
        }

        assertThat(deque.size()).isEqualTo(7);
        assertThat(deque.toList())
                .containsExactly(0, 1, 2, 3, 4, 5, 6)
                .inOrder();
    }

    @Test
    public void resizeUpAndDown() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 32; i++) {
            deque.addLast(i);
        }

        for (int i = 0; i < 25; i++) {
            deque.removeFirst();
        }

        assertThat(deque.toList())
                .containsExactly(25, 26, 27, 28, 29, 30, 31)
                .inOrder();

        deque.addFirst(24);
        deque.addFirst(23);

        assertThat(deque.toList())
                .containsExactly(23, 24, 25, 26, 27, 28, 29, 30, 31)
                .inOrder();
    }
}