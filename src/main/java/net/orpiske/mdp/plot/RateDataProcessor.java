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
import java.util.HashMap;
import java.util.Map;

public class RateDataProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(RateDataProcessor.class);

    private Map<String, RateInfo> cache = new HashMap<>();
    private SimpleDateFormat formatter;
    private long errorCount = 0;


    public RateDataProcessor() {
        // 2017-08-05 10:38:23.934129
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void process(final String eta, final String ata) {
        final int indexLen = 19;

        try {
            String period = ata.substring(0, indexLen);
            RateInfo rateInfo = cache.get(period);

            if (rateInfo == null) {
                Date ataDate = formatter.parse(ata);

                rateInfo = new RateInfo(ataDate, 1);
                cache.put(period, rateInfo);
            } else {
                int i = rateInfo.getCount();

                i++;
                assert i>=0;
                rateInfo.setCount(i);
            }
        } catch (ParseException e) {
            logger.warn("Error parsing record with values {},{}: {}", eta, ata, e.getMessage());
            errorCount++;
        }
    }

    public RateData getRateData() {
        RateData rateData = new RateData();

        cache.forEach((ratePeriod,rateInfo)->{
            rateData.add(rateInfo);
        });

        rateData.setErrorCount(errorCount);
        return rateData;
    }
}
