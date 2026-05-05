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

package net.moctave.bitwise.ui;

import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

/** The main class that controls the UI and interfaces with the simulated computer. */
public abstract class Main {

	// MARK: Entry Point
	/**
	 * Runs the program.
	 * 
	 * @param args any arguments passed into the program
	 * @throws Exception if an exception occurs during execution
	 */
	public static void main(String[] args) throws Exception {
		final List<String> argList = Arrays.stream(args).map(arg -> arg.toLowerCase()).toList();

		final App app;
		if (argList.contains("--cli")) {
			app = new App(new CommandLineInterface());
		} else {
			app = new App(new GraphicalUserInterface());
		}
		SwingUtilities.invokeLater(app);
	}



	// MARK: App
	/** A specific app instance with a UI. */
	private static class App implements Runnable {
		private final UserInterface ui;

		/**
		 * Creates a new app with the given UI.
		 * 
		 * @param ui the user interface to use for this app
		 */
		public App(UserInterface ui) {
			this.ui = ui;
		}

		/**
		 * Runs this app.
		 */
		@Override
		public void run() {
			ui.launch();
		}
	}
}
