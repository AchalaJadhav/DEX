/*
 * Execute Command class
 * Description - Process Completion
 */
package com.dexbackend.dexbackend.pageanalyzer.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class ExecCommand.
 */
public class ExecCommand {

	
	private int multipleTabsErrorCount=0;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecCommand.class);

	/** The output sem. */
	private Semaphore outputSem;

	/** The output. */
	private String output;

	/** The error sem. */
	private Semaphore errorSem;

	/** The error. */
	private String error;

	/** The p. */
	private Process p;

	/**
	 * The Class InputWriter.
	 */
	private class InputWriter extends Thread {

		/** The input. */
		private String input;

		/**
		 * Instantiates a new input writer.
		 *
		 * @param input the input
		 */
		public InputWriter(String input) {
			this.input = input;
		}

		/**
		 * Run.
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			PrintWriter pw = new PrintWriter(p.getOutputStream());
			pw.println(input);
			pw.flush();
		}
	}

	/**
	 * The Class OutputReader.
	 */
	private class OutputReader extends Thread {

		/**
		 * Instantiates a new output reader.
		 */
		public OutputReader() {
			try {
				outputSem = new Semaphore(1);
				outputSem.acquire();
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

		/**
		 * Run.
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			try {
				StringBuffer readBuffer = new StringBuffer();
				BufferedReader isr = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String buff = new String();
				while ((buff = isr.readLine()) != null) {
					// removing existing timestamp of buff from console
					if (buff.contains("GMT")) {
						LOGGER.info(buff.substring(29, buff.length() - 1));
					} else {
						LOGGER.warn(buff);
						if(buff.contains("You probably have multiple tabs open to the same origin."))
						{
							multipleTabsErrorCount++;
						}
					}
					readBuffer.append(buff);
				}
				output = readBuffer.toString();
				outputSem.release();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * The Class ErrorReader.
	 */
	private class ErrorReader extends Thread {

		/**
		 * Instantiates a new error reader.
		 */
		public ErrorReader() {
			try {
				errorSem = new Semaphore(1);
				errorSem.acquire();
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

		/**
		 * Run.
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			try {
				StringBuffer readBuffer = new StringBuffer();
				BufferedReader isr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				String buff = new String();
				while ((buff = isr.readLine()) != null) {
					// removing existing timestamp of buff from console
					if (buff.contains("GMT")) {
						LOGGER.info(buff.substring(29, buff.length() - 1));
					} else {
						LOGGER.warn(buff);
						if(buff.contains("You probably have multiple tabs open to the same origin."))
						{
							multipleTabsErrorCount++;
						}
					}
					readBuffer.append(buff);
				}
				error = readBuffer.toString();
				errorSem.release();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}

		}
	}

	/**
	 * Instantiates a new exec command.
	 *
	 * @param command the command
	 * @param input   the input
	 */
	public ExecCommand(String command, String input) {
		try {
			p = Runtime.getRuntime().exec(makeArray(command));
			new InputWriter(input).start();
			new OutputReader().start();
			new ErrorReader().start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Instantiates a new exec command.
	 *
	 * @param command the command
	 */
	public ExecCommand(String command) {
		try {
			// p = Runtime.getRuntime().exec(makeArray(command));
			p = new ProcessBuilder(makeArray(command)).start();
			new OutputReader().start();
			new ErrorReader().start();
			p.waitFor(5, TimeUnit.SECONDS);
		} catch (IOException | InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Exec commandfor report genration.
	 *
	 * @param command the command
	 */
	public boolean execCommandforReportGenration(String command) {
		boolean status =false;
		try {
			// p = Runtime.getRuntime().exec(makeArray(command));
			p = new ProcessBuilder(makeArray(command)).start();
			new OutputReader().start();
			new ErrorReader().start();
			p.waitFor();
			status = this.multipleTabsErrorCount != 0 ? false : true;
		} catch (IOException | InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return status;
	}

	/**
	 * Instantiates a new exec command.
	 */
	public ExecCommand() {

	}

	/**
	 * Gets the output.
	 *
	 * @return the output
	 */
	public String getOutput() {
		try {
			outputSem.acquire();
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
		String value = output;
		outputSem.release();
		return value;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		try {
			errorSem.acquire();
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
		String value = error;
		errorSem.release();
		return value;
	}

	/**
	 * Make array.
	 *
	 * @param command the command
	 * @return the string[]
	 */
	private String[] makeArray(String command) {
		ArrayList<String> commandArray = new ArrayList<String>();
		String buff = "";
		boolean lookForEnd = false;
		for (int i = 0; i < command.length(); i++) {
			if (lookForEnd) {
				if (command.charAt(i) == '\"') {
					if (buff.length() > 0)
						commandArray.add(buff);
					buff = "";
					lookForEnd = false;
				} else {
					buff += command.charAt(i);
				}
			} else {
				if (command.charAt(i) == '\"') {
					lookForEnd = true;
				} else if (command.charAt(i) == ' ') {
					if (buff.length() > 0)
						commandArray.add(buff);
					buff = "";
				} else {
					buff += command.charAt(i);
				}
			}
		}
		if (buff.length() > 0)
			commandArray.add(buff);

		String[] array = new String[commandArray.size()];
		for (int i = 0; i < commandArray.size(); i++) {
			array[i] = commandArray.get(i);
		}
//		for(String arrayItem : array)
//		{
//			System.out.println("Array :"+arrayItem);
//		}
		return array;
	}
}