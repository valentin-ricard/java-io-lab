package ch.heigvd.dai.binary;

import ch.heigvd.dai.bench.FileWriteState;
import org.openjdk.jmh.annotations.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BufferedBinaryFileWriter {
    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 10)
    public void writeToFile(FileWriteState state) throws IOException {
        // We include the setup
        String fileName = state.getFile();
        int size = state.getFileSize();
        try (OutputStream fileInputStream = new BufferedOutputStream(new FileOutputStream(fileName))) {
            for (int i = 0; i < size; i++) {
                fileInputStream.write(0xbe);
            }
        } finally {
            Files.delete(Path.of(fileName));
        }
    }
}
