package fh.kl.wamomu.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
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
    MeasurementFragment mf = new MeasurementFragment();

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
//                    Toast.makeText(
//                            getActivity(),
//                            "Data point index " + seriesSelection.getPointIndex()+ " was clicked" + "\n"
//                                    + "value X=" + seriesSelection.getXValue() + "\n"
//                                    + "value Y=" + seriesSelection.getValue() + " ", Toast.LENGTH_SHORT).show();

//                    mf.overview_listview.smoothScrollToPosition((int)seriesSelection.getXValue());
                    System.out.println( "Data point index " + seriesSelection.getPointIndex()+ " was clicked"
                            + " value X= " + seriesSelection.getXValue()
                            + " value Y= " + seriesSelection.getValue() + " ");
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    mf.setSfItem((int) seriesSelection.getXValue());
                    ft.replace(R.id.fl_content_frame, mf);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
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
        renderer.setSelectableBuffer(30);       // clickable bereich der punkte
        // title
        // renderer.setChartTitleTextSize(14);     // Titel Größe
        // renderer.setChartTitle(getText(R.string.activity_line_chart_charttitle).toString()); // Titel setzen

        // Achsen
        renderer.setAxisTitleTextSize(50);              // Schriftgröße Titel an Achsen
        renderer.setLabelsTextSize(35);                 // Schriftgröße Werte an Achsen
        renderer.setLabelsColor(Color.BLACK);           // Farbe Achsenwerte
        renderer.setXTitle("\n \n \n Datum/Uhrzeit");   // Überschrift X-Achse
        renderer.setYTitle("Messwerte");                // Überschrift Y-Achse
        renderer.setXAxisMax( databaseMeasurements.measurements.size() - 1);// Anzeigebreich maximum Wert X-Achse
        renderer.setXAxisMin(renderer.getXAxisMax() - 5);                   // Anzeigebreich minimum Wert X-Achse
        renderer.setYAxisMax(10);                                           // Anzeigebereich maximum Wert Y-Achse
        renderer.setYAxisMin(0);                                            // Anzeigebereich minimum Wert Y-Achse
        renderer.setYLabelsAlign(Paint.Align.RIGHT);                        // Ausrichtung Y-Achsenbezeichnung
        renderer.setAxesColor(Color.BLACK);                                 // Farbe der X- und Y-Achsen
        renderer.setLabelsColor(Color.BLACK);                               // Farbe der Labels Überschriften
        renderer.setPanEnabled(true, false);                                // Scrollen auf X- oder Y-Achse
        renderer.setZoomEnabled(true, false);                               // Zoom auf X- oder Y-Achse
        double limit[] = {0-0.05,  databaseMeasurements.measurements.size()-0.7, 0, 10}; // Werte zur Limitierung des Scroll- und Zoombereichs; minX, maxX, minY, maxY
        renderer.setPanLimits(limit);                       // Limitierung des Scrollbereichs
        renderer.setZoomLimits(limit);                      // Limitierung des Zooms

        renderer.setXLabels(0);                     // Standard X-Labels ausblenden
//      renderer.setYLabels(0);                     // Standard Y-Labels ausblenden
        renderer.setXLabelsColor(Color.BLACK);      // Farbe X-Labels
        renderer.setYLabelsColor(0, Color.BLACK);   // Farbe Y-Label
        renderer.setXLabelsAngle(0);                // Rotation X-Labels

        // Legende
        renderer.setShowLegend(false);              // Anzeigen der Legende
//        renderer.setLegendTextSize(30);           // Textgröße
//        renderer.setLegendHeight(10);             // Höhe

        // Datenbereich
        renderer.setShowGrid(true);                     // Rasterlinie anzeigen
        renderer.setApplyBackgroundColor(true);         // Hintergrundfarbe Datenbereich aktivieren
        renderer.setGridColor(Color.DKGRAY);            // Farbe Rasterlinien
        renderer.setBackgroundColor(Color.WHITE);       // Hintergrundfarbe Datenbereich
        renderer.setMargins(new int[]{30, 80, 50, 0});  // Abstand von Oben, Links, Unten, Rechts
        renderer.setMarginsColor(Color.WHITE);          // Hintergrundfarbe außerhalb Diagramm

        // Punkte
        renderer.setPointSize(25f);                     // Größe der Punkte

//        Linienfarbe zwischen den Messpunkten und Farbfüllung unter den Messwerten
//        xySeriesRenderer0.setPointStyle(null);
        xySeriesRenderer0.setColor(getResources().getColor(R.color.color_line));                    // Farbe der Linie
        xySeriesRenderer0.setLineWidth(5f);                                                         // Dicke der Linie
        xySeriesRenderer0.setFillBelowLine(true);                                                   // Füllung des Bereiches unter der zu sehenden Linie
        xySeriesRenderer0.setFillBelowLineColor(getResources().getColor(R.color.color_belowLine));  // Farbe der Füllung

        xySeriesRenderer1.setPointStyle(PointStyle.CIRCLE);                     // Darstellungsart der messpunkte (Kreis)
        xySeriesRenderer1.setColor(getResources().getColor(R.color.color_bad)); // Farbe der Linie und Punkte
        xySeriesRenderer1.setStroke(BasicStroke.DOTTED);                        // Linienart (gepunktet)
        xySeriesRenderer1.setLineWidth(0.00000000000000000000001f);             // Liniendicke zwischen Werte muss so niedrig sein, da sie sonst nur zwischen den positiven Werten sichtbar wären
        xySeriesRenderer1.setFillPoints(true);                                  // Kreis gefüllt

        xySeriesRenderer2.setPointStyle(PointStyle.CIRCLE);                     // Darstellungsart der Messpunkte (Kreis)
        xySeriesRenderer2.setColor(getResources().getColor(R.color.color_good));// Farbe der Linie und Punkte
        xySeriesRenderer2.setStroke(BasicStroke.DOTTED);                        // Linienart (gepunktet)
        xySeriesRenderer2.setLineWidth(0.00000000000000000000001f);             // Liniendicke zwischen Werte muss so niedrig sein, da sie sonst nur zwischen den negativen Werten sichtbar wären
        xySeriesRenderer2.setFillPoints(true);                                  // Kreis gefüllt

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


