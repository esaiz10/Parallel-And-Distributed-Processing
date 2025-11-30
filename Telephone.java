import java.util.Random;
import java.util.Scanner;

public class Telephone {

    static class Player {
        private int id;
        private Random rand;

        public Player(int id) {
            this.id = id;
            this.rand = new Random();
        }

        public String[] passMessage(String[] message) {
            int i = rand.nextInt(message.length);
            int j = rand.nextInt(message.length);
            while (j == i) {
                j = rand.nextInt(message.length);
            }

            String temp = message[i];
            message[i] = message[j];
            message[j] = temp;

            return message;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        
        System.out.print("Enter number of players: ");
        int numPlayers = scan.nextInt();
        scan.nextLine(); // clear newline

        
        System.out.print("Enter your message (8+ words, no punctuation): ");
        String line = scan.nextLine();

        
        String[] message = line.split(" ");

        
        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i + 1);
        }

        
        for (int i = 0; i < numPlayers; i++) {
            message = players[i].passMessage(message);
        }

        
        System.out.print("Final message: ");
        for (String w : message) {
            System.out.print(w + " ");
        }
        System.out.println();
    }
}
