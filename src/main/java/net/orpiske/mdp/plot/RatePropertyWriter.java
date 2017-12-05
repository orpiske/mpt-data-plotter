package net.orpiske.mdp.plot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Writes rate data properties to a file
 */
public class RatePropertyWriter {
    private static final Logger logger = LoggerFactory.getLogger(RatePropertyWriter.class);

    private RatePropertyWriter() {}

    /**
     * Save a summary of the analyzed rate data to a properties file named "rate.properties"
     * @param rateData the rate data to save
     * @param directory the directory where the saved file will be located
     * @throws IOException if unable to save
     */
    public static void write(final RateData<? extends Number> rateData, final File directory) throws IOException {
        logger.trace("Writing properties to {}/rate.properties", directory.getPath());

        Properties prop = new Properties();

        prop.setProperty("rateGeometricMean", Double.toString(rateData.getGeometricMean()));
        prop.setProperty("rateMax", Double.toString(rateData.getMax()));
        prop.setProperty("rateMin", Double.toString(rateData.getMin()));
        prop.setProperty("rateStandardDeviation", Double.toString(rateData.getStandardDeviation()));
        prop.setProperty("rateSamples", Double.toString(rateData.getNumberOfSamples()));

        try (FileOutputStream fos = new FileOutputStream(new File(directory, "rate.properties"))) {
            prop.store(fos, "mpt-data-plotter");
        }
    }


}
