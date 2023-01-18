package Game;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class PlayerState implements Serializable {
    private int locX;
    private int locY;
    private int locZ;
    private int health;
    private int score;
    private int team;
    private final String name;

    public PlayerState(int locX, int locY, int locZ, int health, int score, int team, String name) {
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.health = health;
        this.score = score;
        this.team = team;
        this.name = name;
    }

    public PlayerState(String name) {
        locX = 0;
        locY = 0;
        locZ = 0;
        health = 100;
        score = 0;
        team = 0;
        this.name = name;
    }

    public void updateState(int locX, int locY, int locZ, int health, int score, int team) {
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.health = health;
        this.score = score;
        this.team = team;
    }

    public void advanceGame() {
        locX += 1;
        locY += 2;
        health -= 2;
        score += 20;
    }

    /*
    @Override
    public String toString() {
        return "Player: " + name + "\n" +
                "Position: \n" +
                "\t X:\t\t" + locX + "\n" +
                "\t Y:\t\t" + locY + "\n" +
                "\t Z:\t\t" + locZ + "\n" +
                "Health:\t\t" + health + "\n" +
                "Score:\t\t" + score + "\n" +
                "Team:\t\t" + team;
    }
    */

    @Override
    public String toString() {
        return name + "\t X: " + locX + "\t Y: " + locY + "\t Z: " + locZ;
    }

    //can be removed if serialization works as intended
    public byte[] toByte() {
        byte[] result = new byte[(6 * 4)];
        byte[] locXBytes = IntToByte(locX);
        System.arraycopy(locXBytes, 0, result, 0, 4);
        byte[] locYBytes = IntToByte(locY);
        System.arraycopy(locYBytes, 0, result, 4, 4);
        byte[] locZBytes = IntToByte(locZ);
        System.arraycopy(locZBytes, 0, result, 8, 4);
        byte[] healthBytes = IntToByte(health);
        System.arraycopy(healthBytes, 0, result, 12, 4);
        byte[] scoreBytes = IntToByte(score);
        System.arraycopy(scoreBytes, 0, result, 16, 4);
        byte[] teamBytes = IntToByte(team);
        System.arraycopy(teamBytes, 0, result, 20, 4);

        return result;
    }

    public void fromByte(byte[] bytes){
        if (bytes.length != 6 * 4) return;
        byte[] bytesBuffer = new byte[4];
        System.arraycopy(bytes, 0, bytesBuffer, 0, bytesBuffer.length);
        locX = ByteToInt(bytesBuffer);
        System.arraycopy(bytes, 4, bytesBuffer, 0, bytesBuffer.length);
        locY = ByteToInt(bytesBuffer);
        System.arraycopy(bytes, 8, bytesBuffer, 0, bytesBuffer.length);
        locZ = ByteToInt(bytesBuffer);
        System.arraycopy(bytes, 12, bytesBuffer, 0, bytesBuffer.length);
        health = ByteToInt(bytesBuffer);
        System.arraycopy(bytes, 16, bytesBuffer, 0, bytesBuffer.length);
        score = ByteToInt(bytesBuffer);
        System.arraycopy(bytes, 20, bytesBuffer, 0, bytesBuffer.length);
        team = ByteToInt(bytesBuffer);
    }

    private byte[] IntToByte(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    private int ByteToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
}