package fh.kl.wamomu;

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

/**
 * Created by Thundernator on 04.11.13.
 */
public class StatistikFragment extends Fragment {

    private GraphicalView chart;
    private FrameLayout fl_chartContainer;

    private Button b_addValue;
    private EditText et_valueX;
    private EditText et_valueY;

    private int valueX;
    private int valueY;

    XYMultipleSeriesDataset dataSet;
    Resources res;
    TypedArray menge;
    String name0;
    XYSeries series0;

    /*
    todo # Array-list erstellen - vordefinierte daten reinschreiben - neue daten hinzufügen zur Laufzeit
    todo # xy Labels ändern
    todo # Datum anpassen, bzw. Werte dem aktuellen datum hinzufügen
    todo # Messpunkte wenns geht clickable machen
    todo # Wenn man reinzoomt -> Tage; rauszoomen -> Wochen; weiter Rauszoomen -> Monate auf X-Achse
    todo # Punkte Schwarz färben bzw. je nach Messwert gut -> Grün / schlecht -> Rot
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistik,
                container, false);

        fl_chartContainer = (FrameLayout) view.findViewById(R.id.chartContainerLineChart_frameLayout);
        b_addValue = (Button) view.findViewById(R.id.addValue_button);

        et_valueX = (EditText) view.findViewById(R.id.valueX_editText);
        et_valueY = (EditText) view.findViewById(R.id.valueY_editText);

        dataSet = new XYMultipleSeriesDataset();
        res = getResources();
        menge = res.obtainTypedArray(R.array.menge);
        name0 = getString(R.string.machine_name, 0);
        series0 = new XYSeries(name0);


        b_addValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
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
        }
        else
        {
            chart.repaint();
        }
    }

    // Hinzufügen von Werten
    private void add(){
        valueX = Integer.parseInt(et_valueX.toString());
        valueY = Integer.parseInt(et_valueY.toString());

    }

    private XYMultipleSeriesRenderer createRenderer()
    {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAntialiasing(true);

        // title
        // renderer.setChartTitleTextSize(14);     // Titel Größe
        // renderer.setChartTitle(getText(R.string.activity_line_chart_charttitle).toString()); // Titel setzen

        // axis
        renderer.setAxisTitleTextSize(16);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setLabelsTextSize(20);
        renderer.setXTitle(getText(R.string.activity_statistik_x_title).toString());
        renderer.setYTitle(getText(R.string.activity_statistik_y_title).toString());
        renderer.setXAxisMin(0);
        renderer.setXAxisMax(15);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(10);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setAxesColor(Color.BLACK);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setPanEnabled(true, false);
        renderer.setZoomEnabled(true,false);

        TypedArray dates = getResources().obtainTypedArray(R.array.dates);
        for (int i = 0; i < dates.length(); i++)
        {
            renderer.addXTextLabel(i, dates.getString(i));
        }
        renderer.setXLabelsAngle(90);



        // legend
        renderer.setLegendTextSize(15);
        renderer.setLegendHeight(100);

        // points
        renderer.setPointSize(10f);


        // data area
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.DKGRAY);        // Farbe Rasterlinien
        renderer.setMargins(new int[]{30,10,10,10});
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
    private XYMultipleSeriesDataset createDataSet()
    {

        for (int i = 0; i < menge.length(); i++)
        {
            double x = i*2;                 // x-achse
            double y = menge.getInt(i, -1); // y-achse
            series0.add(x, y);
        }
        dataSet.addSeries(series0);

        return dataSet;
    }

}


