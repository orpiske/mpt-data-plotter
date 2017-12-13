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


import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.*;

/**
 * A container for the collected rate information
 * @param <T>
 */
public class RateData<T extends Number> {
    private Set<RateInfo<T>> rateInfos = new TreeSet<>();
    private SummaryStatistics statistics;
    private long errorCount;
    private long skipCount = 0;

    public void add(RateInfo<T> rateInfo) {
        rateInfos.add(rateInfo);
    }


    public List<Date> getRatePeriods() {
        List<Date> list = new LinkedList<>();
        rateInfos.stream().forEach(item->list.add(item.getPeriod()));

        return list;
    }

    public List<Number> getRateValues() {
        List<Number> list = new LinkedList<>();

        rateInfos.stream().forEach(item->list.add(item.getCount()));

        return list;
    }

    private void prepareStatistics() {
        if (statistics == null) {
            List<Number> rateValues = getRateValues();

            // Use Summary Statistics because the data set might be too large
            // and we don't want to abuse memory usage
            statistics = new SummaryStatistics();

            for (Number n : rateValues) {
                statistics.addValue(n.doubleValue());
            }
        }
    }


    public double getGeometricMean() {
        prepareStatistics();

        return statistics.getGeometricMean();
    }

    public double getMax() {
        prepareStatistics();

        return statistics.getMax();
    }

    public double getMin() {
        prepareStatistics();

        return statistics.getMin();
    }

    public double getStandardDeviation() {
        prepareStatistics();

        return statistics.getStandardDeviation();
    }

    public int getNumberOfSamples() {
        return rateInfos.size();
    }

    public long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    public long getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(long skipCount) {
        this.skipCount = skipCount;
    }
}
