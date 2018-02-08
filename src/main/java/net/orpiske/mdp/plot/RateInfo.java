package net.orpiske.mdp.plot;

import java.util.Date;

/**
 * Rate information for a given period of time
 */
class RateInfo implements Comparable<RateInfo>
{
    private Date period;
    private int count;

    public RateInfo(final Date period, int count)
    {
        this.period = period;
        this.count = count;
    }

    public Date getPeriod()
    {
        return period;
    }

    void setCount(int count)
    {
        this.count = count;
    }

    public int getCount()
    {
        return count;
    }

    @Override
    public int compareTo(final RateInfo rateInfo)
    {
        return this.getPeriod().compareTo(rateInfo.getPeriod());
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        RateInfo rateInfo = (RateInfo) o;

        return period.equals(rateInfo.period);
    }

    @Override
    public int hashCode()
    {
        return period.hashCode();
    }
}
