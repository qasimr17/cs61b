public class PrintIndexed {
   /**
     * Prints each character of a given string followed by the reverse of its index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
   public static void printIndexed(String s) {
      int sLength = s.length();

      for (int i = 0; i < sLength; i++) {
         char letter = s.charAt(i);
         IO.print(letter);
         IO.print(sLength - i - 1);
      }

      IO.println();
   }

   public static void main(String[] args) {
      printIndexed("hello");
      printIndexed("cat"); // should print c2a1t0
   }
}