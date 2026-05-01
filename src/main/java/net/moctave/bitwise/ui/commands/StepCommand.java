package net.moctave.bitwise.ui.commands;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.ui.StepExplainer;
import net.moctave.bitwise.ui.UserInterface;

/** A command which executes a single step of the program. */
public class StepCommand extends Command {
	/**
	 * Creates a new StepCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public StepCommand(@NonNull UserInterface ui) {
		super(ui);
	}

	@Override
	public void run() {
		try {
			Computer.getInstance().step();
			getUI().showState();
			getUI().showInformation(StepExplainer.explainLastStep());
			getUI().showOperationSuccess();
		} catch (LabelNotFoundException e) {
			getUI().showInformation("There was an issue resolving your labels when stepping your program.");
			getUI().showOperationFailure();
		}
	}
}
