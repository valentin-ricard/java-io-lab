package ch.heigvd.dai.text;

import ch.heigvd.dai.bench.FileReadState;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BufferedTextFileReader {
    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 3, time = 10)
    public void benchBufferedReader(FileReadState state, Blackhole blackhole) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(state.getFile(), StandardCharsets.UTF_8))) {
            int character;
            while ((character = bufferedReader.read()) != -1) {
                // Make sure that the caracter is not optimized away
                blackhole.consume(character);
            }
        }
    }
}
