package com.example.covid_19.Track;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid_19.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class DetailActivity extends AppCompatActivity {

    //
    BarChart mBarChart;
    //
    ScrollView scrollView;
    private int positionCountry;
    TextView tvCountry, tvNewConfirmed, tvTotalConfirmed, tvNewDeaths, tvTotalDeaths, tvNewRecovered, tvTotalRecovered, tvDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);

        scrollView = findViewById(R.id.scrollView);
        tvCountry = findViewById(R.id.tvCountry);
        tvNewConfirmed = findViewById(R.id.tvNewConfirmed);
        tvNewDeaths = findViewById(R.id.tvNewDeaths);
        tvNewRecovered = findViewById(R.id.tvNewRecovered);
        tvTotalConfirmed = findViewById(R.id.tvTotalConfirmed);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTotalRecovered = findViewById(R.id.tvTotalRecovered);
        tvDate = findViewById(R.id.tvDate);

        tvCountry.setText(affectedCountriesActivity.countryModelsList.get(positionCountry).getCountry());
        tvNewConfirmed.setText(affectedCountriesActivity.countryModelsList.get(positionCountry).getNewConfirmed());
        tvTotalConfirmed.setText(affectedCountriesActivity.countryModelsList.get(positionCountry).getTotalConfirmed());
        tvNewDeaths.setText(affectedCountriesActivity.countryModelsList.get(positionCountry).getNewDeaths());
        tvTotalDeaths.setText(affectedCountriesActivity.countryModelsList.get(positionCountry).getTotalDeaths());
        tvNewRecovered.setText(affectedCountriesActivity.countryModelsList.get(positionCountry).getNewRecovered());
        tvTotalRecovered.setText(affectedCountriesActivity.countryModelsList.get(positionCountry).getTotalRecovered());
        tvDate.setText(affectedCountriesActivity.countryModelsList.get(positionCountry).getDate());

        //
        mBarChart = findViewById(R.id.barchart);
        mBarChart.addBar(new BarModel(Integer.parseInt(tvNewConfirmed.getText().toString()), Color.parseColor("#48E898")));
        mBarChart.addBar(new BarModel(Integer.parseInt(tvTotalConfirmed.getText().toString()), Color.parseColor("#62E4B7")));
        mBarChart.addBar(new BarModel(Integer.parseInt(tvNewDeaths.getText().toString()), Color.parseColor("#74DA71")));
        mBarChart.addBar(new BarModel(Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#DAD571")));
        mBarChart.addBar(new BarModel(Integer.parseInt(tvNewRecovered.getText().toString()), Color.parseColor("#DAB471")));
        mBarChart.addBar(new BarModel(Integer.parseInt(tvTotalRecovered.getText().toString()), Color.parseColor("#DA9B71")));

        mBarChart.startAnimation();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}