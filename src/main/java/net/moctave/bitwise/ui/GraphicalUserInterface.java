package net.moctave.bitwise.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.exceptions.OperationCancelledException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.model.instructions.Instruction;
import net.moctave.bitwise.ui.commands.*;
import net.moctave.bitwise.ui.guicomponents.ComputerStatePanel;
import net.moctave.bitwise.ui.guicomponents.InstructionPanel;
import net.moctave.bitwise.ui.guicomponents.InstructionPicker;
import net.moctave.bitwise.ui.guicomponents.ToolButton;
import net.moctave.bitwise.utils.Constants;
import net.moctave.bitwise.utils.Conversion;

/** A graphical user interface for the program. */
public class GraphicalUserInterface extends JFrame implements UserInterface {
	// MARK: Fields
	private int selectedIndex;

	private JToolBar menuBar;
	private JPanel mainPanel;

	private JPanel instructionPanel;
	private JScrollPane instructionScroll;

	private JTextArea machineCodePanel;
	private JScrollPane machineCodeScroll;


	private JPanel rightPanel;
	private JPanel statePanel;
	private JTextPane infoPanel;
	private JScrollPane infoScroll;
	private JLabel statusBar;

	// MARK: Constructor
	/**
	 * Creates a new GUI with all its components.
	 */
	public GraphicalUserInterface() {
		selectedIndex = -1;

		initMenuBar();
		initMainPanel();
		setBackground(Constants.COLOR_SILVER);
		setMinimumSize(new Dimension(800, 600));
	}

	// MARK: Methods
	/**
	 * Adds all the necessary buttons to the menu bar.
	 */
	private void initMenuBar() {
		int meta = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
		menuBar = new JToolBar();
		menuBar.add(new ToolButton("append.png", KeyStroke.getKeyStroke("A"), new AppendInstructionCommand(this)));
		menuBar.add(new ToolButton("insert.png", null, new InsertInstructionCommand(this)));
		menuBar.add(new ToolButton("overwrite.png", null, new OverwriteInstructionCommand(this)));
		menuBar.add(new ToolButton("remove.png", KeyStroke.getKeyStroke("X"), new RemoveInstructionCommand(this)));
		menuBar.add(new ToolButton("machine.png", KeyStroke.getKeyStroke("M"), new DisplayMachineCommand(this)));
		menuBar.add(new ToolButton("run.png", KeyStroke.getKeyStroke("R"), new RunAllCommand(this)));
		menuBar.add(new ToolButton("step.png", KeyStroke.getKeyStroke("S"), new StepCommand(this)));
		menuBar.add(new ToolButton("explain.png", null, new ExplainCommand(this)));
		menuBar.add(new ToolButton("reset.png", KeyStroke.getKeyStroke(KeyEvent.VK_R, meta), new ResetCommand(this)));
		menuBar.add(new ToolButton("save.png", KeyStroke.getKeyStroke(KeyEvent.VK_S, meta), new SaveCommand(this)));
		menuBar.add(new ToolButton("load.png", KeyStroke.getKeyStroke(KeyEvent.VK_O, meta), new LoadCommand(this)));
		menuBar.add(new ToolButton("export.png", KeyStroke.getKeyStroke(KeyEvent.VK_E, meta), new ExportCommand(this)));
		menuBar.add(new ToolButton("import.png", KeyStroke.getKeyStroke(KeyEvent.VK_I, meta), new ImportCommand(this)));
		menuBar.add(new ToolButton("quit.png", KeyStroke.getKeyStroke(KeyEvent.VK_Q, meta), new QuitCommand(this)));
		this.add(menuBar, BorderLayout.PAGE_START);
	}


	/**
	 * Sets up the main view.
	 */
	private void initMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		initInstructionPanel();
		initMachineCodePanel();
		initRightPanel();

