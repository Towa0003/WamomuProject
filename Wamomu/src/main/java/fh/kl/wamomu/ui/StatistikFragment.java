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

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
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
    static List<Double> datum = new ArrayList<Double>();
    static List<Double> werte = new ArrayList<Double>();
    static List<meal> meals = new ArrayList<meal>();

        /*
        # Array-list erstellen - vordefinierte daten reinschreiben - neue daten hinzufügen zur Laufzeit CHECK
        # Datum anpassen, bzw. Werte dem aktuellen datum hinzufügen    CHECK
        todo # Werte werden nicht überschrieben, sondern hintendrangehängehängt und neu neu gezeichnet
        todo # xy Labels ändern
        todo # Messpunkte wenns geht clickable machen
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
        b_addValue = (Button) view.findViewById(R.id.addValue_button);

        et_valueY = (EditText) view.findViewById(R.id.valueY_editText);

        legend = getString(R.string.legend_name, 0);
        series = new XYSeries(legend);


        b_addValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              add();
            }
        });

          ///////////////// Vordefinierte Were für Arraylist ///////

           meals.add(new meal("Frühstück","Schinken",01.10,11.11));


            datum.add(0, 01.01);
            datum.add(1, 02.01);
            datum.add(2, 03.01);
            datum.add(3, 04.01);
            datum.add(4, 05.01);
            datum.add(5, 06.01);
            datum.add(6, 07.01);

            werte.add(0, 1.0);
            werte.add(1, 2.0);
            werte.add(2, 2.0);
            werte.add(3, 3.0);
            werte.add(4, 4.0);
            werte.add(5, 2.0);
            werte.add(6, 0.0);

            chart = ChartFactory.getLineChartView(getActivity(), createDataSet(), createRenderer());
            fl_chartContainer.addView(chart);
            System.out.println("ON CREATE VIEW");

        return view;
    }


   @Override
    public void onResume()
    {
        super.onResume();

        if (chart == null)
        {
            chart = ChartFactory.getLineChartView(getActivity(), createDataSet(), createRenderer());
            fl_chartContainer.addView(chart);
            System.out.println("ON RESUME IF");
        }
        else
        {
            System.out.println("ON RESUME ELSE");
           // chart.repaint();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        System.out.println("ON PAUSE");
        datum.clear();
        werte.clear();
    }

    // Hinzufügen von Werten
    private void add(){
        valueY = Double.parseDouble(et_valueY.getText().toString());
        System.out.println("VALUE Y = " + valueY + " ");
        System.out.println("Größe Liste: " + datum.size());

        werte.add(werte.size(), valueY);


        SimpleDateFormat df = new SimpleDateFormat("dd.MM", Locale.GERMANY);
        String tag = df.format(new java.util.Date());
        datum.add(datum.size(), Double.parseDouble(tag));
        System.out.println("TAG.MONAT= " + tag);
    }

    private XYMultipleSeriesRenderer createRenderer()
    {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAntialiasing(true);

        // title
        // renderer.setChartTitleTextSize(14);     // Titel Größe
        // renderer.setChartTitle(getText(R.string.activity_line_chart_charttitle).toString()); // Titel setzen

        // axis
        renderer.setAxisTitleTextSize(40);      // Schriftgröße Titel Achsen
        renderer.setMargins(new int[]{ 50,50,50,50});   // Abstand
        renderer.setLabelsColor(Color.BLACK);
        renderer.setLabelsTextSize(40);         // Schriftgröße an Achsen
        renderer.setXTitle(getText(R.string.activity_statistik_x_title).toString());
        renderer.setYTitle(getText(R.string.activity_statistik_y_title).toString());
        renderer.setXAxisMin(0);
        renderer.setXAxisMax(10);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(10);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setAxesColor(Color.BLACK);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setPanEnabled(true, false);
        renderer.setZoomEnabled(true,false);

        // Daten an die Achse schreiben
        for (int i = 0; i < datum.size(); i++)
        {
            renderer.addXTextLabel(i, datum.get(i).toString());
            renderer.addYTextLabel(i, String.valueOf(i));

           // renderer.setXLabels(0);
           // renderer.setYLabels(0);
        }
        renderer.setXLabelsColor(Color.BLUE);   //  Farbe X Labels
        renderer.setXLabelsAngle(90);



        // legend
        renderer.setLegendTextSize(30);
        renderer.setLegendHeight(100);

        // points
        renderer.setPointSize(10f);


        // data area
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.DKGRAY);        // Farbe Rasterlinien
        renderer.setMargins(new int[]{30,50,50,50});
        renderer.setMarginsColor(Color.WHITE);      // Hintergrundfarbe

        XYSeriesRenderer xySeriesRenderer0 = new XYSeriesRenderer();
        xySeriesRenderer0.setPointStyle(PointStyle.CIRCLE);
        xySeriesRenderer0.setFillPoints(true);
        xySeriesRenderer0.setColor(getResources().getColor(R.color.color_0));
        xySeriesRenderer0.setLineWidth(5f);
        renderer.addSeriesRenderer(xySeriesRenderer0);

        return renderer;
    }

    // Daten ins Diagramm schreiben
    private XYMultipleSeriesDataset createDataSet(){

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();

        for (int i = 0; i < werte.size(); i++)
        {
            double x = i;                   // x-achse
            double y = werte.get(i);        // y-achse
            series.add(x, y);
        }
        dataSet.addSeries(series);

        return dataSet;
    }

}


