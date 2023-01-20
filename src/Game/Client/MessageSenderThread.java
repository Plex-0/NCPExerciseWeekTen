package Game.Client;

import Game.PlayerState;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

@SuppressWarnings("BusyWait")
public class MessageSenderThread extends Thread{
    private final String clientName;
    private PlayerState message;

    public MessageSenderThread(String clientName, PlayerState message) {
        this.clientName = clientName;
        this.message = message;
    }

    @Override
    public void run() {
        try(Socket socket = new Socket("127.0.0.1", 50000)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
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
