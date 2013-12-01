package fh.kl.wamomu.ui;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.tools.PanListener;
import org.achartengine.tools.Zoom;
import org.achartengine.tools.ZoomEvent;
import org.achartengine.tools.ZoomListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.databaseMeals;
import fh.kl.wamomu.database.databaseMeasurements;
import fh.kl.wamomu.meta.meal;
import fh.kl.wamomu.meta.measurement;

/**
 * Created by Thundernator on 04.11.13.
 */
public class StatistikFragment extends Fragment {


    private GraphicalView chart;
    private FrameLayout fl_chartContainer;

    SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    TimeSeries series;
    TimeSeries series2;
    XYSeries series3;
    Zoom zoomV;
    ZoomListener listener;

        /*
            (todo # Messpunkte momentan nur über den X-Wert clickable, wenns probleme gibt evtl. ändern)
            todo # xy Labels ändern
            todo # Wenn man reinzoomt -> Tage; rauszoomen -> Wochen; weiter Rauszoomen -> Monate auf X-Achse
            todo # Punkte Schwarz färben bzw. je nach Messwert gut -> Grün / schlecht -> Rot
        */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistik,
                container, false);
        getActivity().setTitle("Statistik");

        fl_chartContainer = (FrameLayout) view.findViewById(R.id.chartContainerLineChart_frameLayout);

        series = new TimeSeries("random");
        series2 = new TimeSeries("random2");
        series3 = new XYSeries("random3");

        System.out.println("MEEEEALS: " + databaseMeals.meals.size());
        for (int i = 0; i < databaseMeals.meals.size(); i++){
            System.out.println("MEEEEALS DATE: " + databaseMeals.meals.get(i).getDate());
            System.out.println("MEEEEALS TIME: " + databaseMeals.meals.get(i).getTime());
        }

        System.out.println("MEASUREMEEEEENTS: " + databaseMeasurements.measurements.size());
        for(int i = 0; i <  databaseMeasurements.measurements.size(); i++){
            System.out.println("MEASUREMEEEEENTS DATE: " +  databaseMeasurements.measurements.get(i).getDate());
            System.out.println("MEASUREMEEEEENTS TIME: " +  databaseMeasurements.measurements.get(i).getTime());
        }

        System.out.println("ON CREATE VIEW");
        if (chart == null) {
            chart = ChartFactory.getLineChartView(getActivity(), createDataSet(), createRenderer());
            fl_chartContainer.addView(chart);
            System.out.println("chart PAINTED");


        } else {
            System.out.println("chart REPAINTED");
            chart.repaint();
        }
        // onClick um von Punkten auf die jeweilige Mahlzeit zu verweisen
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeriesSelection  seriesSelection = chart.getCurrentSeriesAndPoint();     // initialisierung clickable area
                if (seriesSelection == null) {
                } else {

                    // display information of the clicked point
                    Toast.makeText(
                            getActivity(),
                            "Data point index " + seriesSelection.getPointIndex()+ " was clicked" + "\n"
                                    + "value X=" + seriesSelection.getXValue() + "\n"
                                    + "value Y=" + seriesSelection.getValue() + " ", Toast.LENGTH_SHORT).show();
                    System.out.println( "Data point index " + seriesSelection.getPointIndex()+ " was clicked"
                            + " value X= " + seriesSelection.getXValue()
                            + " value Y= " + seriesSelection.getValue() + " ");
                }
            }
        });
        chart.addZoomListener(new ZoomListener()
        { SeriesSelection  seriesSelection = chart.getCurrentSeriesAndPoint();     // initialisierung clickable area
            @Override
            public void zoomApplied(ZoomEvent ze)
            {
                System.out.println("Zoom rate " + ze.getZoomRate());
            }


            @Override
            public void zoomReset() {
                System.out.println("Reset");
            }

        },true, true);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("ON PAUSE");
