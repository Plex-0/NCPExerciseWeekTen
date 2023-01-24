package Game.Client;

import Game.PlayerState;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageSenderThread extends Thread{
    private final String clientName;
    private PlayerState message;
    private boolean stateUpdated = true;

    private final Socket clientSocket;

    public MessageSenderThread(String clientName, PlayerState message, Socket clientSocket) {
        this.clientName = clientName;
        this.message = message;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            while (!isInterrupted()) {
                if (stateUpdated) {
                    outputStream.writeObject(message);
                    outputStream.flush();
                    System.out.println(clientName + " sent:\n\t" + message);
                    stateUpdated = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateMessage(PlayerState state) {
        //message = state;
        stateUpdated = true;
    }
}
