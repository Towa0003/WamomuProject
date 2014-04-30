package fh.kl.wamomu.ui;



import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.RestfulMeasure;
import fh.kl.wamomu.meta.measurement;

public class StatistikFragment extends Fragment {


    MeasurementFragment mf = new MeasurementFragment();
    SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    XYSeries series3;
    private GraphicalView chart;
    private FrameLayout fl_chartContainer;
    List<measurement> measure = new ArrayList<measurement>();
    public static double pos3;
    public View row;
    TimeSeries series;
    TimeSeries series2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistik,
                container, false);
        getActivity().setTitle("Statistik");

        measure.addAll(RestfulMeasure.measurements);
        Collections.reverse(measure);

        fl_chartContainer = (FrameLayout) view.findViewById(R.id.chartContainerLineChart_frameLayout);


        series = new TimeSeries("random");
        series2 = new TimeSeries("random2");
        series3 = new XYSeries("random3");

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
                SeriesSelection seriesSelection = chart.getCurrentSeriesAndPoint();     // initialisierung clickable area
                if (seriesSelection == null) {
                } else {
                    Log.d("StatistikFragment", "Data point index " + seriesSelection.getPointIndex() + " was clicked"
                            + " value X= " + seriesSelection.getXValue()
                            + " value Y= " + seriesSelection.getValue() + " ");
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    int pos = measure.size();
                    double pos2 = (double) pos;
                    pos3 = pos2 - seriesSelection.getXValue();
                    mf.setSfItem((int) pos3);
                    OverviewArrayAdapter.mSelectedItem = (int) pos3 -1;
                    Log.e("MSELECTEDITEM"," " + OverviewArrayAdapter.mSelectedItem);
                    ft.replace(R.id.fl_content_frame, mf);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }


            }
        });
        return view;

    }

    private XYMultipleSeriesRenderer createRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(); // Objekt zur bearbeitung und verwaltung des Koordinatensystems

        XYSeriesRenderer xySeriesRenderer0 = new XYSeriesRenderer();        // Initialisierung zur bearbeitung der Verbindungslinien und -punkte

        XYSeriesRenderer xySeriesRenderer1 = new XYSeriesRenderer();    // Deklarieren des Objektes für rote Punkte, für schlechte Messwerte
        XYSeriesRenderer xySeriesRenderer2 = new XYSeriesRenderer();    // Deklarieren des Objektes für grüne Punkte, für gute Messwerte


        renderer.setAntialiasing(true);
        renderer.setClickEnabled(true);         // clickable machen
        renderer.setSelectableBuffer(20);       // clickable bereich der punkte - ist größer als der dargestellte Punkt

        // Achsen
        renderer.setAxisTitleTextSize(25);              // Schriftgröße Titel an Achsen
        renderer.setLabelsTextSize(15);                 // Schriftgröße Werte an Achsen
        renderer.setLabelsColor(Color.BLACK);           // Farbe Achsenwerte
        renderer.setXTitle("\n \n \n Datum/Uhrzeit");   // Überschrift X-Achse
        renderer.setYTitle("Messwerte mg/dl");          // Überschrift Y-Achse
        renderer.setXAxisMax(measure.size() - 1); // Anzeigebreich maximum Wert X-Achse
        renderer.setXAxisMin(renderer.getXAxisMax() - 5);                   // Anzeigebreich minimum Wert X-Achse
        renderer.setYAxisMax(10);                                           // Anzeigebereich maximum Wert Y-Achse
        renderer.setYAxisMax(10);                                           // Anzeigebereich maximum Wert Y-Achse

        renderer.setYAxisMin(0);                                            // Anzeigebereich minimum Wert Y-Achse
        renderer.setYLabelsAlign(Paint.Align.RIGHT);                        // Ausrichtung Y-Achsenbezeichnung
        renderer.setAxesColor(Color.BLACK);                                 // Farbe der X- und Y-Achsen
        renderer.setLabelsColor(Color.BLACK);                               // Farbe der Labels Überschriften
        renderer.setPanEnabled(true, false);                                // Scrollen auf X- oder Y-Achse
        renderer.setZoomEnabled(true, false);                               // Zoom auf X- oder Y-Achse
        double limit[] = {0 - 0.05, measure.size() - 0.7, renderer.getYAxisMin(), 10}; // Werte zur Limitierung des Scroll- und Zoombereichs; minX, maxX, minY, maxY
        renderer.setPanLimits(limit);                       // Limitierung des Scrollbereichs
        renderer.setZoomLimits(limit);                      // Limitierung des Zooms
        renderer.setZoomRate(10f);

//       Rasterlinien
        renderer.setXLabels(0);                         // Standard X-Labels ausblenden
