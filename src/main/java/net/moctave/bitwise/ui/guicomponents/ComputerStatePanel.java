package net.moctave.bitwise.ui.guicomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.model.Register;
import net.moctave.bitwise.utils.Constants;

/** A panel that shows the current state of the computer. */
public class ComputerStatePanel extends JPanel {
	public static final int WIDTH = 300;
	public static final int HEIGHT = 430;
	public static final int LINE_OFFSET = 20;


	public static final int DATA_REG_X = 15;
	public static final int CPU_REG_X1 = 15;
	public static final int CPU_REG_X2 = 135;
	public static final int FLAG_X = WIDTH - 160;
	public static final int FLAG_X_OFFSET = 55;

	public static final int DATA_REG_Y = 75;
	public static final int CPU_REG_Y = DATA_REG_Y + 10 * LINE_OFFSET;
	public static final int FLAG_Y = CPU_REG_Y + 7 * LINE_OFFSET;

	public static final int STANDARD_OFFSET = 20;
	public static final int CONTROL_OFFSET = 80;

	// MARK: Constructor
	/**
	 * Creates a new computer state panel with nothing special about it.
	 */
	public ComputerStatePanel() {
		super();
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
	}

	// MARK: Methods
	/**
	 * Draws the state of all the registers.
	 * @param g the graphics object for this component
	 */
	@Override
	public void paintComponent(@NonNull Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		paintHeaders(g);
		paintRectangles(g);
		paintRegisters(g);
		paintDecorativeElements(g);
	}


	/**
	 * Paints the labels for the various boxes on the panel.
	 * @param g the graphics object for this component
	 */
	private void paintHeaders(@NonNull Graphics g) {
		FontMetrics metrics = g.getFontMetrics(Constants.FONT_BOLD);
		g.setFont(Constants.FONT_BOLD);

		g.drawString("Computer State", WIDTH / 2 - metrics.stringWidth("Computer State") / 2, 20);
		g.drawString("Data Registers", WIDTH / 2 - metrics.stringWidth("Data Registers") / 2, DATA_REG_Y - 25);
		g.drawString("Control Registers", WIDTH / 2 - metrics.stringWidth("Control Registers") / 2, CPU_REG_Y - 25);
		g.drawString("Flags", FLAG_X - 20 - metrics.stringWidth("Flags"), FLAG_Y);

		g.setFont(Constants.FONT_STANDARD);
	}


	/**
	 * Draws the background boxes for the panel.
	 * @param g the graphics object for this component
	 */
	private void paintRectangles(@NonNull Graphics g) {
		// Border
		g.setColor(Constants.COLOR_BLUE);
		g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);

		// Header
		g.setColor(Constants.COLOR_GREEN);
		g.drawLine(10, 30, WIDTH - 10, 30);

		// Box for data registers
		g.drawRect(DATA_REG_X - 5, DATA_REG_Y - 15, WIDTH - 20, 8 * LINE_OFFSET);
		g.drawLine(WIDTH / 2, DATA_REG_Y - 15, WIDTH / 2, DATA_REG_Y + 8 * LINE_OFFSET - 15);


		// Box for CPU registers
		g.drawLine(CPU_REG_X2 - 10, CPU_REG_Y - 15, CPU_REG_X2 - 10, CPU_REG_Y - 15 + 2 * LINE_OFFSET);
		g.drawLine(CPU_REG_X1 - 5, CPU_REG_Y - 15 + 2 * LINE_OFFSET, WIDTH - 10, CPU_REG_Y - 15 + 2 * LINE_OFFSET);
		g.drawRect(CPU_REG_X1 - 5, CPU_REG_Y - 15, WIDTH - CPU_REG_X1 - 5, 6 * LINE_OFFSET);


		// Box for flags

		g.drawRect(FLAG_X - 10, FLAG_Y - 15, WIDTH - FLAG_X, LINE_OFFSET);

