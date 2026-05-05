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

/**
 * This package contains all the different commands that any
 * given user interface should be able to act on.
 * 
 * Note that not all of these commands must actually be used
 * by ever user interface; they are technically all supported
 * but a command like {@link TextualHelpCommand} only makes
 * sense for a certain user interface.
 * 
 * @author mOctave
 * @since 1.0
 */
package net.moctave.bitwise.ui.commands;
