/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import list.VisitItem;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
public class ChartView implements Serializable {

    private LineChartModel dateModel;

    @PostConstruct
    public void init() {
        createDateModel();
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    private void createDateModel() {
        List<VisitItem> visits = FilterVisits.getFilteredVisitsForGraph();
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
        
        //из-за бага все рушится)
        if (visits.size() == 0) {
            series1.set("2015-05-05", 0);
        }

        for (int i = 0; i < visits.size(); i++) {
            series1.set(visits.get(i).getYear().toString(), visits.get(i).getCost());
        }

        dateModel.addSeries(series1);

        dateModel.setTitle("Динамика посещений");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Стоимость");
        DateAxis axis = new DateAxis("Дата");
        axis.setTickAngle(-50);
        axis.setTickFormat("%m, %d, %y");

        dateModel.getAxes().put(AxisType.X, axis);
    }
}
