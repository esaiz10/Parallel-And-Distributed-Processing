import java.util.Random;

public class BingoUtils {
    public static final int MAX_SIZE = 5;

    // Generate bingo card exactly like the C++ version
    public static void generateBingoCard(BingoCell[][] card) {
        Random rand = new Random();

        for (int row = 0; row < MAX_SIZE; row++) {
            for (int col = 0; col < MAX_SIZE; col++) {

                int baseNbr = (row + 1) + (col * 15);
                int randomNbr = rand.nextInt(11);
                int cellNbr = baseNbr + randomNbr;

                boolean marked = false;
                if (cellNbr % 3 == 0 || cellNbr % 5 == 0 || cellNbr % 7 == 0) {
                    marked = true;
                }

                card[row][col] = new BingoCell(cellNbr, marked);
            }
        }
    }

    // Print bingo card same style as C++
    public static void displayCard(BingoCell[][] card) {
        System.out.println(" B    I    N    G    O");
        System.out.println(" --   --   --   --   --");

        for (int row = 0; row < MAX_SIZE; row++) {
            for (int col = 0; col < MAX_SIZE; col++) {

                if (card[row][col].isMarked)
                    System.out.print("(");
                else
                    System.out.print(" ");

                if (row == 2 && col == 2)
                    System.out.print("**");
                else
                    System.out.printf("%2d", card[row][col].number);

                if (card[row][col].isMarked)
                    System.out.print(") ");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }
}
