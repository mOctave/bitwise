package net.moctave.bitwise.ui.commands;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.ui.UserInterface;

/** A command which displays the current status of all the computer's registers. */
public class DisplayStateCommand extends Command {
	/**
	 * Creates a new DisplayStateCommand with the given user interface.
	 * @param ui the specific UI this command is linked to
	 */
	public DisplayStateCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		getUI().showState();
	}
}
