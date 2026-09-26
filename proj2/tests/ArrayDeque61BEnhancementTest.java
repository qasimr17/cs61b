import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BEnhancementTest {

    @Test
    public void iteratorEmpty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        int count = 0;

        for (int x : deque) {
            count++;
        }

        assertThat(count).isEqualTo(0);
    }

    @Test
    public void iteratorBasic() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        int[] expected = {1, 2, 3};
        int i = 0;

        for (int x : deque) {
            assertThat(x).isEqualTo(expected[i]);
            i++;
        }

        assertThat(i).isEqualTo(3);
    }

    @Test
    public void iteratorAfterWrapAround() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 6; i++) {
            deque.addLast(i);
        }

        deque.removeFirst();
        deque.removeFirst();

        deque.addLast(6);
        deque.addLast(7);

        int[] expected = {2, 3, 4, 5, 6, 7};
        int i = 0;

        for (int x : deque) {
            assertThat(x).isEqualTo(expected[i]);
            i++;
        }

        assertThat(i).isEqualTo(6);
    }

    @Test
    public void iteratorMixedAdds() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        deque.addLast(3);
        deque.addFirst(2);
        deque.addLast(4);
        deque.addFirst(1);

        int[] expected = {1, 2, 3, 4};
        int i = 0;

        for (int x : deque) {
            assertThat(x).isEqualTo(expected[i]);
            i++;
        }

        assertThat(i).isEqualTo(4);
    }

    @Test
    public void basicToStringTest() {

        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        assertThat(ad.toString()).isEqualTo("[front, middle, back]");
    }


    @Test
    public void equalsSameElements() {
        Deque61B<Integer> deque1 = new ArrayDeque61B<>();
        Deque61B<Integer> deque2 = new ArrayDeque61B<>();

        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);

        assertThat(deque1).isEqualTo(deque2);
    }

    @Test
    public void equalsDifferentElements() {
        Deque61B<Integer> deque1 = new ArrayDeque61B<>();
        Deque61B<Integer> deque2 = new ArrayDeque61B<>();

        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(4);

        assertThat(deque1).isNotEqualTo(deque2);
    }

    @Test
    public void equalsDifferentOrder() {
        Deque61B<Integer> deque1 = new ArrayDeque61B<>();
        Deque61B<Integer> deque2 = new ArrayDeque61B<>();

        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        deque2.addLast(3);
        deque2.addLast(2);
        deque2.addLast(1);

        assertThat(deque1).isNotEqualTo(deque2);
    }
}
