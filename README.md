# Bitwise

Bitwise is a novel RISC simulator designed to provide a stepping stone between simple graphical demos and more complicated technical systems. It provides both a GUI and CLI to allow the user to write, execute, and understand the instructions a simple computer could use to process data. My hope with this project is that it will be a useful aide both to the student and the curious nerd looking to understand computer architecture, assembly, and machine code a little bit better.

## Installation

Currently, the only reliable way to install Bitwise is to build it yourself. I'm hoping to improve this soon. Once it's slightly more user friendly, I also plan to add some more documentation about how to actually use it.

## Architecture

Bitwise uses an architecture inspired by Y86 but with some notable distinctions. This is intentional, since the program is meant to teach how a computer works at a fundamental level rather than how to write code for a specific real architecture (for which there are already many excellent options available online). For more details about the architecture, including the specific instruction set and machine cycle, see [the architecture documentation](architecture.md).

## Build Instructions

Bitwise uses Apache Maven as a build tool and dependency manager, so it should be quite simple to build your copy of the program. Simply make sure you have some distribution of Java JDK 21 installed, plus Maven, and then run

```bash
mvn package
```

to create a jar file in your `./target/` directory, or

```bash
mvn exec:java
```

to run the app directly from the command line.

## Contributing

Your help is welcome! If you'd like to contribute to this project, please check out [the contribution guidelines](CONTRIBUTING.md).
