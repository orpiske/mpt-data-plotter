package net.orpiske.mdp.plot;



import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class RateReader {
    private Processor processor;

    public RateReader(Processor processor) {
        this.processor = processor;
    }



    public RateData read(String filename) throws FileNotFoundException, IOException {
        InputStream fileStream = new FileInputStream(filename);
        InputStream gzipStream = new GZIPInputStream(fileStream);

        Reader in = new InputStreamReader(gzipStream);

        Iterable<CSVRecord> records = CSVFormat.RFC4180
                .withCommentMarker('#')
                .withFirstRecordAsHeader()
                .withRecordSeparator(';')
                .withQuote('"')
                .withQuoteMode(QuoteMode.NON_NUMERIC)
                .parse(in);




        for (CSVRecord record : records) {
            processor.process(record.get(0), record.get(1));
//            String ataStr = record.get(1);
//
//            try {
//                Date ataDate = formatter.parse(ataStr);
//
//                if (ataDate.equals(last)) {
//                    count++;
//                }
//                else {
//                    RateInfo rateInfo = new RateInfo();
//
//                    rateInfo.setDate(ataDate);
//                    rateInfo.setCount(count);
//
//                    System.out.println("Throughput for period " + ataStr + " = " + count);
//                    count = 0;
//                    last = ataDate;
//                }
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//
////            etaList.add();
////            ataList.add();
        }


        return null;
    }
}
