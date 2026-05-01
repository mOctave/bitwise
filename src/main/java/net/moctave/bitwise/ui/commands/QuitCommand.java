package net.moctave.bitwise.ui.commands;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.ui.UserInterface;

/** A command which immediately calls System.exit() */
public class QuitCommand extends Command {
	/**
	 * Creates a new QuitCommand with the given user interface.
	 * @param ui the specific UI this command is linked to
	 */
	public QuitCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		getUI().showInformation("Shutting down...");
		System.exit(0);
	}
}
