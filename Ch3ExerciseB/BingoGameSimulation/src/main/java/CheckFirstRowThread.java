public class CheckFirstRowThread extends Thread {

    private BingoCell[][] card;

    public CheckFirstRowThread(BingoCell[][] card) {
        this.card = card;
    }

    @Override
    public void run() {
        int count = 0;

        for (int col = 0; col < BingoUtils.MAX_SIZE; col++) {
            if (card[0][col].isMarked)
                count++;
        }

        if (count == 5)
            System.out.println("\n(" + this.getId() +
                    ") !!!CONGRATULATIONS!!! There is a Bingo in the first row");
        else
            System.out.println("\n(" + this.getId() +
                    ") There is no Bingo in the first row");
    }
}
