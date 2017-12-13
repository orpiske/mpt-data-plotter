package net.orpiske.mdp.plot;

import net.orpiske.mdp.plot.exceptions.MptEmptyDataSet;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FilteredTest {
    public RateData<Integer> load(final String fileName) throws IOException {
        DateFilter dateFilter = new DateFilter(new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());
        FilteredRateDataProcessor rateDataProcessor = new FilteredRateDataProcessor(dateFilter);
        DefaultRateReader rateReader = new DefaultRateReader(rateDataProcessor);

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


    /**
     * Test parsing sender rates
     * @throws Exception
     */
    @Test
    public void testPlotSenderRate() throws Exception {
        String fileName = this.getClass().getResource("senderd-rate-invalid.csv.gz").getPath();

        RateData<Integer> rateData = load(fileName);

        List<Date> ratePeriods = rateData.getRatePeriods();
        List<Number> rateValues = rateData.getRateValues();

        assertTrue("Incorrect loaded size for the rate periods: " + ratePeriods.size(), 8 == ratePeriods.size());
        assertTrue("Incorrect loaded size for the rate values " + rateValues.size() , 8 == rateValues.size());
        assertTrue("Incorrect skip record count: " + rateData.getSkipCount(), 5 == rateData.getSkipCount());

        plot(fileName, rateData);

        String ratePngFilename = FilenameUtils.removeExtension(FilenameUtils.removeExtension(fileName)) + "_rate.png";

        File outputFile = new File(ratePngFilename);
        assertTrue("Missing output file: " + outputFile, outputFile.exists());
    }



}
