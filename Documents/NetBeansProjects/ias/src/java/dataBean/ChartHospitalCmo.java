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
import list.MoneyOmc;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
public class ChartHospitalCmo implements Serializable {

    private LineChartModel dateModel;
    private int year;
    private String cmo;

    @PostConstruct
    public void init() {
        createDateModel();
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    private void createDateModel() {
        List<MoneyOmc> money = GetMoneyOmc.getMoneyForGraph();
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        //из-за бага все рушится)
        if (money.size() == 0) {
            series1.set(9, 0);
        }

        if ((getCmo() != null) && (getYear() != 0)) {
            for (int i = 0; i < money.size(); i++) {

                if ((money.get(i).getYear() == getYear()) && (money.get(i).getCmo().equals(getCmo()))) {
                    series1.set(money.get(i).getMonth(), money.get(i).getSum());
                }
            }
        } else {
            series1.set(9, 0);
        }

        dateModel.addSeries(series1);

        dateModel.setTitle("Динамика платежей");
        dateModel.getAxis(AxisType.Y).setLabel("Объем финансирования");
        dateModel.getAxis(AxisType.X).setLabel("Месяц");

        Axis xAxis = dateModel.getAxis(AxisType.X);
        xAxis.setTickFormat("%.0f");
        xAxis.setMin(1);
        xAxis.setMax(12);
    }

    public int getYear() {
        return year;
    }

   // public void setYear(String year) {
    //    if (year != null) {
    //        this.year = Integer.parseInt(year);
   //     } else{
   //         this.year = 0;
   //     }
  //  }

    public String getCmo() {
        return cmo;
    }

    public void setCmo(String cmo) {
        this.cmo = cmo;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
