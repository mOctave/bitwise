package net.moctave.bitwise.ui.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.ui.UserInterface;

/** A command which displays a help menu for the CLI. */
public class TextualHelpCommand extends Command {
	/**
	 * Creates a new TextualHelpCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public TextualHelpCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			final String helpText = Files.readString(new File("./strings/helptext.txt").toPath());
			getUI().showInformation(helpText);
			getUI().showOperationSuccess();
		} catch (IOException e) {
			getUI().showInformation("Error! Failed to read help text file.");
			getUI().showOperationFailure();
		}
	}
}
