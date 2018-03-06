package client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.List;

public class FileSync extends Client{
    private static final int BUFFER = 4 * 4096;
    private ByteBuffer buffer = ByteBuffer.allocate(BUFFER);

    public void receiveFile(List<Path> files) { // Receive a file
        for (Path path: files){
            try {
                FileChannel fileChannel = FileChannel.open(path);
                while (fileChannel.read(buffer) > 0) {
                    buffer.flip();
                    fileChannel.read(buffer);
                    buffer.clear();
                }
                fileChannel.close();
            } catch (IOException e) {
                System.err.println("Can't send file");
            }
        }
    }

    public void sendFile(List<Path> files) {
        for (Path path: files){
            try {
                FileChannel fileChannel = FileChannel.open(path);
                while (fileChannel.read(buffer) > 0) {
                    buffer.flip();
                    getChannel().write(buffer);
                    buffer.clear();
                }
                fileChannel.close();
            } catch (IOException e) {
                System.err.println("Can't send file");
            }
        }
    }
}
