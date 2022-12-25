package com.ishopee.logisticsinventorymanagement.utilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class ShipmentTypeUtility {
    public void generatePieChart(String path, List<Object[]> data) {
        try {
            // Preapare Data Source
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Object[] ob : data)
                dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
            // Create JFreeChart Object
            JFreeChart chart = ChartFactory.createPieChart("SHIPMENT MODE", dataset);
            // Save As Image
            ChartUtils.saveChartAsJPEG(new File(path + "/ShipModePie.jpeg"), chart, 300, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateBarChart(String path, List<Object[]> data) {
        try {
            // Preapare Data Source
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Object[] ob : data)
                dataset.setValue(Double.valueOf(ob[1].toString()), ob[0].toString(), "");
            // Create JFreeChart Object
            JFreeChart chart = ChartFactory.createBarChart("SHIPMENT MODE", "MODES", "COUNT", dataset);
            // Save As Image
            ChartUtils.saveChartAsJPEG(new File(path + "/ShipModeBar.jpeg"), chart, 300, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
