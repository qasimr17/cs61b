public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
      for (int row = 1; row <= 5; row++) {
         for (int col = 1; col <= 5 - row; col++) {
            IO.print(" ");
         }

         for (int col = 1; col <= row; col++) {
            IO.print("*");
         }

         IO.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}