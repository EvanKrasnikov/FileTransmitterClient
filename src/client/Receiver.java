package client;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Receiver {
    private static final int BUFFER = 4096;
    private ByteBuffer buffer = ByteBuffer.allocate(BUFFER);
    private SocketChannel channel;

    public Receiver(SocketChannel channel){
        this.channel = channel;
    }

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

    public void receiveMessage(){ // Receive a message
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while (channel.read(buffer) > 0){
                buffer.flip();
                channel.read(buffer);
                stringBuilder.append(result);
                stringBuilder.append(new String(buffer.array()).trim());
                buffer.clear();
            }
        } catch (IOException e) {
            System.err.println("Can't send message");
        }

        String[] strings = result.split("\\s");
        ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>();

        for (String s: strings){
            queue.push(s);
        }

        BaseHandler handler = new BaseHandler();
        handler.parseMessage(queue);
    }
}
