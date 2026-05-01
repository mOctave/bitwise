package net.moctave.bitwise.ui.commands;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.exceptions.OperationCancelledException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.model.instructions.Instruction;
import net.moctave.bitwise.ui.UserInterface;

/** A command which appends an instruction to the computer's list. */
public class AppendInstructionCommand extends Command {
	/**
	 * Creates a new AppendInstructionCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public AppendInstructionCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		try {
			getUI().showInformation("Please provide an instruction to append.");
			final Instruction instruction = getUI().seekInstruction();
			Computer.getInstance().addInstruction(instruction);
			getUI().handleInstructionChange();
			getUI().showInformation("Instruction appended.");
			getUI().showOperationSuccess();
		} catch (InstructionParseException e) {
			getUI().showInformation("Instruction parse error: " + e.getMessage());
			getUI().showOperationFailure();
		} catch (OperationCancelledException e) {
			getUI().showInformation("Instruction addition was cancelled.");
			getUI().showOperationFailure();
		}
	}
}
