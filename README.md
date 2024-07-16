# Topp Standard // Yelstream

Selected functionalities touching upon standard functionalities within Java SE and Jakarta EE.


## Introduction

Welcome to the Topp Standard library, 
a growing collection of utilities and enhancements for Java SE and Jakarta EE.
This project aims to provide high-quality, 
reusable components to help developers create maintainable and efficient software.

As a professional software developer with a passion for all things Java, 
I have compiled this library to encapsulate best practices and useful functionalities 
that address common development needs. 
My focus is on ensuring loose coupling and high cohesion, 
leading to the creation of coherent libraries that can be easily integrated into your projects.

### Scope

Addressed is selected functionalities of Java SE 21 and Jakarta EE 11.

As a foundation of most elements, I use Lombok, SLF4J and Java SE 9 modules.

Started March 2024.

### Naming

The naming _Standard_ is a reference to the Standard Edition of Java and the specifications behind Jakarta Enterprise Edition.

## Releases

Maven Central Repository contains released artifacts:

* https://central.sonatype.com/search?q=com.yelstream.topp.standard

## Examples

### Example 1: Printable Entities

The `Printable` interface allows objects to print their content to a `Printable` and capture the output as a string. 
Here's how you can use it:

```
public class MyPrintable implements Printable {
    @Override
    public void print(PrintStream out) {
        out.println("Hello, World!");
    }

    public static void main(String[] args) {
        MyPrintable printable = new MyPrintable();
        String text = printable.print();
        System.out.println(text);
    }
}
```

This will output:

```
Hello, World!
```

### Example 2: Socket Scanning

The `SocketScanner` utility provides a convenient way to scan input from a socket.
Here's a simple example:

```
    SocketScanner.Builder builder = SocketScanner.builder();
    builder.ports(() -> IntStream.range(0, 65535));
    builder.addressSuppliers(InetAddresses.distinct(Inet4Addresses.StandardAddress.getAllSuppliers()));
    SocketScanner scanner = builder.build();

    SocketScanner.Result result = scanner.scan();

    String resultText = result.print();
    System.out.println(resultText);
```

This example will scan all ports on selected standard addresses and print the result, 
which may look something like this:

```
  1 Address /127.0.0.1:
      1     135
      2     445
      3     2179
      4     2375
      5     3375
      6     5040
      7     5357
      8     6443
      9     7680
     10     8050
     11     27036
     12     27060
     13     32017
     14     32018
     15     34154
     16     49664
     17     49665
     18     49666
     19     49667
     20     49668
     21     52592
     22     53298
     23     54382
     24     54570
     25     54679
     26     55145
     27     55657
     28     55784
     29     55787
     30     55791
     31     56565
     32     57682
     33     63342
     34     64120
     35     65001
  2 Address DESKTOP-KI53BD9/172.24.144.1:
      1     135
      2     139
      3     445
      4     2179
      5     3375
      6     5040
      7     5357
      8     7680
      9     8050
  3 Address /0.0.0.0:
      1     135
      2     139
      3     445
      4     2179
      5     3375
      6     5040
      7     5357
      8     7680
      9     8050
```

Unique for this is that it scans all 65535 TCP ports in just a few seconds.
This using a combination of virtual threads and reactive programming using `CompletableFuture`.

There are different ways to scan ports locally and remote - the `SocketScanner` does still evolve.

### Example 3: Rate-limited Logging

Coming soon: An example demonstrating rate-limited logging to help manage log verbosity.

```
```

### Example 4: Managed Executor

The `ManagedExecutor` abstracts away the lifecycle handling of the executor used, 
whether it is a simple `Executor` -- which continues to be not closable or disposable --, 
an old-style thread-pool `ExecutorService`, or a modern virtual thread based `ExecutorService` like here, 
-- both of which since Java SE 19 have become `AutoClosable` objects.

Using `ManagedExecutor` leaves the setup of an executor and its possible shutdown as a responsibility to 
the calling context while leaving the actual usage uncluttered,
here shown as a small piece of reactive programming:

```
@Builder
@AllArgsConstructor
public class SimpleScanner {

    @Builder.Default
    private final Supplier<IntStream> ports = () -> IntStream.range(0, 65536);

    @Builder.Default
    private final Supplier<ManagedExecutor> executorSupplier = () -> ManagedExecutor.of(Executors.newVirtualThreadPerTaskExecutor());

    public List<Result> scan() {
        try (ManagedExecutor executor = executorSupplier.get()) {

            List<CompletableFuture<Result>> futures =
                ports.get().mapToObj(port -> Sockets.scan(port, executor)).toList();

            CompletableFuture<List<Result>> allFutures = CompletableFutures.allOf(futures);
            return allFutures.thenApply(l -> l.stream().filter(r -> r != null && r.success()).toList()).join();
        }
    }
```

The utility `CompletableFutures` is also part of the libraries, 
here `CompletableFutures#allOf(List<CompletableFuture)` essentially handles type safety.
