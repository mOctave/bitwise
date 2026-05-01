package net.moctave.bitwise.ui.commands;

import java.io.File;
import java.io.IOException;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.OperationCancelledException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.persistence.DataConverter;
import net.moctave.bitwise.persistence.FileManager;
import net.moctave.bitwise.ui.UserInterface;

/** A command which saves the entire state of the computer to a file. */
public class SaveCommand extends Command {
	/**
	 * Creates a new SaveCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public SaveCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			final File file = getUI().seekFile(true);
			final FileManager fm = new FileManager(file);
			fm.writeState(DataConverter.serialize(Computer.getInstance()));
			getUI().showInformation("State saved.");
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
