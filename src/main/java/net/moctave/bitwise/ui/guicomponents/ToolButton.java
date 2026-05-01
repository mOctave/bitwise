package net.moctave.bitwise.ui.guicomponents;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.moctave.bitwise.ui.commands.Command;
import net.moctave.bitwise.utils.Constants;

/** A button used in the toolbar of the GUI. */
public class ToolButton extends JButton {
	private final @NonNull Command cmd;

	// MARK: Constructor
	/**
	 * Creates a new tool button with the given command and keybind using the icon at the
	 * selected path relative to ./img/
	 * @param icon the path of the icon for this button
	 * @param keybind a keystroke that can also be used to trigger this button's command, or null
	 * @param cmd the command this button executes when pressed
	 */
	public ToolButton(@NonNull String icon, @Nullable KeyStroke keybind, @NonNull Command cmd) {
		super();
		this.cmd = cmd;

		setBackground(Constants.COLOR_SILVER);
		setBorder(Constants.BORDER_STANDARD);

		try {
			BufferedImage img = ImageIO.read(new File("./img/" + icon));
			setIcon(new ImageIcon(img.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ToolButtonListener action = new ToolButtonListener();

		if (keybind != null) {
			registerKeyboardAction(action, keybind, JComponent.WHEN_IN_FOCUSED_WINDOW);
		}

		addActionListener(action);
	}


	
	// MARK: ActionListeners
	/** A hook to run the command for this button. */
	private class ToolButtonListener extends AbstractAction {
		/**
		 * Runs the command associated with this button.
		 */
		@Override
		public void actionPerformed(@NonNull ActionEvent e) {
			cmd.run();
		}
	}
}
