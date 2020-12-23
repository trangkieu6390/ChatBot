package com.example.covid_19.Track;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class affectedCountriesActivity extends AppCompatActivity {

    EditText edtSearch;
    ListView listView;


    public static List<CountryHelperClass> countryModelsList = new ArrayList<>();
    CountryHelperClass countryHelperClass;
    AffectedCountriesAdapter affectedCountriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        edtSearch = findViewById(R.id.edtSearch);
        listView = findViewById(R.id.listView);

        jsonParse();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("position",position));
            }
        });


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                affectedCountriesAdapter.getFilter().filter(s);
                affectedCountriesAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void jsonParse() {
        String url = "https://api.covid19api.com/summary";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Countries");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject country = jsonArray.getJSONObject(i);

                                String CountryName = country.getString("Country");
                                String NewConfirmed = country.getString("NewConfirmed");
                                String TotalConfirmed = country.getString("TotalConfirmed");
                                String NewDeaths = country.getString("NewDeaths");
                                String TotalDeaths = country.getString("TotalDeaths");
                                String NewRecovered = country.getString("NewRecovered");
                                String TotalRecovered = country.getString("TotalRecovered");
                                String Date = country.getString("Date");

                                countryHelperClass = new CountryHelperClass(CountryName,NewConfirmed,TotalConfirmed,NewDeaths,TotalDeaths,NewRecovered,TotalRecovered,Date);
                                countryModelsList.add(countryHelperClass);
                            }
                            affectedCountriesAdapter = new AffectedCountriesAdapter(affectedCountriesActivity.this,countryModelsList);
                            listView.setAdapter(affectedCountriesAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}