package net.moctave.bitwise.ui.guicomponents;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import org.jspecify.annotations.NonNull;


/** A pane with buttons allowing the user to enter a number or text. */
public class TextPicker extends InstructionPickerComponent {
	private @NonNull JTextField field;

	// MARK: Constructor
	/**
	 * Creates a new text picker with the given label.
	 * @param label the label to display above this picker
	 */
	public TextPicker(@NonNull String label) {
		super(label);
		getContent().setLayout(new BoxLayout(getContent(), BoxLayout.Y_AXIS));


		field = new JTextField();
		getContent().add(field);

		getContent().add(Box.createVerticalGlue());
	}

	// MARK: Methods
	/**
	 * Returns the text entered by the user.
	 * @return the text currently in this field
	 */
	public @NonNull String getChoice() {
		return field.getText();
	}
}
