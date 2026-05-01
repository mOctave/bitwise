package net.moctave.bitwise.ui.commands;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.ui.UserInterface;

/** A command which removes an instruction from the computer's list. */
public class RemoveInstructionCommand extends Command {
	/**
	 * Creates a new RemoveInstructionCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public RemoveInstructionCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			getUI().showInformation("Please choose the address of the instruction to remove.");
			final int address = getUI().seekInstructionAddress();
			Computer.getInstance().removeInstruction(address);
			getUI().handleInstructionChange();
			getUI().showInformation("Instruction removed.");
			getUI().showOperationSuccess();
		} catch (NumberFormatException e) {
			getUI().showInformation("Please enter a positive integer address.");
			getUI().showOperationFailure();
		} catch (IndexOutOfBoundsException e) {
			getUI().showInformation("The address you selected is out of range for the instruction list.");
			getUI().showOperationFailure();
		}
	}
}
