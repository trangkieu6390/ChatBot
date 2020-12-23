package com.example.covid_19.DashBoard;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.ChatBot.ChatActivity;
import com.example.covid_19.R;
import com.example.covid_19.Track.PieChartActivity;
import com.example.covid_19.UserProfile;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {

    RecyclerView SymptomsRecycle, PreventionRecycle;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient2, gradient1, gradient3, gradient4, gradient5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        SymptomsRecycle = findViewById(R.id.symptoms);
        PreventionRecycle = findViewById(R.id.Prevention);

        SymptomsRecycler();
        PreventionRecycler();
    }

    public void statistic(View view){
        Intent intent = new Intent(getApplicationContext(), PieChartActivity.class);
        startActivity(intent);
    }
    private void SymptomsRecycler() {

        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xff7adccf});


        ArrayList<SymptomsHelperClass> SymptomsHelperClass = new ArrayList<>();
        SymptomsHelperClass.add(new SymptomsHelperClass(R.drawable.image1, "Fewer", gradient1));
        SymptomsHelperClass.add(new SymptomsHelperClass(R.drawable.image2, "Dry Cough", gradient2));
        SymptomsHelperClass.add(new SymptomsHelperClass(R.drawable.image3, "Headache", gradient3));
        SymptomsHelperClass.add(new SymptomsHelperClass(R.drawable.image4, "breathless", gradient4));


        SymptomsRecycle.setHasFixedSize(true);
        adapter = new SymptomsAdapter(SymptomsHelperClass);
        SymptomsRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SymptomsRecycle.setAdapter(adapter);

    }

    private void PreventionRecycler() {

        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xff7adccf});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient5 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xff7adccf});

        ArrayList<PreventionHelperClass> PreventionHelperClass = new ArrayList<>();
        PreventionHelperClass.add(new PreventionHelperClass(R.drawable.washhandsoften, "Wash hands often", gradient1));
        PreventionHelperClass.add(new PreventionHelperClass(R.drawable.coveryourcough, "Cover your cough", gradient2));
        PreventionHelperClass.add(new PreventionHelperClass(R.drawable.alwaysclean, "Always clean", gradient3));
        PreventionHelperClass.add(new PreventionHelperClass(R.drawable.usemask, "Use mask", gradient4));
        PreventionHelperClass.add(new PreventionHelperClass(R.drawable.distance, "distance", gradient5));

        PreventionRecycle.setHasFixedSize(true);
        adapter = new PreventionAdapter(PreventionHelperClass);
        PreventionRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PreventionRecycle.setAdapter(adapter);

    }

    public void ManageAccount(View view) {

        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
        startActivity(intent);
    }

    public void ChatWithBot(View view) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(intent);
    }
}