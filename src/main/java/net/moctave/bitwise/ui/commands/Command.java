package net.moctave.bitwise.ui.commands;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.ui.UserInterface;

/** A command that can be executed by either the CLI or GUI. */
public abstract class Command {
	// MARK: Fields
	private final @NonNull UserInterface ui;


	// MARK: Constructor
	/**
	 * Creates a new command with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public Command(@NonNull UserInterface ui) {
		this.ui = ui;
	}


	// MARK: Methods
	/**
	 * Runs this command.
	 */
	public abstract void run();


	// MARK: Getters
	/**
	 * Getter for this command instance's user interface.
	 * 
	 * @return {@link #ui}
	 */
	public @NonNull UserInterface getUI() {
		return ui;
	}
}
