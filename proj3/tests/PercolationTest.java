import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void percolatesThroughWindingPathTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // an S-shaped path: down column 0, across row 2, down column 4
        int[][] openSites = {
                {0, 0}, {1, 0}, {2, 0},
                {2, 1}, {2, 2}, {2, 3}, {2, 4},
                {3, 4}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
            assertThat(p.percolates()).isFalse();
        }
        // the last site completes the path
        p.open(4, 4);
        assertThat(p.percolates()).isTrue();
        assertThat(p.isFull(4, 4)).isTrue();
        assertThat(p.numberOfOpenSites()).isEqualTo(9);
    }

    @Test
    public void backwashTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        // column 0 percolates; (2, 2) touches the bottom row but has no path to the top
        int[][] openSites = {
                {0, 0},
                {1, 0},
                {2, 0},
                {2, 2}
        };
        Cell[][] expectedState = {
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.OPEN}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void backwashOpenedBeforePercolatingTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        // same as backwashTest, but (2, 2) is opened first
        int[][] openSites = {
                {2, 2},
                {0, 0},
                {1, 0},
                {2, 0}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(p.percolates()).isTrue();
        assertThat(p.isFull(2, 2)).isFalse();
    }

    @Test
    public void openSameSiteTwiceTest() {
        Percolation p = new Percolation(4);
        p.open(1, 2);
        p.open(1, 2);
        p.open(1, 2);
        assertThat(p.numberOfOpenSites()).isEqualTo(1);
    }

    @Test
    public void notPercolatingWithEmptyRowTest() {
        int N = 4;
        Percolation p = new Percolation(N);
        // open every site except row 2
        for (int r = 0; r < N; r++) {
            if (r == 2) {
                continue;
            }
            for (int c = 0; c < N; c++) {
                p.open(r, c);
            }
        }
        assertThat(p.percolates()).isFalse();
        assertThat(p.isFull(1, 3)).isTrue();
        assertThat(p.isFull(3, 0)).isFalse();
        assertThat(p.numberOfOpenSites()).isEqualTo(12);
    }

    @Test
    public void oneByOneClosedTest() {
        Percolation p = new Percolation(1);
        assertThat(p.isOpen(0, 0)).isFalse();
        assertThat(p.isFull(0, 0)).isFalse();
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void exceptionsTest() {
        assertThrows(IllegalArgumentException.class, () -> new Percolation(0));
        assertThrows(IllegalArgumentException.class, () -> new Percolation(-3));

        Percolation p = new Percolation(5);
        assertThrows(IndexOutOfBoundsException.class, () -> p.open(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> p.open(0, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> p.isOpen(5, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> p.isFull(0, -1));
    }

}
