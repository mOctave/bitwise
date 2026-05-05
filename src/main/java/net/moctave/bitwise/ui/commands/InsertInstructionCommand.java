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

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.exceptions.OperationCancelledException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.model.instructions.Instruction;
import net.moctave.bitwise.ui.UserInterface;

/** A command which inserts an instruction into the computer's list. */
public class InsertInstructionCommand extends Command {
	/**
	 * Creates a new InsertInstructionCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public InsertInstructionCommand(@NonNull UserInterface ui) {
		super(ui);
	}

	@Override
	public void run() {
		try {
			getUI().showInformation("Please choose where to insert an instruction.");
			final int address = getUI().seekInstructionAddress();
			getUI().showInformation("Please provide an instruction to insert.");
			final Instruction instruction = getUI().seekInstruction();
			Computer.getInstance().addInstruction(address, instruction);
			getUI().handleInstructionChange();
			getUI().showInformation("Instruction inserted.");
			getUI().showOperationSuccess();
		} catch (NumberFormatException e) {
			getUI().showInformation("Please enter a positive integer address.");
			getUI().showOperationFailure();
		} catch (IndexOutOfBoundsException e) {
			getUI().showInformation("The address you selected is out of range for the instruction list.");
			getUI().showOperationFailure();
		} catch (InstructionParseException e) {
			getUI().showInformation("Instruction parse error: " + e.getMessage());
			getUI().showOperationFailure();
		} catch (OperationCancelledException e) {
			getUI().showInformation("Instruction insertion was cancelled.");
			getUI().showOperationFailure();
		}
	}
}
