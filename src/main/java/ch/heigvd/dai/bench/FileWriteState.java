package ch.heigvd.dai.bench;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.atomic.AtomicLong;

@State(Scope.Benchmark)
public class FileWriteState {
    @Param({ "1", "1024", "1048576", "5242880"})
    private int fileSize;

    AtomicLong fileIndex = new AtomicLong(0L);

    public String getFile() {
        return "file-text-" + fileIndex.getAndIncrement();
    }

    public int getFileSize() {
        return fileSize;
    }
}