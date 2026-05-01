package net.moctave.bitwise.ui;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.model.InstructionParser;
import net.moctave.bitwise.model.Register;
import net.moctave.bitwise.model.instructions.Instruction;
import net.moctave.bitwise.ui.commands.*;
import net.moctave.bitwise.utils.Conversion;

/** A command-line interface. */
public class CommandLineInterface implements UserInterface {
	private final @NonNull Map<String, Command> commands;
	private final @NonNull Scanner scanner;
	private final @NonNull TextualHelpCommand helpCommand;


	// MARK: Constructor
	/**
	 * Creates a new CLI with an empty command map, a scanner that
	 * reads from {@link System#in}, and a basic helpCommand.
	 */
	public CommandLineInterface() {
		commands = new HashMap<>();
		scanner = new Scanner(System.in);
		helpCommand = new TextualHelpCommand(this);
	}

	// MARK: Methods
	@Override
	public void launch() {
		System.out.println("NOVEL RISC SIMULATOR");
		System.out.println("Enter a command or type \"help\" to see the help menu.");
		initCommandMap();
		while (true) {
			System.out.print(">>> ");
			String command = scanner.nextLine().toLowerCase();
			Command nextCommand = commands.get(command);
			if (nextCommand == null) {
				nextCommand = helpCommand;
			}
			nextCommand.run();
		}
	}


	/**
	 * Maps user-enterable strings to commands.
	 */
	private void initCommandMap() {
		commands.put("instructions", new DisplayAssemblyCommand(this));
		commands.put("machine", new DisplayMachineCommand(this));
		commands.put("append", new AppendInstructionCommand(this));
		commands.put("insert", new InsertInstructionCommand(this));
		commands.put("overwrite", new OverwriteInstructionCommand(this));
		commands.put("remove", new RemoveInstructionCommand(this));
		commands.put("status", new DisplayStateCommand(this));
		commands.put("run", new RunAllCommand(this));
		commands.put("step", new StepCommand(this));
		commands.put("explain", new ExplainCommand(this));
		commands.put("reset", new ResetCommand(this));
		commands.put("save", new SaveCommand(this));
		commands.put("load", new LoadCommand(this));
		commands.put("export", new ExportCommand(this));
		commands.put("import", new ImportCommand(this));
		commands.put("quit", new QuitCommand(this));
	}

	@Override
	public int seekInstructionAddress() {
		return Integer.parseInt(scanner.nextLine()) - 1;
	}

	@Override
	public @NonNull Instruction seekInstruction() throws InstructionParseException {
		return InstructionParser.convertToInstruction(scanner.nextLine());
	}

	@Override
	public @NonNull File seekFile(boolean saveMode) {
		System.out.println("Please enter a file path relative to ./data/:");
		return new File("./data/" + scanner.nextLine());
	}

	@Override
	public void showAssembly() {
		Computer computer = Computer.getInstance();
		for (int i = 0; i < computer.getInstructions().size(); i++) {
			System.out.printf("%-4s %s%n", i + 1, computer.getInstructions().get(i).toString());
		}
	}

	@Override
	public void showMachineCode() throws LabelNotFoundException {
		List<Byte> bytes = Computer.getInstance().asByteList();
		for (int i = 0; i < bytes.size(); i++) {
			System.out.print(Conversion.toHexString(bytes.get(i)) + " ");
			if (i % 16 == 15) {
				System.out.println();
			}
		}
		if (bytes.size() % 16 != 0) {
			System.out.println();
		}
	}

	@Override
	public void showState() {
		Computer computer = Computer.getInstance();
		System.out.println("STATUS:");
		System.out.printf("%s   %s | Flags: %s   %s   %s%n",
				represent(computer.getProgramCounter()), represent(computer.getNextProgramCounter()),
					represent(computer.getFlagZ()), represent(computer.getFlagN()), represent(computer.getFlagO())
		);
		System.out.printf("%s   %s    %s     %s    %s%n",
				represent(computer.getRegA()), represent(computer.getRegB()), represent(computer.getRegWrite()),
					represent(computer.getOpCode()), represent(computer.getFnCode())
		);
		System.out.printf("%s       %s       %s%n",
				represent(computer.getValA()), represent(computer.getValB()),
					represent(computer.getValC()), represent(computer.getValWrite())
		);
		System.out.println("--Registers--");
		Register[] registers = computer.getRegisters();
		for (int i = 0; i < 3; i++) {
			System.out.printf("%s  %s  %s  %s  %s%n",
					represent(registers[i * 5]), represent(registers[i * 5 + 1]), represent(registers[i * 5 + 2]),
						 represent(registers[i * 5 + 3]), represent(registers[i * 5 + 4])
			);
		}
		System.out.println("--------");
	}

	/**
	 * Represents the given register with its label and value string.
	 * @param r the register to represent
	 * @return the labelled representation of the register
	 */
	private static @NonNull String represent(@NonNull Register r) {
		return String.format("%s %s", r.getName(), r.valueAsString());
	}

	@Override
	public void showInformation(@NonNull String msg) {
		System.out.println(msg);
	}

	@Override
	public void showOperationSuccess() {
		System.out.println("[Operation Successful]");
	}

	@Override
	public void showOperationFailure() {
		System.out.println("[Operation Cancelled]");
	}


	/**
	 * Does nothing; the CLI doesn't need to handle instruction changes.
	 */
	@Override
	public void handleInstructionChange() {
		return;
	}
}
