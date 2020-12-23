package com.example.covid_19.Track;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class PieChartActivity extends AppCompatActivity {

    PieChart pieChart;
    TextView tvCase, tvRecovered, tvTotalDeaths, tvActive, tvCritical, tvTodayCases, tvTodayDeaths, tvAffectedCountries;

    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pie_chart);

        pieChart = findViewById(R.id.PieChart);
        tvCase = findViewById(R.id.tvCase);
        tvActive = findViewById(R.id.tvActive);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvTotalDeaths = findViewById(R.id.tvTotalDeath);
        scrollView = findViewById(R.id.scrollView);
        tvCritical = findViewById(R.id.tvCritical);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);
        tvTodayDeaths = findViewById(R.id.tvTodayDeath);
        tvTodayCases = findViewById(R.id.tvTodayCase);


        fetchData();
    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/all/";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            tvCase.setText(jsonObject.getString("cases"));
                            tvRecovered.setText(jsonObject.getString("recovered"));
                            tvCritical.setText(jsonObject.getString("critical"));
                            tvActive.setText(jsonObject.getString("active"));
                            tvTodayCases.setText(jsonObject.getString("todayCases"));
                            tvTotalDeaths.setText(jsonObject.getString("deaths"));
                            tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                            tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                            pieChart.addPieSlice(new PieModel("cases", Integer.parseInt(tvCase.getText().toString()), Color.parseColor("#48E898")));
                            pieChart.addPieSlice(new PieModel("recovered", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#48AAD6")));
                            pieChart.addPieSlice(new PieModel("deaths", Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#9EADFB")));
                            pieChart.addPieSlice(new PieModel("active", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#A28DC6")));
                            pieChart.startAnimation();

                            scrollView.setVisibility(View.VISIBLE);
                        } catch (JSONException e){
                            e.printStackTrace();
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(PieChartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


    public void track(View view) {
        Intent intent = new Intent(getApplicationContext(), affectedCountriesActivity.class);
        startActivity(intent);
    }
}