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
      1     22
      2     53
      3     80
      4     135
      5     442
      6     445
      7     623
      8     1433
      9     2179
     10     2375
     11     2701
     12     2810
     13     3000
     14     3100
     15     3215
     16     3389
     17     4369
     18     5040
     19     5672
     20     5985
     21     6443
     22     7200
     23     7201
     24     7220
     25     7231
     26     7232
     27     7285
     28     7287
     29     7288
     30     7289
     31     7291
     32     8005
     33     9100
     34     16992
     35     25672
     36     32017
     37     32018
     38     47001
     39     48825
     40     49052
     41     49664
     42     49665
     43     49666
     44     49667
     45     49668
     46     49676
     47     52103
     48     57086
     49     58599
     50     62045
     51     62069
     52     62411
     53     62522
     54     62722
     55     62861
     56     63342
     57     63421
     58     63423
     59     63424
     60     64529
  2 Address LPCP2563/10.64.141.6:
      1     22
      2     80
      3     135
      4     442
      5     445
      6     623
      7     1433
      8     2179
      9     2701
     10     3000
     11     3100
     12     3389
     13     4369
     14     5040
     15     5672
     16     5985
     17     7200
     18     7201
     19     7220
     20     7231
     21     7232
     22     7285
     23     7287
     24     7288
     25     7289
     26     7291
     27     8005
     28     9100
     29     16992
     30     25672
  3 Address /0.0.0.0:
      1     22
      2     80
      3     135
      4     442
      5     445
      6     623
      7     1433
      8     2179
      9     2701
     10     3000
     11     3100
     12     3389
     13     4369
     14     5040
     15     5672
     16     5985
     17     7200
     18     7201
     19     7220
     20     7231
     21     7232
     22     7285
     23     7287
     24     7288
     25     7289
     26     7291
     27     8005
     28     9100
     29     16992
     30     25672
```

Unique for this is that it scans all 65535 TCP ports in just a few seconds.
This using a combination of virtual threads and reactive programming using `CompletableFuture`.

There are different ways to scan ports locally and remote - the `SocketScanner` does still evolve.

### Example 3: Rate-limited Logging

Coming soon: An example demonstrating rate-limited logging to help manage log verbosity.

```
```

