package fh.kl.wamomu.ui;

import android.app.Fragment;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fh.kl.wamomu.R;
import fh.kl.wamomu.meta.meal;
import fh.kl.wamomu.meta.measurement;

/**
 * Created by Thundernator on 04.11.13.
 */
public class StatistikFragment extends Fragment {


    private GraphicalView chart;
    private FrameLayout fl_chartContainer;

    private Button b_addValue;
    public EditText et_valueY;
    private double valueY;

    Resources res;
    TypedArray menge;
    String legend;
    XYSeries series;

    /////// Arraylist, wird später evtl ausgelagert ////////
    static List<meal> meals = new ArrayList<meal>();
    static List<measurement> measurements = new ArrayList<measurement>();

        /*
            # Array-list erstellen - vordefinierte daten reinschreiben - neue daten hinzufügen zur Laufzeit CHECK
            # Datum anpassen, bzw. Werte dem aktuellen datum hinzufügen    CHECK
            # Werte werden nicht überschrieben, sondern hintendrangehängehängt und neu neu gezeichnet CHECK
            # Messpunkte wenns geht clickable machen CHECK
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
//        b_addValue = (Button) view.findViewById(R.id.addValue_button);
//        et_valueY = (EditText) view.findViewById(R.id.valueY_editText);

        legend = getString(R.string.legend_name, 0);
        series = new XYSeries(legend);


//        b_addValue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                add();
//            }
//        });

        ///////////////// Vordefinierte Werte für Meals-Arraylist ///////

        meals.add(new meal("Frühstück", "Schinken", 01.10, 11.11));
        meals.add(new meal("Mittagessen", "Gulasch", 01.10, 15.30));
        meals.add(new meal("Abendessen", "Salamibrot", 01.10, 18.20));

        meals.add(new meal("Frühstück", "Käsebrot, Ei", 02.10, 10.00));
        meals.add(new meal("Mittagessen", "Lasagne", 02.10, 13.50));
        meals.add(new meal("Abendessen", "Schinkenbrot", 02.10, 18.30));

        meals.add(new meal("Frühstück", "Tomaten, Mozarella,, Toastbrot, Frischkäse", 03.10, 11.30));

        measurements.add(new measurement(01.10, 11.00, 3.6));
        measurements.add(new measurement(01.10, 11.12, 5.0));

        measurements.add(new measurement(01.10, 15.10, 4.6));
        measurements.add(new measurement(01.10, 15.32, 6.3));

        measurements.add(new measurement(01.10, 18.00, 4.9));
        measurements.add(new measurement(01.10, 18.21, 6.8));

        measurements.add(new measurement(02.10, 09.40, 3.0));
        measurements.add(new measurement(02.10, 10.05, 4.8));

        measurements.add(new measurement(02.10, 13.25, 3.2));
        measurements.add(new measurement(02.10, 13.55, 5.0));

        measurements.add(new measurement(02.10, 18.15, 3.9));
        measurements.add(new measurement(02.10, 18.34, 5.6));

        measurements.add(new measurement(03.10, 11.06, 3.2));
        measurements.add(new measurement(03.10, 11.32, 5.3));

        System.out.println("ON CREATE VIEW");

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (chart == null) {
            chart = ChartFactory.getLineChartView(getActivity(), createDataSet(), createRenderer());
            fl_chartContainer.addView(chart);
            System.out.println("ON RESUME IF");

            // onClick um von Punkten auf die jeweilige Mahlzeit zu verweisen
            chart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeriesSelection seriesSelection = chart.getCurrentSeriesAndPoint();     // initialisierung clickable area

                    if (seriesSelection == null) {
                        System.out.println("No Point");
                    } else {
                        // display information of the clicked point
                        Toast.makeText(
                                getActivity(),
                                "Data point index " + seriesSelection.getPointIndex() + " was clicked" + "\n"
                                        + "value X=" + seriesSelection.getXValue() + "\n"
                                        + "value Y=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            System.out.println("ON RESUME ELSE");
            chart.repaint();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("ON PAUSE");

    }

//    // Hinzufügen von Werten
//    private void add() {
//        valueY = Double.parseDouble(et_valueY.getText().toString());
//        System.out.println("VALUE Y = " + valueY + " ");
//        werte.add(werte.size(), valueY);
//    }

    private XYMultipleSeriesRenderer createRenderer() {

        System.out.println("create renderer ");


        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
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
        renderer.setXAxisMin(0);
        renderer.setXAxisMax(5);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(10);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setAxesColor(Color.BLACK);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setPanEnabled(true, false);
        renderer.setZoomEnabled(true, false);

        // Daten an die Achse schreiben
        for (int i = 1; i < measurements.size(); i+=2) {

            renderer.addXTextLabel(i-1, String.valueOf(measurements.get(i-1).getDate() + "\n" + measurements.get(i-1).getTime()));              // Datum an X-Achse schreiben
            renderer.addXTextLabel(i, String.valueOf(measurements.get(i).getDate() + "\n" + measurements.get(i).getTime()));                // 2 mal, weil vor und nach dem Essen der Wert gemessen wird
           if(i<=10)
               renderer.addYTextLabel(i, String.valueOf(i));               // Werte an Y-Achse schreiben
        }
        System.out.println("meals size: " + meals.size());
        System.out.println("measurements size: " + measurements.size());
        renderer.setXLabels(0);             // Standard X-Labels ausblenden
//      renderer.setYLabels(0);             // Standard Y-Labels ausblenden
        renderer.setXLabelsColor(Color.BLACK);      // Farbe X-Labels
        renderer.setYLabelsColor(0, Color.BLACK);   // Farbe Y-Label
        renderer.setXLabelsAngle(0);                // Rotation X-Labels

        // Legende
        renderer.setLegendTextSize(30);
        renderer.setLegendHeight(100);

        // Punkte
        renderer.setPointSize(20f);

        // data area
        renderer.setShowGrid(true);
        renderer.setApplyBackgroundColor(true);
        renderer.setGridColor(Color.DKGRAY);        // Farbe Rasterlinien
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setMargins(new int[]{60, 80, 100, 0});     // Abstand
        renderer.setMarginsColor(Color.WHITE);      // Hintergrundfarbe außerhalb Diagramm

        XYSeriesRenderer xySeriesRenderer0 = new XYSeriesRenderer();
        xySeriesRenderer0.setPointStyle(PointStyle.CIRCLE);
        xySeriesRenderer0.setFillPoints(true);
        xySeriesRenderer0.setColor(getResources().getColor(R.color.color_0));
        xySeriesRenderer0.setLineWidth(5f);

        renderer.addSeriesRenderer(xySeriesRenderer0);

        return renderer;
    }

    // Daten ins Diagramm schreiben
    private XYMultipleSeriesDataset createDataSet() {

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();

        for (int i = 0; i < measurements.size(); i++) {
            double x = i;                                       // Wert X-Achse
            double y = measurements.get(i).getMeasurement();    // Wert Y-Achse
            series.add(x, y);
        }
        dataSet.addSeries(series);

        return dataSet;
    }

}


