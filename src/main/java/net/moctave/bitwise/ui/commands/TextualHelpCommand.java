package net.moctave.bitwise.ui.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		try (
			InputStream in = getClass().getResourceAsStream("/helptext.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		) {
			final StringBuilder helpText = reader.lines().collect(StringBuilder::new,
					(x, y) -> x.append(System.lineSeparator()).append(y),
					(a, b) -> a.append(System.lineSeparator()).append(b));
			getUI().showInformation(helpText.toString());
			getUI().showOperationSuccess();
		} catch (IOException e) {
			getUI().showInformation("Error! Failed to read help text file.");
			getUI().showOperationFailure();
		}
	}
}
