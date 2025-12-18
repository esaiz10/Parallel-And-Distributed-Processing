import java.io.*;
import java.net.*;

public class FTPServer {

    public static void main(String[] args) throws Exception {
        int portNumber = 6000;

        if (args.length == 1) {
            portNumber = Integer.parseInt(args[0]);
        }

        ServerSocket welcomeSocket = new ServerSocket(portNumber);
        System.out.println("FTPServer listening on port " + portNumber);

        Socket connectionSocket = welcomeSocket.accept();
        System.out.println("Client connected");

        BufferedReader inFromFile =
                new BufferedReader(new InputStreamReader(System.in));

        DataOutputStream outToClient =
                new DataOutputStream(connectionSocket.getOutputStream());

        String line;
        while ((line = inFromFile.readLine()) != null) {
            outToClient.writeBytes(line + "\n");
        }

        outToClient.writeBytes("ZZZZ\n");
        outToClient.flush();

        connectionSocket.close();
        welcomeSocket.close();
        System.out.println("FTPServer finished");
    }
}