public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      // TODO: Fill in this function
      for (int row = 1; row <= N; row++) {
         for (int col = 1; col <= N - row; col ++) {
            IO.print(" ");
         }

         for (int col = 1; col <= row; col++) {
            IO.print("*");
         }

         IO.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}