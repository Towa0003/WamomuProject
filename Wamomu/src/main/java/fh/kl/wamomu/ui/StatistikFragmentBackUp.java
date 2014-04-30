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
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.RestfulMeasure;

public class StatistikFragmentBackUp extends Fragment {


    MeasurementFragment mf = new MeasurementFragment();
    SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    XYSeries series;
    private GraphicalView chart;
    private FrameLayout fl_chartContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistik,
                container, false);
        getActivity().setTitle("Statistik");

        fl_chartContainer = (FrameLayout) view.findViewById(R.id.chartContainerLineChart_frameLayout);

        series = new XYSeries("random");

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
                    mf.setSfItem((int) seriesSelection.getXValue());

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

        renderer.setAntialiasing(true);
        renderer.setClickEnabled(true);         // clickable machen
        renderer.setSelectableBuffer(35);       // clickable bereich der punkte - ist größer als der dargestellte Punkt

        // Achsen
        renderer.setAxisTitleTextSize(50);              // Schriftgröße Titel an Achsen
        renderer.setLabelsTextSize(30);                 // Schriftgröße Werte an Achsen
        renderer.setLabelsColor(Color.BLACK);           // Farbe Achsenwerte
        renderer.setXTitle("\n \n \n Datum/Uhrzeit");   // Überschrift X-Achse
        renderer.setYTitle("Messwerte mg/dl");          // Überschrift Y-Achse
        renderer.setXAxisMax(RestfulMeasure.measurements.size() - 1); // Anzeigebreich maximum Wert X-Achse
        renderer.setXAxisMin(renderer.getXAxisMax() - 5);                   // Anzeigebreich minimum Wert X-Achse
        renderer.setYAxisMax(10);                                           // Anzeigebereich maximum Wert Y-Achse
        renderer.setYAxisMin(0);                                            // Anzeigebereich minimum Wert Y-Achse
        renderer.setYLabelsAlign(Paint.Align.RIGHT);                        // Ausrichtung Y-Achsenbezeichnung
        renderer.setAxesColor(Color.BLACK);                                 // Farbe der X- und Y-Achsen
        renderer.setLabelsColor(Color.BLACK);                               // Farbe der Labels Überschriften
        renderer.setPanEnabled(true, false);                                // Scrollen auf X- oder Y-Achse
        renderer.setZoomEnabled(true, false);                               // Zoom auf X- oder Y-Achse
        double limit[] = {0 - 0.05, RestfulMeasure.measurements.size() - 0.7, renderer.getYAxisMin(), 10}; // Werte zur Limitierung des Scroll- und Zoombereichs; minX, maxX, minY, maxY
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
        renderer.setMargins(new int[]{20, 115, 40, 0});  // Abstand von Oben, Links, Unten, Rechts
        renderer.setMarginsColor(Color.WHITE);          // Hintergrundfarbe außerhalb Diagramm

        // Punkte
        renderer.setPointSize(25f);                     // Größe der Punkte

//        Linienfarbe zwischen den Messpunkten und Farbfüllung unter den Messwerten
        xySeriesRenderer0.setPointStyle(PointStyle.CIRCLE);                                         // Darstellungsart des Punktes der Messwerte als
        xySeriesRenderer0.setFillPoints(true);                                                      // Füllung des Kreises am Messwert
        xySeriesRenderer0.setColor(getResources().getColor(R.color.color_line));                    // Farbe der Linie
        xySeriesRenderer0.setLineWidth(5f);                                                         // Dicke der Linie
        xySeriesRenderer0.setFillBelowLine(true);                                                   // Füllung des Bereiches unter der zu sehenden Linie
        xySeriesRenderer0.setFillBelowLineColor(getResources().getColor(R.color.color_belowLine));  // Farbe der Füllung


        // Datum und Zeit von Datenbank holen und an die X-Achse schreiben
        for (int i = 0; i < RestfulMeasure.measurements.size(); i++) {
            String strDate = sdfDate.format(RestfulMeasure.measurements.get(i).getDate());
            String strTime = sdfTime.format(RestfulMeasure.measurements.get(i).getTime());
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
        return renderer;
    }

    // Daten ins Diagramm schreiben
    private XYMultipleSeriesDataset createDataSet() {

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        double x;
        double y;

        for (int i = 0; i < RestfulMeasure.measurements.size(); i++) {
            x = i;                                       // Wert X-Achse
            y = RestfulMeasure.measurements.get(i).getmvalue();    // Wert Y-Achse
            y /= 18.02;

            series.add(x, y); // alle werte werden in den Graphen gesetzt
        }

        dataSet.addSeries(0, series);

        return dataSet;
    }
}