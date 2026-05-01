package net.moctave.bitwise.ui.commands;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.ui.UserInterface;

/** A command which displays the computer's instructions in assembly format. */
public class DisplayAssemblyCommand extends Command {
	/**
	 * Creates a new DisplayAssembluCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public DisplayAssemblyCommand(@NonNull UserInterface ui) {
		super(ui);
	}

	@Override
	public void run() {
		getUI().showAssembly();
	}
}
