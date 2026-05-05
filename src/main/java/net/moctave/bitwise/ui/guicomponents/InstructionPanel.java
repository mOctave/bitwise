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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.ui.GraphicalUserInterface;
import net.moctave.bitwise.utils.Constants;

/** A panel showing a single instruction (in assembly format) and a button to select it. */
public class InstructionPanel extends JPanel {
	/** The user interface this panel is contained in. */
	private final @NonNull GraphicalUserInterface gui;
	/** The index of the instruction this panel should display. */
	private final int index;
	/** The textual display for this panel. */
	private final @NonNull JLabel label;
	/** The button that lets the user select this instruction. */
	private final @NonNull JButton button;

	// MARK: Constructor
	/**
	 * Creates a new instruction panel with a button that reflects the instruction
	 * stored at the given index.
	 * 
	 * @param index an index corresponding to the index of an actual instruction
	 * stored in the computer
	 * @param gui the user interface this panel is a part of
	 */
	public InstructionPanel(int index, @NonNull GraphicalUserInterface gui) {
		this.gui = gui;
		this.index = index;

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		button = new JButton("");
		initButton();

		add(Box.createRigidArea(new Dimension(5, 1)));

		label = new JLabel("Update required!", SwingConstants.LEFT);
		label.setFont(Constants.FONT_MONOSPACED);
		add(label);

		add(Box.createHorizontalGlue());

		setAlignmentX(Component.LEFT_ALIGNMENT);

		setPreferredSize(new Dimension(200, 20));

		setBorder(new EmptyBorder(2, 2, 2, 2));

		update();
	}

	// MARK: Methods
	/**
	 * Updates the contents of the label to reflect its linked instruction.
	 */
	public void update() {
		label.setText(String.format("%-4s %s%n", index + 1,
				Computer.getInstance().getInstructions().get(index).toString()));

		if (gui.getSelectedIndex() == index) {
			button.setBackground(Constants.COLOR_YELLOW);
			button.setForeground(Constants.COLOR_YELLOW);
		} else {
			button.setBackground(Color.WHITE);
			button.setForeground(Color.WHITE);
		}

		revalidate();
		repaint();
	}

	/**
	 * Creates the button for this panel.
	 */
	private void initButton() {
		button.setPreferredSize(new Dimension(15, 15));
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.addActionListener(new SelectButtonListener());
		add(button);
	}

	// MARK: ActionListeners
	/** A listener that selects this instruction. */
	private final class SelectButtonListener implements ActionListener {
		/**
		 * Selects the index of the associated instruction panel.
		 */
		@Override
		public void actionPerformed(@NonNull ActionEvent e) {
			if (gui.getSelectedIndex() == index) {
				gui.setSelectedIndex(-1);
			} else {
				gui.setSelectedIndex(index);
			}
			gui.updateAllComponents();
		}
	}
}
