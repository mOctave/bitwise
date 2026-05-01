package net.moctave.bitwise.ui.commands;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.ui.StepExplainer;
import net.moctave.bitwise.ui.UserInterface;

/** A command which explains the last step of the program. */
public class ExplainCommand extends Command {
	/**
	 * Creates a new ExplainCommand with the given user interface.
	 * 
	 * @param ui the specific UI this command is linked to
	 */
	public ExplainCommand(@NonNull UserInterface ui) {
		super(ui);
	}


	@Override
	public void run() {
		getUI().showInformation(StepExplainer.explainLastStep());
		getUI().showOperationSuccess();
	}
}
