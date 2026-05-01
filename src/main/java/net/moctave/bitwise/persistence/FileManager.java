package net.moctave.bitwise.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.model.InstructionParser;
import net.moctave.bitwise.model.instructions.Instruction;

/** A wrapper for a file with methods to read or modify its contents as JSON or assembly. */
public class FileManager {
	// MARK: Fields
	private File file;

	// MARK: Constructor
	/**
	 * Creates a new FileManager for the given file.
	 * @param file the file to manage
	 */
	public FileManager(File file) {
		this.file = file;
	}


	// MARK: Methods
	/**
	 * Writes a given JSON object to this FileManager's file.
	 * Overwrites anything already stored there.
	 * @param object the object to write
	 * @throws IOException if the operation fails
	 */
	public void writeState(JSONObject object) throws IOException {
		if (!file.isFile() && (file.exists() || file.isDirectory())) {
			throw new IOException("Attempted to write to an abnormal file (eg, a directory)");
		}

		FileWriter fw = new FileWriter(file, false);
		fw.write(object.toString());
		fw.close();
	}


	/**
	 * Reads this FileManager's file and converts it to a JSON object.
	 * @return the JSON object stored in this file
	 * @throws IOException if the operation fails
	 */
	public JSONObject readState() throws IOException {
		if (!file.exists()) {
			throw new IOException("File does not exist");
		}

		if (!file.isFile()) {
			throw new IOException("Attempted to read from an abnormal file (eg, a directory)");
		}

		Scanner s = new Scanner(file);
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
	 * @param instructions the list of instructions to write
	 * @throws IOException if the operation fails
	 */
	public void writeInstructions(List<Instruction> instructions) throws IOException {
		if (!file.isFile() && (file.exists() || file.isDirectory())) {
			throw new IOException("Attempted to write to an abnormal file (eg, a directory)");
		}

		FileWriter fw = new FileWriter(file, false);
		for (Instruction instruction : instructions) {
			fw.write(instruction.toString());
			fw.write(System.lineSeparator());
		}
		fw.close();
	}


	/**
	 * Reads this FileManager's file and converts it to a list of instructions.
	 * @return the list of instructions stored in this file
	 * @throws IOException if the operation fails
	 */
	public List<Instruction> readInstructions() throws IOException {
		if (!file.exists()) {
			throw new IOException("File does not exist");
		}

		if (!file.isFile()) {
			throw new IOException("Attempted to read from an abnormal file (eg, a directory)");
		}

		List<Instruction> instructions = new ArrayList<>();

		try {
			Scanner s = new Scanner(file);
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
	public File getFile() {
		return file;
	}
}
