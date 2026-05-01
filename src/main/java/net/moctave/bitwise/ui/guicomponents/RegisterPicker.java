package net.moctave.bitwise.ui.guicomponents;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** A pane with buttons allowing the user to select a register from 1 to 15. */
public class RegisterPicker extends InstructionPickerComponent {
	private int choice = 1;
	private @NonNull JButton[] buttons;

	// MARK: Constructor
	/**
	 * Creates a new register picker with r1 selected and the given label.
	 * 
	 * @param label the label to display above this picker
	 */
	public RegisterPicker(@NonNull String label) {
		super(label);

		getContent().setLayout(new GridLayout(0, 2));

		buttons = new JButton[15];
		for (int i = 1; i <= 15; i++) {
			final JButton button = new JButton(String.format("r%s", Constants.HEX_DIGITS[i]));
			buttons[i - 1] = button;

			button.addActionListener(new RegisterButtonListener(i));
			getContent().add(button);
		}
		setButtonFonts();
	}

	// MARK: Methods
	/**
	 * Bolds the button currently selected, unbolds the rest of the buttons.
	 */
	private void setButtonFonts() {
		for (int k = 0; k < buttons.length; k++) {
			if (k == choice - 1) {
				buttons[k].setFont(Constants.FONT_BOLD);
			} else {
				buttons[k].setFont(Constants.FONT_STANDARD);
			}
		}
	}

	// MARK: Getters
	/**
	 * Getter for this register picker's selected choice.
	 * 
	 * @return {@link #choice}
	 */
	public int getChoice() {
		return choice;
	}



	// MARK: ActionListeners
	/** An action listener for the register selection buttons. */
	private class RegisterButtonListener implements ActionListener {
		private final int index;

		/**
		 * Constructs a new button listener for the given register.
		 * 
		 * @param index the register to be selected when the button is clicked
		 */
		public RegisterButtonListener(int index) {
			this.index = index;
		}

		/**
		 * Sets the chosen register to {@link #index}, turns on/off the appropriate picker panels,
		 * and updates the fonts of the buttons to match the new selection.
		 */
		@Override
		public void actionPerformed(@NonNull ActionEvent e) {
			RegisterPicker.this.choice = index;
			setButtonFonts();
		}
	}
}
