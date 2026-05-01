package net.moctave.bitwise.ui.commands;

import java.io.File;
import java.io.IOException;

import net.moctave.bitwise.exceptions.OperationCancelledException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.model.instructions.Instruction;
import net.moctave.bitwise.persistence.FileManager;
import net.moctave.bitwise.ui.UserInterface;

/** A command which imports assembly instructions from a file. */
public class ImportCommand extends Command {
	/**
	 * Creates a new ImportCommand with the given user interface.
	 * @param ui the specific UI this command is linked to
	 */
	public ImportCommand(UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			File file = getUI().seekFile(false);
			FileManager fm = new FileManager(file);

			Computer computer = Computer.getInstance();

			computer.getInstructions().clear();
			for (Instruction instruction : fm.readInstructions()) {
				computer.addInstruction(instruction);
			}

			getUI().handleInstructionChange();
			getUI().showInformation("Instructions imported.");
			getUI().showOperationSuccess();
		} catch (IOException e) {
			getUI().showInformation("An I/O error was encountered.");
			getUI().showOperationFailure();
		} catch (OperationCancelledException e) {
			getUI().showInformation("File selection was cancelled.");
			getUI().showOperationFailure();
		}
	}
}
