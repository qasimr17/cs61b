import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntListOptionalTests {
    // TBD
    @Test
    public void testIncrRecursiveDestructive() {
        IntList L = new IntList(1, new IntList(2, new IntList(3, null)));

        IntList M = IntList.incrRecursiveDestructive(L, 3);

        assertThat(L.get(0)).isEqualTo(4);
        assertThat(L.get(1)).isEqualTo(5);
        assertThat(L.get(2)).isEqualTo(6);

        assertThat(M.get(0)).isEqualTo(4);
        assertThat(M.get(1)).isEqualTo(5);
        assertThat(M.get(2)).isEqualTo(6);

    }

    @Test
    public void testSum() {
        IntList L = new IntList(1, new IntList(2, new IntList(3, null)));
        IntList M = new IntList(1, null);
        IntList N = new IntList(-1, new IntList(1, null));

        assertThat(L.sum()).isEqualTo(6);
        assertThat(M.sum()).isEqualTo(1);
        assertThat(N.sum()).isEqualTo(0);
    }

    @Test
    public void testAddLast() {
        IntList L = new IntList(1, new IntList(2, new IntList(3, null)));

        L.addLast(4);

        assertThat(L.get(3)).isEqualTo(4);
    }

    @Test
    public void testAddFirst() {
        IntList L = new IntList(1, new IntList(2, new IntList(3, null)));
        assertThat(L.get(0)).isEqualTo(1);
        assertThat(L.get(1)).isEqualTo(2);
        assertThat(L.get(2)).isEqualTo(3);

        L.addFirst(0);

        assertThat(L.get(0)).isEqualTo(0);
        assertThat(L.get(1)).isEqualTo(1);
        assertThat(L.get(2)).isEqualTo(2);
        assertThat(L.get(3)).isEqualTo(3);

    }
}
