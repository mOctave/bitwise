# Architecture

This document discusses the architecture used by Bitwise. It is loosely based on the Y86 architecture, but intentionally designed to be unique since this program is designed to teach general theory rather than a specific architecture.

## Data Storage

Since memory management is somewhat complicated, this RISC uses a ROM-only approach to store instructions. Actual data is kept in sixteen registers, indicated by the hex characters 1-F and the corresponding binary numbers 0b0001 to 0b1111. Register 0 corresponds to the program counter and can't be manually modified.

To keep things simple and prevent having more restrictions than already imposed by Java, all numbers and memory addresses are stored as 23-bit integers using the default Java `int` primitive type.

There are also a number of special registers and flags that are available for the user to see but not modify:
- `opCode` and `fnCode` will each store a 4-bit instruction code.
- `regA`, `regB`, and `regWrite` will each store a 4-bit pointer to a register.
- `valA`, `valB`, `valC`, and `valWrite` will each store a 32-bit integer value.
- `PC` and `nextPC` will each store a 32-bit pointer to a memory address.
- `Z`, `N`, and `O` will each store boolean values.

## Instruction Set

| Assembly | Machine Code | Bytes | Description |
|---|---|---|---|
| halt | 0x | 0 (1) | Stops program execution, setting program counter to the current adddress. |
| move r0 42 | 1R ## ## ## ## | 5 | Loads the given value into the chosen register. |
| copy r0 r1 | 20 RR | 2 | Copies the value of r1 to r0. |
| add r0 r1 | 21 RR | 2 | Adds the value in r1 to the value in r0. |
| sub r0 r1 | 22 RR | 2 | Subtracts the value in r1 from the value in r0. |
| and r0 r1 | 23 RR | 2 | Performs bitwise AND on r0 with the value in r1. |
| or r0 r1 | 24 RR | 2 | Performs bitwise OR on r0 with the value in r1. |
| xor r0 r1 | 25 RR | 2 | Performs bitwise XOR on r0 with the value in r1. |
| inc r0 | 30 Rx | 2 | Increments the value in r0. |
| neg r0 | 31 Rx | 2 | Performs two's complement on the value in r0. |
| not r0 | 32 Rx | 2 | Performs bitwise NOT on the value in r0. |
| jump 26 | 40 ## ## ## ## | 5 | Unconditional jump to the given address. |
| je 26 | 41 ## ## ## ## | 5 | Jump to the given address if the last result was equal to 0. |
| jle 26 | 42 ## ## ## ## | 5 | Jump to the given address if the last result was less than or equal to 0. |
| jge 26 | 43 ## ## ## ## | 5 | Jump to the given address if the last result was greater than or equal to 0. |
| jne 26 | 44 ## ## ## ## | 5 | Jump to the given address if the last result was not equal to 0. |
| jl 26 | 45 ## ## ## ## | 5 | Jump to the given address if the last result was less than 0. |
| jg 26 | 46 ## ## ## ## | 5 | Jump to the given address if the last result was greater than 0. |
| label: | N/A | 0 | Marks the next instruction so it can be referred to in jump statements. | 

## Processing Rules

### Fetch / Decode

At the beginning of each cycle, the byte at the location specified by the program counter is read. Its first four bits are stored in the special `opCode` register, and the next step depends on the value of this opcode. If a special register other than the PC is not assigned a value, it is blanked.

If the opcode is 0, no further bytes are read. The ALU does not perform any operations, and the program counter is not incremented.

If the opcode is 1, four further bytes are read. The last four bits of the first byte are stored to the special `regA` register, and the remaining four bytes are stored to the `valC` register.

If the opcode is 2, one further byte is read. Each four-bit section is stored as follows: the first into `fnCode`, the second into `regA`, and the third into `regB`. `regWrite` is set to `regA`.

If the opcode is 3, one further byte is read. Two four-bit sections are stored in `fnCode` and `regA` respectively. `regWrite` is set to `regA`.

If the opcode is 4, four further bytes are read. The last four bits of the first byte are stored in `fnCode` and the remaining four bytes in `valC`.

If the opcode is 5 or higher, no further bytes are read and no values are stored.

Now, values are read from each nonzero special register: `regA` to `valA` and `regB` to `valB`. `nextPC` is set to point to the appropriate next instruction assuming no branching occurs.

### ALU
If the opcode is not 2 or 3, no operations are performed. Flags are not reset.

If the opcode is 2, the desired ALU operation is performed on `valA` and `valB` and the result is stored in `valWrite`. Flags `Z` (for zero), `N` (for negative), and `O` (for overflow) may also be set depending on the function and result.

If the opcode is 3, the desired ALU operation is performed on `valA` and the result is stored in `valWrite`. Flags may be set.

### Branching
If the opcode is not 4, no branching occurs. Otherwise, the conditions for branching are checked. If branching is found to be required, `nextPC` is updated to point to the provided address.

### Memory Write
The value in `valWrite` is written to `regWrite`, assuming `regWrite` ≠ 0. `PC` is updated to `nextPC`, and the cycle begins again.

## References

The instruction set and general operations for this RISC are inspired by Kim, S. (n.d.). *Y86-86 Instruction Set Architecture.* Texas A&M University CSCE 312 Term Project Tutorials. Retrieved February 1, 2026, from https://csce312-termproject-tutorial.readthedocs.io/en/latest/isa2.html.
