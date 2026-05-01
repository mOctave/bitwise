package net.moctave.bitwise.ui.commands;

import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.ui.UserInterface;

/** A command which displays the computer's instructions in machine code format. */
public class DisplayMachineCommand extends Command {
	/**
	 * Creates a new DisplayMachineCommand with the given user interface.
	 * @param ui the specific UI this command is linked to
	 */
	public DisplayMachineCommand(UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			getUI().showMachineCode();
			getUI().showInformation("Code compiled.");
			getUI().showOperationSuccess();
		} catch (LabelNotFoundException e) {
			getUI().showInformation("There was an issue resolving your labels when converting to machine code.");
			getUI().showOperationFailure();
		}
	}
}
