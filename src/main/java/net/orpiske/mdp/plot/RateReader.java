package net.orpiske.mdp.plot;

import java.io.IOException;

/**
 * An interface for reading rate records from a MPT compressed rated file
 */
public interface RateReader {

    /**
     * Reads a mpt compressed CSV rate file
     * @param filename
     * @throws IOException
     */
    void read(final String filename) throws IOException;
}
