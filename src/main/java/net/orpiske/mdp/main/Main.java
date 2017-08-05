package net.orpiske.mdp.main;


import net.orpiske.mdp.plot.RateData;
import net.orpiske.mdp.plot.RateDataProcessor;
import net.orpiske.mdp.plot.RatePlotter;
import net.orpiske.mdp.plot.RateReader;
import net.orpiske.mdp.utils.Constants;
import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;

import java.util.Date;

public class Main {
    private static CommandLine cmdLine;
    private static Options options;

    private static String fileName;
    private static String seriesName;

    /**
     * Prints the help for the action and exit
     * @param options the options object
     * @param code the exit code
     */
    private static void help(final Options options, int code) {
        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp(Constants.BIN_NAME, options);
        System.exit(code);
    }

    private static void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("f", "file", true, "file to plot");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            help(options, -1);
        }

        if (cmdLine.hasOption("help")) {
            help(options, 0);
        }

        fileName = cmdLine.getOptionValue('f');
        if (fileName == null) {
            help(options, -1);
        }
    }

    public static void main(String[] args) {
        processCommand(args);

        try {
            RateDataProcessor rateDataProcessor = new RateDataProcessor();
            RateReader rateReader = new RateReader(rateDataProcessor);

            rateReader.read(fileName);


            RateData rateData = rateDataProcessor.getRateData();

//            // Plotter
            RatePlotter plotter = new RatePlotter(FilenameUtils.removeExtension(fileName));
            System.out.println("Records to add: " + rateData.getRatePeriods().size());
            for (Date d : rateData.getRatePeriods()) {
                System.out.println("Date = : " + d);
            }

            plotter.plot(rateData.getRatePeriods(), rateData.getRateValues());
//
//            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        System.exit(1);
    }


}