		this.add(mainPanel, BorderLayout.CENTER);
	}


	/**
	 * Adds the instruction panel to the main view.
	 */
	private void initInstructionPanel() {
		instructionPanel = new JPanel();
		instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));

		instructionScroll = new JScrollPane(instructionPanel);
		instructionScroll.setPreferredSize(new Dimension(200, 500));
		instructionScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		instructionScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		instructionScroll.setBorder(Constants.BORDER_STANDARD);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		mainPanel.add(instructionScroll, constraints);
	}


	/**
	 * Adds the machine code panel to the main view.
	 */
	private void initMachineCodePanel() {
		machineCodePanel = new JTextArea(String.format("Generate machine code%nto see it here."));
		machineCodePanel.setFont(Constants.FONT_MONOSPACED);
		machineCodePanel.setColumns(24);
		machineCodePanel.setEditable(false);

		machineCodeScroll = new JScrollPane(machineCodePanel);
		machineCodeScroll.setPreferredSize(new Dimension(400, 500));
		machineCodeScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		machineCodeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		machineCodeScroll.setBorder(Constants.BORDER_STANDARD);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;

		mainPanel.add(machineCodeScroll, constraints);
	}


	/**
	 * Adds the right panel to the main view.
	 */
	private void initRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(360, 500));
		rightPanel.setLayout(new GridBagLayout());
		rightPanel.setBorder(Constants.BORDER_STANDARD);

		initStatePanel();
		initInfoPanel();
		initStatusBar();

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 2;
		constraints.weightx = 0;
		constraints.weighty = 1;

		mainPanel.add(rightPanel, constraints);
	}


	/**
	 * Adds the computer state panel to the right panel.
	 */
	private void initStatePanel() {
		statePanel = new ComputerStatePanel();

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridy = 0;
		constraints.weighty = 0;

		rightPanel.add(statePanel, constraints);
	}

	/**
	 * Adds the info panel to the right panel.
	 */
	private void initInfoPanel() {
		infoPanel = new JTextPane();
		infoScroll = new JScrollPane(infoPanel);
		infoScroll.setPreferredSize(new Dimension(150, 500));
		infoScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		infoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		infoScroll.setBorder(new EmptyBorder(0, 0, 0, 0));


		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 1;
		constraints.weighty = 1;

		rightPanel.add(infoScroll, constraints);
	}

	/**
	 * Adds the status bar to the right panel.
	 */
	private void initStatusBar() {
		statusBar = new JLabel("[Nothing Going On]", SwingConstants.CENTER);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 2;
		constraints.weighty = 0;

		rightPanel.add(statusBar, constraints);
	}


	@Override
	public void launch() {
		this.setVisible(true);
	}

	@Override
	public int seekInstructionAddress() {
		return selectedIndex;
	}

	@Override
	public Instruction seekInstruction() throws InstructionParseException {
		InstructionPicker picker = new InstructionPicker();
		try {
			return picker.getSelectedInstruction();
		} catch (NumberFormatException e) {
			throw new InstructionParseException("The value you entered for valC was not a valid integer.");
		}
	}

	@Override
	public File seekFile(boolean saveMode) throws OperationCancelledException {
		JFileChooser chooser = new JFileChooser("./data");
		int click = JFileChooser.ERROR_OPTION;
		if (saveMode) {
			click = chooser.showSaveDialog(this);
		} else {
			click = chooser.showOpenDialog(this);
		}

		if (click == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else {
			throw new OperationCancelledException();
		}
	}

	@Override
	public void showAssembly() {
		int totalInstructions = Computer.getInstance().getInstructions().size();
		Component[] components = instructionPanel.getComponents().clone();
		int i = 0;
		for (Component c : components) {
			if (c instanceof InstructionPanel) {
				if (i >= totalInstructions) {
					instructionPanel.remove(c);
				}
				i++;
			} else {
				instructionPanel.remove(c);
			}
		}

		while (i < totalInstructions) {
			instructionPanel.add(new InstructionPanel(i, this));
			i++;
		}

		instructionPanel.add(Box.createVerticalGlue());

		updateAllComponents();
	}


	public void updateAllComponents() {
		for (Component c : instructionPanel.getComponents()) {
			if (c instanceof InstructionPanel) {
				((InstructionPanel) c).update();
			}
		}
		revalidate();
		repaint();
	}

	@Override
	public void showMachineCode() throws LabelNotFoundException {
		List<Byte> bytes = Computer.getInstance().asByteList();
		String rsf = "";
		for (int i = 0; i < bytes.size(); i++) {
			rsf += Conversion.toHexString(bytes.get(i)) + " ";
			if (i % 8 == 7) {
				rsf += System.lineSeparator();
			}
		}
		if (bytes.size() % 8 != 0) {
			rsf += System.lineSeparator();
		}
		machineCodePanel.setText(rsf);
	}

	@Override
	public void showState() {
		updateAllComponents();
	}

	@Override
	public void showInformation(String msg) {
		infoPanel.setText(msg);
	}

	@Override
	public void showOperationSuccess() {
		statusBar.setForeground(Color.BLACK);
		statusBar.setText("Operation successful!");
		revalidate();
		repaint();
	}

	@Override
	public void showOperationFailure() {
		statusBar.setForeground(Constants.COLOR_RED);
		statusBar.setText("Operation cancelled.");
		revalidate();
		repaint();
	}


	/**
	 * Updates the list of instructions in response to a change.
	 */
	@Override
	public void handleInstructionChange() {
		selectedIndex = -1;
		showAssembly();
	}


	// MARK: Getters / Setters
	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
}
