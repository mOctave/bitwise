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

package net.moctave.bitwise.ui;

import java.io.File;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.model.instructions.Instruction;

/** A general interface that every UI should implement. */
public interface UserInterface {
	// MARK: Methods
	/**
	 * Loads the user interface and starts it running.
	 */
	public abstract void launch();

	/**
	 * Prompts the user to choose an instruction address, and returns the parsed integer.
	 * 
	 * @return an integer chosen by the user
	 * @throws NumberFormatException if user input is unparseable
	 */
	public abstract int seekInstructionAddress();

	/**
	 * Prompts the user to enter an instruction, and returns the parsed instruction.
	 * 
	 * @return an instruction chosen by the user
	 * @throws InstructionParseException if user input is unparseable
	 */
	public abstract @NonNull Instruction seekInstruction() throws InstructionParseException;

	/**
	 * Prompts the user to select a file. If saveMode is enabled, any file will do, otherwise
	 * only files that actually exist should be selectable in implementations of UserInterface that
	 * support file browsing.
	 * 
	 * @param saveMode true if the file will be written to, false if it must be read from
	 * @return a file chosen by the user
	 */
	public abstract @NonNull File seekFile(boolean saveMode);

	/**
	 * Displays the current instructions stored in the computer in assembly format.
	 */
	public abstract void showAssembly();

	/**
	 * Displays the current instructions stored in the computer in assembly format.
	 * 
	 * @throws LabelNotFoundException if instructions cannot be compiled due to
	 * issues with labels
	 */
	public abstract void showMachineCode() throws LabelNotFoundException;

	/**
	 * Displays the current state of the computer.
	 */
	public abstract void showState();

	/**
	 * Displays the given message to the user.
	 * 
	 * @param msg the message to display
	 */
	public abstract void showInformation(@NonNull String msg);

	/**
	 * Logs that the last operation was a success.
	 */
	public abstract void showOperationSuccess();

	/**
	 * Logs that the last operation was a failure.
	 */
	public abstract void showOperationFailure();

	/**
	 * Does any interface-specific tasks that must be done when instructions
	 * are changed.
	 */
	public abstract void handleInstructionChange();
}
