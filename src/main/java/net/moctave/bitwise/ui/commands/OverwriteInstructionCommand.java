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

/** A command which overwrites an instruction in the computer's list. */
public class OverwriteInstructionCommand extends Command {
	/**
	 * Creates a new OverwriteInstructionCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public OverwriteInstructionCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			getUI().showInformation("Please choose the address of the instruction to overwrite.");
			final int address = getUI().seekInstructionAddress();
			getUI().showInformation(String.format("Please provide an instruction to insert (Currently %s).%n",
					Computer.getInstance().getInstructions().get(address).toString()));
			final Instruction instruction = getUI().seekInstruction();
			Computer.getInstance().setInstruction(address, instruction);
			getUI().handleInstructionChange();
			getUI().showInformation("Instruction overwritten.");
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
			getUI().showInformation("Instruction overwrite was cancelled.");
			getUI().showOperationFailure();
		}
	}
}
