package net.orpiske.mdp.plot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RateDataProcessor implements Processor {
    RateData rateData = new RateData();

    SimpleDateFormat formatter;
    Date last = new Date();
    int count = 0;

    public RateDataProcessor() {
        // 2017-08-05 10:38:23.934129
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void process(final String eta, final String ata) {
        try {
            Date ataDate = formatter.parse(ata);

            if (ataDate.equals(last)) {
                count++;
            }
            else {
                rateData.getRatePeriods().add(ataDate);
                rateData.getRateValues().add(count);

//                System.out.println("Throughput for period " + ataDate + " = " + count);
                count = 0;
                last = ataDate;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public RateData getRateData() {
        return rateData;
    }
}
