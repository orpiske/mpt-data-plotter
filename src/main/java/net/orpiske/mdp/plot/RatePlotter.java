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

import net.orpiske.mdp.plot.exceptions.MptEmptyDataSet;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class RatePlotter extends AbstractRatePlotter {
    private final String baseName;


    public RatePlotter(final String baseName) {
        this.baseName = baseName;
    }

    private void plotAll(final java.util.List<Date> xData, final java.util.List<? extends Number> yData) throws IOException {
        // Create Chart
        XYChart chart = buildCommonChart();

        // Series
        XYSeries series = chart.addSeries(getChartProperties().getSeriesName(), xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, baseName + "_rate.png", BitmapEncoder.BitmapFormat.PNG);
    }


    public void plot(final java.util.List<Date> xData, final List<? extends Number> yData) throws IOException, MptEmptyDataSet {
        if (xData == null || xData.size() == 0) {
            throw new MptEmptyDataSet("The 'X' column data set is empty");
        }

        if (yData == null || yData.size() == 0) {
            throw new MptEmptyDataSet("The 'Y' column data set is empty");
        }

        plotAll(xData, yData);
    }
}
