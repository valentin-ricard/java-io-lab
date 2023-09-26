package ch.heigvd.dai.binary;

import ch.heigvd.dai.bench.FileReadState;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.*;

public class BinaryFileReader {

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 10)
    public void benchBufferedReader(FileReadState state, Blackhole blackhole) throws IOException {
        try (InputStream inputStream = new FileInputStream(state.getFile())) {
            int character;
            while ((character = inputStream.read()) != -1) {
                // Make sure that the character is not optimized away
                blackhole.consume(character);
            }
        }
    }
}
