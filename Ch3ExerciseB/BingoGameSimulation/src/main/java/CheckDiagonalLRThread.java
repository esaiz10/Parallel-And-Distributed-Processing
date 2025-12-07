public class CheckDiagonalLRThread extends Thread {

    private BingoCell[][] card;

    public CheckDiagonalLRThread(BingoCell[][] card) {
        this.card = card;
    }

    @Override
    public void run() {
        int count = 0;

        if (card[0][0].isMarked) count++;
        if (card[1][1].isMarked) count++;
        if (card[2][2].isMarked) count++;
        if (card[3][3].isMarked) count++;
        if (card[4][4].isMarked) count++;

        if (count == 5)
            System.out.println("\n(" + this.getId() +
                    ") !!!CONGRATULATIONS!!! There is a Bingo in the LR diagonal");
        else
            System.out.println("\n(" + this.getId() +
                    ") There is no Bingo in the LR diagonal");
    }
}