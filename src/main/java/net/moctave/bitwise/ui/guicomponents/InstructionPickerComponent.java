package net.moctave.bitwise.ui.guicomponents;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** An abstract class providing methods shared by all components of an instrucion picker. */
public abstract class InstructionPickerComponent extends JPanel {
	private @NonNull JLabel header;
	private @NonNull JPanel content;

	// MARK: Constructor
	/**
	 * Creates a new empty panel with the given label and a vertical box layout.
	 * @param label the label to display above the component
	 */
	public InstructionPickerComponent(@NonNull String label) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel headerPanel = new JPanel();
		headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		header = new JLabel(label);
		header.setFont(Constants.FONT_BOLD);
		headerPanel.add(header);
		add(headerPanel);

		content = new JPanel();
		add(content);

		add(Box.createVerticalGlue());
	}

	// MARK: Methods
	/**
	 * Enables or disables every button or text box in this picker.
	 */
	@Override
	public void setEnabled(boolean active) {
		header.setEnabled(active);

		for (Component c : getContent().getComponents()) {
			c.setEnabled(active);
		}
	}

	// MARK: Getters
	public @NonNull JPanel getContent() {
		return content;
	}
}
