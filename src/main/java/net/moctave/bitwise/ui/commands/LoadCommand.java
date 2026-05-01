package net.moctave.bitwise.ui.commands;

import java.io.File;
import java.io.IOException;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.exceptions.OperationCancelledException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.persistence.DataConverter;
import net.moctave.bitwise.persistence.FileManager;
import net.moctave.bitwise.ui.UserInterface;

/** A command which loads the entire state of the computer from a file. */
public class LoadCommand extends Command {
	/**
	 * Creates a new LoadCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public LoadCommand(@NonNull UserInterface ui) {
		super(ui);
	}

	@Override
	public void run() {
		try {
			final File file = getUI().seekFile(false);
			final FileManager fm = new FileManager(file);
			Computer.setInstance(DataConverter.deserialize(fm.readState()));
			getUI().handleInstructionChange();
			getUI().showInformation("State loaded.");
			getUI().showOperationSuccess();
		} catch (IOException e) {
			getUI().showInformation("An I/O error was encountered.");
			getUI().showOperationFailure();
		} catch (InstructionParseException e) {
			getUI().showInformation("A malformed instruction was encountered.");
			getUI().showOperationFailure();
		} catch (OperationCancelledException e) {
			getUI().showInformation("File selection was cancelled.");
			getUI().showOperationFailure();
		}
	}
}
