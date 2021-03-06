package com.example.nerdherd.UserInterfaceTrials;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nerdherd.MenuController;
import com.example.nerdherd.Model.Experiment;
import com.example.nerdherd.Model.Trial;
import com.example.nerdherd.ObjectManager.ExperimentManager;
import com.example.nerdherd.R;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Displays a scatterplot of the given experimentId
 */
public class TrialScatterPlotActivity extends AppCompatActivity  {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private MenuController menuController;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_plot);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.draw_layout_experiment_statistics);
        navigationView = findViewById(R.id.navigate);

        TextView tvStartDate = findViewById(R.id.tv_start_date);
        TextView tvEndDate = findViewById(R.id.tv_end_date);
        TextView tvBottomLabel = findViewById(R.id.tv_bottom_label_scatter);

        setSupportActionBar(toolbar);
        menuController = new MenuController(TrialScatterPlotActivity.this, toolbar, navigationView, drawerLayout);
        menuController.useMenu(true);

        Intent fromIntent = getIntent();
        String experimentId = fromIntent.getStringExtra("experimentId");

        ExperimentManager eMgr = ExperimentManager.getInstance();
        Experiment experiment = eMgr.getExperiment(experimentId);
        if (experiment == null) {
            Log.d("TrialHisto", "exp=NULL");
            return;
        }

        ArrayList<Trial> list = (ArrayList<Trial>) eMgr.getTrialsExcludeBlacklist(experimentId).clone();
        ScatterChart scatterChart;
        ScatterData scatterData;
        ScatterDataSet scatterDataSet;
        ArrayList scatterEntries= new ArrayList<>();

        scatterChart = findViewById(R.id.scatterChart);

        Timestamp end = new Timestamp(0, 0);
        for(Trial t : list) {
            if(t.getDate().compareTo(end) == 1) {
                end = t.getDate();
            }
            double diffTime = t.getDate().getSeconds()-experiment.getDate().getSeconds();
            diffTime = diffTime / 3600; // By the hour?
            //diffTime = diffTime / 24; // By the day?
            BarEntry b = new BarEntry((float)diffTime, (float)t.getOutcome());
            scatterEntries.add(b);
        }

        tvStartDate.setText(experiment.getDate().toDate().toString());
        tvEndDate.setText(end.toDate().toString());
        tvBottomLabel.setText("Experiment outcomes over time");

        scatterDataSet = new ScatterDataSet(scatterEntries, "Measurement Value");
        scatterData = new ScatterData(scatterDataSet);
        scatterChart.setData(scatterData);
        Description description = new Description();
        description.setText("");
        scatterChart.setDescription( description );
        scatterDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        scatterDataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet.setValueTextColor(Color.BLACK);
        scatterDataSet.setValueTextSize(10f);
        scatterDataSet.setScatterShapeSize(40f);
    }
}
