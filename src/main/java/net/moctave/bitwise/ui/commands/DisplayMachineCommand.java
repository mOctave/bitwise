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

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.ui.UserInterface;

/** A command which displays the computer's instructions in machine code format. */
public class DisplayMachineCommand extends Command {
	/**
	 * Creates a new DisplayMachineCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public DisplayMachineCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			getUI().showMachineCode();
			getUI().showInformation("Code compiled.");
			getUI().showOperationSuccess();
		} catch (LabelNotFoundException e) {
			getUI().showInformation("There was an issue resolving your labels when converting to machine code.");
			getUI().showOperationFailure();
		}
	}
}
