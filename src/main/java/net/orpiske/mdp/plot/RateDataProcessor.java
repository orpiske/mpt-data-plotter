/*
 *  Copyright 2017 Otavio Rodolfo Piske
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.orpiske.mdp.plot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("ALL")
public class RateDataProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(RateDataProcessor.class);

    private RateData rateData = new RateData();
    private SimpleDateFormat formatter;
    private Date last = new Date();
    private int count = 0;

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
                rateData.add(count, ataDate);

                logger.debug("Throughput for period {} = {}", ataDate, count);

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
