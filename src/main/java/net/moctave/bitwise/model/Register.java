package net.moctave.bitwise.model;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** A register capable of storing a value of a certain size. */
public class Register {
    // MARK: Fields
    @NonNull
    private final String name;
    private int value;
    private final int size;



    // MARK: Constructor
    /**
     * Creates a new registre with the given name and apparent size
     * @param name The name associated with this register
     * @param size The apparent size of this register, in bits, between 1 and 32 inclusive
     */
    public Register(String name, int size) {
        this.name = name;
        this.value = 0;
        this.size = size;
    }



    // MARK: Methods
    /**
     * Sets this register to a new value
     * @param value The new value for this register
     */
    public void setValue(int value) {
        this.value = value;
    }



    /**
     * Represents the value stored in the register as a string of hex
     * digits, limited by the apparent size
     * @returns A string representation of the value stored in this register
     */
    @NonNull
    public String valueAsString() {
        String resultSoFar = "";
        int valueRemaining = getValue();
        int parsedBits = 0;

        while (parsedBits + 4 <= getSize()) {
            int hexDigit = valueRemaining & 0xF;
            resultSoFar = Constants.HEX_DIGITS[hexDigit] + resultSoFar;
            valueRemaining = valueRemaining >>> 4;
            parsedBits += 4;
        }

        int bitsLeft = getSize() - parsedBits;
        if (bitsLeft > 0) {
            int hexDigit = valueRemaining & ((int) Math.pow(2, bitsLeft) - 1);
            resultSoFar = Constants.HEX_DIGITS[hexDigit] + resultSoFar;
        }

        return resultSoFar;
    }



    // MARK: Getters
    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }
}
