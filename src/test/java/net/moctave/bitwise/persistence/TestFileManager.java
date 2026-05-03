package net.moctave.bitwise.persistence;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.moctave.bitwise.model.instructions.*;

public class TestFileManager {
	private FileManager fileManagerA;
	private File testFileA;

	private FileManager fileManagerB;
	private File testFileB;

	private FileManager fileManagerC;
	private File testFileC;

	private JSONObject jsonObject;

	private List<Instruction> instructions;

	@BeforeEach
	public void init() {
		testFileA = new File("./data/tests/foo.txt");
		testFileB = new File("./data/tests/foo.txt");
		testFileC = new File("./data/tests");
		try {
			Files.deleteIfExists(testFileA.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		
		fileManagerA = new FileManager(testFileA);
		fileManagerB = new FileManager(testFileB);
		fileManagerC = new FileManager(testFileC);

		jsonObject = new JSONObject();
		jsonObject.put("key", "value");
		jsonObject.put("entry", 1);

		instructions = new ArrayList<>();
		instructions.add(new MoveInstruction(1, 2));
		instructions.add(new HaltInstruction());
	}

	@Test
	public void testConstructor() {
		assertEquals(testFileA, fileManagerA.getFile());
	}

	@Test
	public void testReadWriteStateNormal() {
		try {
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
			fileManagerC.writeState(jsonObject);
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadStateDirectory() {
		try {
			fileManagerC.readState();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}


	@Test
	public void testReadStateNonExistant() {
		try {
			FileManager fileManagerD = new FileManager(new File("./THIS_FILE_DNE.txt"));
			fileManagerD.readState();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadWriteInstructionsNormal() {
		try {
			fileManagerA.writeInstructions(instructions);
			fileManagerB.writeInstructions(instructions);
			List<Instruction> result = fileManagerB.readInstructions();
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
			fileManagerC.writeInstructions(instructions);
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadInstructionsDirectory() {
		try {
			fileManagerC.readInstructions();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadInstructionsNonExistant() {
		try {
			FileManager fileManagerD = new FileManager(new File("./THIS_FILE_DNE.txt"));
			fileManagerD.readInstructions();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}

	@Test
	public void testReadBadInstructions() {
		try {
			FileWriter fw = new FileWriter(testFileB);
			fw.write("NOT AN INSTRUCTION!");
			fw.close();
			fileManagerB.readInstructions();
			fail();
		} catch (IOException e) {
			// PASS
		}
	}
}
