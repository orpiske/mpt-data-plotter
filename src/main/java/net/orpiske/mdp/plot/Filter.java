package net.orpiske.mdp.plot;

/**
 * Provides an interface that can filter records
 * @param <T>
 */
public interface Filter<T> {

    /**
     * Evaluates whether or not to read the current value based on the ata/atd
     * @param ata The ATA or ATD value
     * @return true if the record should be read or false otherwise
     */
    boolean eval(T ata);
}
