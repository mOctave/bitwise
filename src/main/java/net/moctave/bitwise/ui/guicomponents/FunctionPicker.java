package net.moctave.bitwise.ui.guicomponents;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** A pane with buttons allowing the user to select a function and opcode. */
public class FunctionPicker extends InstructionPickerComponent {
	private final @NonNull InstructionPickerComponent regAPicker;
	private final @NonNull InstructionPickerComponent regBPicker;
	private final @NonNull InstructionPickerComponent labelPicker;
	private final @NonNull InstructionPickerComponent valCPicker;

	private @NonNull String choice;
	private @NonNull List<JButton> buttons;

	// MARK: Constructor
	/**
	 * Creates a new function picker with the function "halt" selected and the given label.
	 * 
	 * @param label the label to display above this picker
	 * @param regAPicker the component to enable iff an instruction has a regA component
	 * @param regBPicker the component to enable iff an instruction has a regB component
	 * @param labelPicker the component to enable iff an instruction has a label component
	 * @param valCPicker the component to enable iff an instruction has a valC component
	 */
	public FunctionPicker(@NonNull String label, @NonNull InstructionPickerComponent regAPicker,
			@NonNull InstructionPickerComponent regBPicker, @NonNull InstructionPickerComponent labelPicker,
			@NonNull InstructionPickerComponent valCPicker) {
		super(label);
		this.regAPicker = regAPicker;
		this.regBPicker = regBPicker;
		this.labelPicker = labelPicker;
		this.valCPicker = valCPicker;

		choice = "halt";
		regAPicker.setEnabled(false);
		regBPicker.setEnabled(false);
		labelPicker.setEnabled(false);
		valCPicker.setEnabled(false);

		initButtons();
	}

	// MARK: Methods
	/**
	 * Adds this picker's buttons to it.
	 */
	private void initButtons() {
		buttons = new ArrayList<>();
		getContent().setLayout(new GridLayout(0, 2));
		addButton("halt", false, false, false, false);
		addButton("label", false, false, true, false);
		addButton("move", true, false, false, true);
		addButton("copy", true, true, false, false);
		addButton("add", true, true, false, false);
		addButton("sub", true, true, false, false);
		addButton("and", true, true, false, false);
		addButton("or", true, true, false, false);
		addButton("xor", true, true, false, false);
		addButton("inc", true, false, false, false);
		addButton("neg", true, false, false, false);
		addButton("not", true, false, false, false);
		addButton("jump", false, false, true, false);
		addButton("je", false, false, true, false);
		addButton("jle", false, false, true, false);
		addButton("jge", false, false, true, false);
		addButton("jne", false, false, true, false);
		addButton("jl", false, false, true, false);
		addButton("jg", false, false, true, false);
		setButtonFonts();
	}


	/**
	 * Creates a new button to set the instruction type and editability of the various pickers.
	 * 
	 * @param label the label for the button
	 * @param hasA whether the regA selector should be enabled
	 * @param hasB whether the regB selector should be enabled
	 * @param hasLabel whether the label selector should be enabled
	 * @param hasC whether the valC selector should be enabled
	 */
	private void addButton(@NonNull String label, boolean hasA, boolean hasB,
			boolean hasLabel, boolean hasC) {
		final JButton button = new JButton(label);
		buttons.add(button);
		button.addActionListener(new FunctionButtonListener(label, hasA, hasB, hasLabel, hasC));
		getContent().add(button);
	}


	/**
	 * Bolds the button currently selected and unbolds the rest of the buttons.
	 */
	private void setButtonFonts() {
		for (JButton button : buttons) {
			if (choice.equals(button.getText())) {
				button.setFont(Constants.FONT_BOLD);
			} else {
				button.setFont(Constants.FONT_STANDARD);
			}
		}
	}

	// MARK: Getters
	/**
	 * Getter for this function picker's selected choice.
	 * 
	 * @return {@link #choice}
	 */
	public String getChoice() {
		return choice;
	}



	// MARK: ActionListeners
	/** An action listener for the function selection buttons. */
	private class FunctionButtonListener implements ActionListener {
		private final @NonNull String label;
		private final boolean hasA;
		private final boolean hasB;
		private final boolean hasLabel;
		private final boolean hasC;

		/**
		 * Constructs a new button listener with the given parameters.
		 * 
		 * @param label the label for the button
		 * @param hasA whether the regA selector should be enabled
		 * @param hasB whether the regB selector should be enabled
		 * @param hasLabel whether the label selector should be enabled
		 * @param hasC whether the valC selector should be enabled
		 */
		public FunctionButtonListener(@NonNull String label, boolean hasA, boolean hasB,
				boolean hasLabel, boolean hasC) {
			this.label = label;
			this.hasA = hasA;
			this.hasB = hasB;
			this.hasLabel = hasLabel;
			this.hasC = hasC;
		}

		/**
		 * Sets the chosen function to {@link #label}, turns on/off the appropriate picker panels,
		 * and updates the fonts of the buttons to match the new selection.
		 */
		@Override
		public void actionPerformed(@NonNull ActionEvent e) {
			choice = label;
			regAPicker.setEnabled(hasA);
			regBPicker.setEnabled(hasB);
			labelPicker.setEnabled(hasLabel);
			valCPicker.setEnabled(hasC);
			setButtonFonts();
		}
	}
}
