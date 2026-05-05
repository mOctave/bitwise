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
	 * 
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
	 * 
	 * @return the text currently in this field
	 */
	public @NonNull String getChoice() {
		return field.getText();
	}
}
