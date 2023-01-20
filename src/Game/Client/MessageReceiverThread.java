package Game.Client;

import Game.PlayerState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessageReceiverThread extends Thread{
    private final String clientName;

    public MessageReceiverThread(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try(Socket socket = new Socket("127.0.0.1", 50000)) {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (!isInterrupted()) {
                PlayerState message = (PlayerState) inputStream.readObject();
                System.out.println(clientName + " received : \n\t" + message);
            }
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}