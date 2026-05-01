package net.moctave.bitwise.ui.guicomponents;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.OperationCancelledException;
import net.moctave.bitwise.model.instructions.*;

/** A modal dialog that allows you to pick an instruction. */
public class InstructionPicker extends JDialog {
	private @NonNull FunctionPicker functionPicker;
	private @NonNull RegisterPicker regAPicker;
	private @NonNull RegisterPicker regBPicker;
	private @NonNull TextPicker labelPicker;
	private @NonNull TextPicker valCPicker;
	private @NonNull JButton okayButton;
	private @NonNull JButton cancelButton;
	private boolean didCancel;

	// MARK: Constructor
	/**
	 * Creates and opens a new modal instruction picker.
	 */
	public InstructionPicker() {
		super();
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		initPickerView();
		getRootPane().setDefaultButton(okayButton);
		didCancel = false;
		setMinimumSize(new Dimension(700, 350));
		setVisible(true);
	}

	// MARK: Methods
	/**
	 * Returns a new instruction based on the values selected by the picker.
	 * @return the instruction chosen by the user
	 * @throws OperationCancelledException if the dialog was closed without choosing an instruction
	 */
	public @NonNull Instruction getSelectedInstruction() throws OperationCancelledException {
		if (didCancel) {
			throw new OperationCancelledException();
		}

		String instructionType = functionPicker.getChoice();
		int regA = regAPicker.getChoice();
		int regB = regBPicker.getChoice();
		String label = labelPicker.getChoice();
		String valC = valCPicker.getChoice();

		return buildInstruction(instructionType, regA, regB, label, valC);
	}

	/**
	 * Creates the instruction of the proper type with the given arguments.
	 * @param instructionType the type of the instruction, as a string
	 * @param regA the regA value of the instruction, bounded on [0, 15]
	 * @param regB the regB value of the instruction, bounded on [0, 15]
	 * @param label the label associated with the instruction
	 * @param valC the valC value of the instruction, unbounded
	 * @return the instruction associated with the chosen arguments
	 */
	private @NonNull Instruction buildInstruction(@NonNull String instructionType, int regA, int regB, String label,
				String valC) {
		switch (instructionType) {
			case "halt": return new HaltInstruction();
			case "move": return new MoveInstruction(regA, Integer.parseInt(valC));
			case "copy": return new CopyInstruction(regA, regB);
			case "add": return new AddInstruction(regA, regB);
			case "sub": return new SubInstruction(regA, regB);
			case "and": return new AndInstruction(regA, regB);
			case "or": return new OrInstruction(regA, regB);
			case "xor": return new XorInstruction(regA, regB);
			case "inc": return new IncInstruction(regA);
			case "neg": return new NegInstruction(regA);
			case "not": return new NotInstruction(regA);
			case "jump": return new JumpAlwaysInstruction(label);
			case "je": return new JumpEqualsInstruction(label);
			case "jle": return new JumpLessEqualsInstruction(label);
			case "jge": return new JumpGreaterEqualsInstruction(label);
			case "jne": return new JumpNotEqualsInstruction(label);
			case "jl": return new JumpLessInstruction(label);
			case "jg": return new JumpGreaterInstruction(label);
			default: return new Label(label);
		}
	}

	/**
	 * Lays out the picker view.
	 */
	private void initPickerView() {
		setMinimumSize(new Dimension(400, 300));

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));

		regAPicker = new RegisterPicker("Register A");
		regBPicker = new RegisterPicker("Register B");
		labelPicker = new TextPicker("Label");
		valCPicker = new TextPicker("Value C");

		initButtons();

		functionPicker = new FunctionPicker("Function", regAPicker, regBPicker, labelPicker, valCPicker);

		add(functionPicker);
		add(regAPicker);
		add(regBPicker);
		JPanel rpane = new JPanel();
		rpane.setLayout(new GridBagLayout());
		rpane.add(labelPicker, makeRightPanelConstraints(0, 0, GridBagConstraints.HORIZONTAL));
		rpane.add(valCPicker, makeRightPanelConstraints(1, 0, GridBagConstraints.HORIZONTAL));
		rpane.add(new JPanel(), makeRightPanelConstraints(2, 1, GridBagConstraints.BOTH));
		rpane.add(okayButton, makeRightPanelConstraints(3, 0, GridBagConstraints.HORIZONTAL));
		rpane.add(cancelButton, makeRightPanelConstraints(4, 0, GridBagConstraints.HORIZONTAL));
		add(rpane);
	}


	/**
	 * Creates the OK and cancel buttons.
	 */
	private void initButtons() {
		okayButton = new JButton("OK");
		okayButton.addActionListener(new OKButtonListener());
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonListener());
	}


	/**
	 * Creates grid constraints for an element on the right panel with the given arguments.
	 * @param gridy the initial gridy value
	 * @param weighty the initial weighty value
	 * @param fill the initial fill value
	 * @return appropraite constraints for an element on the right panel
	 */
	private @NonNull GridBagConstraints makeRightPanelConstraints(int gridy, int weighty, int fill) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = gridy;
		constraints.weightx = 1;
		constraints.weighty = weighty;
		constraints.fill = fill;
		return constraints;
	}



	// MARK: ActionListeners
	/** A listener for the OK button. */
	private class OKButtonListener implements ActionListener {
		/**
		 * Closes the instruction picker.
		 */
		@Override
		public void actionPerformed(@NonNull ActionEvent e) {
			setVisible(false);
		}
	}

	/** A listener for the cancel button. */
	private class CancelButtonListener implements ActionListener {
		/**
		 * Marks that selection was cancelled, then closes the instruction picker.
		 */
		@Override
		public void actionPerformed(@NonNull ActionEvent e) {
			didCancel = true;
			setVisible(false);
		}
	}
}
