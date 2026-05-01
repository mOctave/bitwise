package net.moctave.bitwise.ui.commands;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.ui.UserInterface;

/** A command which runs the entire program. */
public class RunAllCommand extends Command {
	/**
	 * Creates a new RunAllCommand with the given user interface.
	 * @param ui the specific UI this command is linked to
	 */
	public RunAllCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			Computer.getInstance().runAll();
			getUI().showState();
			getUI().showInformation("Program run.");
			getUI().showOperationSuccess();
		} catch (LabelNotFoundException e) {
			getUI().showInformation("There was an issue resolving your labels when running your program.");
			getUI().showOperationFailure();
		}
	}
}
