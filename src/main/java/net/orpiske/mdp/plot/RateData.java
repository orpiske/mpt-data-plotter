package net.orpiske.mdp.plot;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RateData {
    private List<Date> ratePeriods = new LinkedList<Date>();
    private List<Integer> rateValues = new LinkedList<Integer>();

    public List<Date> getRatePeriods() {
        return ratePeriods;
    }

    public List<Integer> getRateValues() {
        return rateValues;
    }
}
