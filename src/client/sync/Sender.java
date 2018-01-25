package client.sync;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.List;

public class Sender {
    private static final int BUFFER = 4096;
    private ByteBuffer buffer = ByteBuffer.allocate(BUFFER);
    private SocketChannel channel;

    public Sender(SocketChannel channel){
        this.channel = channel;
    }

    public void sendFile(List<File> files) {
        for (File file: files){
            Path path = file.toPath();
            try {
                FileChannel fileChannel = FileChannel.open(path);
                while (fileChannel.read(buffer) > 0) {
                    buffer.flip();
                    fileChannel.write(buffer);
                    buffer.clear();
                }
                fileChannel.close();
            } catch (IOException e) {
                System.err.println("Can't send file");
            }
        }
    }

    public void sendMessage(String message){
        try {
            byte[] bytes =  message.getBytes();
            buffer.get(bytes);
            channel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            System.err.println("Can't send message");
        }
    }
}
