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

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.moctave.bitwise.model.instructions.*;

public class TestFileManager {
	private FileManager directoryManager;
	private File testDirectory;

	private JSONObject jsonObject;

	private List<Instruction> instructions;

	@BeforeEach
	public void init() {
		testDirectory = new File("./data/tests");

		directoryManager = new FileManager(testDirectory);

		jsonObject = new JSONObject();
		jsonObject.put("key", "value");
		jsonObject.put("entry", 1);

		instructions = new ArrayList<>();
		instructions.add(new MoveInstruction(1, 2));
		instructions.add(new HaltInstruction());
	}

	@Test
	public void testConstructor() {
		try {
			final File file = File.createTempFile("temp-", ".txt", testDirectory);
			file.deleteOnExit();
			final FileManager fm = new FileManager(file);
			assertEquals(file, fm.getFile());
		} catch (IOException e) {
			fail();
		}
	}

	@Test
	public void testReadWriteStateNormal() {
		try {
			final File fileA = File.createTempFile("temp-", ".txt", testDirectory);
			final File fileB = new File(fileA.getPath());
			fileA.deleteOnExit();

			final FileManager fileManagerA = new FileManager(fileA);
			final FileManager fileManagerB = new FileManager(fileB);

			fileManagerA.writeState(jsonObject);
			fileManagerB.writeState(jsonObject);

			assertEquals(jsonObject.toString(), fileManagerA.readState().toString());
		} catch (IOException e) {
			fail();
		}
	}

	@Test
	public void testWriteStateDirectory() {
		try {
			directoryManager.writeState(jsonObject);
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadStateDirectory() {
		try {
			directoryManager.readState();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}


	@Test
	public void testReadStateNonExistant() {
		try {
			final FileManager fileManagerD = new FileManager(new File("./THIS_FILE_DNE.txt"));
			fileManagerD.readState();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadWriteInstructionsNormal() {
		try {
			final File fileA = File.createTempFile("temp-", ".txt", testDirectory);
			final File fileB = new File(fileA.getPath());
			fileA.deleteOnExit();

			final FileManager fileManagerA = new FileManager(fileA);
			final FileManager fileManagerB = new FileManager(fileB);

			fileManagerA.writeInstructions(instructions);
			fileManagerB.writeInstructions(instructions);

			final List<Instruction> result = fileManagerB.readInstructions();

			assertEquals(instructions.size(), result.size());
			for (int i = 0; i < instructions.size(); i++) {
				assertEquals(instructions.get(i).toString(), result.get(i).toString());
			}
		} catch (IOException e) {
			fail();
		}
	}

	@Test
	public void testWriteInstructionsDirectory() {
		try {
			directoryManager.writeInstructions(instructions);
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadInstructionsDirectory() {
		try {
			directoryManager.readInstructions();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadInstructionsNonExistant() {
		try {
			final FileManager fileManagerD = new FileManager(new File("./THIS_FILE_DNE.txt"));
			fileManagerD.readInstructions();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadBadInstructions() {
		try {
			final File file = File.createTempFile("temp-", ".txt", testDirectory);
			file.deleteOnExit();

			final FileManager fileManager = new FileManager(file);
			final FileWriter fw = new FileWriter(file);
			fw.write("NOT AN INSTRUCTION!");
			fw.close();
			fileManager.readInstructions();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}
}
