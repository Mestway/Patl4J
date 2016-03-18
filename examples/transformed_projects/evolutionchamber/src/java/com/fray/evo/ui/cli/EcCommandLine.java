package com.fray.evo.ui.cli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.swing.Timer;

import com.fray.evo.EcReportable;
import com.fray.evo.EcState;
import com.fray.evo.EvolutionChamber;
import com.fray.evo.ui.swingx.EcSwingXMain;
import com.fray.evo.util.EcMessages;


public class EcCommandLine {
	public static final EcMessages messages = new EcMessages("com/fray/evo/ui/cli/messages");

	private static String lastDetailed;

	private static String lastYabot;

	private static int threads;

	private static int maxAge;

	private static int timeLimit;

	private static boolean onlyOutputFinal;

	private static boolean printYabot;

	private static InputFile inputFile;

	private static long startTime;

	private static boolean stop = false;

	public static void main(String args[]) throws Exception {
		getArguments(args);

		final EvolutionChamber ec = new EvolutionChamber();
		ec.setDestination(inputFile.getDestination());
		ec.setThreads(threads);
		ec.setReportInterface(new EcReportable() {
			@Override
			public void threadScore(int threadIndex, String output) {
			}

			@Override
			public synchronized void bestScore(EcState finalState, int intValue, String detailedText, String simpleText, String yabotText) {
				boolean isSatisfied = detailedText.contains(EcSwingXMain.messages.getString("Satisfied"));

				if (isSatisfied) {
					lastDetailed = detailedText;
					lastYabot = yabotText;
					if (!onlyOutputFinal) {
						System.out.println(detailedText);
						if (printYabot) {
							System.out.println(yabotText);
						}
					}
				}
			}
		});

		Timer ageTimer = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (maxAge > 0) {
					int oldThreads = 0;
					for (int i = 0; i < ec.getThreads(); i++) {
						int age = ec.getEvolutionsSinceDiscovery(i);
						if (age > maxAge) {
							oldThreads++;
						}
					}
					if (oldThreads == ec.getThreads()) {
						stop = true;
					}
				}

				if (timeLimit > 0) {
					double runningMinutes = (System.currentTimeMillis() - startTime) / 1000.0 / 60.0;
					if (runningMinutes > timeLimit) {
						stop = true;
					}
				}
			}
		});

		startTime = System.currentTimeMillis();
		ec.go();
		ageTimer.start();

		while (!stop) {
			Thread.sleep(200);
		}

		ageTimer.stop();
		ec.stopAllThreads();
		if (lastDetailed == null) {
			System.out.println(messages.getString("cli.noBuild"));
		} else {
			if (onlyOutputFinal) {
				System.out.println(lastDetailed);
				if (printYabot) {
					System.out.println(lastYabot);
				}
			}
		}
	}

	private static void getArguments(String args[]) throws Exception {
		int index;
		
		index = findArg(args, "-l");
		if (index >= 0) {
			Locale lang = new Locale(args[index + 1]);
			EcSwingXMain.messages.changeLocale(lang);
			messages.changeLocale(lang);
		}
		
		if (args.length == 0 || findArg(args, "--help") >= 0) {
			System.out.println(messages.getString("cli.help", EvolutionChamber.VERSION));
			System.exit(0);
		}

		if (findArg(args, "-s") >= 0) {
			BufferedReader in = new BufferedReader(new InputStreamReader(EcCommandLine.class.getResourceAsStream("sample.txt")));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			System.exit(0);
		}

		index = findArg(args, "-t");
		threads = index >= 0 ? Integer.parseInt(args[index + 1]) : Runtime.getRuntime().availableProcessors();

		index = findArg(args, "-a");
		maxAge = index >= 0 ? Integer.parseInt(args[index + 1]) : -1;

		index = findArg(args, "-i");
		timeLimit = index >= 0 ? Integer.parseInt(args[index + 1]) : -1;

		index = findArg(args, "-f");
		onlyOutputFinal = index >= 0 && (maxAge >= 0 || timeLimit >= 0);

		index = findArg(args, "-y");
		printYabot = index >= 0;

		File waypoints = new File(args[args.length - 1]);
		try{
			inputFile = new InputFile(waypoints);
		} catch (UnknownKeywordException e){
			System.out.println(messages.getString("cli.unknownKeywords", e.getKeywords()));
			System.exit(1);
		}
	}
	private static int findArg(String args[], String arg) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(arg)) {
				return i;
			}
		}
		return -1;
	}
}
