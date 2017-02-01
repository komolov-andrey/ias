/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import list.StreamItem;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
public class ChartHospital implements Serializable {

    private LineChartModel dateModel;

    @PostConstruct
    public void init() {
        createDateModel();
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    private void createDateModel() {
        List<StreamItem> stream = StreamPatient.getVisitsForGraph();
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        //из-за бага все рушится)
        if (stream.size() == 0) {
            series1.set(9, 0);
        }

        for (int i = 0; i < stream.size(); i++) {
            series1.set(Integer.parseInt(stream.get(i).getTime().substring(0, 2)), stream.get(i).getCount());
        }

        dateModel.addSeries(series1);

        dateModel.setTitle("Динамика количества песещений");
        dateModel.getAxis(AxisType.Y).setLabel("Количество песещений");
        dateModel.getAxis(AxisType.X).setLabel("Интервал времени");

        Axis xAxis = dateModel.getAxis(AxisType.X);
        xAxis.setTickFormat("%.0f");
        xAxis.setMin(7);        
        xAxis.setMax(17);
    }
}
