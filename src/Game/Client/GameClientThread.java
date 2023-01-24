package Game.Client;

import Game.PlayerState;

import java.io.IOException;
import java.net.Socket;


public class GameClientThread extends Thread {
    private final String clientName;
    private final PlayerState playerState;
    private Socket serverSocket;

    public GameClientThread(String clientName, String playerName) {
        this.clientName = clientName;
        playerState = new PlayerState(playerName);
    }

    @Override
    public void run() {
        try {
            serverSocket = new Socket("127.0.0.1", 50000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MessageSenderThread messageSender = new MessageSenderThread(clientName, playerState, serverSocket);
        MessageReceiverThread messageReceiver = new MessageReceiverThread(clientName, serverSocket);
        messageSender.start();
        messageReceiver.start();
        while (!isInterrupted()) {
            try {
                playerState.advanceGame();
                messageSender.updateMessage(playerState);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                interrupt();
            }
        }
        messageSender.interrupt();
        messageReceiver.interrupt();
        try {
            messageSender.join();
            messageReceiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(clientName + " terminated");
    }
}
