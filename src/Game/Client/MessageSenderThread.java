package Game.Client;

import Game.PlayerState;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

@SuppressWarnings("BusyWait")
public class MessageSenderThread extends Thread{
    private final String clientName;
    private PlayerState message;

    private Socket clientSocket;

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
                outputStream.writeObject(message);
                outputStream.flush();
                System.out.println(clientName + " sent:\n\t" + message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    interrupt();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateMessage(PlayerState state) {
        message = state;
    }
}
