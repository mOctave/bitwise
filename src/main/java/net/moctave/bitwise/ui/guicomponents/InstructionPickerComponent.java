// Bitwise - A RISC simulator
// Copyright (C) 2026 mOctave
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

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
	 * 
	 * @param label the label to display above the component
	 */
	public InstructionPickerComponent(@NonNull String label) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final JPanel headerPanel = new JPanel();
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
	/**
	 * Getter for this component's content panel.
	 * 
	 * @return {@link #content}
	 */
	public @NonNull JPanel getContent() {
		return content;
	}
}