		g.setColor(Color.BLACK);
	}


	/**
	 * Renders all the register labels and values on the panel.
	 * @param g the graphics object for this component
	 */
	private void paintRegisters(@NonNull Graphics g) {
		Computer computer = Computer.getInstance();
		for (int i = 0; i < 15; i++) {
			if (i >= 8) {
				paintRegister(g, computer.getRegisters()[i], DATA_REG_X + WIDTH / 2 - 10,
						DATA_REG_Y + LINE_OFFSET * (i - 8), STANDARD_OFFSET);
			} else {
				paintRegister(g, computer.getRegisters()[i], DATA_REG_X, DATA_REG_Y + LINE_OFFSET * i, STANDARD_OFFSET);
			}
		}
		paintRegister(g, computer.getOpCode(), CPU_REG_X1, CPU_REG_Y, CONTROL_OFFSET);
		paintRegister(g, computer.getProgramCounter(), CPU_REG_X2, CPU_REG_Y, CONTROL_OFFSET);
		paintRegister(g, computer.getFnCode(), CPU_REG_X1, CPU_REG_Y + LINE_OFFSET, CONTROL_OFFSET);
		paintRegister(g, computer.getNextProgramCounter(), CPU_REG_X2, CPU_REG_Y + LINE_OFFSET, CONTROL_OFFSET);
		paintRegister(g, computer.getRegA(), CPU_REG_X1, CPU_REG_Y + 2 * LINE_OFFSET, CONTROL_OFFSET);
		paintRegister(g, computer.getValA(), CPU_REG_X2, CPU_REG_Y + 2 * LINE_OFFSET, CONTROL_OFFSET);
		paintRegister(g, computer.getRegB(), CPU_REG_X1, CPU_REG_Y + 3 * LINE_OFFSET, CONTROL_OFFSET);
		paintRegister(g, computer.getValB(), CPU_REG_X2, CPU_REG_Y + 3 * LINE_OFFSET, CONTROL_OFFSET);
		paintRegister(g, computer.getValC(), CPU_REG_X2, CPU_REG_Y + 4 * LINE_OFFSET, CONTROL_OFFSET);
		paintRegister(g, computer.getRegWrite(), CPU_REG_X1, CPU_REG_Y + 5 * LINE_OFFSET, CONTROL_OFFSET);
		paintRegister(g, computer.getValWrite(), CPU_REG_X2, CPU_REG_Y + 5 * LINE_OFFSET, CONTROL_OFFSET);
		paintRegister(g, computer.getFlagZ(), FLAG_X, FLAG_Y, STANDARD_OFFSET);
		paintRegister(g, computer.getFlagN(), FLAG_X + FLAG_X_OFFSET, FLAG_Y, STANDARD_OFFSET);
		paintRegister(g, computer.getFlagO(), FLAG_X + 2 * FLAG_X_OFFSET, FLAG_Y, STANDARD_OFFSET);
	}


	/**
	 * Renders a single register on the panel.
	 * @param g the graphics object for this component
	 */
	private void paintRegister(@NonNull Graphics g, @NonNull Register register, int x, int y, int spacing) {
		g.setFont(Constants.FONT_MONOSPACED);
		g.drawString(register.getName() + ": ", x, y);
		g.drawString(register.valueAsString(), x + spacing, y);
		g.setFont(Constants.FONT_STANDARD);
	}


	/**
	 * Renders decorative elements on the panel.
	 * @param g the graphics object for this component
	 */
	private void paintDecorativeElements(@NonNull Graphics g) {
		g.setColor(Constants.COLOR_GREY);
		g.drawOval(4, 4, 3, 3);
		g.drawOval(WIDTH - 7, 4, 3, 3);
		g.drawOval(4, HEIGHT - 7, 3, 3);
		g.drawOval(WIDTH - 7, HEIGHT - 7, 3, 3);

		g.setColor(Constants.COLOR_BLUE);
		g.drawOval(3, 3, 5, 5);
		g.drawOval(WIDTH - 8, 3, 5, 5);
		g.drawOval(3, HEIGHT - 8, 5, 5);
		g.drawOval(WIDTH - 8, HEIGHT - 8, 5, 5);

		g.setColor(Constants.COLOR_GREY);
		g.drawRect(FLAG_X - 30 * 4, FLAG_Y - 15, 40, LINE_OFFSET);
		for (int i = 20; i < 30; i++) {
			g.drawLine(FLAG_X - i * 4, FLAG_Y - 10, FLAG_X - i * 4, FLAG_Y);
		}

		g.setColor(Constants.COLOR_YELLOW);
		g.drawRect(FLAG_X - 30 * 4 - 1, FLAG_Y - 16, 42, LINE_OFFSET + 2);

		g.setColor(Color.BLACK);
	}
}
