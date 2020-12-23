package com.example.covid_19.DashBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;

import java.util.ArrayList;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.AdapterAllSymptomsViewHolder> {
    ArrayList<SymptomsHelperClass> viewAll;
    public SymptomsAdapter(ArrayList<SymptomsHelperClass> viewAll){
        this.viewAll=viewAll;
    }
    @NonNull
    @Override
    public SymptomsAdapter.AdapterAllSymptomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.symptoms_card_design, parent, false);
        AdapterAllSymptomsViewHolder lvh = new AdapterAllSymptomsViewHolder(view);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomsAdapter.AdapterAllSymptomsViewHolder holder, int position) {

        SymptomsHelperClass helperClass = viewAll.get(position);
        holder.imageView.setImageResource(helperClass.getImage());
        holder.textView.setText(helperClass.getTitle());
        holder.relativeLayout.setBackground(helperClass.getGradient());
    }

    @Override
    public int getItemCount() {
        return viewAll.size();
    }

    public static class AdapterAllSymptomsViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        ImageView imageView;
        TextView textView;
        public AdapterAllSymptomsViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.background_symptoms);
            imageView = itemView.findViewById(R.id.symptoms_image);
            textView = itemView.findViewById(R.id.symptoms_title);
        }
    }
}
