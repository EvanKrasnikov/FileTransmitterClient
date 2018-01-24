package client;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.List;

public class Sender{
    private static final int FILE_BUFFER = 4096;
    private SocketChannel channel;

    public void sendFile(List<File> files) {
        try {
            for (File file: files){
                Path path = file.toPath();
                FileChannel fileChannel = FileChannel.open(path);
                ByteBuffer buffer = ByteBuffer.allocate(FILE_BUFFER);

                while (fileChannel.read(buffer) > 0){
                    buffer.flip();
                    channel.write(buffer);
                    buffer.clear();
                }

                fileChannel.close();
            }
        } catch (IOException e) {
            System.err.println(Sender.class.getName() + "Can't send files");
        }
    }
}
