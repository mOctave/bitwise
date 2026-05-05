// Bitwise - A RISC simulator
// Copyright (C) 2026 mOctave
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

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
