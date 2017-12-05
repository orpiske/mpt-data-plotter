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
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class RatePlotter {
    private static final String SERIES_NAME = "Throughput rate";
    private String baseName;

    private int outputWidth = 1200;
    private int outputHeight = 700;
    private boolean plotGridLinesVisible = true;

    public RatePlotter(final String baseName) {
        this.baseName = baseName;
    }

    private XYChart buildCommonChart() {

        // Create Chart
        XYChart chart = new XYChartBuilder()
                .width(outputWidth)
                .height(outputHeight)
                .title("Throughput rate")
                .xAxisTitle("Time")
                .yAxisTitle("Rate")
                .build();

        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));

        Font font = new Font("Verdana", Font.PLAIN, 12);
        chart.getStyler().setBaseFont(font);
        chart.getStyler().setPlotGridLinesVisible(plotGridLinesVisible);

        chart.getStyler().setYAxisTickMarkSpacingHint(15);

        chart.getStyler().setXAxisLabelRotation(45);

        chart.getStyler().setAxisTickMarkLength(15);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);
        chart.getStyler().setDatePattern("yyyy-MM-dd HH:mm:ss");

        chart.getStyler().setChartTitleFont(new Font("Verdana", Font.BOLD, 14));
        chart.getStyler().setLegendFont(new Font("Verdana", Font.PLAIN, 12));
        chart.getStyler().setAxisTitleFont(new Font("Verdana", Font.PLAIN, 12));
        chart.getStyler().setAxisTickLabelsFont(new Font("Verdana", Font.PLAIN, 10));

        return chart;
    }


    private void plotAll(java.util.List<Date> xData, java.util.List<? extends Number> yData) throws IOException {
        // Create Chart
        XYChart chart = buildCommonChart();

        // Series
        XYSeries series = chart.addSeries(SERIES_NAME, xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, baseName + "_rate.png", BitmapEncoder.BitmapFormat.PNG);
    }


    public void plot(java.util.List<Date> xData, List<? extends Number> yData) throws IOException, MptEmptyDataSet {
        if (xData == null || xData.size() == 0) {
            throw new MptEmptyDataSet("The 'X' column data set is empty");
        }

        if (yData == null || yData.size() == 0) {
            throw new MptEmptyDataSet("The 'Y' column data set is empty");
        }

        plotAll(xData, yData);
    }

    public void setOutputWidth(int outputWidth) {
        this.outputWidth = outputWidth;
    }

    public void setOutputHeight(int outputHeight) {
        this.outputHeight = outputHeight;
    }

    public void setPlotGridLinesVisible(boolean plotGridLinesVisible) {
        this.plotGridLinesVisible = plotGridLinesVisible;
    }
}
