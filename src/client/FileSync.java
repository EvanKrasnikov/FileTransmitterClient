package client;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.List;

public class FileSync extends Client{
    private static final int BUFFER = 4 * 4096;
    private ByteBuffer buffer = ByteBuffer.allocate(BUFFER);
    private SocketChannel channel;

    public void receiveFile(List<File> files) { // Receive a file
        for (File file: files){
            Path path = file.toPath();
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

    public void sendFile(List<File> files) {
        for (File file: files){
            Path path = file.toPath();
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
