import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class TelephonePlayer {
    /*
      Run modes:
      A: prompts user, validates >= 8 words, swaps, sends to B
      B/C/D: listens, prints received, swaps, sends to next
      E: listens, prints received, prints final (no further send), ends

      Args:
      role listenPort nextHost nextPort

      Examples:
      A 5001 192.168.1.12 5002
      B 5002 192.168.1.13 5003
      C 5003 192.168.1.14 5004
      D 5004 192.168.1.15 5005
      E 5005 - -
    */

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String role = args[0].trim().toUpperCase();
        int listenPort = parsePort(args[1]);
        if (listenPort <= 0) {
            System.out.println("Invalid listenPort.");
            printUsage();
            return;
        }

        String nextHost = null;
        int nextPort = -1;

        if (!role.equals("E")) {
            if (args.length < 4) {
                printUsage();
                return;
            }
            nextHost = args[2];
            nextPort = parsePort(args[3]);
            if (nextPort <= 0) {
                System.out.println("Invalid nextPort.");
                printUsage();
                return;
            }
        }

        try {
            if (role.equals("A")) {
                runAsA(nextHost, nextPort);
            } else if (role.equals("B") || role.equals("C") || role.equals("D")) {
                runAsMiddle(role, listenPort, nextHost, nextPort);
            } else if (role.equals("E")) {
                runAsE(listenPort);
            } else {
                System.out.println("Role must be A, B, C, D, or E.");
                printUsage();
            }
        } catch (IOException e) {
            System.out.println("Network error: " + e.getMessage());
        }
    }

    private static void runAsA(String nextHost, int nextPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Random rng = new Random();

        String sentence;
        do {
            System.out.print("Enter an initial sentence with at least 8 words: ");
            sentence = scanner.nextLine();
            int wordCount = MessageUtil.countWords(sentence);
            if (wordCount < 8) {
                System.out.println("Not enough words. Found " + wordCount + " words, but need at least 8.\n");
            }
        } while (MessageUtil.countWords(sentence) < 8);

        String altered = MessageUtil.swapTwoRandomWords(sentence, rng);

        System.out.println("Message Received by Computer A: " + sentence);
        System.out.println("Message Sent to Computer B: " + altered);

        sendToNext(nextHost, nextPort, altered);
    }

    private static void runAsMiddle(String role, int listenPort, String nextHost, int nextPort) throws IOException {
        Random rng = new Random();

        String received = listenOnce(listenPort);
        String altered = MessageUtil.swapTwoRandomWords(received, rng);

        System.out.println("Message Received by Computer " + role + ": " + received);
        System.out.println("Message Sent to Computer " + nextRole(role) + ": " + altered);

        sendToNext(nextHost, nextPort, altered);
    }

    private static void runAsE(int listenPort) throws IOException {
        String received = listenOnce(listenPort);

        System.out.println("Message Received by Computer E: " + received);
        System.out.println("Final Message on Computer E: " + received);
    }

    private static String listenOnce(int port) throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            try (Socket client = server.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                return in.readLine();
            }
        }
    }

    private static void sendToNext(String host, int port, String message) throws IOException {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
            out.println(message);
        }
    }

    private static String nextRole(String role) {
        switch (role) {
            case "B": return "C";
            case "C": return "D";
            case "D": return "E";
            default: return "?";
        }
    }

    private static int parsePort(String s) {
        try {
            int p = Integer.parseInt(s);
            if (p < 1024 || p > 65535) return -1;
            return p;
        } catch (Exception e) {
            return -1;
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  A <listenPort> <nextHost> <nextPort>");
        System.out.println("  B <listenPort> <nextHost> <nextPort>");
        System.out.println("  C <listenPort> <nextHost> <nextPort>");
        System.out.println("  D <listenPort> <nextHost> <nextPort>");
        System.out.println("  E <listenPort> - -");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  java TelephonePlayer A 5001 192.168.1.12 5002");
    }
}