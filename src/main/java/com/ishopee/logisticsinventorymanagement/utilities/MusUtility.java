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
public class MusUtility {
    public void generatePieChart(String path, List<Object[]> data) {
        try {
            // Preapare Data Source
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Object[] ob : data)
                dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
            // Create JFreeChart Object
            JFreeChart chart = ChartFactory.createPieChart("MUS TYPE", dataset);
            // Save As Image
            ChartUtils.saveChartAsJPEG(new File(path + "/musTypePie.jpeg"), chart, 300, 300);
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
            JFreeChart chart = ChartFactory.createBarChart("MUS TYPE", "TYPES", "COUNT", dataset);
            // Save As Image
            ChartUtils.saveChartAsJPEG(new File(path + "/musTypeBar.jpeg"), chart, 300, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
