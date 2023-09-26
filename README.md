# Java IOs - Valentin Ricard

### Why is this totally different?
This is a total experiment, to see if a Benchmark Harness could be used instead.

This is heavily inspired from my work on an experimental ray tracer made in rust, where benchmarks are crucial on
functions that are called billions of times for each frame.

This projects uses JBH (Java Benchmark Harness), a OpenJDK project, to accelerate development. This is the one used in
the [JDK source](https://github.com/openjdk/jdk/blob/master/test/micro/org/openjdk/bench/java/io/ByteStreamDecoder.java).

The advantages are the following:
- Allow state injection in benchmarks (reduces code duplicaton)
- Allows for in-place benchmarking (using annotations, collects all benchmarks automatically)
- Allow for variants (same code, but in the state, incremental values)

Most of the interesting sections are available in the following files:
- [FileReadState](./src/main/java/ch/heigvd/dai/bench/FileReadState.java) & [FileWriteState](./src/main/java/ch/heigvd/dai/bench/FileWriteState.java):
    The different states, used for code de-duplication, benchmark preparation, and variable state (using params).
- [BinaryFileReader](./src/main/java/ch/heigvd/dai/binary/BinaryFileReader.java) (or any other bench), with the `@Benchmark` annotated function.
    This is the code actually executed, with the annotations declaring how it should work. All parameters are provided by the harness,
    including Blackhole (the class that ensures that the compiler doesn't optimize away some variable assignations)