/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.graph;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Combined temperature demo chart.
 */
public class CombinedTemperatureChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Combined temperature";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The average temperature in 2 Greek islands, water temperature and sun shine hours (combined chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Intent execute(Context context) {
    String[] titles = new String[] { " " };
    List<double[]> x = new ArrayList<double[]>();
    for (int i = 0; i < titles.length; i++) {
      x.add(new double[] { 1, 2, 3, 4, 5, 6});
    }
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 4, 6, 5, 3, 4, 5});
  //  values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });
    int[] colors = new int[] { Color.GREEN };
    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    renderer.setPointSize(5.5f);
    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
      r.setLineWidth(5);
      r.setFillPoints(true);
    }
  /*  setChartSettings(renderer, "Weather data", "Month", "Temperature", 0.5, 12.5, 0, 40,
        Color.LTGRAY, Color.LTGRAY);*/
    setChartSettings(renderer, "Drinkers graph", "", "Value", 0, 10, 0, 9,   Color.LTGRAY, Color.LTGRAY);

    renderer.setXLabels(12);
    renderer.setYLabels(10);
    renderer.setShowGrid(true);
    renderer.setXLabelsAlign(Align.RIGHT);
    renderer.setYLabelsAlign(Align.RIGHT);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(false, false);
    renderer.setPanEnabled(false, false);
    
    renderer.setBackgroundColor(Color.BLACK);
    renderer.setYAxisMax(8);
    renderer.setXAxisMax(7);
    
    
    renderer.addXTextLabel(1, "Aroma  ");
	renderer.addXTextLabel(3, "Bitter  ");
	renderer.addXTextLabel(2, "Sweet  ");
	renderer.addXTextLabel(4, "Malt  ");
	renderer.addXTextLabel(5, "Yeast  ");
	renderer.addXTextLabel(6, "Mouth feel  ");
	 renderer.setXLabelsAngle(270);

   /* XYValueSeries sunSeries = new XYValueSeries("Sunshine hours");
    sunSeries.add(1f, 35, 4.3);
    
    sunSeries.add(2f, 35, 4.9);
    sunSeries.add(3f, 35, 5.9);
    sunSeries.add(4f, 35, 8.8);
    sunSeries.add(5f, 35, 10.8);
    sunSeries.add(6f, 35, 11.9);
    sunSeries.add(7f, 35, 13.6);
    sunSeries.add(8f, 35, 12.8);
    sunSeries.add(9f, 35, 11.4);
    sunSeries.add(10f, 35, 9.5);
    sunSeries.add(11f, 35, 7.5);
    sunSeries.add(12f, 35, 5.5);
    XYSeriesRenderer lightRenderer = new XYSeriesRenderer();
    lightRenderer.setColor(Color.YELLOW);*/

    XYSeries waterSeries = new XYSeries("");
    waterSeries.add(1, 0);
    waterSeries.add(2, 0);
    waterSeries.add(3, 0);
    waterSeries.add(4, 0);
    waterSeries.add(5, 0);
    waterSeries.add(6, 0);
   
  
    renderer.setBarSpacing(0.5);
    XYSeriesRenderer waterRenderer = new XYSeriesRenderer();
    waterRenderer.setColor(Color.argb(250, 0, 210, 250));

    XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
   // dataset.addSeries(0, sunSeries);
    dataset.addSeries(0, waterSeries);
  //  renderer.addSeriesRenderer(0, lightRenderer);
    renderer.addSeriesRenderer(0, waterRenderer);
    waterRenderer.setDisplayChartValues(true);
    waterRenderer.setChartValuesTextSize(10);

    String[] types = new String[] { BarChart.TYPE, LineChart.TYPE };
    Intent intent = ChartFactory.getCombinedXYChartIntent(context, dataset, renderer, types,
        "Drinker's parameter");
    return intent;
  }

}
