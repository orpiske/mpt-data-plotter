package net.orpiske.mdp.plot;

import java.time.Instant;
import java.util.Date;

/**
 * A date filter
 */
public class DateFilter implements Filter<Date> {

    private Instant cutoffDate;

    public DateFilter(Date cutoffDate) {
        this.cutoffDate = cutoffDate.toInstant();
    }

    @Override
    public boolean eval(Date periodDate) {
        Instant period = periodDate.toInstant();

        if (period.isBefore(cutoffDate)) {
            return false;
        }
        else {
            if (period.isAfter(cutoffDate)) {
                return true;
            }
        }

        return true;
    }
}
