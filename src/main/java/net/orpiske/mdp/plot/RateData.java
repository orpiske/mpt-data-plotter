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

import java.time.Instant;
import java.util.*;

public class RateData {
    private static class RateInfo implements Comparable<RateInfo> {
        private Date period;
        private int count;

        public RateInfo(Date period, int count) {
            this.period = period;
            this.count = count;
        }

        public Date getPeriod() {
            return period;
        }

        public int getCount() {
            return count;
        }

        @Override
        public int compareTo(RateInfo rateInfo) {

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

            RateInfo rateInfo = (RateInfo) o;

            return period.equals(rateInfo.period);
        }

        @Override
        public int hashCode() {
            return period.hashCode();
        }
    };

    private Set<RateInfo> rateInfos = new TreeSet<>();

    public void add(Integer count, Date period) {
        RateInfo ri = new RateInfo(period, count);

        rateInfos.add(ri);
    }

    private void add(List<?> ret) {

    }

    public List<Date> getRatePeriods() {
        List<Date> list = new LinkedList<>();
        rateInfos.stream().forEach(item->list.add(item.getPeriod()));

        return list;
    }

    public List<Integer> getRateValues() {
        List<Integer> list = new LinkedList<>();
        rateInfos.stream().forEach(item->list.add(item.getCount()));

        return list;
    }
}
