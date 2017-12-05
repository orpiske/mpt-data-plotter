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


import java.time.Instant;
import java.util.*;

public class RateData<T extends Number> {
    private static class RateInfo<T extends Number> implements Comparable<RateInfo<T>> {
        private Date period;
        private T count;

        public RateInfo(Date period, T count) {
            this.period = period;
            this.count = count;
        }

        public Date getPeriod() {
            return period;
        }

        public T getCount() {
            return count;
        }

        @Override
        public int compareTo(RateInfo<T> rateInfo) {

            Instant current = this.getPeriod().toInstant();
            Instant other = rateInfo.getPeriod().toInstant();

            if (current.isBefore(other)) {
                return -1;
            }
            else {
                if (current.isAfter(other)) {
                    return 1;
                }
            }

            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RateInfo<T> rateInfo = (RateInfo<T>) o;

            return period.equals(rateInfo.period);
        }

        @Override
        public int hashCode() {
            return period.hashCode();
        }
    };

    private Set<RateInfo> rateInfos = new TreeSet<>();
    private SummaryStatistics statistics;

    public void add(T count, Date period) {
        RateInfo<T> ri = new RateInfo<T>(period, count);

        rateInfos.add(ri);
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
}
