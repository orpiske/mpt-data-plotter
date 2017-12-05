package net.orpiske.mdp.plot;

import net.orpiske.mdp.plot.exceptions.MptEmptyDataSet;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SimpleTest {
    public RateData<Integer> load(final String fileName) throws IOException {
        RateDataProcessor rateDataProcessor = new RateDataProcessor();
        RateReader rateReader = new RateReader(rateDataProcessor);

        rateReader.read(fileName);

        return rateDataProcessor.getRateData();
    }

    public void plot(final String fileName, final RateData<? extends Number> rateData) throws IOException, MptEmptyDataSet {
        // Removes the gz
        String baseName = FilenameUtils.removeExtension(fileName);

        // Removes the csv
        baseName = FilenameUtils.removeExtension(baseName);

        RatePlotter plotter = new RatePlotter(baseName);

        plotter.plot(rateData.getRatePeriods(), rateData.getRateValues());
    }

    @Test
    public void testPlotReceiverRate() throws Exception {
        String fileName = this.getClass().getResource("receiverd-rate-01.csv.gz").getPath();

        RateData<Integer> rateData = load(fileName);

        assertTrue("Incorrect loaded size for the receiver rate periods", 11 == rateData.getRatePeriods().size());
        assertTrue("Incorrect loaded size for the receiver rate values", 11 == rateData.getRateValues().size());

        plot(fileName, rateData);

        String ratePngFilename = FilenameUtils.removeExtension(FilenameUtils.removeExtension(fileName)) + "_rate.png";

        File outputFile = new File(ratePngFilename);
        assertTrue("Missing output file: " + outputFile, outputFile.exists());
    }

    @Test
    public void testPlotSenderRate() throws Exception {
        String fileName = this.getClass().getResource("senderd-rate-01.csv.gz").getPath();

        RateData<Integer> rateData = load(fileName);

        assertTrue("Incorrect loaded size for the rate periods", 8 == rateData.getRatePeriods().size());
        assertTrue("Incorrect loaded size for the rate values", 8 == rateData.getRateValues().size());

        plot(fileName, rateData);

        String ratePngFilename = FilenameUtils.removeExtension(FilenameUtils.removeExtension(fileName)) + "_rate.png";

        File outputFile = new File(ratePngFilename);
        assertTrue("Missing output file: " + outputFile, outputFile.exists());

        File input = new File(fileName);
        RatePropertyWriter.write(rateData, input.getParentFile());

        File propertiesFile = new File(input.getParentFile(), "rate.properties");
        assertTrue("Missing properties output file: " + propertiesFile, propertiesFile.exists());
    }


    @Test
    public void testPlotOutOrderRate() throws Exception {
        String fileName = this.getClass().getResource("out-of-order-01.csv.gz").getPath();

        RateData<Integer> rateData = load(fileName);

        assertTrue("Incorrect loaded size for the rate periods", 6 == rateData.getRatePeriods().size());
        assertTrue("Incorrect loaded size for the rate values", 6 == rateData.getRateValues().size());

        plot(fileName, rateData);

        List<Date> periods = rateData.getRatePeriods();
        Date last = periods.get(0);
        for (int i = 1; i < periods.size(); i++) {
            Date current = periods.get(i);
            if (current.before(last)) {
                fail("Data ordered incorrectly");
            }
        }

        String ratePngFilename = FilenameUtils.removeExtension(FilenameUtils.removeExtension(fileName)) + "_rate.png";

        File outputFile = new File(ratePngFilename);
        assertTrue("Missing output file: " + outputFile, outputFile.exists());
    }

}
