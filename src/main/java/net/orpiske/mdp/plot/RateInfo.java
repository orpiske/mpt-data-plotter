package net.orpiske.mdp.plot;

import java.time.Instant;
import java.util.Date;

/**
 * Rate information for a given period of time
 * @param <T>
 */
class RateInfo<T extends Number> implements Comparable<RateInfo<T>> {
    private Date period;
    private T count;

    public RateInfo(Date period, T count) {
        this.period = period;
        this.count = count;
    }

    public Date getPeriod() {
        return period;
    }

    void setCount(T count) {
        this.count = count;
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
