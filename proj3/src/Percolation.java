import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF myGrid;
    private final int N;
    private final boolean[] sitesOpened;
    private int openSites = 0;
    private final int topVirtualNode;
    private final int bottomVirtualNode;

    // To solve backwash problem
    private final WeightedQuickUnionUF myBackwashGrid;
    private final int backwashTopVirtualNode;

    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }

        myGrid = new WeightedQuickUnionUF((N * N) + 2); // Two extra spaces for top and bottom virtual nodes
        sitesOpened = new boolean[N * N];

        this.N = N;
        topVirtualNode = (N * N) + 1;
        bottomVirtualNode = (N * N);

        // To solve backwash
        myBackwashGrid = new WeightedQuickUnionUF((N * N) + 1);
        backwashTopVirtualNode = (N * N);
    }

    public void open(int row, int col) {

        if (!isValidSite(row, col)) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }

        if (isOpen(row, col)) { return; }

        int index = gridToArray(row, col);

        // open the site
        sitesOpened[index] = true;
        openSites += 1;

        // connect to all open neighbors (left, right, up, down)
        connectIfOpen(index, row, col - 1);
        connectIfOpen(index, row, col + 1);
        connectIfOpen(index, row - 1, col);
        connectIfOpen(index, row + 1, col);

        // if a site in the top row is opened, connect to top virtual node in both grids
        if (row == 0) {
            myGrid.union(index, topVirtualNode);
            myBackwashGrid.union(index, backwashTopVirtualNode);
        }

        // if a site in the bottom row is opened, connect to bottom virtual node (with N = 1, top = bottom)
        // Only myGrid has a bottom node, so myBackwashGrid never gets backwash
        if (row == N - 1) {
            myGrid.union(index, bottomVirtualNode);
        }
    }

    // Union site index with neighbor (row, col) in both grids, if the neighbor exists and is open
    private void connectIfOpen(int index, int row, int col) {

        if (isValidSite(row, col) && isOpen(row, col)) {
            int neighbor = gridToArray(row, col);
            myGrid.union(index, neighbor);
            myBackwashGrid.union(index, neighbor);
        }
    }

    public boolean isOpen(int row, int col) {

        if (!isValidSite(row, col)) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }

        int index = gridToArray(row, col);
        return sitesOpened[index];

    }

    public boolean isFull(int row, int col) {

        if (!isOpen(row, col)) { return false; }
        return myBackwashGrid.find(backwashTopVirtualNode) == myBackwashGrid.find(gridToArray(row, col));
    }

    public int numberOfOpenSites() {

        return openSites;
    }

    public boolean percolates() {

        return myGrid.find(topVirtualNode) == myGrid.find(bottomVirtualNode);
    }

    private int gridToArray(int row, int col) {
        return (N * row) + col;
    }

    private boolean isValidSite(int row, int col) {

        boolean isValidRow = (row >= 0) && (row < N);
        boolean isValidCol = (col >= 0) && (col < N);

        return (isValidRow && isValidCol);
    }
}
