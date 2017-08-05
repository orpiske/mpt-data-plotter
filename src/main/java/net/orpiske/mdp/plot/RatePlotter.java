package net.orpiske.mdp.plot;

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

public class RatePlotter {
    private static final String SERIES_NAME = "Throughput rate";
    private String baseName;

    public RatePlotter(final String baseName) {
        this.baseName = baseName;
    }

    private XYChart buildCommonChart() {

        // Create Chart
        XYChart chart = new XYChartBuilder()
                .width(1200)
                .height(700)
                .title("Throughput rate")
                .xAxisTitle("Time")
                .yAxisTitle("Rate")
                .build();

        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));

        chart.getStyler().setPlotGridLinesVisible(true);

        chart.getStyler().setYAxisTickMarkSpacingHint(15);

        chart.getStyler().setXAxisLabelRotation(45);

        chart.getStyler().setAxisTickMarkLength(15);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);
        chart.getStyler().setDatePattern("yyyy-MM-dd HH:mm:ss");

        return chart;

    }


    public void plotAll(java.util.List<Date> xData, java.util.List<Integer> yData) throws IOException {

        // Create Chart
        XYChart chart = buildCommonChart();
        
        // Series
        XYSeries series = chart.addSeries(SERIES_NAME, xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.DIAMOND);
        series.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, baseName + "_rate.png", BitmapEncoder.BitmapFormat.PNG);

    }


    public void plot(java.util.List<Date> xData, List<Integer> yData) throws IOException {
        plotAll(xData, yData);
    }
}
