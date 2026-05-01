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
