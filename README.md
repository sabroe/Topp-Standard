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

As a foundation of most elements, I use Lombok, SLF4J, Java SE 9 modules and Java SE 21.

Started March 2024.

### Naming

The naming _Standard_ is a reference to the Standard Edition of Java and the specifications behind Jakarta Enterprise Edition.

## Releases

Maven Central Repository contains released artifacts:

* https://central.sonatype.com/namespace/com.yelstream.topp.standard

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
        
        String text = printable.print();            // <= Yes, this line triggers the secret sauce!
        
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

Management of log verbosity beyond just the log level is possible.
Here a demonstration of rate-limited logging in SLF4J:
This builds upon the fluent API features introduced in SLF4J version 2.0.0. 

Here some example one-liners introducing the filtering form building upon the fluent API object `LoggingEventBuilder`: 

OLD:
```
    Slip.of(log.atInfo()).nop().use().log("Logging!");
    Slip.of(log.atInfo()).nop().use().setMessage("Logging!").log();
    Slip.of(log.atInfo()).nop().apply(leb->leb.setMessage("Logging!").log());
    Slip.of(log.atInfo()).nop().apply((c,leb)->leb.setMessage("Logging!").log());
```

NEW:
```
    Log.of(log.atInfo()).use().log("Logging!");
    Log.of(log.atInfo()).use().setMessage("Logging!").log();
    Log.of(log.atInfo()).apply(leb->leb.setMessage("Logging!").log());
    Log.of(log.atInfo()).apply((c,leb)->leb.setMessage("Logging!").log());
    
    Log.of(log).atInfo().use().log("Logging!");
    Log.of(log).atInfo().use().setMessage("Logging!").log();
    Log.of(log).atInfo().apply(e->e.setMessage("Logging!").log());
    Log.of(log).atInfo().apply((c,leb)->leb.setMessage("Logging!").log());
    
    Log.atInfo(log).use().log("Logging!");
    Log.atInfo(log).use().setMessage("Logging!").log();
    Log.atInfo(log).apply(e->e.setMessage("Logging!").log());
    Log.atInfo(log).apply((c,e)->e.setMessage("Logging!").log());
    
    Log.atInfo(log).use().log("Logging!");
    Log.atInfo(log).use().message("Logging!").log();
    Log.atInfo(log).log(e->e.message("Logging!"));
    Log.atInfo(log).log((c,e)->e.message("Logging!"));
```

Here, the instance `log` is the actual `org.slf4j.Logger` instance, possibly introduced by Lombok `@Slf4j`.
The call `#nop()` leads to a result unfiltered beyond what has been done by the log-level,
and e.g., `#use()` returns a normal instance of `LoggingEventBuilder` to be used in the normal, fluent fashion -
calling `#setMessage(String)`, `#addArgument(Supplier<?>)`, `#setCause(Throwable)` ending in `#log()`.

These lines are somewhat not too intrusive!

It is possible to apply rate-limited logging like this:

```
    IntStream.range(0,11).forEach(index->{
        Slip.of(log.atInfo()).id("3f35",b->b.limit(5)).apply((c,leb)->leb.log("Logging; index {}, suppressed {}, accepted {}, rejected {}.",index,c.suppressed(),c.accepted(),c.rejected()));
        Threads.sleep(Duration.ofMillis(100));
    });
```

Here is said, that no more than five log entries per second should be generated, so the output is this:

```
Logging; index 0, suppressed 0, accepted 1, rejected 0.
Logging; index 1, suppressed 0, accepted 2, rejected 0.
Logging; index 2, suppressed 0, accepted 3, rejected 0.
Logging; index 3, suppressed 0, accepted 4, rejected 0.
Logging; index 4, suppressed 0, accepted 5, rejected 0.
Logging; index 10, suppressed 5, accepted 6, rejected 5.
```

The state information can be obtained in a simpler manner:

```
    IntStream.range(0,21).forEach(index->{
        Slip.of(log.atInfo()).id("12ab",b->b.limit(5)).apply((c,leb)->leb.log("Logging; index {}, state {}.",index,c.state()));
        Threads.sleep(Duration.ofMillis(100));
    });
```

Leading to longer log lines, revealing the internal state of limiting output:

```
Logging; index 0, state Context.State(index=1, rejectCount=0, acceptCount=1, suppressedCount=0, nextSuppressedCount=0).
Logging; index 1, state Context.State(index=2, rejectCount=0, acceptCount=2, suppressedCount=0, nextSuppressedCount=0).
Logging; index 2, state Context.State(index=3, rejectCount=0, acceptCount=3, suppressedCount=0, nextSuppressedCount=0).
Logging; index 3, state Context.State(index=4, rejectCount=0, acceptCount=4, suppressedCount=0, nextSuppressedCount=0).
Logging; index 4, state Context.State(index=5, rejectCount=0, acceptCount=5, suppressedCount=0, nextSuppressedCount=0).
Logging; index 10, state Context.State(index=11, rejectCount=5, acceptCount=6, suppressedCount=5, nextSuppressedCount=0).
Logging; index 11, state Context.State(index=12, rejectCount=5, acceptCount=7, suppressedCount=0, nextSuppressedCount=0).
Logging; index 12, state Context.State(index=13, rejectCount=5, acceptCount=8, suppressedCount=0, nextSuppressedCount=0).
Logging; index 13, state Context.State(index=14, rejectCount=5, acceptCount=9, suppressedCount=0, nextSuppressedCount=0).
Logging; index 14, state Context.State(index=15, rejectCount=5, acceptCount=10, suppressedCount=0, nextSuppressedCount=0).
Logging; index 19, state Context.State(index=20, rejectCount=9, acceptCount=11, suppressedCount=4, nextSuppressedCount=0).
Logging; index 20, state Context.State(index=21, rejectCount=9, acceptCount=12, suppressedCount=0, nextSuppressedCount=0).
```

Out of 21 log statements run, the 12 have been logged while the 9 have been left out.

After statements at index 0, 1, 2, 3, 4 has been logged, the time limit of at most five per second applies,
after which statements at index 5, 6, 7, 8, 9 have been left out and the `suppressCounter` of the statement at index 10 says
"hey, left out was the previous 5 statements!".

Hence, there is a context containing simple statistics about what has happened and what has been suppressed.

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
