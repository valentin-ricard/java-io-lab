package ch.heigvd.dai.binary;

import ch.heigvd.dai.bench.FileReadState;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedBinaryFileReader {
    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 10)
    public void benchBufferedReader(FileReadState state, Blackhole blackhole) throws IOException {
        try (InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(state.getFile()))) {
            int character;
            while ((character = bufferedInputStream.read()) != -1) {
                // Make sure that the caracter is not optimized away
                blackhole.consume(character);
            }
        }
    }
}
