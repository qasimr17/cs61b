import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void isEmptyListEmpty() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        assertThat(lld.isEmpty()).isTrue();
    }

    @Test
    public void isNonEmptyListNotEmpty() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        lld.addFirst(1);

        assertThat(lld.isEmpty()).isFalse();

    }

    @Test
    public void isEmptyListZeroSize() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void isNonEmptyListNonZeroSize() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        lld.addFirst(1);
        assertThat(lld.size()).isEqualTo(1);

        lld.addLast(2);
        assertThat(lld.size()).isEqualTo(2);
    }

    @Test
    public void getLastItem() {
        Deque61B<Object> lld = new LinkedListDeque61B<>();

        assertThat(lld.getLast()).isNull(); // []

        lld.addFirst(1);
        assertThat(lld.getLast()).isEqualTo(1); // [1]

        lld.addFirst(2);
        assertThat(lld.getLast()).isEqualTo(1); // [2, 1]

        lld.addLast(3);
        assertThat(lld.getLast()).isEqualTo(3); // [2, 1, 3]
    }

    @Test
    public void getFirstItem() {
        Deque61B<Object> lld = new LinkedListDeque61B<>();

        assertThat(lld.getFirst()).isNull(); // []

        lld.addLast(1); // [1]
        assertThat(lld.getFirst()).isEqualTo(1);

        lld.addLast(2); // [1, 2]
        assertThat(lld.getFirst()).isEqualTo(1);

        lld.addFirst(3); // [3, 1, 2]
        assertThat(lld.getFirst()).isEqualTo(3);
    }

    @Test
    public void getItem() {
        Deque61B<Object> lld = new LinkedListDeque61B<>();

        assertThat(lld.get(0)).isNull();

        lld.addFirst(2263);
        assertThat(lld.get(0)).isEqualTo(2263);
        assertThat(lld.get(1)).isNull();

        lld.addFirst(11);
        assertThat(lld.get(0)).isEqualTo(11);
        assertThat(lld.get(1)).isEqualTo(2263);
        assertThat(lld.get(2)).isNull();

        assertThat(lld.get(2400)).isNull();
        assertThat(lld.get(-1)).isNull();
    }

    @Test
    public void getItemRecursively() {
        Deque61B<Object> lld = new LinkedListDeque61B<>();

        assertThat(lld.getRecursive(0)).isNull();

        lld.addFirst(2263);
        assertThat(lld.getRecursive(0)).isEqualTo(2263);
        assertThat(lld.getRecursive(1)).isNull();

        lld.addFirst(11);
        assertThat(lld.getRecursive(0)).isEqualTo(11);
        assertThat(lld.getRecursive(1)).isEqualTo(2263);
        assertThat(lld.getRecursive(2)).isNull();

        assertThat(lld.getRecursive(2400)).isNull();
        assertThat(lld.getRecursive(-1)).isNull();
    }

    @Test
    public void removeFirst() {
        Deque61B<Object> lld = new LinkedListDeque61B<>();

        // 1. Empty deque
        assertThat(lld.removeFirst()).isNull();
        assertThat(lld.isEmpty()).isTrue();

        // 2. One element
        lld.addFirst(1); // [1]
        assertThat(lld.removeFirst()).isEqualTo(1); // []
        assertThat(lld.isEmpty()).isTrue();
        assertThat(lld.toList()).isEmpty();

        // 3. Remove first from two elements
        lld.addFirst(1); // [1]
        lld.addFirst(2); // [2, 1]
        assertThat(lld.removeFirst()).isEqualTo(2); // [1]
        assertThat(lld.toList()).containsExactly(1);

        // 4. Remove first from multiple elements
        lld.addLast(2);  // [1, 2]
        lld.addLast(3);  // [1, 2, 3]
        assertThat(lld.removeFirst()).isEqualTo(1); // [2, 3]
        assertThat(lld.toList()).containsExactly(2, 3).inOrder();

        // 5. Remove repeatedly until empty
        assertThat(lld.removeFirst()).isEqualTo(2); // [3]
        assertThat(lld.removeFirst()).isEqualTo(3); // []
        assertThat(lld.isEmpty()).isTrue();
        assertThat(lld.toList()).isEmpty();

        // 6. Can still use deque correctly after becoming empty
        lld.addFirst(4); // [4]
        lld.addLast(5);  // [4, 5]
        assertThat(lld.removeFirst()).isEqualTo(4); // [5]
        assertThat(lld.removeFirst()).isEqualTo(5); // []
        assertThat(lld.removeFirst()).isNull();
        assertThat(lld.isEmpty()).isTrue();
    }

    @Test
    public void removeLast() {
        Deque61B<Object> lld = new LinkedListDeque61B<>();

        // Empty deque
        assertThat(lld.removeLast()).isNull();
        assertThat(lld.isEmpty()).isTrue();
        assertThat(lld.size()).isEqualTo(0);

        // One element
        lld.addFirst(1); // [1]
        assertThat(lld.removeLast()).isEqualTo(1); // []
        assertThat(lld.isEmpty()).isTrue();
        assertThat(lld.size()).isEqualTo(0);
        assertThat(lld.toList()).isEmpty();

        // Two elements
        lld.addFirst(1); // [1]
        lld.addFirst(2); // [2, 1]
        assertThat(lld.removeLast()).isEqualTo(1); // [2]
        assertThat(lld.toList()).containsExactly(2);
        assertThat(lld.size()).isEqualTo(1);

        // Multiple elements
        lld.addLast(3); // [2, 3]
        lld.addLast(4); // [2, 3, 4]
        assertThat(lld.removeLast()).isEqualTo(4); // [2, 3]
        assertThat(lld.toList()).containsExactly(2, 3).inOrder();

        // Repeated removal
        assertThat(lld.removeLast()).isEqualTo(3); // [2]
        assertThat(lld.removeLast()).isEqualTo(2); // []
        assertThat(lld.isEmpty()).isTrue();
        assertThat(lld.size()).isEqualTo(0);

        // Can still use deque after becoming empty
        lld.addFirst(5); // [5]
        lld.addLast(6);  // [5, 6]
        assertThat(lld.removeLast()).isEqualTo(6); // [5]
        assertThat(lld.removeLast()).isEqualTo(5); // []
        assertThat(lld.removeLast()).isNull();
        assertThat(lld.isEmpty()).isTrue();
    }
}