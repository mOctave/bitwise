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
