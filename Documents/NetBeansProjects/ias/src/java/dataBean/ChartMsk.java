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
import list.Money;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
public class ChartMsk implements Serializable {

    private LineChartModel dateModel;

    @PostConstruct
    public void init() {
        createDateModel();
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    private void createDateModel() {
        List<Money> pay = Cmo.getFilterPayForGraph();
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
        
        //из-за бага все рушится)
        if (pay.size() == 0) {
            series1.set("2015-05-05", 0);
        }

        for (int i = 0; i < pay.size(); i++) {
            series1.set(pay.get(i).getYear().toString(), pay.get(i).getCost());
        }

        dateModel.addSeries(series1);

        dateModel.setTitle("Динамика платежей");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Стоимость");
        DateAxis axis = new DateAxis("Дата");
        axis.setTickAngle(-50);
        axis.setTickFormat("%d, %m, %y");

        dateModel.getAxes().put(AxisType.X, axis);
    }
}

