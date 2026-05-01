package net.moctave.bitwise.ui.commands;

import net.moctave.bitwise.ui.UserInterface;

/** A command that can be executed by either the CLI or GUI. */
public abstract class Command {
	// MARK: Fields
	private final UserInterface ui;


	// MARK: Constructor
	/**
	 * Creates a new command with the given user interface.
	 * @param ui the specific UI this command is linked to
	 */
	public Command(UserInterface ui) {
		this.ui = ui;
	}


	// MARK: Methods
	/**
	 * Runs this command.
	 */
	public abstract void run();


	// MARK: Getters
	public UserInterface getUI() {
		return ui;
	}
}
