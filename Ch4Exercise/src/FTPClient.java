import java.io.*;
import java.net.*;

public class FTPClient {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("Usage: java FTPClient <server-ip> <port>");
            return;
        }

        String serverIP = args[0];
        int portNumber = Integer.parseInt(args[1]);

        Socket clientSocket = new Socket(serverIP, portNumber);

        BufferedReader inFromServer =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String line;
        while ((line = inFromServer.readLine()) != null) {
            if (line.equals("ZZZZ")) {
                break;
            }
            System.out.println(line);
        }

        clientSocket.close();
    }
}