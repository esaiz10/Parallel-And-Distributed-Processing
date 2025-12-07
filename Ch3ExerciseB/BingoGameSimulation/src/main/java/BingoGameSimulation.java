public class BingoGameSimulation {

    public static void main(String[] args) {

        BingoCell[][] card = new BingoCell[BingoUtils.MAX_SIZE][BingoUtils.MAX_SIZE];

        BingoUtils.generateBingoCard(card);
        BingoUtils.displayCard(card);

        // Create threads similar to fork() cases
        Thread firstRowThread = new CheckFirstRowThread(card);
        Thread diagLRThread = new CheckDiagonalLRThread(card);

        firstRowThread.start();
        diagLRThread.start();

        try {
            firstRowThread.join();
            diagLRThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nSimulation complete.\n");
    }
}