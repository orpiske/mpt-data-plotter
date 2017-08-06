package net.orpiske.mdp.plot;



import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class RateReader {
    private static final Logger logger = LoggerFactory.getLogger(RateReader.class);

    private Processor processor;

    public RateReader(Processor processor) {
        this.processor = processor;
    }


    public void read(String filename) throws IOException {
        InputStream fileStream = null;
        InputStream gzipStream = null;
        Reader in = null;

        logger.debug("Reading file {}", filename);

        try {
            fileStream = new FileInputStream(filename);
            gzipStream= new GZIPInputStream(fileStream);

            in = new InputStreamReader(gzipStream);

            Iterable<CSVRecord> records = CSVFormat.RFC4180
                    .withCommentMarker('#')
                    .withFirstRecordAsHeader()
                    .withRecordSeparator(';')
                    .withQuote('"')
                    .withQuoteMode(QuoteMode.NON_NUMERIC)
                    .parse(in);

            for (CSVRecord record : records) {
                processor.process(record.get(0), record.get(1));
            }
        }
        finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(gzipStream);
            IOUtils.closeQuietly(fileStream);
        }
    }
}
