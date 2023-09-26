package ch.heigvd.dai.bench;

import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/**
 * Manages a file for each thread
 */
@State(Scope.Thread)
public class FileReadState {
    @Param({ "1", "1024", "1048576", "5242880"})
    private int fileSize;
    private String path;

    private static final byte[] CHAR = "\uD83C\uDF00".getBytes(StandardCharsets.UTF_8);

    @Setup
    public void setup() {
        path = "text-file-" + generateName();

        try (OutputStream input = new BufferedOutputStream(new FileOutputStream(path))) {
            // We insert the character 🌀, that takes 4 bytes (0xf0, 0x9f, 0x8c, 0x80)
            for (int i = 0; i < fileSize / 4; i++) {
                input.write(CHAR);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFile() {
        return path;
    }

    @TearDown
    public void teardown() throws IOException {
        // Delete the file
        int count = 0;
        while (true) {
            if (count > 1000) {
                return;
            }
            try {
                Files.delete(Path.of(path));
                return;
            } catch (Exception e) {
                // NO-OP
                ++count;
            }
        }

    }

    private static final Random RANDOM = new Random();
    private String generateName() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        return RANDOM.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
