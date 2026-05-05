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

import java.io.File;
import java.io.IOException;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.OperationCancelledException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.persistence.DataConverter;
import net.moctave.bitwise.persistence.FileManager;
import net.moctave.bitwise.ui.UserInterface;

/** A command which saves the entire state of the computer to a file. */
public class SaveCommand extends Command {
	/**
	 * Creates a new SaveCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public SaveCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			final File file = getUI().seekFile(true);
			final FileManager fm = new FileManager(file);
			fm.writeState(DataConverter.serialize(Computer.getInstance()));
			getUI().showInformation("State saved.");
			getUI().showOperationSuccess();
		} catch (IOException e) {
			getUI().showInformation("An I/O error was encountered.");
			getUI().showOperationFailure();
		} catch (OperationCancelledException e) {
			getUI().showInformation("File selection was cancelled.");
			getUI().showOperationFailure();
		}
	}
}
