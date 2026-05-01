package net.moctave.bitwise.ui.commands;

import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.ui.UserInterface;

/** A command which blanks all the computer's registers. */
public class ResetCommand extends Command {
	/**
	 * Creates a new ResetCommand with the given user interface.
	 * @param ui the specific UI this command is linked to
	 */
	public ResetCommand(UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		Computer.getInstance().reset();
		getUI().showInformation("The computer's state was reset.");
		getUI().showOperationSuccess();
	}
}