//      renderer.setYLabels(0);                         // Standard Y-Labels ausblenden
        renderer.setXLabelsColor(Color.BLACK);          // Farbe X-Labels
        renderer.setYLabelsColor(0, Color.BLACK);       // Farbe Y-Label
        renderer.setXLabelsAngle(0);                    // Rotation X-Labels

        // Legende
        renderer.setShowLegend(false);                  // Anzeigen der Legende

        // Datenbereich
        renderer.setShowGrid(true);                     // Rasterlinie anzeigen
        renderer.setApplyBackgroundColor(true);         // Hintergrundfarbe Datenbereich aktivieren
        renderer.setGridColor(Color.DKGRAY);            // Farbe Rasterlinien
        renderer.setBackgroundColor(Color.WHITE);       // Hintergrundfarbe Datenbereich
        renderer.setMargins(new int[]{20, 50, 40, 0});  // Abstand von Oben, Links, Unten, Rechts
        renderer.setMarginsColor(Color.WHITE);          // Hintergrundfarbe außerhalb Diagramm

        // Punkte
        renderer.setPointSize(10f);                     // Größe der Punkte


//        Linienfarbe zwischen den Messpunkten und Farbfüllung unter den Messwerten
        xySeriesRenderer0.setPointStyle(PointStyle.CIRCLE);                                         // Darstellungsart des Punktes der Messwerte als

        xySeriesRenderer0.setFillPoints(true);                                                      // Füllung des Kreises am Messwert
        xySeriesRenderer0.setColor(getResources().getColor(R.color.color_line));                    // Farbe der Linie
        xySeriesRenderer0.setLineWidth(5f);                                                         // Dicke der Linie
        xySeriesRenderer0.setFillBelowLine(true);                                                   // Füllung des Bereiches unter der zu sehenden Linie
        xySeriesRenderer0.setFillBelowLineColor(getResources().getColor(R.color.color_belowLine));  // Farbe der Füllung

        xySeriesRenderer1.setPointStyle(PointStyle.CIRCLE);                     // Darstellungsart der messpunkte (Kreis)
        xySeriesRenderer1.setColor(NavigationDrawer.lowColor); // Farbe der Linie und Punkte
        xySeriesRenderer1.setStroke(BasicStroke.DOTTED);                        // Linienart (gepunktet)
        xySeriesRenderer1.setLineWidth(0.00000000000000000000001f);             // Liniendicke zwischen Werte muss so niedrig sein, da sie sonst nur zwischen den positiven Werten sichtbar wären
        xySeriesRenderer1.setFillPoints(true);                                  // Kreis gefüllt

        xySeriesRenderer2.setPointStyle(PointStyle.CIRCLE);                     // Darstellungsart der Messpunkte (Kreis)
        xySeriesRenderer2.setColor(NavigationDrawer.highcolor);// Farbe der Linie und Punkte
        xySeriesRenderer2.setStroke(BasicStroke.DOTTED);                        // Linienart (gepunktet)
        xySeriesRenderer2.setLineWidth(0.00000000000000000000001f);             // Liniendicke zwischen Werte muss so niedrig sein, da sie sonst nur zwischen den negativen Werten sichtbar wären
        xySeriesRenderer2.setFillPoints(true);


        // Datum und Zeit von Datenbank holen und an die X-Achse schreiben
        for (int i = 0; i < measure.size(); i++) {
            String strDate = sdfDate.format(measure.get(i).getDate());
            String strTime = sdfTime.format(measure.get(i).getTime());
            renderer.addXTextLabel(i, String.valueOf(strDate + "\n" + strTime));        // Datum an X-Achsen Indizes schreiben
        }

        // Messpunkte 0-180 an Y-Achse schreiben
        int j = 0;
        int l = 0;
        double k = 0;
        while (l <= 20) {
            renderer.addYTextLabel(k, String.valueOf(j));// Werte an Y-Achse schreiben
            renderer.addYTextLabel(l, " ");// Werte an Y-Achse schreiben
            j += 10;
            k += 0.55;
            l++;
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



        for (int i = 0; i < measure.size(); i++) {

            x = i;                                       // Wert X-Achse
            y = measure.get(i).getmvalue();    // Wert Y-Achse
            y /= 18.02;
            if(y <= NavigationDrawer.lowvalue / 18.02){
                Log.e("IF", "Y = " + y + " ta"  + NavigationDrawer.lowvalue / 18.02);
                series2.add(x, y);
            }

            else if(NavigationDrawer.highvalue / 18.02 <= y){
                Log.e("ELSE IF", NavigationDrawer.highvalue / 18.02 + "");
                series.add(x,y);
            }
            series3.add(x, y);
        }

        dataSet.addSeries(0,series3);
        dataSet.addSeries(1,series2);
        dataSet.addSeries(2,series);

        return dataSet;
    }
}