//        meals.clear();
//        measurements.clear();

    }

    private XYMultipleSeriesRenderer createRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        XYSeriesRenderer xySeriesRenderer0 = new XYSeriesRenderer();
        XYSeriesRenderer xySeriesRenderer1 = new XYSeriesRenderer();
        XYSeriesRenderer xySeriesRenderer2 = new XYSeriesRenderer();

        renderer.setAntialiasing(true);
        renderer.setClickEnabled(true);         // clickable machen
        renderer.setSelectableBuffer(30);       // clickable area der punkte
        // title
        // renderer.setChartTitleTextSize(14);     // Titel Größe
        // renderer.setChartTitle(getText(R.string.activity_line_chart_charttitle).toString()); // Titel setzen

        // Achsen
        renderer.setAxisTitleTextSize(50);                      // Schriftgröße Titel an Achsen
        renderer.setLabelsColor(Color.BLACK);                   // Farbe Achsenwerte
        renderer.setLabelsTextSize(35);                         // Schriftgröße Werte an Achsen
        renderer.setXTitle("\n \n \n Datum/Uhrzeit");
        renderer.setYTitle("Messwerte");
        renderer.setXAxisMax( databaseMeasurements.measurements.size() - 1);
        renderer.setXAxisMin(renderer.getXAxisMax() - 5);
        renderer.setYAxisMax(10);
        renderer.setYAxisMin(0);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setAxesColor(Color.BLACK);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setPanEnabled(true, false);
        renderer.setZoomEnabled(true, false);
        double limit[] = {0-0.01,  databaseMeasurements.measurements.size()-0.99, 0, 10}; // minX, maxX, minY, maxY
        renderer.setPanLimits(limit);
        renderer.setZoomLimits(limit);

        renderer.setXLabels(0);             // Standard X-Labels ausblenden
//      renderer.setYLabels(0);             // Standard Y-Labels ausblenden
        renderer.setXLabelsColor(Color.BLACK);      // Farbe X-Labels
        renderer.setYLabelsColor(0, Color.BLACK);   // Farbe Y-Label
        renderer.setXLabelsAngle(0);                // Rotation X-Labels

        // Legende
        renderer.setLegendTextSize(30);
        renderer.setLegendHeight(100);
        renderer.setShowLegend(false);
        // Punkte
        renderer.setPointSize(25f);
        // data area
        renderer.setShowGrid(true);
        renderer.setApplyBackgroundColor(true);
        renderer.setGridColor(Color.DKGRAY);        // Farbe Rasterlinien
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setMargins(new int[]{60, 80, 100, 0});     // Abstand
        renderer.setMarginsColor(Color.WHITE);      // Hintergrundfarbe außerhalb Diagramm

//        xySeriesRenderer0.setPointStyle(null);
        xySeriesRenderer0.setColor(getResources().getColor(R.color.color_line));
        xySeriesRenderer0.setLineWidth(5f);
        xySeriesRenderer0.setFillBelowLine(true);
        xySeriesRenderer0.setFillBelowLineColor(getResources().getColor(R.color.color_belowLine));

        xySeriesRenderer1.setPointStyle(PointStyle.CIRCLE);
        xySeriesRenderer1.setColor(getResources().getColor(R.color.color_bad));
        xySeriesRenderer1.setStroke(BasicStroke.DASHED);
//        xySeriesRenderer1.setLineWidth(5f);
        xySeriesRenderer1.setFillPoints(true);

        xySeriesRenderer2.setPointStyle(PointStyle.CIRCLE);
        xySeriesRenderer2.setColor(getResources().getColor(R.color.color_good));
        xySeriesRenderer2.setStroke(BasicStroke.DASHED);
//        xySeriesRenderer2.setLineWidth(5f);
        xySeriesRenderer2.setFillPoints(true);

//        xySeriesRenderer3.setPointStyle(null);
//        xySeriesRenderer3.setColor(getResources().getColor(R.color.color_good));
//        xySeriesRenderer3.setLineWidth(5f);
        // Daten an die Achse schreiben
        for (int i = 0; i <  databaseMeasurements.measurements.size(); i++) {
          String strDate = sdfDate.format( databaseMeasurements.measurements.get(i).getDate());
          String strTime = sdfTime.format( databaseMeasurements.measurements.get(i).getTime());
            System.out.println("x-label: " + i);
            renderer.addXTextLabel(i, String.valueOf(strDate + "\n" + strTime));        // Datum an X-Achse schreiben
        }

        int j = 0;
        while (j <= 10){
            System.out.println("y-label: " + j);
            renderer.addYTextLabel(j, String.valueOf(j));// Werte an Y-Achse schreiben
            j++;
        }
        renderer.addSeriesRenderer(0, xySeriesRenderer0);
        renderer.addSeriesRenderer(1, xySeriesRenderer1);
        renderer.addSeriesRenderer(2, xySeriesRenderer2);
        return renderer;
    }

    // Daten ins Diagramm schreiben
    private XYMultipleSeriesDataset createDataSet() {

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        double x;
        double y;

        for (int i = 0; i <  databaseMeasurements.measurements.size(); i++) {
            x = i;                                       // Wert X-Achse
            y =  databaseMeasurements.measurements.get(i).getmvalue();    // Wert Y-Achse
            if(4.5<= databaseMeasurements.measurements.get(i).getmvalue()){
                series3.add(x, y);
            }
            else{
                series2.add(x,y);
            }
            series.add(x,y);
        }

        dataSet.addSeries(0,series);
        dataSet.addSeries(1,series2);
        dataSet.addSeries(2,series3);

        return dataSet;
    }
}


