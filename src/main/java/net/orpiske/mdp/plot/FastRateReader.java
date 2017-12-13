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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/**
 * A reader for the rate information
 */
public final class FastRateReader implements RateReader {
    private static final Logger logger = LoggerFactory.getLogger(FastRateReader.class);
    public static final char SEPARATOR = ',';

    private final Processor processor;

    public FastRateReader(Processor processor) {
        this.processor = processor;

        logger.debug("Reading records using the fast rate reader");
    }

    @Override
    public void read(String fileName) throws IOException {
        final Processor processor = this.processor;
        final boolean compressed = fileName.endsWith(".gz");
        final File file = new File(fileName);
        final InputStream inputStream;
        if (compressed) {
            inputStream = new GZIPInputStream(new FileInputStream(file));
        } else {
            inputStream = new FileInputStream(file);
        }
        try (BufferedReader stream = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.US_ASCII))) {
            if (stream.readLine() == null) {
                return;
            }

            long samples = 0;
            String line;
            while ((line = stream.readLine()) != null) {
                final int separatorIndex = line.indexOf(SEPARATOR);
                assert separatorIndex != -1 : "Wrong separator";

                final String start = line.substring(1, separatorIndex - 1);
                final String end = line.substring(separatorIndex + 2, line.length() - 1);

                processor.process(start, end);

                samples++;

            }
        }
    }
}
