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

package net.moctave.bitwise.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.jspecify.annotations.NullMarked;

/** A class holding constants to be used by across multiple parts of the program. */
@NullMarked
public abstract class Constants {
	// MARK: Hex Digits
	/** An array of hex digits used to represent binary numbers. */
	public static final char[] HEX_DIGITS = new char[]{
		'0',
		'1',
		'2',
		'3',
		'4',
		'5',
		'6',
		'7',
		'8',
		'9',
		'A',
		'B',
		'C',
		'D',
		'E',
		'F'
	};


	// MARK: Fonts
	/** A monospaced font. */
	public static final Font FONT_MONOSPACED = new Font("Courier New", Font.PLAIN, 14);
	/** An ordinary sans-serif font. */
	public static final Font FONT_STANDARD = new Font("Arial", Font.PLAIN, 14);
	/** A bold sans-serif font. */
	public static final Font FONT_BOLD = new Font("Arial", Font.BOLD, 14);


	// MARK: Colours
	/** The standard grey for this app. */
	public static final Color COLOR_GREY = new Color(160, 160, 160);
	/** The standard silver for this app. */
	public static final Color COLOR_SILVER = new Color(235, 235, 235);
	/** The standard green for this app. */
	public static final Color COLOR_GREEN = new Color(0, 164, 11);
	/** The standard yellow for this app. */
	public static final Color COLOR_YELLOW = new Color(204, 159, 51);
	/** The standard red for this app. */
	public static final Color COLOR_RED = new Color(191, 64, 64);
	/** The standard blue for this app. */
	public static final Color COLOR_BLUE = new Color(13, 121, 242);

	// MARK: Standard Border
	private static final int INNER = 1;
	private static final int OUTER = 1;
	/** A border to be shared by GUI components. */
	public static final Border BORDER_STANDARD = BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(INNER, INNER, INNER, INNER),
			BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(COLOR_GREY),
					BorderFactory.createEmptyBorder(OUTER, OUTER, OUTER, OUTER)
			)
	);
}
