package Game.Client;

import Game.PlayerState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

public class MessageReceiverThread extends Thread {
    private final String clientName;
    private Socket clientSocket;

    public MessageReceiverThread(String clientName, Socket clientSocket) {
        this.clientName = clientName;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (!isInterrupted()) {
                PlayerState message = (PlayerState) inputStream.readObject();
                System.out.println(clientName + " received : \n\t" + message);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}