# Bitwise

Bitwise is a novel RISC simulator designed to provide a stepping stone between simple graphical demos and more complicated technical systems. It provides both a GUI and CLI to allow the user to write, execute, and understand the instructions a simple computer could use to process data. My hope with this project is that it will be a useful aide both to the student and the curious nerd looking to understand computer architecture, assembly, and machine code a little bit better.

## Installation

You can find the latest continuous build of Bitwise [here](https://github.com/mOctave/bitwise/releases/tag/continuous), from where you can download Bitwise as a raw .jar file (good if you have Java 21 or higher already installed in some form), or bundled into a larger platform-specific app with an icon and JRE. The .jar and platform-specific builds are otherwise completely identical in function, so choose whichever one works best for your usecase.

Please note that since I'm too lazy (and cheap) to properly sign my software, MacOS and Windows have both been known to cause issues when you try to open the app. On MacOS, you can follow [the steps that Apple provides](https://support.apple.com/en-ca/guide/mac-help/mh40616/mac) and ignore all the many warnings about how it will infect your computer with the bubonic plague. For Windows, you may have to disable more significant security settings in order to run the app; I hope to be able to fix this soon. I promise I'm not intentionally giving you a virus.

If you'd rather build Bitwise yourself, you can do so following the instructions below.

## Architecture

Bitwise uses an architecture inspired by Y86 but with some notable distinctions. This is intentional, since the program is meant to teach how a computer works at a fundamental level rather than how to write code for a specific real architecture (for which there are already many excellent options available online). For more details about the architecture, including the specific instruction set and machine cycle, see [the architecture documentation](architecture.md).

## Build Instructions

Bitwise uses Apache Maven as a build tool and dependency manager, so it should be quite simple to build your copy of the program. Just make sure you have some distribution of Java JDK 21 installed, plus Maven, and then run

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
