// Bitwise - A RISC simulator
// Copyright (C) 2026 mOctave
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package net.moctave.bitwise.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.model.InstructionParser;
import net.moctave.bitwise.model.instructions.Instruction;

/** A wrapper for a file with methods to read or modify its contents as JSON or assembly. */
public class FileManager {
	// MARK: Fields
	/** The file this file manager manages. */
	private final @NonNull File file;

	// MARK: Constructor
	/**
	 * Creates a new FileManager for the given file.
	 * 
	 * @param file the file to manage
	 */
	public FileManager(@NonNull File file) {
		this.file = file;
	}


	// MARK: Methods
	/**
	 * Writes a given JSON object to this FileManager's file.
	 * Overwrites anything already stored there.
	 * 
	 * @param object the object to write
	 * @throws IOException if the operation fails
	 */
	public void writeState(@NonNull JSONObject object) throws IOException {
		if (!file.isFile() && (file.exists() || file.isDirectory())) {
			throw new IOException("Attempted to write to an abnormal file (eg, a directory)");
		}

		final FileWriter fw = new FileWriter(file, false);
		fw.write(object.toString());
		fw.close();
	}


	/**
	 * Reads this FileManager's file and converts it to a JSON object.
	 * 
	 * @return the JSON object stored in this file
	 * @throws IOException if the operation fails
	 */
	public @NonNull JSONObject readState() throws IOException {
		if (!file.exists()) {
			throw new IOException("File does not exist");
		}

		if (!file.isFile()) {
			throw new IOException("Attempted to read from an abnormal file (eg, a directory)");
		}

		final Scanner s = new Scanner(file);
		String rsf = "";
		while (s.hasNextLine()) {
			rsf += s.nextLine();
		}
		s.close();

		return new JSONObject(rsf);
	}

	/**
	 * Writes a given list of assembly instructions to this FileManager's file.
	 * Overwrites anything already stored there.
	 * 
	 * @param instructions the list of instructions to write
	 * @throws IOException if the operation fails
	 */
	public void writeInstructions(@NonNull List<Instruction> instructions) throws IOException {
		if (!file.isFile() && (file.exists() || file.isDirectory())) {
			throw new IOException("Attempted to write to an abnormal file (eg, a directory)");
		}

		final FileWriter fw = new FileWriter(file, false);
		for (Instruction instruction : instructions) {
			fw.write(instruction.toString());
			fw.write(System.lineSeparator());
		}
		fw.close();
	}


	/**
	 * Reads this FileManager's file and converts it to a list of instructions.
	 * 
	 * @return the list of instructions stored in this file
	 * @throws IOException if the operation fails
	 */
	public @NonNull List<Instruction> readInstructions() throws IOException {
		if (!file.exists()) {
			throw new IOException("File does not exist");
		}

		if (!file.isFile()) {
			throw new IOException("Attempted to read from an abnormal file (eg, a directory)");
		}

		final List<Instruction> instructions = new ArrayList<>();

		try {
			final Scanner s = new Scanner(file);
			while (s.hasNextLine()) {
				instructions.add(InstructionParser.convertToInstruction(s.nextLine()));
			}
			s.close();
		} catch (InstructionParseException e) {
			throw new IOException();
		}

		return instructions;
	}


	// MARK: Getters
	/**
	 * Getter for this file manager's file.
	 * 
	 * @return {@link #file}
	 */
	public File getFile() {
		return file;
	}
}
