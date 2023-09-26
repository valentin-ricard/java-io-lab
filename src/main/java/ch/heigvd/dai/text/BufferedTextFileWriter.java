package ch.heigvd.dai.text;

import ch.heigvd.dai.bench.FileWriteState;
import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class BufferedTextFileWriter {
    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 10)
    public void writeToFile(FileWriteState state) throws IOException {
        // We include the setup
        String fileName = state.getFile();
        int size = state.getFileSize();
        try (Writer fileInputStream = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < size; i++) {
                fileInputStream.write(0xbe);
            }
        } finally {
            Files.delete(Path.of(fileName));
        }
    }
}
